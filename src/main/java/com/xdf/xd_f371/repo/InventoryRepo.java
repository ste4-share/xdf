package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    @Query(value = "select lxd.id,tenxd,case when NHAP_fb.price is null then 0 else NHAP_fb.price end as gia,\n" +
            "(case when NHAP_fb.tonkho is null then 0 else NHAP_fb.tonkho end)-(case when XUAT_fb.tonkho is null then 0 else XUAT_fb.tonkho end) as fb\n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id \n" +
            "left join (select loaixd_id,don_gia as price, sum(nhap_nvdx+nhap_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE' and dvi_nhan_id=:dvi_id and loai_phieu like 'NHAP' and don_gia=:gia group by 1,2) NHAP_fb on lxd.id=NHAP_fb.loaixd_id\n" +
            "left join (select loaixd_id,don_gia as price, sum(xuat_nvdx+xuat_sscd) as tonkho from ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE' and dvi_xuat_id=:dvi_id and loai_phieu like 'XUAT' and don_gia=:gia group by 1,2) XUAT_fb on lxd.id=XUAT_fb.loaixd_id\n" +
            "where lxd.id=:lxd_id",nativeQuery = true)
    List<Object[]> findPreInventoryPriceAndUnit(@Param("lxd_id") int lxd_id,@Param("gia") double gia,@Param("dvi_id") int dvi_id);
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
            "where status like 'ACTIVE' and l.from_date between :sd and :ed and (dvi_nhan_id=:dvi_id or dvi_xuat_id=:dvi_id) \n" +
            "group by 1) b on lxd.id=loaixd_id\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllTonkho_toanDv(@Param("sd") LocalDate sd, @Param("ed") LocalDate ed,@Param("dvi_id") int dv_id);
    @Query(value = "select lxd.id,maxd,tenxd,loai,\n" +
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
    @Query(value = "select dvi_id from inventory limit 1",nativeQuery = true)
    Optional<Integer> getdviIdFromIn();
}
