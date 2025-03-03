package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.InvRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvRecordsRepo extends JpaRepository<InvRecords,Long> {
    @Query(value = "select new com.xdf.xd_f371.entity.InvRecords(loaixd_id,price,sum(nvdx),sum(sscd)) from InvRecords inv where inv.status like 'ACTIVE' and (inv.dvi_nhan_id=:dvn_id or inv.dvi_xuat_id=:dvx_id) group by 1,2")
    List<InvRecords> getInventoryWithPrice(@Param("dvn_id")Long dvn_id,@Param("dvx_id")Long dvx_id);
    @Query(value = "select loaixd_id,sum(nvdx) as nvdx,sum(sscd) as sscd from inv_records where status like 'ACTIVE' and (dvi_nhan_id=:dvn_id or dvi_xuat_id=:dvx_id) group by 1",nativeQuery = true)
    List<InvRecords> getInventoryNoPrice(@Param("dvn_id")Long dvn_id,@Param("dvx_id")Long dvx_id);
    @Query(value = "update inv_records set status='IN_ACTIVE' where ledger_details_id=:ld_id",nativeQuery = true)
    int inActiveRecord(@Param("ld_id") Long ldId);
    @Query(value = "update inv_records set nvdx=:n,sscd=:s where ledger_details_id=:ld_id",nativeQuery = true)
    int updateNVDXAndSSCD(@Param("n") double nvdx,@Param("s") double sscd);
}
