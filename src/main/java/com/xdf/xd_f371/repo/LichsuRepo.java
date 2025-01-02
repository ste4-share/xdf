package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LichsuXNK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LichsuRepo extends JpaRepository<LichsuXNK,Integer> {
    @Query(value = "Select * from lichsuxnk order by timestamp DESC", nativeQuery = true)
    List<LichsuXNK> findAll();
    @Query(value = "Select * from LichsuXNK ls where ls.sd between :sd and :ed order by ls.timestamp DESC",nativeQuery = true)
    List<LichsuXNK> findAllByQuyid(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);
}