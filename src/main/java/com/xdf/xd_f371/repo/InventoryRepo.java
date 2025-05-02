package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "SELECT loaixd_id,ld.id as id,don_gia,sum(nhap_nvdx),sum(xuat_nvdx),sum(nhap_sscd),sum(xuat_sscd)\n" +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id \n" +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id and dvi_nhan_id=:dvi_id group by 1,2,3",nativeQuery = true)
    List<Object[]> findPreInventoryPetro(@Param("lxd_id") int lxd_id,@Param("dvi_id") int dvid);
}
