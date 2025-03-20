package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.TransactionHistory;
import com.xdf.xd_f371.repo.TransactionHistoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionHistoryRepo transactionHistoryRepo;

    public void save(TransactionHistory history){
        transactionHistoryRepo.save(history);
    }
    @Transactional
    public void createNewPartitiontable(String tbn,int xd_id){
        transactionHistoryRepo.createPartitionTable(tbn,xd_id);
    }
}
