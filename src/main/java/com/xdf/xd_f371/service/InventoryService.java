package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.controller.ConnectLan;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import javafx.scene.control.DatePicker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final LoaiXangDauRepo loaiXangDauRepo;
    private final AccountRepo accountRepo;

    public Inventory save(Inventory inventory){
        return inventoryRepo.save(inventory);
    }
    public Inventory findById(int id){
        return inventoryRepo.findById(id).orElse(null);
    }
    public List<TonkhoDto> getAllTonkhoNotCondition(){
        return mapToTonkhoDto(inventoryRepo.getAllTonkhoNotCondition());
    }
    public List<TonkhoDto> getAllTonkho(LocalDate sd, LocalDate ed) {
        return mapToTonkhoDto(inventoryRepo.getAllTonkho(sd,ed));
    }
    public List<TonkhoDto> mapToTonkhoDto(List<Object[]> results) {
        List<TonkhoDto> ls = new ArrayList<>();
        for (Object[] row : results) {
            ls.add(new TonkhoDto((int) row[0], (String) row[1], (String) row[2], (String) row[3],
                    (double) row[4],(double) row[5], (double) row[6],(double) row[7],(double) row[8],(double) row[9],(double) row[10],(double) row[11]));
        }return ls;
    }
    public List<InvDto2> mapPreInvWithPrice(List<Object[]> results) {
        return results.stream()
                .map(row -> new InvDto2((int) row[0],(double) row[2],(String) row[1],(double) row[3]))
                .collect(Collectors.toList());
    }
    public InvDto2 mapPreInvenPrice(List<Object[]> results) {
        return results.stream()
                .map(row -> new InvDto2((int) row[0],(double) row[2],(String) row[1],(double) row[3]))
                .toList().get(0);
    }
    public InvDto2 mapPreInven(List<Object[]> results) {
        return results.stream()
                .map(row -> new InvDto2((int) row[0],(String) row[1],(String) row[2],(double) row[3]))
                .toList().get(0);
    }
    public List<InventoryDto> mapPreInventoryPetro(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],(Long) row[1],(double) row[2],(double) row[3],
                        (double) row[4],(double) row[5],(double) row[6]))
                .toList();
    }
    public List<PttkDto> mapPttkPetro() {
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> pttk = reportDAO.findByWhatEver(SubQuery.bc_pttk_q());
        return pttk.stream()
                .map(row -> new PttkDto((String) row[1],(String) row[2],(double) row[3],(double) row[4],
                        (double) row[5],(double) row[6],(double) row[7],(double) row[8],(double) row[9],
                        (double) row[10],(double) row[11]))
                .toList();
    }
    public InvDto2 getPreInvPriceAndUnit(int petro_id,double dongia,int unit_id){
        if (!inventoryRepo.findPreInventoryPriceAndUnit(petro_id,dongia,unit_id).isEmpty()){
            return mapPreInvenPrice(inventoryRepo.findPreInventoryPriceAndUnit(petro_id,dongia,unit_id));
        }
        return null;
    }
    public List<InventoryDto> findPreInventoryPetro(int petro_id){
        if (!inventoryRepo.findPreInventoryPetro(petro_id).isEmpty()){
            return mapPreInventoryPetro(inventoryRepo.findPreInventoryPetro(petro_id));
        }
        return null;
    }
    public List<InvDto2> getPreInvPriceList(int petro_id,int dvid){
        if (!inventoryRepo.findPreInventoryAndPrice(petro_id,dvid).isEmpty()){
            return mapPreInvWithPrice(inventoryRepo.findPreInventoryAndPrice(petro_id,dvid));
        }
        return new ArrayList<>();
    }
    public InvDto2 getPreInvWithDvi(int petro_id, int dvi_id){
        if (!inventoryRepo.findPreInventoryFllowUnit(petro_id,dvi_id).isEmpty()){
            return mapPreInven(inventoryRepo.findPreInventoryFllowUnit(petro_id,dvi_id));
        }
        return null;
    }
    public List<InvDto> mapToInvDto(List<Object[]> results) {
        List<InvDto> list = new ArrayList<>();
        results.forEach(x->{
            Date s = (Date) x[6];
            Date e = (Date) x[7];
            LocalDate se = (s==null) ? null : s.toLocalDate();
            LocalDate ee = (e==null) ? null : e.toLocalDate();
            list.add(new InvDto((int) x[0],(double) x[1],(double) x[2],(double) x[3],
                    (double) x[4],(double) x[5],se, ee));
        });
        return list;
    }

    @Transactional
    public void saveInventoryWithLedger(InventoryDto inv){
        Optional<LedgerDetails> l = ledgerDetailRepo.findById(inv.getLedger_id());
        if (l.isPresent()){
            LedgerDetails l_pre = l.get();
            l_pre.setNhap_nvdx(inv.getNhap_nvdx());
            l_pre.setXuat_nvdx(inv.getXuat_nvdx());
            l_pre.setNhap_sscd(inv.getNhap_sscd());
            l_pre.setXuat_sscd(inv.getXuat_sscd());
            ledgerDetailRepo.save(l_pre);
        }else{
            DialogMessage.errorShowing("Something went wrong!");
            throw new RuntimeException("Something went wrong!");
        }
    }
    @Transactional
    public void saveInvWhenSwitchQuarter(DatePicker sd, DatePicker ed,NguonNx nnx,boolean ischecked) {
        Accounts acc = ConnectLan.pre_acc;
        acc.setSd(sd.getValue());
        acc.setEd(ed.getValue());
        Accounts a = accountRepo.save(acc);
        List<Ledger> previous_invs = previous_invs = ledgersRepo.findAllByBeforeDateRange(a.getSd());
//        if (ischecked){
//            previous_invs = ledgersRepo.findAllByBeforeDateRange(a.getSd());
//        }else{
//            previous_invs = ledgersRepo.findAllByBeforeDateRange2(a.getSd(),nnx.getId());
//        }
        if (!previous_invs.isEmpty()){
            inventoryRepo.deleteAll();
            List<InvDto> invDtoList = mapToInvDto(ledgersRepo.findAllInvByRangeBefore(a.getSd()));
            invDtoList.forEach(x -> {
                if (x.getNhap_nvdx()-x.getXuat_nvdx()<=0 && x.getNhap_sscd()-x.getXuat_sscd()<=0){
                    inventoryRepo.save(new Inventory(x.getPetro_id(),x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(),
                            MucGiaEnum.OUT_STOCK_ALL.getStatus(), x.getDon_gia(),x.getSd(),x.getEd()));
                }else{
                    inventoryRepo.save(new Inventory(x.getPetro_id(),x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(),
                            MucGiaEnum.IN_STOCK.getStatus(), x.getDon_gia(),x.getSd(),x.getEd()));
                }
            });
        }else{
            inventoryRepo.deleteAll();
            loaiXangDauRepo.findAll().forEach(x->{
                inventoryRepo.save(new Inventory(x.getId(),0, 0,0, 0,0,0,
                        MucGiaEnum.OUT_STOCK_ALL.getStatus(), 0,null,null));
            });
        }
    }
}
