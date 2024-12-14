package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.repo.LedgersRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {
    private final LedgersRepo ledgersRepo;

    public List<LedgerDto> getLedgers() {
        return ledgersRepo.findLedgerByBillIdAndQuarter_id(DashboardController.so_select,DashboardController.findByTime.getId());
    }
}
