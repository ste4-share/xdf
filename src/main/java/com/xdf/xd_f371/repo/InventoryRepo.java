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
    @Query(value = "select * from inventory where quarter_id=:quarter_id",nativeQuery = true)
    List<Inventory> findByQuarter_id(@Param("quarter_id") int quarter_id);
    @Query(value = "select * from inventory where petro_id=:petro_id and quarter_id=:quarter_id and status like :st order by price",nativeQuery = true)
    List<Inventory> findByPetro_idAndQuarter_id(@Param("petro_id") int petro_id,@Param("quarter_id") int quarter_id,@Param("st") String st);
    @Query(value = "select * from inventory where petro_id=:petro_id and quarter_id=:quarter_id and price=:p and status like :st order by price",nativeQuery = true)
    Optional<Inventory> findByUnique(@Param("petro_id") int petro_id,@Param("quarter_id") int quarter_id,@Param("st") String st,@Param("p") int p);
    @Query(value = "select max(id) as id,petro_id,quarter_id,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd) as tdk_sscd," +
            "sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd, sum(xuat_nvdx) as xuat_nvdx," +
            "sum(xuat_sscd) as xuat_sscd, max(status) as status,max(price) as price, max(create_at) as create_at " +
            "from inventory where petro_id=:p and quarter_id=:qid group by 2,3 order by price",nativeQuery = true)
    Optional<Inventory> findByUniqueGroupby(@Param("p") int petro_id,@Param("qid") int quarter_id);
    @Query(value = "select i.petro_id,maxd,tenxd,loai,i.tdk_nvdx,i.tdk_sscd,\n" +
            "case when a.nhap_nvdx is null then 0 else a.nhap_nvdx end as nhap_nvdx,\n" +
            "case when a.xuat_nvdx is null then 0 else a.xuat_nvdx end as xuat_nvdx,\n" +
            "case when a.nhap_nvdx-a.xuat_nvdx is null then 0 else a.nhap_nvdx-a.xuat_nvdx end as nvdx,\n" +
            "case when a.nhap_sscd is null then 0 else a.nhap_sscd end as nhap_sscd,\n" +
            "case when a.xuat_sscd is null then 0 else a.xuat_sscd end as xuat_sscd,\n" +
            "case when a.nhap_sscd-a.xuat_sscd is null then 0 else a.nhap_sscd-a.xuat_sscd end as sscd from inventory i\n" +
            "left join (SELECT loaixd_id,ten_xd,chung_loai,sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd,sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd \n" +
            "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE' and quarter_id=:qid\n" +
            "group by 1,2,3) a on i.petro_id=a.loaixd_id\n" +
            "left join loaixd2 lxd on lxd.id=i.petro_id\n" +
            "left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "where quarter_id=:qid\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllTonkho(@Param("qid") int quarter_id);

}
