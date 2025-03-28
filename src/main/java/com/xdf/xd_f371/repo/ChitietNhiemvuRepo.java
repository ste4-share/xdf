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
    @Query(value = "select ct.* from chitiet_nhiemvu ct left join nhiemvu n on ct.nhiemvu_id=n.id where loainv like 'NV_BAY'",nativeQuery = true)
    List<ChitietNhiemVu> findAllCtnv();
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where lnv.task_name like :lnv")
    List<NhiemVuDto> findAllDtoById(@Param("lnv") String lnv);
    @Query(value = "select * from chitiet_nhiemvu where nhiemvu like :nv and nhiemvu_id=:nv_id",nativeQuery = true)
    Optional<ChitietNhiemVu> findByNhiemvu(@Param("nv") String nhiemvu,@Param("nv_id") int nv_id);
    @Query("select new ChitietNhiemVu(ct.id,ct.nhiemvu_id,ct.nhiemvu) from ChitietNhiemVu ct where ct.nhiemvu_id=:od")
    List<ChitietNhiemVu> findByNhiemvuId(@Param("od") int id);
    @Query("select new com.xdf.xd_f371.dto.NhiemVuDto(t.id,lnv.id, n.id, ct.id, n.priority,n.tenNv,ct.nhiemvu,t.name,lnv.task_name) from ChitietNhiemVu ct join ct.nhiemVu n join n.team t join n.loaiNhiemVu lnv where ct.nhiemvu like :chitiet")
    Optional<NhiemVuDto> findByTenNv(@Param("chitiet") String tennv);
}
