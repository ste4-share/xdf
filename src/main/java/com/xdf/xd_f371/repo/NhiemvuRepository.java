package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.NhiemVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhiemvuRepository extends JpaRepository<NhiemVu,Integer> {
    @Query(value = "select * from nhiemvu where ten_nv like :n and status like :st",nativeQuery = true)
    Optional<NhiemVu> findByName(@Param("n") String n,@Param("st") String st);
}
