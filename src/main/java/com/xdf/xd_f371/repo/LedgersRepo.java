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
public interface LedgersRepo extends JpaRepository<Ledger, Integer> {
    @Query(value = "select new com.xdf.xd_f371.dto.LedgerDto(ld.ledger_id,ld.id,l.quarter_id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.so_km,l.giohd_md,l.giohd_tk," +
            "l.sl_tieuthu_md,l.sl_tieuthu_tk,l.dvi_nhan_id,l.dvi_xuat_id,l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.loaigiobay," +
            "l.nguoi_nhan,l.so_xe,l.lenh_so,l.nhiemvu,l.nhiemvu_id,l.tcn_id,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.chat_luong,ld.phai_xuat,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.loaixd_id,ld.phuongtien_id,ld.thuc_xuat, ld.thuc_xuat_tk,ld.soluong,ld.thuc_nhap," +
            "ld.phai_nhap,ld.haohut_sl,l.loainv,ld.nl_gio,ld.nl_km,ld.soluong_px) from Ledger l join l.ledgerDetails ld where l.bill_id=:bId and l.quarter_id=:qId and l.loai_phieu like :lp")
    List<LedgerDto> findLedgerByBillIdAndQuarter_id(@Param("bId") Long billId,@Param("qId") Integer quarterId,@Param("lp") String lp);

    @Query("select new com.xdf.xd_f371.dto.LedgerDto(ld.ledger_id,ld.id,l.quarter_id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.so_km,l.giohd_md,l.giohd_tk," +
            "l.sl_tieuthu_md,l.sl_tieuthu_tk,l.dvi_nhan_id,l.dvi_xuat_id,l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.loaigiobay," +
            "l.nguoi_nhan,l.so_xe,l.lenh_so,l.nhiemvu,l.nhiemvu_id,l.tcn_id,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.chat_luong,ld.phai_xuat,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.loaixd_id,ld.phuongtien_id,ld.thuc_xuat, ld.thuc_xuat_tk,ld.soluong,ld.thuc_nhap," +
            "ld.phai_nhap,ld.haohut_sl,l.loainv,ld.nl_gio,ld.nl_km,ld.soluong_px) from Ledger l join l.ledgerDetails ld")
    List<LedgerDto> findAllDtoBy();

    @Query("select new com.xdf.xd_f371.dto.MiniLedgerDto(l.bill_id, l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.timestamp,l.nhiemvu," +
            "a.username, count(ld.ten_xd), sum(ld.soluong*ld.don_gia)) from Ledger l join " +
            "l.ledgerDetails ld join l.accounts a where l.status like :s and l.from_date between :sd and :ed " +
            "group by 1,2,3,4,5,6,7 order by l.timestamp desc")
    List<MiniLedgerDto> findInterfaceLedger(@Param("s") String s, @Param("sd") LocalDate sd, @Param("ed") LocalDate ed);
    @Query(value = "select * from ledgers where quarter_id=:qid and loai_phieu like :lp", nativeQuery = true)
    List<Ledger> findAllByQuarter(@Param("qid") int quarterId,@Param("lp") String lp);
    @Query(value = "select lxd.id,maxd,tenxd,petroleum_type_id,don_gia,nhap_nvdx,nhap_sscd,xuat_nvdx,xuat_sscd from loaixd2 lxd left join (select loaixd_id, ma_xd,ten_xd,chung_loai,don_gia,sum(nhap_nvdx) as nhap_nvdx,\n" +
            "sum(nhap_sscd) as nhap_sscd,sum(xuat_nvdx) as xuat_nvdx,sum(xuat_sscd) as xuat_sscd\n" +
            "from ledgers l join ledger_details ld on l.id=ld.ledger_id \n" +
            "where status like 'ACTIVE' and quarter_id=20\n" +
            "group by 1,2,3,4,5) a on lxd.id=a.loaixd_id",nativeQuery = true)
    List<Object[]> findAllInvByQuarter(@Param("qid") int quarterId);
    @Modifying
    @Query(value = "update ledgers set tructhuoc=:c where (dvi_nhan_id=:nid or dvi_xuat_id=:nid) and quarter_id=:qid", nativeQuery = true)
    void updateTrucThuocFromNxx(@Param("nid") int nguonnx_id,@Param("c") String code,@Param("qid") int qId);
    @Modifying
    @Query(value = "update ledgers set status='IN_ACTIVE' where bill_id=:so and loai_phieu like :lp and quarter_id=:qid", nativeQuery = true)
    void inactiveLedgers(@Param("so") int so,@Param("lp") String lp,@Param("qid") int qId);

}
