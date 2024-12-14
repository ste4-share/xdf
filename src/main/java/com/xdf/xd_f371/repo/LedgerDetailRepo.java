package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LedgerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LedgerDetailRepo extends JpaRepository<LedgerDetails, Integer> {
}
