package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final InventoryUnitsRepo inventoryUnitsRepo;
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
    public List<Ledger> findAllLedgerDto(LocalDate st,LocalDate et,int id){
        return ledgersRepo.findAllLedgerDtoByTime(st,et,id);
    }public List<Ledger> findAllLedgerDto(LocalDate st,LocalDate et){
        return ledgersRepo.findAllLedgerDtoByTime(st,et);
    }
    public List<Ledger> findAllLedgerByUnit(int id){
        return ledgersRepo.findAllLedgerByUnit(id);
    }
    public List<Ledger> findAllLedgerActive(){
        return ledgersRepo.findAllLedgerActive();
    }
    public List<String> getColumnNames_LEDGER(){
        return ledgersRepo.getColumnNames_LEDGER();
    }public List<String> getColumnNames_LEDGER_DETAIL(){
        return ledgersRepo.getColumnNames_LEDGER_DETAIL();
    }
    public List<Ledger> getAll(){
        return ledgersRepo.findAll();
    }
    public LedgerDetails save(LedgerDetails ledgerDetails) {
        return ledgerDetailRepo.save(ledgerDetails);
    }
    public List<LedgerDetails> getLedgerDetailById(String id) {
        return ledgerDetailRepo.findAllById(id);
    }
    public Ledger save(Ledger ledger) {
        return ledgersRepo.save(ledger);
    }
    @Transactional
    public Ledger saveLedgerWithDetails(Ledger ledger, List<LedgerDetails> details){
        Ledger savedLedger = ledgersRepo.save(ledger);
        try {
            for (int i=0;i<details.size();i++){
                LedgerDetails detail = details.get(i);
                detail.setId(generateLEdgerDetailId(savedLedger.getId(),i));
                detail.setLedger(savedLedger);
                detail.setLedger_id(savedLedger.getId());
                saveInventoryUnit(savedLedger,detail,ledger.getLoai_phieu());
                saveQuantity(detail,savedLedger);
                ledgerDetailRepo.save(detail);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return savedLedger;
    }
    private String generateLEdgerDetailId(String ledgerid,int index){
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")).concat("_"+ledgerid).concat("_"+index);
    }
    private void saveInventoryUnit(Ledger l,LedgerDetails detail,String loaiphieu) {
        Optional<Configuration> config = configurationService.findByParam(ConfigCons.ROOT_ID.getName());
        if (config.isPresent()){
            Long root_id = Long.parseLong(config.get().getValue());
            Optional<InventoryUnits> existInvUnit = inventoryUnitService.getInventoryByUnitByPetroByPrice(root_id,detail.getLoaixd_id(),detail.getDon_gia());
            if (existInvUnit.isPresent()){
                if(loaiphieu.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                    double existnvdx = existInvUnit.get().getNvdx_quantity();
                    existInvUnit.get().setNvdx_quantity(existnvdx+detail.getSoluong());
                } else {
                    double existnvdx = existInvUnit.get().getNvdx_quantity();
                    existInvUnit.get().setNvdx_quantity(existnvdx-detail.getSoluong());
                }
                inventoryUnitsRepo.save(existInvUnit.get());
            }else{
                inventoryUnitsRepo.save(new InventoryUnits(l,detail,root_id));
            }
        }else {
            throw new RuntimeException();
        }
    }
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
    public void inactiveLedger(String id ) {
        ledgersRepo.inactiveLedgers(id);
    }
}
