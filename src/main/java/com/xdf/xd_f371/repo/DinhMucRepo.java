package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DinhMucRepo extends JpaRepository<DinhMuc, Integer> {
    @Query("select new com.xdf.xd_f371.dto.DinhMucPhuongTienDto(d.id, q.id, p.id, lpt.id, d.dm_md_gio,d.dm_tk_gio, d.dm_xm_gio,d.dm_xm_km,q.index,n.ten,q.start_date,q.end_date," +
            "p.name,p.quantity,lpt.typeName,lpt.type) from DinhMuc d left join d.phuongTien p left join p.loaiPhuongTien lpt " +
            "left join d.quarter q left join p.nguonNx n where q.id=:qid and lpt.type like :type_pt")
    List<DinhMucPhuongTienDto> findAllBy(@Param("qid") int quarter_id,@Param("type_pt") String type);
    @Query("select d from PhuongTien p join p.dinhmuc d where d.quarter_id=:quarter_id and d.phuongtien_id=:pt_id")
    Optional<DinhMuc> findDinhmucByPhuongtien(@Param("pt_id") int pt_id, @Param("quarter_id") int quarter_id);
}
