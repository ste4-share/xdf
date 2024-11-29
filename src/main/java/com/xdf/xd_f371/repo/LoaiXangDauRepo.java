package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.LoaiXangDau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoaiXangDauRepo extends JpaRepository<LoaiXangDau, Integer> {
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<LoaiXangDauDto> findAllOrderby();
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where lxd.tenxd like :name order by cl.priority_1,cl.priority_2,cl.priority_3")
    Optional<LoaiXangDauDto> findAllTenxdDto(@Param("name") String name);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where lxd.tenxd=:id")
    Optional<LoaiXangDauDto> findById(@Param("id") int id);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where cl.code like :code1 or cl.code like :code2 order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<LoaiXangDauDto> findByType(@Param("code1") String code1,@Param("code2") String code2);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd")
    List<LoaiXangDauDto> findAllBy();
}
