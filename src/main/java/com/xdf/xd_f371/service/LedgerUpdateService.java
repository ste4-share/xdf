package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LedgerUpdateService extends ScheduledService<List<MiniLedgerDto>> {
    @Autowired
    private LedgerService ledgerService;

    public LedgerUpdateService() {
        this.setPeriod(Duration.seconds(5)); // Run every 5 seconds
    }

    @Override
    protected Task<List<MiniLedgerDto>> createTask() {
        return new Task<List<MiniLedgerDto>>() {
            @Override
            protected List<MiniLedgerDto> call() throws Exception {
                return ledgerService.findAllInterfaceLedger(StatusCons.ACTIVED.getName());
            }
        };
    }
}
