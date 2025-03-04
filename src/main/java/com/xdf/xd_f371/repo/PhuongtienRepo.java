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

    @Query("select p from PhuongTien p where p.name like :n")
    Optional<PhuongTien> findPhuongTienByName(@Param("n") String name);
}
