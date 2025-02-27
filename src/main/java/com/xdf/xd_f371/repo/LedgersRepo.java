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

@Repository
public interface LedgersRepo extends JpaRepository<Ledger, Long> {
    @Query(value = "select new com.xdf.xd_f371.dto.LedgerDto(ld.ledger_id,ld.id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.so_km,l.giohd_md,l.giohd_tk," +
            "l.sl_tieuthu_md,l.sl_tieuthu_tk,l.dvi_nhan_id,l.dvi_xuat_id,l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.loaigiobay," +
            "l.nguoi_nhan,l.so_xe,l.lenh_so,l.nhiemvu,l.nhiemvu_id,l.tcn_id,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.chat_luong,ld.phai_xuat,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.loaixd_id,ld.phuongtien_id,ld.thuc_xuat, ld.thuc_xuat_tk,ld.soluong,ld.thuc_nhap," +
            "ld.phai_nhap,ld.haohut_sl,l.loainv,ld.nl_gio,ld.nl_km,ld.soluong_px) from Ledger l join l.ledgerDetails ld where l.id=:i")
    List<LedgerDto> findLedgerByID(@Param("i") Long billId);
    @Query("select new com.xdf.xd_f371.dto.MiniLedgerDto(l.id,l.bill_id, l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.from_date,l.nhiemvu," +
            "a.username, count(ld.ten_xd), sum(ld.soluong*ld.don_gia)) from Ledger l join " +
            "l.ledgerDetails ld join l.accounts a where l.status like :s " +
            "group by 1,2,3,4,5,6,7,8 order by l.timestamp desc")
    List<MiniLedgerDto> findAllInterfaceLedger(@Param("s") String s);
    @Query(value = "select * from ledgers l where l.from_date < :sd and l.status like 'ACTIVE'", nativeQuery = true)
    List<Ledger> findAllByBeforeDateRange(@Param("sd") LocalDate sd);
    @Query(value = "select * from ledgers l where (l.from_date < :sd and l.status like 'ACTIVE') and (dvi_nhan_id=:dvid or dvi_xuat_id=dvid)", nativeQuery = true)
    List<Ledger> findAllByBeforeDateRange2(@Param("sd") LocalDate sd,@Param("dvid") int dvnid);
    @Modifying
    @Query(value = "update ledgers l set tructhuoc=:c where (dvi_nhan_id=:nid or dvi_xuat_id=:nid)", nativeQuery = true)
    void updateTrucThuocFromNxx(@Param("nid") int nguonnx_id,@Param("c") String code);
    @Modifying
    @Query(value = "update ledgers l set status='IN_ACTIVE' where l.id=:i", nativeQuery = true)
    void inactiveLedgers(@Param("i") Long id);
    @Query(value = "select lxd.id,\n" +
            "case when don_gia is null then 0 else don_gia end,\n" +
            "case when nhap_nvdx is null then 0 else nhap_nvdx end,\n" +
            "case when nhap_sscd is null then 0 else nhap_sscd end,\n" +
            "case when xuat_nvdx is null then 0 else xuat_nvdx end,\n" +
            "case when xuat_sscd is null then 0 else xuat_sscd end,from_date,end_date \n" +
            "from loaixd2 lxd left join chungloaixd cl on lxd.petroleum_type_id=cl.id \n" +
            "left join (SELECT loaixd_id,don_gia,sum(nhap_nvdx) as nhap_nvdx,sum(nhap_sscd) as nhap_sscd,\n" +
            "sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd, min(from_date) as from_date,max(end_date) as end_date\n" +
            "FROM ledgers l join ledger_details ld on l.id=ld.ledger_id\n" +
            "where status like 'ACTIVE' and from_date < :sd\n" +
            "group by 1,2) a on lxd.id=a.loaixd_id",nativeQuery = true)
    List<Object[]> findAllInvByRangeBefore(@Param("sd") LocalDate sd);

    @Query(value = "select * from ledgers l where status like 'ACTIVE' and l.from_date between :sd and :ed",nativeQuery = true)
    List<Ledger> findAllLedgerDtoByTime(@Param("sd") LocalDate sd,@Param("ed") LocalDate ed);
}
