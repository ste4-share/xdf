package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MucGiaRepo extends JpaRepository<Mucgia, Integer> {
    Optional<Mucgia> findMucGiaByIdAndStatus(int id, String status);

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and status like :s", nativeQuery = true)
    List<Mucgia> findAllMucgiaByItemID(@Param("petroId") int itemID,@Param("qId") int quarter_id,@Param("s") String s);

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and price=:price", nativeQuery = true)
    Optional<Mucgia> findAllMucgiaUnique(@Param("petroId") int itemID,@Param("qId") int quarter_id, @Param("price") int price);

    @Query(value = "select new com.xdf.xd_f371.dto.SpotDto(lxd.id,lxd.maxd,lxd.tenxd,cl.loai,i.tdk_nvdx+i.nhap_nvdx-i.xuat_nvdx,i.tdk_sscd+i.nhap_sscd-i.xuat_sscd) from Inventory i join i.loaiXangDau lxd join lxd.chungLoaiXd cl on cl.id=lxd.petroleum_type_id where i.quarter_id=:qid order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<SpotDto> getAllSpots(@Param("qid") int quarter_id);

    @Query(value = "select new com.xdf.xd_f371.dto.SpotDto(lxd.id,lxd.maxd,lxd.tenxd,cl.loai,i.tdk_nvdx,i.tdk_sscd,i.nhap_nvdx,i.xuat_nvdx, i.nhap_sscd,i.xuat_sscd) from Inventory i join i.loaiXangDau lxd join lxd.chungLoaiXd cl on cl.id=lxd.petroleum_type_id where i.quarter_id=:qid order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<SpotDto> getAllSpots2(@Param("qid") int quarter_id);
}
