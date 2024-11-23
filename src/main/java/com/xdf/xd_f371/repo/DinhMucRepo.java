package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DinhMucRepo extends JpaRepository<DinhMuc, Integer> {
    @Query("select new com.xdf.xd_f371.dto.DinhMucPhuongTienDto(d.id, q.id, p.id, lpt.id, d.dm_md_gio,d.dm_tk_gio, d.dm_xm_gio,d.dm_xm_km,q.name,q.start_date,q.end_date,p.name,p.quantity,lpt.typeName,lpt.type) from DinhMuc d join d.phuongTien p join p.loaiPhuongTien lpt join d.quarter q where q.id=:qid")
    List<DinhMucPhuongTienDto> findAllBy(@Param("qid") int quarter_id);
}
