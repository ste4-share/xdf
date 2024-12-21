package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "select * from inventory where quarter_id=:quarter_id",nativeQuery = true)
    List<Inventory> findByQuarter_id(@Param("quarter_id") int quarter_id);
    @Query(value = "select * from inventory where petro_id=:petro_id and quarter_id=:quarter_id and status like :st order by price",nativeQuery = true)
    List<Inventory> findByPetro_idAndQuarter_id(@Param("petro_id") int petro_id,@Param("quarter_id") int quarter_id,@Param("st") String st);
    @Query(value = "select * from inventory where petro_id=:petro_id and quarter_id=:quarter_id and price=:p and status like :st order by price",nativeQuery = true)
    Optional<Inventory> findByUnique(@Param("petro_id") int petro_id,@Param("quarter_id") int quarter_id,@Param("st") String st,@Param("p") int p);
    @Query(value = "select id,petro_id,quarter_id,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd) as tdk_sscd," +
            "sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd, sum(xuat_nvdx) as xuat_nvdx," +
            "sum(xuat_sscd) as xuat_sscd, max(status) as status,max(price) as price, max(create_at) as create_at " +
            "from inventory where petro_id=:petro_id and quarter_id=:quarter_id group by 1,2,3 order by price",nativeQuery = true)
    Optional<Inventory> findByUniqueGroupby(@Param("petro_id") int petro_id,@Param("quarter_id") int quarter_id);
    @Query(value = "select new com.xdf.xd_f371.dto.SpotDto(lxd.id,lxd.maxd,lxd.tenxd,cl.loai,sum(i.tdk_nvdx),sum(i.tdk_sscd),sum(i.nhap_nvdx),sum(i.xuat_nvdx),sum(i.nhap_nvdx-i.xuat_nvdx),sum(i.nhap_sscd),sum(i.xuat_sscd),sum(i.nhap_sscd-i.xuat_sscd)) from Inventory i join i.loaiXangDau lxd join lxd.chungLoaiXd cl on cl.id=lxd.petroleum_type_id where i.quarter_id=:qid group by 1,2,3,4,cl.priority_1,cl.priority_2,cl.priority_3 order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<SpotDto> getAllSpots(@Param("qid") int quarter_id);
}
