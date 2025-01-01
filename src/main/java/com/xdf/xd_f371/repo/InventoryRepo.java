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
    @Query(value = "select * from inventory where petro_id=:petro_id and sd >= :std and sd <= :end order by price",nativeQuery = true)
    List<Inventory> findByPetro_idAndDate(@Param("petro_id") int petro_id, @Param("std") LocalDate sd, @Param("end") LocalDate ed);
    @Query(value = "select * from inventory where petro_id=:petro_id and sd >= :std and sd <= :end and status like :st order by price ",nativeQuery = true)
    List<Inventory> findByPetro_idAndDateStatus(@Param("petro_id") int petro_id, @Param("std") LocalDate sd, @Param("end") LocalDate ed,@Param("st") String st);
    @Query(value = "select * from inventory where petro_id=:petro_id and sd >= :std and sd <= :end and price=:p order by price",nativeQuery = true)
    Optional<Inventory> findByUnique(@Param("petro_id") int petro_id, @Param("std") LocalDate sd, @Param("end") LocalDate ed,@Param("p") int p);
    @Query(value = "select max(id) as id,petro_id,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd) as tdk_sscd," +
            "sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd, sum(xuat_nvdx) as xuat_nvdx," +
            "sum(xuat_sscd) as xuat_sscd, max(status) as status,max(price) as price, max(create_at) as create_at " +
            "from inventory where petro_id=:p and sd >= :std and sd <= :en group by 2 order by price",nativeQuery = true)
    Optional<Inventory> findByUniqueGroupby(@Param("p") int petro_id,@Param("std") LocalDate sd, @Param("en") LocalDate ed);
    @Query(value = "select i.petro_id,maxd,tenxd,loai,max(i.tdk_nvdx) as tdk_nvdx,max(i.tdk_sscd),\n" +
            "case when max(a.nhap_nvdx) is null then 0 else max(a.nhap_nvdx) end as nhap_nvdx,\n" +
            "case when max(a.xuat_nvdx) is null then 0 else max(a.xuat_nvdx) end as xuat_nvdx,\n" +
            "case when max(a.nhap_nvdx-a.xuat_nvdx) is null then 0 else max(a.nhap_nvdx-a.xuat_nvdx) end as nvdx,\n" +
            "case when max(a.nhap_sscd) is null then 0 else max(a.nhap_sscd) end as nhap_sscd,\n" +
            "case when max(a.xuat_sscd) is null then 0 else max(a.xuat_sscd) end as xuat_sscd,\n" +
            "case when max(a.nhap_sscd-a.xuat_sscd) is null then 0 else max(a.nhap_sscd-a.xuat_sscd) end as sscd,\n" +
            "max(cl.priority_1),max(cl.priority_2),max(cl.priority_3)\n" +
            "from inventory i\n" +
            "left join (SELECT loaixd_id,ten_xd,chung_loai,sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd,sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd \n" +
            "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE' and l.from_date >= :std and l.from_date <= :end\n" +
            "group by 1,2,3) a on i.petro_id=a.loaixd_id\n" +
            "left join loaixd2 lxd on lxd.id=i.petro_id\n" +
            "left join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "where sd >= :std and sd <= :end\n" +
            "group by 1,2,3,4,cl.priority_1,cl.priority_2,cl.priority_3\n" +
            "order by cl.priority_1,cl.priority_2,cl.priority_3",nativeQuery = true)
    List<Object[]> getAllTonkho(@Param("std") LocalDate sd, @Param("end") LocalDate ed);

}
