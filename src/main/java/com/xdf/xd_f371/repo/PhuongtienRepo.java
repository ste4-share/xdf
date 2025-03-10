package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.PhuongTien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhuongtienRepo extends JpaRepository<PhuongTien,Integer> {
    @Query("select p from PhuongTien p join p.loaiPhuongTien lpt where lpt.type like :loaipt and p.nguonnx_id=:dvid")
    List<PhuongTien> findPhuongTienByLoaiPhuongTien(@Param("loaipt") String loaiPhuongTien,@Param("dvid") int dvid);
    @Query(value = "select p.id, p.name,lpt.type_name,timestamp,cast(a.quantity as integer) from phuongtien p \n" +
            "left join loai_phuongtien lpt on p.loaiphuongtien_id=lpt.id \n" +
            "left join (SELECT xmt_id,count(*) as quantity FROM public.unit_xmt group by 1) a on a.xmt_id=p.id\n" +
            "where lpt.type like :loaipt and p.nguonnx_id=:dvid \n" +
            "order by timestamp desc",nativeQuery = true)
    List<Object[]> findXmtByType(@Param("loaipt") String loaiPhuongTien,@Param("dvid") int dvid);
    @Query(value = "select p.id, p.name,lpt.type_name,timestamp,cast(a.quantity as integer) from phuongtien p \n" +
            "left join loai_phuongtien lpt on p.loaiphuongtien_id=lpt.id \n" +
            "left join (SELECT xmt_id,count(*) as quantity FROM public.unit_xmt group by 1) a on a.xmt_id=p.id\n" +
            "where lpt.type like :loaipt \n" +
            "order by timestamp desc",nativeQuery = true)
    List<Object[]> findXmtByTypeAll(@Param("loaipt") String loaiPhuongTien);
    @Query("select p from PhuongTien p where p.name like :n")
    Optional<PhuongTien> findPhuongTienByName(@Param("n") String name);
}
