package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.model.MucGiaEnum;
import com.xdf.xd_f371.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final InventoryRepo inventoryRepo;
    private final LichsuRepo lichsuRepo;
    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByBillIdAndQuarter_id(DashboardController.so_select,DashboardController.findByTime.getId());
    }
    public List<MiniLedgerDto> findInterfaceLedger(String status, int quarter_id){
        return ledgersRepo.findInterfaceLedger(status, quarter_id);
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
    @Transactional
    public Ledger saveLedgerWithDetails(Ledger ledger, List<LedgerDetails> details){
        Ledger savedLedger = ledgersRepo.save(ledger);
        for (LedgerDetails detail : details) {
            detail.setLedger(savedLedger);
            detail.setLedger_id(savedLedger.getId());
            ledgerDetailRepo.save(detail);
            Inventory inventory= inventoryRepo.findByUnique(detail.getLoaixd_id(), ledger.getQuarter_id(), MucGiaEnum.IN_STOCK.getStatus(), detail.getDon_gia()).orElse(null);
            Inventory inventory_1= inventoryRepo.findByUniqueGroupby(detail.getLoaixd_id(), ledger.getQuarter_id()).orElse(null);
            if (inventory==null) {
                if (inventory_1!=null) {
                    createNewInv(ledger, detail,inventory_1);
                    saveHistory(ledger,detail,inventory_1.getNhap_nvdx()-inventory_1.getXuat_nvdx());
                }
            }else {
                saveInv(ledger, detail, inventory);
                saveHistory(ledger,detail,inventory.getNhap_nvdx()-inventory.getXuat_nvdx());
            }
        }
        return savedLedger;
    }
    private void saveHistory(Ledger l,LedgerDetails ld, int tontruoc){
        LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), tontruoc, ld.getSoluong(), tontruoc+ld.getSoluong(), ld.getDon_gia(),  ld.getSscd_nvdx(),
        l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getQuarter_id());
        lichsuRepo.save(lichsuXNK);
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
        inventoryRepo.save(inventory);
    }
    private void createNewInv(Ledger ledger, LedgerDetails detail, Inventory inventory){
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())) {
            inventory.setNhap_nvdx(inventory.getNhap_nvdx()+detail.getSoluong());
            inventory.setNhap_sscd(0);
            inventory.setXuat_sscd(0);
            inventory.setXuat_nvdx(0);
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())){
            inventory.setXuat_nvdx(inventory.getXuat_nvdx()+detail.getSoluong());
            inventory.setNhap_nvdx(0);
            inventory.setNhap_sscd(0);
            inventory.setXuat_sscd(0);
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            inventory.setNhap_sscd(inventory.getNhap_sscd()+detail.getSoluong());
            inventory.setNhap_nvdx(0);
            inventory.setXuat_nvdx(0);
            inventory.setXuat_sscd(0);
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            inventory.setXuat_sscd(inventory.getXuat_sscd()+detail.getSoluong());
            inventory.setNhap_sscd(0);
            inventory.setNhap_nvdx(0);
            inventory.setXuat_nvdx(0);
        }
        inventoryRepo.save(new Inventory(detail.getLoaixd_id(),ledger.getQuarter_id(),inventory.getTdk_nvdx(), inventory.getTdk_sscd(),
                inventory.getNhap_nvdx(),inventory.getNhap_sscd(),inventory.getXuat_nvdx(),inventory.getXuat_sscd(), inventory.getStatus(),detail.getDon_gia()));
    }

    public List<Ledger> getAllByQuarter(int quarter_id, String lp){
        return ledgersRepo.findAllByQuarter(quarter_id,lp);
    }
}
