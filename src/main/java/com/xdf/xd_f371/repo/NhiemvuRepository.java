package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.NhiemVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhiemvuRepository extends JpaRepository<NhiemVu,Integer> {
    List<NhiemVu> findAll();
}
