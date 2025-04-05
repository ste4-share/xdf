package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.InventoryUnitDto;
import com.xdf.xd_f371.entity.TransactionHistory;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.repo.TransactionHistoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<TransactionHistory> getTransactionHistoryByDate(int lxd_id,LocalDate ed){
        return transactionHistoryRepo.getTransactionHistoryByDate(lxd_id,ed);
    }
    public Optional<TransactionHistory> getInventoryOf_Lxd(int xdid, int root_id){
        return transactionHistoryRepo.getInventoryOf_Lxd(xdid,root_id);
    }
    public List<InventoryUnitDto> getInventoryOf_Lxd(LocalDate sd,LocalDate ed){
        return mapToInventoryUnitDto(transactionHistoryRepo.getInvByTime(sd,ed, DashboardController.ref_Dv.getId()));
    }
    public List<InventoryUnitDto> mapToInventoryUnitDto(List<Object[]> results) {
        List<InventoryUnitDto> ls = new ArrayList<>();
        for (Object[] row : results) {
            ls.add(new InventoryUnitDto((int) row[0], (String) row[1], (String) row[2], (String) row[3],
                    (double) row[4],(double) row[5], (double) row[6],(double) row[7]));
        }return ls;
    }
}
