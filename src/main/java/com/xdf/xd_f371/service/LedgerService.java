package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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
    private final TransactionHistoryRepo transactionHistoryRepo;

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
                saveTransactionHistory(savedLedger,detail,i+1);
                saveQuantity(detail,savedLedger);
                ledgerDetailRepo.save(detail);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return savedLedger;
    }
    @Transactional
    private void saveTransactionHistory(Ledger savedLedger, LedgerDetails detail,int index) {
        try {
            Optional<TransactionHistory> inv_price = transactionHistoryRepo.getInventoryOfPrice_Lxd(detail.getLoaixd_id(),detail.getDon_gia());
            Optional<TransactionHistory> inv = transactionHistoryRepo.getInventoryOf_Lxd(detail.getLoaixd_id());
            Optional<TransactionHistory> volumn_tructhuoc = transactionHistoryRepo.getSoluongTructhuoc(detail.getLoaixd_id(),savedLedger.getLoai_phieu(),savedLedger.getTructhuoc());

            List<TransactionHistory> transactionHistoryListByDay = transactionHistoryRepo.getSizeOfTransactionByDay(detail.getLoaixd_id(),savedLedger.getFrom_date());
            String uid = RandomStringUtils.randomAlphanumeric(10).concat(String.valueOf(detail.getLoaixd_id())).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))).concat("_000"+index);

            if (savedLedger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getLoai_phieu(),
                        savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                        inv.map(history -> (history.getTonkhotong() + detail.getSoluong())).orElseGet(detail::getSoluong),
                        inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() + detail.getSoluong())).orElseGet(detail::getSoluong),
                        transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                        volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + detail.getSoluong())).orElseGet(detail::getSoluong)));
            } else{
                transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getLoai_phieu(),
                        savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                        inv.map(history -> (history.getTonkhotong() - detail.getSoluong())).orElseGet(detail::getSoluong),
                        inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - detail.getSoluong())).orElseGet(detail::getSoluong),
                        transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                        volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - detail.getSoluong())).orElseGet(detail::getSoluong)));
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
                double existnvdx = existInvUnit.get().getNvdx_quantity();
                if(loaiphieu.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                    existInvUnit.get().setNvdx_quantity(existnvdx+detail.getSoluong());
                } else {
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
