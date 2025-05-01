package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.controller.LedgerController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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

    private String splitBillNumber(String billNum){
        String numberPart = billNum.replaceAll("[^0-9]", "");
        try {
            int number = Integer.parseInt(numberPart);
            return String.valueOf(number);
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        }
    }
    private String findDuplicateBillNumber(String billn, List<Ledger> ls,String lp){
        String pre_bnum = splitBillNumber(billn);
        for (Ledger l : ls) {
            String b_num = splitBillNumber(l.getBill_id());
            if (b_num.equals(pre_bnum) && l.getLoai_phieu().equals(lp)){
                return l.getGroup_code();
            }
        }
        return null;
    }
    private int findCountGroup(String billn, List<Ledger> ls,String lp){
        int i = 0;
        String pre_bnum = splitBillNumber(billn);
        for (Ledger l : ls) {
            String b_num = splitBillNumber(l.getBill_id());
            if (b_num.equals(pre_bnum) && l.getLoai_phieu().equals(lp)){
                i= i+1;
            }
        }
        return i;
    }
    @Transactional
    public void updateBillNumber(Ledger l,List<Ledger> ledgers, boolean isDup) {
        String group = findDuplicateBillNumber(l.getBill_id(), ledgers,l.getLoai_phieu());
        if (group!=null){
            l.setGroup_code(group);
            if (isDup){
                boolean inc = false;
                List<Ledger> ls;
                if (findCountGroup(l.getBill_id(),ledgers,l.getLoai_phieu())==1){
                    inc = true;
                    ls = new java.util.ArrayList<>(ledgers.stream().filter(x -> x.getBill_id().compareTo(l.getBill_id()) >= 0).toList());
                }else{
                    ls = new java.util.ArrayList<>(ledgers.stream().filter(x -> x.getGroup_code().equals(group) && x.getBill_id().compareTo(l.getBill_id()) >= 0).toList());
                }
                ls.sort((a, b) -> {
                    int numA = Integer.parseInt(a.getBill_id().replaceAll("[^0-9]", ""));
                    int numB = Integer.parseInt(b.getBill_id().replaceAll("[^0-9]", ""));
                    if (numA != numB) {
                        return Integer.compare(numA, numB);
                    } else {
                        String charA = a.getBill_id().replaceAll("[0-9]", "");
                        String charB = b.getBill_id().replaceAll("[0-9]", "");
                        return charA.compareTo(charB);
                    }
                });
                for (int i = 0; i<  ls.size();i++){
                    Ledger sl = ls.get(i);
                    if (inc){
                        sl.setBill_id(CommonFactory.incrementOrdinal(sl.getBill_id()));
                    }else{
                        if (i==ls.size()-1){
                            sl.setBill_id(CommonFactory.getNextInSequence(sl.getBill_id()));
                        }else{
                            sl.setBill_id(ls.get(i+1).getBill_id());
                        }
                    }
                }
                this.updateLedgers(ls);
            }
        }else{
            l.setGroup_code(RandomStringUtils.randomAlphanumeric(10).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))));
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
            Optional<TransactionHistory> inv = transactionHistoryRepo.getInventoryOf_Lxd(detail.getLoaixd_id(),savedLedger.getRoot_id());
            Optional<TransactionHistory> inv_price = transactionHistoryRepo.getInventoryOfPrice_Lxd2(detail.getLoaixd_id(),detail.getDon_gia(),savedLedger.getRoot_id());
            Optional<TransactionHistory> volumn_tructhuoc = transactionHistoryRepo.getSoluongTructhuoc(detail.getLoaixd_id(),savedLedger.getLoai_phieu(),savedLedger.getTructhuoc(),savedLedger.getRoot_id());

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
                }else{
                    transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                            savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                            inv.map(history -> (history.getTonkhotong() - detail.getSoluong())).orElseGet(detail::getSoluong),
                            inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - detail.getSoluong())).orElseGet(detail::getSoluong),
                            transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                            volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                }
            } else {
                String uid = RandomStringUtils.randomAlphanumeric(10).concat(String.valueOf(detail.getLoaixd_id())).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))).concat("_000"+index);
                Optional<TransactionHistory> inv_price_pre = transactionHistoryRepo.getInventoryOfPrice_Lxd(detail.getLoaixd_id(),detail.getDon_gia(),savedLedger.getRoot_id(),savedLedger.getId());

                if (inv_price.isPresent()){
                    if (inv_price_pre.isPresent()){
                        if (inv_price_pre.get().getSoluong()-detail.getSoluong()!=0){
                            if (lp.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                                transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                                        savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                                        inv.map(history -> (history.getTonkhotong() + (detail.getSoluong() - inv_price_pre.get().getSoluong()))).orElseGet(()->(detail.getSoluong() - inv_price_pre.get().getSoluong())),
                                        inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() + (detail.getSoluong() - inv_price_pre.get().getSoluong()))).orElseGet(()->(detail.getSoluong() - inv_price_pre.get().getSoluong())),
                                        transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                                        volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + (detail.getSoluong()-inv_price_pre.get().getSoluong()))).orElseGet(()->(detail.getSoluong()-inv_price_pre.get().getSoluong())),savedLedger.getId()));
                            }else{
                                transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                                        savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                                        inv.map(history -> (history.getTonkhotong() - (detail.getSoluong() - inv_price_pre.get().getSoluong()))).orElseGet(()->(detail.getSoluong() - inv_price_pre.get().getSoluong())),
                                        inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - (detail.getSoluong()-inv_price_pre.get().getSoluong()))).orElseGet(()->(detail.getSoluong()-inv_price_pre.get().getSoluong())),
                                        transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                                        volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + (detail.getSoluong()-inv_price_pre.get().getSoluong()))).orElseGet(()->(detail.getSoluong()+inv_price_pre.get().getSoluong())),savedLedger.getId()));
                            }
                        }else{
                            if (inv_price.get().getTonkho_gia()-inv_price_pre.get().getTonkho_gia()<0){
                                DialogMessage.errorShowing("Không thể thay đổi. Trữ lượng của " + detail.getTen_xd() + " với mức giá "+ TextToNumber.textToNum_2digits(inv_price_pre.get().getMucgia())
                                        +" đã được thực hiện trên các phiếu xuất.");
                            }else{
                                if(lp.equals(LoaiPhieuCons.PHIEU_THAYDOI.getName())) {
                                    if (savedLedger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())) {
                                        transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),savedLedger.getLoai_phieu(),
                                                savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                                                inv.map(history -> (history.getTonkhotong() - detail.getSoluong())).orElseGet(detail::getSoluong),
                                                inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - detail.getSoluong())).orElseGet(detail::getSoluong),
                                                transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                                                volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                                    }else{
                                        transactionHistoryRepo.save(new TransactionHistory(uid,detail.getLoaixd_id(),savedLedger.getRoot_id(),savedLedger.getLoai_phieu(),
                                                savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                                                inv.map(history -> (history.getTonkhotong() + detail.getSoluong())).orElseGet(detail::getSoluong),
                                                inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() + detail.getSoluong())).orElseGet(detail::getSoluong),
                                                transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                                                volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                                    }
                                }
                            }
                        }
                    }
                }else{
                    String uide = RandomStringUtils.randomAlphanumeric(10).concat(String.valueOf(detail.getLoaixd_id())).concat(LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss"))).concat("_000"+index);
                    if (lp.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                        transactionHistoryRepo.save(new TransactionHistory(uide,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                                savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                                inv.map(history -> (history.getTonkhotong() + detail.getSoluong())).orElseGet(detail::getSoluong),
                                inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() + detail.getSoluong())).orElseGet(detail::getSoluong),
                                transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                                volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() + detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                    } else {
                        transactionHistoryRepo.save(new TransactionHistory(uide,detail.getLoaixd_id(),savedLedger.getRoot_id(),lp,
                                savedLedger.getFrom_date(),detail.getDon_gia(),detail.getSoluong(),savedLedger.getTructhuoc(),
                                inv.map(history -> (history.getTonkhotong() - detail.getSoluong())).orElseGet(detail::getSoluong),
                                inv_price.map(transactionHistory -> (transactionHistory.getTonkho_gia() - detail.getSoluong())).orElseGet(detail::getSoluong),
                                transactionHistoryListByDay.isEmpty() ? 1 : transactionHistoryListByDay.size()+1,
                                volumn_tructhuoc.map(volumn -> (volumn.getSoluong_tt() - detail.getSoluong())).orElseGet(detail::getSoluong),savedLedger.getId()));
                    }
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
                        ledger.getBill_id()
                )
        );
    }
}
