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
    @Query("select new com.xdf.xd_f371.dto.DinhMucPhuongTienDto(d.id, p.id, lpt.id, d.dm_md_gio,d.dm_tk_gio, d.dm_xm_gio,d.dm_xm_km,n.ten," +
            "p.name,p.quantity,lpt.typeName,lpt.type) from DinhMuc d left join d.phuongTien p left join p.loaiPhuongTien lpt " +
            "left join p.nguonNx n where d.years=:y and lpt.type like :type_pt")
    List<DinhMucPhuongTienDto> findAllBy(@Param("y") int y,@Param("type_pt") String type);
    @Query("select d from PhuongTien p join p.dinhmuc d where d.years=:y and d.phuongtien_id=:pt_id")
    Optional<DinhMuc> findDinhmucByPhuongtien(@Param("pt_id") int pt_id, @Param("y") int y);
    @Query(value = "select * from dinhmuc where years=:y",nativeQuery = true)
    List<DinhMuc> findAllByYear(int y);
}
