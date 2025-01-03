package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LedgersRepo extends JpaRepository<Ledger, Integer> {
    @Query(value = "select new com.xdf.xd_f371.dto.LedgerDto(ld.ledger_id,ld.id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.so_km,l.giohd_md,l.giohd_tk," +
            "l.sl_tieuthu_md,l.sl_tieuthu_tk,l.dvi_nhan_id,l.dvi_xuat_id,l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.loaigiobay," +
            "l.nguoi_nhan,l.so_xe,l.lenh_so,l.nhiemvu,l.nhiemvu_id,l.tcn_id,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.chat_luong,ld.phai_xuat,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.loaixd_id,ld.phuongtien_id,ld.thuc_xuat, ld.thuc_xuat_tk,ld.soluong,ld.thuc_nhap," +
            "ld.phai_nhap,ld.haohut_sl,l.loainv,ld.nl_gio,ld.nl_km,ld.soluong_px) from Ledger l join l.ledgerDetails ld where l.id=:i")
    List<LedgerDto> findLedgerByID(@Param("i") Long billId);
    @Query(value = "select * from ledgers l where l.id=:i",nativeQuery = true)
    Optional<Ledger> findLedgerById(@Param("i") int id);
    @Query("select new com.xdf.xd_f371.dto.MiniLedgerDto(l.id,l.bill_id, l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.from_date,l.nhiemvu," +
            "a.username, count(ld.ten_xd), sum(ld.soluong*ld.don_gia)) from Ledger l join " +
            "l.ledgerDetails ld join l.accounts a where l.status like :s and l.from_date between :sd and :ed " +
            "group by 1,2,3,4,5,6,7,8 order by l.timestamp desc")
    List<MiniLedgerDto> findInterfaceLedger(@Param("s") String s, @Param("sd") LocalDate sd, @Param("ed") LocalDate ed);
    @Query("select new com.xdf.xd_f371.dto.MiniLedgerDto(l.id,l.bill_id, l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.from_date,l.nhiemvu," +
            "a.username, count(ld.ten_xd), sum(ld.soluong*ld.don_gia)) from Ledger l join " +
            "l.ledgerDetails ld join l.accounts a where l.status like :s " +
            "group by 1,2,3,4,5,6,7,8 order by l.timestamp desc")
    List<MiniLedgerDto> findAllInterfaceLedger(@Param("s") String s);
    @Query(value = "select * from ledgers l where l.loai_phieu like :lp and l.from_date between :sd and :ed", nativeQuery = true)
    List<Ledger> findAllByQuarter(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed,@Param("lp") String lp);
    @Query(value = "select * from ledgers l where l.from_date between :sd and :ed", nativeQuery = true)
    List<Ledger> findAllByInDateRange(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);
    @Query(value = "select * from ledgers l where l.from_date < :sd", nativeQuery = true)
    List<Ledger> findAllByBeforeDateRange(@Param("sd") LocalDate sd);
    @Query(value = "select * from ledgers l where limit 1 order by l.from_date ASC", nativeQuery = true)
    Optional<Ledger> findFirstLedger();
    @Query(value = "select lxd.id,maxd,tenxd,petroleum_type_id,\n" +
            "case when don_gia is null then 0 else don_gia end as don_gia,\n" +
            "case when nhap_nvdx is null then 0 else nhap_nvdx end as nhap_nvdx,\n" +
            "case when nhap_sscd is null then 0 else nhap_sscd end as nhap_sscd,\n" +
            "case when xuat_nvdx is null then 0 else xuat_nvdx end as xuat_nvdx,\n" +
            "case when xuat_sscd is null then 0 else xuat_sscd end as xuat_sscd,from_date,end_date\n" +
            "from loaixd2 lxd left join (select loaixd_id, ma_xd,ten_xd,chung_loai,don_gia,sum(nhap_nvdx) as nhap_nvdx,\n" +
            "sum(nhap_sscd) as nhap_sscd,sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd, min(l.from_date) as from_date,max(l.end_date) as end_date \n" +
            "from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
            "where status like 'ACTIVE' and l.from_date between :sd and :ed \n" +
            "group by 1,2,3,4,5) a on lxd.id=a.loaixd_id",nativeQuery = true)
    List<Object[]> findAllInvByQuarter(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);
    @Modifying
    @Query(value = "update ledgers l set tructhuoc=:c where (dvi_nhan_id=:nid or dvi_xuat_id=:nid) and l.from_date between :sd and :ed", nativeQuery = true)
    void updateTrucThuocFromNxx(@Param("nid") int nguonnx_id,@Param("c") String code,@Param("sd") LocalDate sd, @Param("ed") LocalDate ed);
    @Modifying
    @Query(value = "update ledgers l set status='IN_ACTIVE' where l.id=:i", nativeQuery = true)
    void inactiveLedgers(@Param("i") int id);

}
