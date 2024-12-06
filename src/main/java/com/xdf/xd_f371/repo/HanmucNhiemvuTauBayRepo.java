package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HanmucNhiemvuTauBayRepo extends JpaRepository<NhiemvuTaubay,Long> {
    @Query("select new com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto(n.id,ctnv.id,pt.id,n.ten,nv.tenNv,ctnv.nhiemvu,pt.name,nvtb.tk,nvtb.md,nvtb.nhienlieu) from NhiemvuTaubay nvtb left join nvtb.nguonNx n join nvtb.chitietNhiemVu ctnv join nvtb.phuongTien pt join ctnv.nhiemVu nv")
    List<HanmucNhiemvuTaubayDto> getAllBy();
}
