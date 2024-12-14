package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.PhuongTien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhuongtienRepo extends JpaRepository<PhuongTien,Integer> {
    @Query("select p from PhuongTien p join p.loaiPhuongTien lpt where lpt.type like :loaipt")
    List<PhuongTien> findPhuongTienByLoaiPhuongTien(@Param("loaipt") String loaiPhuongTien);
}
