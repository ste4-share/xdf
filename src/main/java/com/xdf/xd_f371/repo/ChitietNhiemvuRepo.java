package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.ChitietNhiemVuDto;
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
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where lnv.task_name <> :lnv")
    List<NhiemVuDto> findAllDtoBy(@Param("lnv") String lnv);
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv")
    List<NhiemVuDto> findAllBy();
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where lnv.task_name like :lnv")
    List<NhiemVuDto> findAllDtoById(@Param("lnv") String lnv);
    @Query(value = "select * from chitiet_nhiemvu where nhiemvu like :nv and nhiemvu_id=:nv_id",nativeQuery = true)
    Optional<ChitietNhiemVu> findByNhiemvu(@Param("nv") String nhiemvu,@Param("nv_id") int nv_id);
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where n.tenNv like :nv and ct.nhiemvu like :chitiet")
    Optional<NhiemVuDto> findAllByChitietNhiemvu(@Param("nv") String nv, @Param("chitiet") String chitiet);
    @Query("select new ChitietNhiemVu(ct.id,ct.nhiemvu_id,ct.nhiemvu) from ChitietNhiemVu ct where ct.nhiemvu_id=:od")
    List<ChitietNhiemVu> findByNhiemvuId(@Param("od") int id);
    @Query("select new com.xdf.xd_f371.dto.ChitietNhiemVuDto(n.id,ct.id,n.tenNv,ct.nhiemvu) from ChitietNhiemVu ct join ct.nhiemVu n where n.assignmentTypeId=:loainv or n.assignmentTypeId=:loainv1")
    List<ChitietNhiemVuDto> findAllByLoaiNv(@Param("loainv") int loainv_id,@Param("loainv1") int loainv_id1);
    @Query("select new com.xdf.xd_f371.dto.ChitietNhiemVuDto(n.id,ct.id,n.tenNv,ct.nhiemvu) from ChitietNhiemVu ct join ct.nhiemVu n where ct.nhiemvu like :tennv")
    Optional<ChitietNhiemVuDto> findByTenNhiemvu(@Param("tennv") String tennv);
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where ct.nhiemvu like :chitiet")
    Optional<NhiemVuDto> findByTenNv(@Param("chitiet") String tennv);
}
