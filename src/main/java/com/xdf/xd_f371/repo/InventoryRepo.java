package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    @Query(value = "SELECT loaixd_id,don_gia,(sum(nhap_nvdx)-sum(xuat_nvdx)) as pre_nvdx,(sum(nhap_sscd)-sum(xuat_sscd)) as pre_sscd " +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id " +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id and don_gia=:gia group by 1,2",nativeQuery = true)
    List<Object[]> findPreInventoryPrice(@Param("lxd_id") int lxd_id,@Param("gia") int gia);
    @Query(value = "SELECT loaixd_id,don_gia,(sum(nhap_nvdx)-sum(xuat_nvdx)) as pre_nvdx,(sum(nhap_sscd)-sum(xuat_sscd)) as pre_sscd " +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id " +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id group by 1,2",nativeQuery = true)
    List<Object[]> findPreInventoryAndPrice(@Param("lxd_id") int lxd_id);
    @Query(value = "SELECT loaixd_id,max(ld.id),don_gia,sum(nhap_nvdx),sum(xuat_nvdx),sum(nhap_sscd),sum(xuat_sscd)\n" +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id \n" +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id group by 1,3",nativeQuery = true)
    List<Object[]> findPreInventoryPetro(@Param("lxd_id") int lxd_id);
    @Query(value = "SELECT loaixd_id,(sum(nhap_nvdx)-sum(xuat_nvdx)) as pre_nvdx,(sum(nhap_sscd)-sum(xuat_sscd)) as pre_sscd " +
            "FROM ledger_details ld join ledgers l on l.id=ld.ledger_id " +
            "where l.status like 'ACTIVE' and loaixd_id=:lxd_id group by 1 limit 1",nativeQuery = true)
    List<Object[]> findPreInventory(@Param("lxd_id") int lxd_id);
    @Query(value = "select lxd.id,maxd,tenxd,loai,\n" +
            "case when tdk_nvdx is null then 0 else tdk_nvdx end,\n" +
            "case when tdk_sscd is null then 0 else tdk_sscd end,\n" +
            "case when b.nhap_nvdx is null then 0 else b.nhap_nvdx end,\n" +
            "case when b.xuat_nvdx is null then 0 else b.xuat_nvdx end,\n" +
            "case when b.nhap_nvdx-b.xuat_nvdx is null then 0 else b.nhap_nvdx-b.xuat_nvdx end,\n" +
            "case when b.nhap_sscd is null then 0 else b.nhap_sscd end,\n" +
            "case when b.xuat_sscd is null then 0 else b.xuat_sscd end,\n" +
            "case when b.nhap_sscd-b.xuat_sscd is null then 0 else b.nhap_sscd-b.xuat_sscd end,\n" +
            "cl.priority_1,cl.priority_2,cl.priority_3\n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (SELECT petro_id,sum(nhap_nvdx)-sum(xuat_nvdx) as tdk_nvdx,sum(nhap_sscd)-sum(xuat_sscd) as tdk_sscd FROM public.inventory\n" +
            "group by 1) a on a.petro_id=lxd.id\n" +
            "left join (SELECT loaixd_id,sum(nhap_nvdx) as nhap_nvdx,\n" +
            "sum(nhap_sscd) as nhap_sscd,sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd\n" +
            "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE' and l.from_date between :sd and :ed \n" +
            "group by 1) b on lxd.id=loaixd_id\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllTonkho(@Param("sd") LocalDate sd, @Param("ed") LocalDate ed);
    @Query(value = "select lxd.id,maxd,tenxd,loai,cast(0 as bigint),cast(0 as bigint),\n" +
            "case when max(a.nhap_nvdx) is null then 0 else max(a.nhap_nvdx) end as nhap_nvdx,\n" +
            "case when max(a.xuat_nvdx) is null then 0 else max(a.xuat_nvdx) end as xuat_nvdx,\n" +
            "case when max(a.nhap_nvdx-a.xuat_nvdx) is null then 0 else max(a.nhap_nvdx-a.xuat_nvdx) end as nvdx,\n" +
            "case when max(a.nhap_sscd) is null then 0 else max(a.nhap_sscd) end as nhap_sscd,\n" +
            "case when max(a.xuat_sscd) is null then 0 else max(a.xuat_sscd) end as xuat_sscd,\n" +
            "case when max(a.nhap_sscd-a.xuat_sscd) is null then 0 else max(a.nhap_sscd-a.xuat_sscd) end as sscd,\n" +
            "max(cl.priority_1),max(cl.priority_2),max(cl.priority_3)\n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (SELECT loaixd_id,ten_xd,chung_loai,sum(nhap_nvdx) as nhap_nvdx,\n" +
            "sum(nhap_sscd) as nhap_sscd,sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd \n" +
            "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE'\n" +
            "group by 1,2,3) a on lxd.id=a.loaixd_id\n" +
            "group by 1,2,3,4,cl.priority_1,cl.priority_2,cl.priority_3\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllTonkhoNotCondition();
}
