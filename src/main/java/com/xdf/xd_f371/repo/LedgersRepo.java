package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LedgersRepo extends JpaRepository<Ledger, Integer> {
    @Query(value = "Select * from ledgers where quarter_id=:qId and bill_id=:bId", nativeQuery = true)
    Ledger findLedgerByBillIdAndQuarter_id(@Param("bId") Integer billId,@Param("qId") Integer quarterId);
}
