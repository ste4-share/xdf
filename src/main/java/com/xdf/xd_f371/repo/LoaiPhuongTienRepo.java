package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LoaiPhuongTien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoaiPhuongTienRepo extends JpaRepository<LoaiPhuongTien, Integer> {
    @Query(value = "select * from loai_phuongtien where type_name like :tn",nativeQuery = true)
    LoaiPhuongTien findLptByName(@Param("tn") String tn);
    @Query(value = "select * from loai_phuongtien where id=:id",nativeQuery = true)
    Optional<LoaiPhuongTien> findByLptId(@Param("id") int id);
}
