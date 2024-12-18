package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.model.MucGiaEnum;
import com.xdf.xd_f371.repo.InventoryRepo;
import com.xdf.xd_f371.repo.LedgerDetailRepo;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.MucGiaRepo;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    private final MucGiaRepo mucGiaRepo;
    private final InventoryRepo inventoryRepo;
    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByBillIdAndQuarter_id(DashboardController.so_select,DashboardController.findByTime.getId());
    }
    public List<MiniLedgerDto> findInterfaceLedger(String status, int quarter_id){
        return ledgersRepo.findInterfaceLedger(status, quarter_id);
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
        for (LedgerDetails detail : details) {
            detail.setLedger(savedLedger);
            detail.setLedger_id(savedLedger.getId());
            ledgerDetailRepo.save(detail);
            Inventory inventory= inventoryRepo.findByPetro_idAndQuarter_id(detail.getLoaixd_id(), savedLedger.getQuarter_id()).orElseThrow();
            Mucgia m = mucGiaRepo.findAllMucgiaUnique(detail.getLoaixd_id(), DashboardController.findByTime.getId(), detail.getDon_gia()).orElse(null);
            if (m == null){
                mucGiaRepo.save(new Mucgia(detail.getDon_gia(), detail.getSoluong(),savedLedger.getQuarter_id(),detail.getLoaixd_id(),MucGiaEnum.IN_STOCK.getStatus(),(long)detail.getSoluong(),0L));
                if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())) {
                    inventory.setNhap_nvdx(inventory.getNhap_nvdx()+detail.getSoluong());
                }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName())){
                    inventory.setXuat_nvdx(inventory.getXuat_nvdx()+detail.getSoluong());
                }
            } else {
                if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())) {
                    inventory.setNhap_nvdx(inventory.getNhap_nvdx()+detail.getSoluong());
                    m.setNvdx(m.getNvdx()+detail.getSoluong());
                    m.setAmount(m.getAmount()+detail.getSoluong());
                    mucGiaRepo.save(m);
                }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName())){
                    inventory.setXuat_nvdx(inventory.getXuat_nvdx()+detail.getSoluong());
                    m.setNvdx(m.getNvdx()-detail.getSoluong());
                    m.setAmount(m.getAmount()-detail.getSoluong());
                    mucGiaRepo.save(m);
                }
            }
            inventoryRepo.save(inventory);
        }
        return savedLedger;
    }
    public List<Ledger> getAllByQuarter(int quarter_id, String lp){
        return ledgersRepo.findAllByQuarter(quarter_id,lp);
    }
}
