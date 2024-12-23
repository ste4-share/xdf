package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LoaiNhiemVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoaiNhiemvuRepo extends JpaRepository<LoaiNhiemVu, Integer> {
    @Query(value = "select * from loai_nhiemvu where task_name like :n",nativeQuery = true)
    Optional<LoaiNhiemVu> findLoaiNvByName(@Param("n") String n);
}
