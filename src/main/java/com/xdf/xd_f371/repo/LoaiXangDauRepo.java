package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.dto.LoaiXdLedgerDto;
import com.xdf.xd_f371.entity.LoaiXangDau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LoaiXangDauRepo extends JpaRepository<LoaiXangDau, Integer> {
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<LoaiXangDauDto> findAllOrderby();
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where lxd.tenxd like :name order by cl.priority_1,cl.priority_2,cl.priority_3")
    Optional<LoaiXangDauDto> findAllTenxdDto(@Param("name") String name);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where lxd.id=:id")
    Optional<LoaiXangDauDto> findById(@Param("id") int id);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where cl.code like :code1 or cl.code like :code2 order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<LoaiXangDauDto> findByType(@Param("code1") String code1,@Param("code2") String code2);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) from ChungLoaiXd cl join cl.loaiXangDau lxd where cl.code like :code1 order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<LoaiXangDauDto> findByTypeName(@Param("code1") String code1);
    @Query("select new com.xdf.xd_f371.dto.LoaiXangDauDto(lxd.id,cl.id,lxd.maxd, lxd.tenxd, cl.loai, cl.chungloai,cl.tinhchat, cl.code,lxd.status) " +
            "from ChungLoaiXd cl join cl.loaiXangDau lxd order by cl.priority_1,cl.priority_2,cl.priority_3")
    List<LoaiXangDauDto> findAllBy();
    @Query(value = "select lxd.id,tenxd,nhap_nvdx,nhap_sscd,xuat_nvdx,xuat_sscd,from_date,end_date from loaixd2 lxd \n" +
            "join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (select ld.loaixd_id as xd_id,don_gia,sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd, sum(xuat_nvdx) as xuat_nvdx,\n" +
            "sum(xuat_sscd) as xuat_sscd,min(l.from_date) as from_date,max(l.end_date) as end_date\n" +
            "from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
            "where l.from_date is between :sd and :ed\n" +
            "group by 1,2) a on lxd.id=a.xd_id",nativeQuery = true)
    List<Object[]> findAllByLedger(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);
    @Query(value = "select lxd.id,tenxd,\n" +
            "case when nhap_nvdx is null then 0 else nhap_nvdx end,\n" +
            "case when nhap_sscd is null then 0 else nhap_sscd end,\n" +
            "case when xuat_nvdx is null then 0 else xuat_nvdx end,\n" +
            "case when xuat_sscd is null then 0 else xuat_sscd end,\n" +
            "case when don_gia is null then 0 else don_gia end,\n" +
            "from_date,end_date from loaixd2 lxd \n" +
            "join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
            "left join (select ld.loaixd_id as xd_id,don_gia,sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd, sum(xuat_nvdx) as xuat_nvdx,\n" +
            "sum(xuat_sscd) as xuat_sscd,min(l.from_date) as from_date,max(l.end_date) as end_date\n" +
            "from ledgers l join ledger_details ld on l.id=ld.ledger_id where l.from_date < :sd \n" +
            "group by 1,2) a on lxd.id=a.xd_id",nativeQuery = true)
    List<Object[]> findAllByLedgerBefore(@Param("sd") LocalDate sd);

    Optional<LoaiXangDau> findByMaxd(String maxd);
}
