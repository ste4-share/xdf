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

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and purpose=:pur", nativeQuery = true)
    List<Mucgia> findAllMucgiaByItemID(@Param("pur") String purpose,@Param("petroId") int itemID,@Param("qId") int quarter_id);

    @Query(value = "Select * from mucgia where quarter_id=:qId and item_id=:petroId and purpose like :pur and price=:price", nativeQuery = true)
    Optional<Mucgia> findAllMucgiaUnique(@Param("pur") String purpose,@Param("petroId") int itemID,@Param("qId") int quarter_id, @Param("price") int price);

    @Query(value = "SELECT lxd2.id as lxd_id,lxd2.maxd, lxd2.tenxd, 'NVDX',sum(amount) as nvdx_total, 'SSCD', (select sum(amount) as total_sscd from mucgia join loaixd2 on mucgia.item_id=loaixd2.id where purpose like 'SSCD' and tenxd=lxd2.tenxd limit 1) as sscd_total FROM mucgia mg right join loaixd2 lxd2 on mg.item_id=lxd2.id where purpose like 'NVDX' and mg.quarter_id=? group by lxd_id, maxd, tenxd order by nvdx_total desc", nativeQuery = true)
    List<SpotDto> getAllSpots(int quarter_id);

}
