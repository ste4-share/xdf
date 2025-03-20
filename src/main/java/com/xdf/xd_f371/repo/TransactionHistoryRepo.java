package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.TransactionHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TransactionHistoryRepo extends JpaRepository<TransactionHistory, String> {
    @Modifying
    @Query(value = "CREATE TABLE IF NOT EXISTS :tbn PARTITION OF xd_id FOR VALUES IN (:xd);",nativeQuery = true)
    void createPartitionTable(@Param("tbn") String table_name,@Param("xd") int xd);
}
