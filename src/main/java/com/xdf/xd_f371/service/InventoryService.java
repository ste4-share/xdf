package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.controller.ConnectLan;
import com.xdf.xd_f371.dto.InvDto;
import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.TonkhoDto;
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
        return results.stream()
                .map(row -> new TonkhoDto((int) row[0], (String) row[1], (String) row[2], (String) row[3],
                        (long) row[4], (long) row[5],  row[6].toString(),
                         row[7].toString(), row[8].toString(), row[9].toString(), row[10].toString(), row[11].toString()))
                .collect(Collectors.toList());
    }
    public List<InventoryDto> mapPreInvWithPrice(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],(int) row[1],((BigDecimal) row[2]).longValue(),((BigDecimal) row[3]).longValue()))
                .collect(Collectors.toList());
    }
    public InventoryDto mapPreInvenPrice(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],(int) row[1],((BigDecimal) row[2]).longValue(),((BigDecimal) row[3]).longValue()))
                .toList().get(0);
    }
    public InventoryDto mapPreInven(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],((BigDecimal) row[1]).longValue(),((BigDecimal) row[2]).longValue()))
                .toList().get(0);
    }
    public List<InventoryDto> mapPreInventoryPetro(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],(int) row[1],(int) row[2],((BigDecimal) row[3]).longValue(),
                        ((BigDecimal) row[4]).longValue(),((BigDecimal) row[5]).longValue(),((BigDecimal) row[6]).longValue()))
                .toList();
    }
    public InventoryDto getPreInvPrice(int petro_id,int dongia){
        if (!inventoryRepo.findPreInventoryPrice(petro_id,dongia).isEmpty()){
            return mapPreInvenPrice(inventoryRepo.findPreInventoryPrice(petro_id,dongia));
        }
        return null;
    }
    public List<InventoryDto> findPreInventoryPetro(int petro_id){
        if (!inventoryRepo.findPreInventoryPetro(petro_id).isEmpty()){
            return mapPreInventoryPetro(inventoryRepo.findPreInventoryPetro(petro_id));
        }
        return null;
    }
    public List<InventoryDto> getPreInvPriceList(int petro_id){
        if (!inventoryRepo.findPreInventoryAndPrice(petro_id).isEmpty()){
            return mapPreInvWithPrice(inventoryRepo.findPreInventoryAndPrice(petro_id));
        }
        return new ArrayList<>();
    }
    public InventoryDto getPreInv(int petro_id){
        if (!inventoryRepo.findPreInventory(petro_id).isEmpty()){
            return mapPreInven(inventoryRepo.findPreInventory(petro_id));
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
            list.add(new InvDto((int) x[0],(int) x[1],((BigDecimal) x[2]).intValue(),((BigDecimal) x[3]).intValue(),
                    ((BigDecimal) x[4]).intValue(),((BigDecimal) x[5]).intValue(),se, ee));
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
    public void saveInvWhenSwitchQuarter(DatePicker sd, DatePicker ed) {
        Accounts acc = ConnectLan.pre_acc;
        acc.setSd(sd.getValue());
        acc.setEd(ed.getValue());
        Accounts a = accountRepo.save(acc);
        List<Ledger> previous_invs = ledgersRepo.findAllByBeforeDateRange(a.getSd());
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
