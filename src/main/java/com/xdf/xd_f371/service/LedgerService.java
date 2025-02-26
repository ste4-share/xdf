package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.ConnectLan;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final LichsuRepo lichsuRepo;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private NguonNxService nguonNxService;

    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByID(DashboardController.so_select);
    }
    public List<MiniLedgerDto> findAllInterfaceLedger(String status){
        return ledgersRepo.findAllInterfaceLedger(status);
    }
    public List<LedgerDto2> findAllLedgerDto(LocalDate st,LocalDate et){
        return mapToLedgerDto(ledgersRepo.findAllLedgerDtoByTime(st,et));
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
                inv(savedLedger,detail);
                saveQuantity(detail,savedLedger);
                ledgerDetailRepo.save(detail);
            }
            if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                Optional<NguonNx> dvx = nguonNxService.findAllByNguonnxId(ledger.getDvi_xuat_id());
                if (dvx.isPresent()){
                    Ledger l = new Ledger(ledger);
                    if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
                    } else {
                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_NHAP.getName());
                    }
                    Ledger l1 = ledgersRepo.save(l);
                    for (LedgerDetails detail : details) {
                        LedgerDetails details1 = new LedgerDetails(detail);
                        details1.setLedger(l1);
                        details1.setLedger_id(l1.getId());
                        inv(l1,details1);
                        saveQuantity(details1,l1);
                        ledgerDetailRepo.save(details1);
                    }
                }
            }else{
                Optional<NguonNx> dvn = nguonNxService.findAllByNguonnxId(ledger.getDvi_nhan_id());
                if (dvn.isPresent()){
                    Ledger l = new Ledger(ledger);
                    if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
                    } else {
                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_NHAP.getName());
                    }
                    Ledger l1 = ledgersRepo.save(l);
                    for (LedgerDetails detail : details) {
                        LedgerDetails details1 = new LedgerDetails(detail);
                        details1.setLedger(l1);
                        details1.setLedger_id(l1.getId());
                        inv(l1,details1);
                        saveQuantity(details1,l1);
                        ledgerDetailRepo.save(details1);
                    }
                }
            }

        } catch (Exception e){
            DialogMessage.errorShowing("Something wrong!");
            e.printStackTrace();
        }
        return savedLedger;
    }
    private void inv(Ledger ledger,LedgerDetails details1){
        Accounts acc = ConnectLan.pre_acc;
        if (acc.getSd()!=null && acc.getEd()!=null) {
            if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                InvDto2 inventory = inventoryService.getPreInvWithDvi(details1.getLoaixd_id(),ledger.getDvi_nhan_id());
                if (inventory==null) {
                    saveHistory(ledger,details1,0L);
                } else {
                    saveHistory(ledger,details1,inventory.getSl_ton());
                }
            }else{
                InvDto2 inventory = inventoryService.getPreInvWithDvi(details1.getLoaixd_id(),ledger.getDvi_xuat_id());
                if (inventory==null) {
                    saveHistory(ledger,details1,0L);
                } else {
                    saveHistory(ledger,details1,inventory.getSl_ton());
                }
            }
        } else {
            saveHistory(ledger,details1,0L);
        }
    }
    private void saveHistory(Ledger l,LedgerDetails ld,Long tontruoc){
        if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), ld.getSoluong(),
                    tontruoc+ld.getSoluong(), ld.getDon_gia(),  ld.getSscd_nvdx(),
                    l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getFrom_date());
            lichsuRepo.save(lichsuXNK);
        }else {
            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(),
                    ld.getSoluong(), tontruoc - ld.getSoluong(), ld.getDon_gia(),  ld.getSscd_nvdx(),
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
    public void inactiveLedger(Long id ) {
        ledgersRepo.inactiveLedgers(id);
    }
    public List<LedgerDto2> mapToLedgerDto(List<Object[]> results) {
        List<LedgerDto2> list = new ArrayList<>();
        results.forEach(x->{
            Date s = (Date) x[7];
            Date e = (Date) x[8];
            LocalDate se = (s==null) ? null : s.toLocalDate();
            LocalDate ee = (e==null) ? null : e.toLocalDate();
            list.add(new LedgerDto2((long) x[0], (long) x[1],(int) x[2],(int) x[3],
                    (Long) x[4],((Short) x[5]).intValue(),(int) x[6],se,ee,(String) x[9],(String) x[10],
                    (String) x[11],(String)x[12],(String)x[13],(String)x[14],(String)x[15],((Short)x[16]).toString()));
        });
        return list;
    }
}
