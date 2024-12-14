package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.repo.LedgerDetailRepo;
import com.xdf.xd_f371.repo.LedgersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;
    private final LedgerDetailRepo ledgerDetailRepo;
    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByBillIdAndQuarter_id(DashboardController.so_select,DashboardController.findByTime.getId());
    }
    public List<MiniLedgerDto> findInterfaceLedger(){
        return ledgersRepo.findInterfaceLedger();
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
}
