package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.ChitieuNhiemvuDto;
import com.xdf.xd_f371.entity.HanmucNhiemvu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HanmucNhiemvuRepo extends JpaRepository<HanmucNhiemvu, Integer> {
    @Query(value = "select * from hanmuc_nhiemvu where unit_id=:unit and nhiemvu_id=:nv and quarter_id=:q_id", nativeQuery = true)
    Optional<HanmucNhiemvu> findByUniqueIds(@Param("unit") int unit_id, @Param("nv") int nv_id,@Param("q_id") int q_id);

    @Query(value = "select new com.xdf.xd_f371.dto.ChitieuNhiemvuDto(n.id,hm.unit_id,ct.id,n.tenNv,ct.nhiemvu,hm.ct_tk,hm.ct_md,hm.consumpt) from HanmucNhiemvu hm join hm.chitietNhiemVu ct join ct.nhiemVu n where hm.unit_id=:unit and hm.quarter_id=:q_id")
    List<ChitieuNhiemvuDto> findAllByUnit(@Param("unit") int unit_id, @Param("q_id") int q_id);
}
