package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.NguonNxPt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NguonnxPtRepo extends JpaRepository<NguonNxPt, Long> {

}
