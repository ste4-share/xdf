package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.ChungLoaiXd;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChungLoaiXdRepo extends JpaRepository<ChungLoaiXd, Integer> {
    @Query(value = "select * from chungloaixd where code like :cod",nativeQuery = true)
    Optional<ChungLoaiXd> findByCode(@Param("cod") String code);
}