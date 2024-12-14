package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LichsuXNK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LichsuRepo extends JpaRepository<LichsuXNK,Integer> {
    @Query(value = "Select * from lichsuxnk order by timestamp DESC", nativeQuery = true)
    List<LichsuXNK> findAll();
}