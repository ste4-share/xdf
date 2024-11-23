package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.HanmucNhiemvu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HanmucNhiemvuRepo extends JpaRepository<HanmucNhiemvu, Integer> {
}
