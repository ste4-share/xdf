package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChitietNhiemvuRepo extends JpaRepository<ChitietNhiemVu, Integer> {
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where lnv.id <> :lnv_id")
    List<NhiemVuDto> findAllDtoBy(@Param("lnv_id") int lnv_id);

    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv")
    List<NhiemVuDto> findAllBy();

    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where lnv.id=:lnv_id")
    List<NhiemVuDto> findAllDtoById(@Param("lnv_id") int lnv_id);

    Optional<ChitietNhiemVu> findByNhiemvu(String nhiemvu);
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where n.tenNv like :nv and ct.nhiemvu like :chitiet")
    Optional<NhiemVuDto> findAllByChitietNhiemvu(@Param("nv") String nv, @Param("chitiet") String chitiet);

    @Query("select new ChitietNhiemVu(ct.id,ct.nhiemvu_id,ct.nhiemvu) from ChitietNhiemVu ct where ct.nhiemvu_id=:od")
    List<ChitietNhiemVu> findByNhiemvuId(@Param("od") int id);
}
