package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.InvRecords;
import com.xdf.xd_f371.repo.InvRecordsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InvRecordsService {
    private final InvRecordsRepo invRecordsRepo;

    public InvRecords saveInvRecord(InvRecords inv){
        return this.invRecordsRepo.save(inv);
    }
}
