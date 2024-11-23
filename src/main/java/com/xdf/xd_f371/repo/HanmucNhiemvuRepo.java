package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.HanmucNhiemvu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HanmucNhiemvuRepo extends JpaRepository<HanmucNhiemvu, Integer> {
    @Query(value = "select * from hanmuc_nhiemvu where unit_id=:unit and nhiemvu_id=:nv and quarter_id=:q_id", nativeQuery = true)
    Optional<HanmucNhiemvu> findByUniqueIds(@Param("unit") int unit_id, @Param("nv") int nv_id,@Param("q_id") int q_id);
}
