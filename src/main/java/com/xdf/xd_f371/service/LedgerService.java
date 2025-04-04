package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.controller.LedgerController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
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
    private final TransactionHistoryRepo transactionHistoryRepo;
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
    public List<Ledger> findAllLedgerByUnit(int id,int y){
        return ledgersRepo.findAllLedgerByUnit(id,y);
    }
    public List<Ledger> findAllLedgerActive(){
        return ledgersRepo.findAllLedgerActive();
    }
    public List<String> getColumnNames_LEDGER(){
        return ledgersRepo.getColumnNames_LEDGER();
    }public List<String> getColumnNames_LEDGER_DETAIL(){
        return ledgersRepo.getColumnNames_LEDGER_DETAIL();
    }public List<String> getColumnNames_TRANSACTION_HISTORY(){
        return ledgersRepo.getColumnNames_TRANSACTION_HISTORY();
    }public List<String> getColumnNames_HMNV(){
        return ledgersRepo.getColumnNames_HANMUCNHIEMVU();
    }public List<String> getColumnNames_HANMUCNHIEMVU_TAUBAY(){
        return ledgersRepo.getColumnNames_HANMUCNHIEMVU_TAUBAY();
    }public Ledger findLastLedgerByBillId(String lp){
        return ledgersRepo.findLastLedgerByBillId(lp,DashboardController.ref_Dv.getId()).orElse(null);
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
    public void updateBillNumber(Ledger l,List<Ledger> ledgers) {
        if (l.getBill_id().equals(l.getBill_id()) && !l.getBill_id2().isBlank()){
            List<Ledger> ls = ledgers.stream().filter(x-> x.getBill_id().equals(l.getBill_id())
                    && (x.getBill_id().compareTo(l.getBill_id())>=0 && x.getBill_id2().compareTo(l.getBill_id2())>=0)
                    && x.getLoai_phieu().equals(l.getLoai_phieu())).toList();
            for (Ledger sl : ls) {
                sl.setBill_id2(CommonFactory.nextExcelStyle(sl.getBill_id2()));
            }
            this.updateLedgers(ls);
        }else{
            List<Ledger> allLs = ledgers.stream().filter(x->(x.getBill_id().compareTo(l.getBill_id())>=0 && x.getBill_id2().compareTo(l.getBill_id2())>=0)
                    && x.getLoai_phieu().equals(l.getLoai_phieu())).toList();
            allLs.forEach(x->x.setBill_id(String.valueOf(Integer.parseInt(x.getBill_id()) +1)));
            this.updateLedgers(allLs);
        }
    }
    @Transactional
    public Ledger saveLedgerWithDetails(Ledger ledger,List<LedgerDetails> delLs){
        Ledger savedLedger = ledgersRepo.save(ledger);
        try {
            for (int i=0;i<savedLedger.getLedgerDetails().size();i++) {
                LedgerDetails detail = savedLedger.getLedgerDetails().get(i);
                saveTransactionHistory(savedLedger,detail,i+1,savedLedger.getLoai_phieu());
                saveQuantity(detail,savedLedger);
            }
            for (int i=0;i<delLs.size();i++) {
                LedgerDetails detail = delLs.get(i);
                saveTransactionHistory(savedLedger,detail,i+1,LoaiPhieuCons.PHIEU_THAYDOI.getName());
                saveQuantity(detail,savedLedger);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return savedLedger;
    }
    @Transactional
    private void saveTransactionHistory(Ledger savedLedger, LedgerDetails detail,int index,String lp) {
        try {
            Optional<TransactionHistory> inv_price = transactionHistoryRepo.getInventoryOfPrice_Lxd(detail.getLoaixd_id(),detail.getDon_gia(),savedLedger.getRoot_id());
            Optional<TransactionHistory> inv = transactionHistoryRepo.getInventoryOf_Lxd(detail.getLoaixd_id(),savedLedger.getRoot_id());
            Optional<TransactionHistory> volumn_tructhuoc = transactionHistoryRepo.getSoluongTructhuoc(detail.getLoaixd_id(),savedLedger.getLoai_phieu(),detail.getDon_gia(),savedLedger.getTructhuoc(),savedLedger.getRoot_id());

            List<TransactionHistory> transactionHistoryListByDay = transactionHistoryRepo.getSizeOfTransactionByDay(detail.getLoaixd_id(),savedLedger.getFrom_date(),savedLedger.getRoot_id());
            if (LedgerController.status.equals(StatusCons.ADD.getName())){
                String uid = RandomStringUtils.randomAlphanumeric(10).concat(String.valueOf(detail.getLoaixd_id())).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))).concat("_000"+index);
                if (lp.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                    transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                            savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                            inv.map(history -> (history.getTonkhotong() + detail.getSoluong())).orElseGet(detail::getSoluong),
                            inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() + detail.getSoluong())).orElseGet(detail::getSoluong),
                            transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                            volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                } else {
                    transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                            savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                            inv.map(history -> (history.getTonkhotong() - detail.getSoluong())).orElseGet(detail::getSoluong),
                            inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - detail.getSoluong())).orElseGet(detail::getSoluong),
                            transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                            volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                }
            } else {
                String uid = RandomStringUtils.randomAlphanumeric(10).concat(String.valueOf(detail.getLoaixd_id())).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))).concat("_000"+index);
                if (lp.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                    transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                            savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                            inv.map(history -> (history.getTonkhotong() + (detail.getSoluong() - history.getSoluong()))).orElseGet(detail::getSoluong),
                            inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() + (detail.getSoluong() - transactionHistory.getSoluong()))).orElseGet(detail::getSoluong),
                            transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                            volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + (detail.getSoluong()-volumn.getSoluong()))).orElseGet(detail::getSoluong),savedLedger.getId()));
                } else if(lp.equals(LoaiPhieuCons.PHIEU_THAYDOI.getName())) {
                    transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                            savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                            inv.map(history -> (history.getTonkhotong() - detail.getSoluong())).orElseGet(detail::getSoluong),
                            inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - detail.getSoluong())).orElseGet(detail::getSoluong),
                            transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                            volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                } else {
                    transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                            savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                            inv.map(history -> (history.getTonkhotong() - (detail.getSoluong() - history.getSoluong()))).orElseGet(detail::getSoluong),
                            inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - (detail.getSoluong()-transactionHistory.getSoluong()))).orElseGet(detail::getSoluong),
                            transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                            volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - (detail.getSoluong()-volumn.getSoluong()))).orElseGet(detail::getSoluong),savedLedger.getId()));
                }
            }

        } catch (RuntimeException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
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

    @Transactional
    public void updateLedgers(List<Ledger> ls) {
        ls.forEach(ledger ->
                ledgersRepo.updateLedger(
                        ledger.getId(),
                        ledger.getBill_id(),
                        ledger.getBill_id2()
                )
        );
    }
}
