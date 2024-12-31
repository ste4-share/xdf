package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HanmucNhiemvu2Repository extends JpaRepository<HanmucNhiemvu2, Integer> {
    @Query("select new com.xdf.xd_f371.dto.HanmucNhiemvu2Dto(hm.id,hm.years,hm.dvi_id,hm.nhiemvu_id,hm.diezel,hm.daubay,hm.xang,n.tenNv,ct.nhiemvu) " +
            "from HanmucNhiemvu2 hm join hm.chitietNhiemVu ct join ct.nhiemVu n join n.team t where hm.years=:y order by t.tt,n.priority")
    List<HanmucNhiemvu2Dto> findAllDto(@Param("y") int y);

    @Query(value = "select * from hanmuc_nhiemvu2 where years=:y",nativeQuery = true)
    List<HanmucNhiemvu2> findAllByYear(@Param("y") int y);

    @Query(value = "select * from hanmuc_nhiemvu2 where nhiemvu_id=:n and years=:y",nativeQuery = true)
    Optional<HanmucNhiemvu2> findByUnique(@Param("y") int y,@Param("n") int ctnv);
}
