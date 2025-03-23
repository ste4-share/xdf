package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.controller.ConnectLan;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public List<InventoryDto> mapPreInventoryPetro(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],(String) row[1],(double) row[2],(double) row[3],
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
    public List<InventoryDto> findPreInventoryPetro(int petro_id){
        if (!inventoryRepo.findPreInventoryPetro(petro_id).isEmpty()){
            return mapPreInventoryPetro(inventoryRepo.findPreInventoryPetro(petro_id));
        }
        return null;
    }
    public List<InventoryDto> findPreInventoryPetroFollowUnit(int petro_id, int id){
        if (!inventoryRepo.findPreInventoryPetro(petro_id,id).isEmpty()){
            return mapPreInventoryPetro(inventoryRepo.findPreInventoryPetro(petro_id,id));
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
            DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
            throw new RuntimeException(MessageCons.CO_LOI_XAY_RA.getName());
        }
    }
    @Transactional
    public void saveInvWhenSwitchQuarter(NguonNx nnx,boolean ischecked) {
        List<Ledger> previous_invs;
        if (ischecked){
            previous_invs = ledgersRepo.findAllByBeforeDateRange(DashboardController.ref_Quarter.getStart_date());
        }else{
            previous_invs = ledgersRepo.findAllByBeforeDateRange2(DashboardController.ref_Quarter.getStart_date(),nnx.getId());
        }
        inventoryRepo.deleteAll();
        if (!previous_invs.isEmpty()){
            List<InvDto> invDtoList = mapToInvDto(ledgersRepo.findAllInvByRangeBefore(DashboardController.ref_Quarter.getStart_date()));
            invDtoList.forEach(x -> {
                if (x.getNhap_nvdx()-x.getXuat_nvdx()<=0 && x.getNhap_sscd()-x.getXuat_sscd()<=0){
                    inventoryRepo.save(new Inventory(x.getPetro_id(),x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(),
                            MucGiaEnum.OUT_STOCK_ALL.getStatus(), x.getDon_gia(),x.getSd(),x.getEd(),nnx.getId()));
                }else{
                    inventoryRepo.save(new Inventory(x.getPetro_id(),x.getNhap_nvdx()-x.getXuat_nvdx(),
                            x.getNhap_sscd()-x.getXuat_sscd(),x.getNhap_nvdx(), x.getNhap_sscd(),x.getXuat_nvdx(),x.getXuat_sscd(),
                            MucGiaEnum.IN_STOCK.getStatus(), x.getDon_gia(),x.getSd(),x.getEd(),nnx.getId()));
                }
            });
        }else{
            loaiXangDauRepo.findAll().forEach(x->{
                inventoryRepo.save(new Inventory(x.getId(),0, 0,0, 0,0,0,
                        MucGiaEnum.OUT_STOCK_ALL.getStatus(), 0,null,null,nnx.getId()));
            });
        }
    }
}
