package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChitietNhiemvuRepo extends JpaRepository<ChitietNhiemVu, Integer> {
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv")
    List<NhiemVuDto> findAllDtoBy();
}
