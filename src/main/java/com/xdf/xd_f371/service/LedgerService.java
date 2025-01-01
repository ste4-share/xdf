package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final InventoryRepo inventoryRepo;
    private final LichsuRepo lichsuRepo;
    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByBillIdAndQuarter_id(DashboardController.so_select,DashboardController.findByTime.getStart_date(),
                DashboardController.findByTime.getEnd_date(),DashboardController.lp);
    }
    public List<MiniLedgerDto> findInterfaceLedger(String status, Quarter q){
        return ledgersRepo.findInterfaceLedger(status, q.getStart_date(),q.getEnd_date());
    }
    public List<Ledger> getAll(){
        return ledgersRepo.findAll();
    }
    public LedgerDetails save(LedgerDetails ledgerDetails) {
        return ledgerDetailRepo.save(ledgerDetails);
    }
    public Ledger save(Ledger ledger) {
        return ledgersRepo.save(ledger);
    }
    public Ledger saveLedgerWithDetails(Ledger ledger, List<LedgerDetails> details){
        Ledger savedLedger = ledgersRepo.save(ledger);
        try {
            for (LedgerDetails detail : details) {
                detail.setLedger(savedLedger);
                detail.setLedger_id(savedLedger.getId());
                saveQuantity(detail,savedLedger);
                ledgerDetailRepo.save(detail);
                Inventory inventory = inventoryRepo.findByUnique(detail.getLoaixd_id(), DashboardController.findByTime.getStart_date(),
                        DashboardController.findByTime.getEnd_date(), detail.getDon_gia()).orElse(null);
                Inventory inventory_1 = inventoryRepo.findByUniqueGroupby(detail.getLoaixd_id(), DashboardController.findByTime.getStart_date(),
                        DashboardController.findByTime.getEnd_date()).orElse(null);
                if (inventory==null) {
                    if (inventory_1!=null) {
                        createNewInv(ledger, detail,inventory_1);
                        saveHistory(ledger,detail,0);
                    }
                } else {
                    saveInv(ledger, detail, inventory);
                    saveHistory(ledger,detail,inventory.getNhap_nvdx()-inventory.getXuat_nvdx());
                }
            }
        } catch (Exception e){
            DialogMessage.errorShowing("Something wrong!");
            e.printStackTrace();
        }
        return savedLedger;
    }
    private void saveHistory(Ledger l,LedgerDetails ld, int tontruoc){
        if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), tontruoc, ld.getSoluong(), (tontruoc+ld.getSoluong()), ld.getDon_gia(),  ld.getSscd_nvdx(),
                    l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getFrom_date());
            lichsuRepo.save(lichsuXNK);
        }else {
            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), tontruoc+ld.getSoluong(), ld.getSoluong(), tontruoc, ld.getDon_gia(),  ld.getSscd_nvdx(),
                    l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getFrom_date());
            lichsuRepo.save(lichsuXNK);
        }
    }
    private void saveQuantity(LedgerDetails detail, Ledger ledger){
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())) {
            detail.setNhap_nvdx(Long.parseLong(String.valueOf(detail.getSoluong())));
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())){
            detail.setXuat_nvdx(Long.parseLong(String.valueOf(detail.getSoluong())));
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            detail.setNhap_sscd(Long.parseLong(String.valueOf(detail.getSoluong())));
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            detail.setXuat_sscd(Long.parseLong(String.valueOf(detail.getSoluong())));
        }
    }
    private void saveInv(Ledger ledger, LedgerDetails detail, Inventory inventory) {
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())) {
            inventory.setNhap_nvdx(inventory.getNhap_nvdx()+detail.getSoluong());
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())){
            inventory.setXuat_nvdx(inventory.getXuat_nvdx()+detail.getSoluong());
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            inventory.setNhap_sscd(inventory.getNhap_sscd()+detail.getSoluong());
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            inventory.setXuat_sscd(inventory.getXuat_sscd()+detail.getSoluong());
        }
        if (inventory.getNhap_nvdx()-inventory.getXuat_nvdx()<=0){
            inventory.setStatus(MucGiaEnum.OUT_STOCK_NVDX.getStatus());
        }if (inventory.getNhap_sscd()-inventory.getXuat_sscd()<=0){
            inventory.setStatus(MucGiaEnum.OUT_STOCK_NVDX.getStatus());
        }
        if (inventory.getNhap_sscd()-inventory.getXuat_sscd()<=0 && inventory.getNhap_nvdx()-inventory.getXuat_nvdx()<=0){
            inventory.setStatus(MucGiaEnum.OUT_STOCK_ALL.getStatus());
        }
        inventoryRepo.save(inventory);
    }
    private void createNewInv(Ledger ledger, LedgerDetails detail, Inventory inventory){
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())) {
            inventory.setNhap_nvdx(detail.getSoluong());
            if (inventory.getNhap_nvdx()-inventory.getXuat_nvdx()>0){
                inventoryRepo.save(new Inventory(detail.getLoaixd_id(),inventory.getTdk_nvdx(), inventory.getTdk_sscd(),
                        inventory.getNhap_nvdx(),0, 0,0, MucGiaEnum.IN_STOCK.getStatus(), detail.getDon_gia(),ledger.getFrom_date(),ledger.getEnd_date()));
            }else{
                inventoryRepo.save(new Inventory(detail.getLoaixd_id(),inventory.getTdk_nvdx(), inventory.getTdk_sscd(),
                        inventory.getNhap_nvdx(),0, 0,0, MucGiaEnum.OUT_STOCK_NVDX.getStatus(), detail.getDon_gia(),ledger.getFrom_date(),ledger.getEnd_date()));
            }
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            inventory.setNhap_sscd(detail.getSoluong());
            if (inventory.getNhap_sscd()-inventory.getXuat_sscd()>0) {
                inventoryRepo.save(new Inventory(detail.getLoaixd_id(), inventory.getTdk_nvdx(), inventory.getTdk_sscd(),
                        0, detail.getSoluong(), 0, 0,
                        MucGiaEnum.IN_STOCK.getStatus(), detail.getDon_gia(),ledger.getFrom_date(),ledger.getEnd_date()));
            } else {
                inventoryRepo.save(new Inventory(detail.getLoaixd_id(), inventory.getTdk_nvdx(), inventory.getTdk_sscd(),
                        0, detail.getSoluong(), 0, 0,
                        MucGiaEnum.OUT_STOCK_SSCD.getStatus(), detail.getDon_gia(),ledger.getFrom_date(),ledger.getEnd_date()));
            }
        }
    }

    public List<Ledger> getAllByQuarter(Quarter q, String lp){
        return ledgersRepo.findAllByQuarter(q.getStart_date(),q.getEnd_date(),lp);
    }
    @Transactional
    public void inactiveLedger(int so, String loaiphieu) {
        ledgersRepo.inactiveLedgers(so,loaiphieu, DashboardController.findByTime.getStart_date(),DashboardController.findByTime.getEnd_date());
    }
}
