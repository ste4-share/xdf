package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LedgerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LedgerDetailRepo extends JpaRepository<LedgerDetails, Long> {
    @Query(value = "select * from ledger_details where ledger_id like :ld_id",nativeQuery = true)
    List<LedgerDetails> findAllById(@Param("ld_id") String id);
}
