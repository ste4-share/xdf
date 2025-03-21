package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.entity.TransactionHistory;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.repo.TransactionHistoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TransactionHistoryService {
    private final TransactionHistoryRepo transactionHistoryRepo;

    public void save(TransactionHistory history){
        transactionHistoryRepo.save(history);
    }
    @Transactional
    public void createNewPartitiontable(String tbn,int xd_id){
        ReportDAO reportDAO = new ReportDAO();
        reportDAO.updateDataDAO("CREATE TABLE IF NOT EXISTS "+tbn+" PARTITION OF transaction_history FOR VALUES IN ("+xd_id+");");
    }
    public List<TransactionHistory> getLastestTimeForEachPrices(int lxd_id){
        return transactionHistoryRepo.getLastestTimeForEachPrices(lxd_id);
    }
    public Optional<TransactionHistory> getInventoryOf_Lxd(int xdid){
        return transactionHistoryRepo.getInventoryOf_Lxd(xdid);
    }
}
