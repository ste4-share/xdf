package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.LoaiXangDau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiXangDauRepo extends JpaRepository<LoaiXangDau, Integer> {
    LoaiXangDau findByTenxd(String tenxd);

    List<LoaiXangDau> findByType(String type);

    List<LoaiXangDau> findByChungloai(String chungloai);
    @Query(value = "SELECT chungloai FROM loaixd2 group by chungloai", nativeQuery = true)
    List<String> findAllByChungloai();
}
