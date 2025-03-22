package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "SELECT loaixd_id,ld.id as id,don_gia,sum(nhap_nvdx),sum(xuat_nvdx),sum(nhap_sscd),sum(xuat_sscd)\n" +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id \n" +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id group by 1,2,3",nativeQuery = true)
    List<Object[]> findPreInventoryPetro(@Param("lxd_id") int lxd_id);
    @Query(value = "SELECT loaixd_id,ld.id as id,don_gia,sum(nhap_nvdx),sum(xuat_nvdx),sum(nhap_sscd),sum(xuat_sscd)\n" +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id \n" +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id and dvi_nhan_id=:dvi_id group by 1,2,3",nativeQuery = true)
    List<Object[]> findPreInventoryPetro(@Param("lxd_id") int lxd_id,@Param("dvi_id") int dvid);
    @Query(value = "select lxd.id,maxd,tenxd,loai,\n" +
            "case when tdk_nvdx is null then 0 else tdk_nvdx end,\n" +
            "case when tdk_sscd is null then 0 else tdk_sscd end,\n" +
            "case when pre_nvdx is null then 0 else pre_nvdx end,\n" +
            "case when pre_sscd is null then 0 else pre_sscd end,\n" +
            "cl.priority_1,cl.priority_2,cl.priority_3\n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (SELECT petro_id,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd FROM public.inventory\n" +
            "group by 1) a on a.petro_id=lxd.id\n" +
            "left join (SELECT petro_id,sum(nvdx_quantity) as pre_nvdx,sum(sscd_quantity) as pre_sscd FROM public.inventory_units\n" +
            "group by 1) b on b.petro_id=lxd.id\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllInventoryUnit();
    @Query(value = "select lxd.id,maxd,tenxd,loai,\n" +
            "case when tdk_nvdx is null then 0 else tdk_nvdx end,\n" +
            "case when tdk_sscd is null then 0 else tdk_sscd end,\n" +
            "case when pre_nvdx is null then 0 else pre_nvdx end,\n" +
            "case when pre_sscd is null then 0 else pre_sscd end,\n" +
            "cl.priority_1,cl.priority_2,cl.priority_3\n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (SELECT xd_id,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd FROM public.inventory where dvi_id=:rid \n" +
            "group by 1) a on a.xd_id=lxd.id\n" +
            "left join (SELECT xd_id,sum(nvdx_quantity) as pre_nvdx,sum(sscd_quantity) as pre_sscd FROM public.inventory_units where root_unit_id=:rid \n" +
            "group by 1) b on b.xd_id=lxd.id\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllInventoryUnitByRootId(@Param("rid") int root_unitid);
    @Query(value = "select dvi_id from inventory limit 1",nativeQuery = true)
    Optional<Integer> getdviIdFromIn();
}
