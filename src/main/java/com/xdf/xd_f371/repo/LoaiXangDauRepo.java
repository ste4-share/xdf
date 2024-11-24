package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.LoaiXangDau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoaiXangDauRepo extends JpaRepository<LoaiXangDau, Integer> {
    LoaiXangDau findByTenxd(String tenxd);

    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where cl.chungloai=:type")
    List<LoaiXangDauDto> findByType(@Param("type") String type);

    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd")
    List<LoaiXangDauDto> findAllBy();
}
