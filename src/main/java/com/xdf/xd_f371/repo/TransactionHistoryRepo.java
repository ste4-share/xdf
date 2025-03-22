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
    @Query(value = "select * from transaction_history where xd_id=:xd_id and mucgia=:gia order by created_at desc limit 1",nativeQuery = true)
    Optional<TransactionHistory> getInventoryOfPrice_Lxd(@Param("xd_id") int xd_id,@Param("gia") double dongia);
    @Query(value = "select * from transaction_history where xd_id=:xd_id and date=:d order by created_at desc",nativeQuery = true)
    List<TransactionHistory> getSizeOfTransactionByDay(@Param("xd_id") int xd_id, @Param("d")LocalDate date);
    @Query(value = "select * from transaction_history where xd_id=:xd_id and date < :e order by created_at desc",nativeQuery = true)
    List<TransactionHistory> getTransactionHistoryByDate(@Param("xd_id") int xd_id, @Param("e")LocalDate date);
    @Query(value = "SELECT distinct on (mucgia) * FROM public.transaction_history where xd_id=:xd_id order by mucgia,created_at desc",nativeQuery = true)
    List<TransactionHistory> getLastestTimeForEachPrices(@Param("xd_id") int xd_id);
    @Query(value = "select lxd.id,maxd,tenxd,loai,\n" +
            "case when tdk_nvdx is null then 0 else tdk_nvdx end,\n" +
            "case when tdk_sscd is null then 0 else tdk_sscd end,\n" +
            "case when pre_nvdx is null then 0 else pre_nvdx end,\n" +
            "case when pre_sscd is null then 0 else pre_sscd end,\n" +
            "cl.priority_1,cl.priority_2,cl.priority_3\n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (SELECT distinct on (xd_id) xd_id,tonkhotong as tdk_nvdx,tonkh_sscd as tdk_sscd FROM transaction_history where date < :s order by xd_id,created_at desc) a on a.xd_id=lxd.id\n" +
            "left join (SELECT distinct on (xd_id) xd_id,tonkhotong as pre_nvdx,tonkh_sscd as pre_sscd FROM transaction_history where date < :e order by xd_id,created_at desc) b on b.xd_id=lxd.id",nativeQuery = true)
    List<Object[]> getInvByTime(@Param("s") LocalDate sd,@Param("e") LocalDate ed);
}
