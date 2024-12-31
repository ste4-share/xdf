package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface HanmucNhiemvuTauBayRepo extends JpaRepository<NhiemvuTaubay,Long> {
    @Query("select new com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto(nvtb.id,n.id,ctnv.id,pt.id,n.ten,nv.tenNv,ctnv.nhiemvu,pt.name,nvtb.tk,nvtb.md,nvtb.nhienlieu) from NhiemvuTaubay nvtb " +
            "left join nvtb.nguonNx n join nvtb.chitietNhiemVu ctnv join nvtb.phuongTien pt join ctnv.nhiemVu nv where nvtb.years=:y order by n.ten")
    List<HanmucNhiemvuTaubayDto> getAllByYear(@Param("y") int y);
    @Query("select n from NhiemvuTaubay nvtb join nvtb.nguonNx n where nvtb.pt_id=:ptid and nvtb.quy_id=:qid")
    List<NguonNx> getAllDviTructhuocByTaubay(@Param("ptid") int phuongtien_id, @Param("qid") int quy_id);
    @Query("select new com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto(nvtb.id,n.id,ctnv.id,pt.id,n.ten,nv.tenNv,ctnv.nhiemvu,pt.name,nvtb.tk,nvtb.md,nvtb.nhienlieu) from NhiemvuTaubay nvtb " +
            "left join nvtb.nguonNx n join nvtb.chitietNhiemVu ctnv join nvtb.phuongTien pt join ctnv.nhiemVu nv " +
            "where nvtb.years=:y and nvtb.pt_id=:pid and nvtb.ctnv_id=:nv_id and nvtb.dviXuatId=:dvi_id order by n.ten")
    Optional<HanmucNhiemvuTaubayDto> findHmUnique(@Param("y") int y,@Param("pid") int pid,@Param("nv_id") int nv_id,@Param("dvi_id") int dvi_id);
    @Query(value = "select * from hanmuc_nhiemvu_taubay where years=:y",nativeQuery = true)
    List<NhiemvuTaubay> findAllByYear(@Param("y") int year);
}
