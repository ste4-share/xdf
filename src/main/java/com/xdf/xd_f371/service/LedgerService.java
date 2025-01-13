package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.LoaiXuat;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.ConnectLan;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.controller.XuatController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final LichsuRepo lichsuRepo;
    @Autowired
    private InventoryService inventoryService;

    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByID(DashboardController.so_select);
    }
    public List<MiniLedgerDto> findInterfaceLedger(String status, Quarter q){
        if (q==null){
            return ledgersRepo.findAllInterfaceLedger(status);
        }
        return ledgersRepo.findInterfaceLedger(status, q.getStart_date(),q.getEnd_date());
    }
    public List<MiniLedgerDto> findAllInterfaceLedger(String status){
        return ledgersRepo.findAllInterfaceLedger(status);
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
        try {
            for (LedgerDetails detail : details) {
                detail.setLedger(savedLedger);
                detail.setLedger_id(savedLedger.getId());
                saveQuantity(detail,savedLedger);
                ledgerDetailRepo.save(detail);
                Accounts acc = ConnectLan.pre_acc;
                if (acc.getSd()!=null && acc.getEd()!=null) {
                    if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                        InventoryDto inventory = inventoryService.getPreInvWithDvi(detail.getLoaixd_id(),ledger.getDvi_nhan_id());
                        if (inventory==null) {
                            saveHistory(ledger,detail,0L);
                        } else {
                            saveHistory(ledger,detail,inventory.getPre_nvdx());
                        }
                    }else{
                        InventoryDto inventory = inventoryService.getPreInvWithDvi(detail.getLoaixd_id(),ledger.getDvi_xuat_id());
                        if (inventory==null) {
                            saveHistory(ledger,detail,0L);
                        } else {
                            saveHistory(ledger,detail,inventory.getPre_nvdx());
                        }
                    }
                } else {
                    saveHistory(ledger,detail,0L);
                }
            }
        } catch (Exception e){
            DialogMessage.errorShowing("Something wrong!");
            e.printStackTrace();
        }
        return savedLedger;
    }
    private void saveHistory(Ledger l,LedgerDetails ld, Long tontruoc){
        if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), tontruoc, ld.getSoluong(),
                    (tontruoc+ld.getSoluong()), ld.getDon_gia(),  ld.getSscd_nvdx(),
                    l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getFrom_date());
            lichsuRepo.save(lichsuXNK);
        }else {
            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), tontruoc+ld.getSoluong(),
                    ld.getSoluong(), tontruoc, ld.getDon_gia(),  ld.getSscd_nvdx(),
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
    @Transactional
    public void inactiveLedger(int id ) {
        ledgersRepo.inactiveLedgers(id);
    }
}
