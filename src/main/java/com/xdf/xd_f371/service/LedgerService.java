package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final LichsuRepo lichsuRepo;
    private final InventoryUnitsRepo inventoryUnitsRepo;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private InventoryUnitService inventoryUnitService;

    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByID(DashboardController.so_select);
    }
    public List<MiniLedgerDto> findAllInterfaceLedger(String status){
        return ledgersRepo.findAllInterfaceLedger(status);
    }
    public List<Ledger> findAllLedgerDto(LocalDate st,LocalDate et){
        return ledgersRepo.findAllLedgerDtoByTime(st,et);
    }

    public List<Ledger> getAll(){
        return ledgersRepo.findAll();
    }
    public LedgerDetails save(LedgerDetails ledgerDetails) {
        return ledgerDetailRepo.save(ledgerDetails);
    }
    public List<LedgerDetails> getLedgerDetailById(Long id) {
        return ledgerDetailRepo.findAllById(id);
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
//                inv(savedLedger,detail);
                saveInventoryUnit(detail,ledger.getLoai_phieu());
                saveQuantity(detail,savedLedger);
                ledgerDetailRepo.save(detail);
            }
//            if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
//                Optional<NguonNx> dvx = nguonNxService.findAllByNguonnxId(ledger.getDvi_xuat_id());
//                if (dvx.isPresent()){
//                    Ledger l = new Ledger(ledger);
//                    if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
//                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
//                    } else {
//                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_NHAP.getName());
//                    }
//                    Ledger l1 = ledgersRepo.save(l);
//                    for (LedgerDetails detail : details) {
//                        LedgerDetails details1 = new LedgerDetails(detail);
//                        details1.setLedger(l1);
//                        details1.setLedger_id(l1.getId());
//                        inv(l1,details1);
//                        saveQuantity(details1,l1);
//                        ledgerDetailRepo.save(details1);
//                    }
//                }
//            }else{
//                Optional<NguonNx> dvn = nguonNxService.findAllByNguonnxId(ledger.getDvi_nhan_id());
//                if (dvn.isPresent()){
//                    Ledger l = new Ledger(ledger);
//                    if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
//                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
//                    } else {
//                        l.setLoai_phieu(LoaiPhieuCons.PHIEU_NHAP.getName());
//                    }
//                    Ledger l1 = ledgersRepo.save(l);
//                    for (LedgerDetails detail : details) {
//                        LedgerDetails details1 = new LedgerDetails(detail);
//                        details1.setLedger(l1);
//                        details1.setLedger_id(l1.getId());
//                        inv(l1,details1);
//                        saveQuantity(details1,l1);
//                        ledgerDetailRepo.save(details1);
//                    }
//                }
//            }

        } catch (Exception e){
            DialogMessage.errorShowing("Something wrong!");
            e.printStackTrace();
        }
        return savedLedger;
    }

    private void saveInventoryUnit(LedgerDetails detail,String loaiphieu) {
        Optional<Configuration> config = configurationService.findByParam(ConfigCons.ROOT_ID.getName());
        if (config.isPresent()){
            Long root_id = Long.parseLong(config.get().getValue());
            Optional<InventoryUnits> existInvUnit = inventoryUnitService.getInventoryByUnitByPetroByPrice(root_id,Long.parseLong(String.valueOf(detail.getLoaixd_id())),detail.getDon_gia());
            if (existInvUnit.isPresent()){
                if(loaiphieu.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                    double existnvdx = existInvUnit.get().getNvdx_quantity();
                    existInvUnit.get().setNvdx_quantity(existnvdx+detail.getSoluong());
                }else {
                    double existnvdx = existInvUnit.get().getNvdx_quantity();
                    existInvUnit.get().setNvdx_quantity(existnvdx-detail.getSoluong());
                }
                inventoryUnitsRepo.save(existInvUnit.get());
            }else{
                inventoryUnitsRepo.save(new InventoryUnits(detail,root_id));
            }
        }else {
            throw new RuntimeException();
        }
    }

    //    private void inv(Ledger ledger,LedgerDetails details1){
//        Accounts acc = ConnectLan.pre_acc;
//        if (acc.getSd()!=null && acc.getEd()!=null) {
//            if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
//                List<InvDto2> inventory = inventoryService.getPreInvPriceList(details1.getLoaixd_id(),ledger.getDvi_nhan_id());
//                if (inventory==null) {
//                    saveHistory(ledger,details1,0L);
//                } else {
//                    saveHistory(ledger,details1,inventory.get(0).getSl_ton());
//                }
//            }else{
//                List<InvDto2> inventory = inventoryService.getPreInvPriceList(details1.getLoaixd_id(),ledger.getDvi_xuat_id());
//                if (inventory==null) {
//                    saveHistory(ledger,details1,0L);
//                } else {
//                    saveHistory(ledger,details1,inventory.get(0).getSl_ton());
//                }
//            }
//        } else {
//            saveHistory(ledger,details1,0L);
//        }
//    }
//    private void saveHistory(Ledger l,LedgerDetails ld,double tontruoc){
//        if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
//            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(), ld.getSoluong(),
//                    tontruoc+ld.getSoluong(), ld.getDon_gia(),  ld.getSscd_nvdx(),
//                    l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getFrom_date());
//            lichsuRepo.save(lichsuXNK);
//        } else {
//            LichsuXNK lichsuXNK = new LichsuXNK(ld.getTen_xd(), l.getLoai_phieu(),
//                    ld.getSoluong(), tontruoc - ld.getSoluong(), ld.getDon_gia(),  ld.getSscd_nvdx(),
//                    l.getBill_id(), l.getDvi_nhan(), l.getDvi_xuat(), ld.getChung_loai(),l.getFrom_date());
//            lichsuRepo.save(lichsuXNK);
//        }
//    }
    private void saveQuantity(LedgerDetails detail, Ledger ledger){
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())) {
            detail.setNhap_nvdx(Double.parseDouble(String.valueOf(detail.getSoluong())));
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.NVDX.getName())){
            detail.setXuat_nvdx(Double.parseDouble(String.valueOf(detail.getSoluong())));
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            detail.setNhap_sscd(Double.parseDouble(String.valueOf(detail.getSoluong())));
        }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName()) && detail.getSscd_nvdx().equals(Purpose.SSCD.getName())){
            detail.setXuat_sscd(Double.parseDouble(String.valueOf(detail.getSoluong())));
        }
    }
    @Transactional
    public void inactiveLedger(Long id ) {
        ledgersRepo.inactiveLedgers(id);
    }
}
