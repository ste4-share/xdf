package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, String> {
    @Query(value = "select * from transaction_history where xd_id=:xd order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getInventoryOf_Lxd(@Param("xd") int xd_id);
    @Query(value = "select * from transaction_history where xd_id=:xd and loaiphieu like :lp and tructhuoc like :tt order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getSoluongTructhuoc(@Param("xd") int xd_id,@Param("lp") String lp,@Param("tt") String tt);
    @Query(value = "select * from transaction_history where xd_id=:xd and nhiemvu_id=:nvid order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getTaskAccumulate(@Param("xd") int xd_id,@Param("nvid") int nvid);
    @Query(value = "select * from transaction_history where xd_id=:xd and xmt_unit_id like :xmt_id order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getXmtAccumulate(@Param("xd") int xd_id,@Param("xmt_id") String unit_xmt_id);
    @Query(value = "select * from transaction_history where xd_id=:xd and xmt_unit_id like :xmt_id and nhiemvu_id=:nvid order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getXmtAndTaskAccumulate(@Param("xd") int xd_id,@Param("xmt_id") String unit_xmt_id,@Param("nvid") int nv_id);
    @Query(value = "select * from transaction_history where xd_id=:xd_id and mucgia=:gia order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getInventoryOfPrice_Lxd(@Param("xd_id") int xd_id,@Param("gia") double dongia);
    @Query(value = "select * from transaction_history where xd_id=:xd_id and date=:d order by created_at desc",nativeQuery = true)
    List<TransactionHistory> getSizeOfTransactionByDay(@Param("xd_id") int xd_id, @Param("d")LocalDate date);
    @Query(value = "SELECT distinct on (mucgia) * FROM public.transaction_history where xd_id=:xd_id order by mucgia,created_at desc",nativeQuery = true)
    List<TransactionHistory> getLastestTimeForEachPrices(@Param("xd_id") int xd_id);
}
