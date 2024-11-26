package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.dto.MiniLedgerDto;
import com.xdf.xd_f371.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;


@Repository
public interface LedgersRepo extends JpaRepository<Ledger, Integer> {
    @Query(value = "Select * from ledgers where quarter_id=:qId and bill_id=:bId", nativeQuery = true)
    Ledger findLedgerByBillIdAndQuarter_id(@Param("bId") Integer billId,@Param("qId") Integer quarterId);

    @Query("select new com.xdf.xd_f371.dto.LedgerDto(ld.ledger_id,ld.id,l.quarter_id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.so_km,l.giohd_md,l.giohd_tk," +
            "l.sl_tieuthu_md,l.sl_tieuthu_tk, l.inventoryId,l.dvi_nhan_id,l.dvi_xuat_id,l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.loaigiobay," +
            "l.nguoi_nhan,l.so_xe,l.lenh_so,l.nhiemvu,l.nhiemvu_id,l.tcn_id,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.chat_luong,ld.phai_xuat,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.loaixd_id,ld.phuongtien_id,ld.thuc_xuat, ld.thuc_xuat_tk, ld.nhiemvu_hanmuc_id,ld.soluong,ld.thuc_nhap," +
            "ld.phai_nhap,ld.px_soluong,ld.haohut_sl,l.loainv) from Ledger l join l.ledgerDetails ld")
    List<LedgerDto> findAllDtoBy();

    @Query("select new com.xdf.xd_f371.dto.MiniLedgerDto(l.bill_id, l.loai_phieu,l.dvi_nhan,l.dvi_xuat,l.timestamp, count(ld.ten_xd), sum(ld.soluong*ld.don_gia)) from Ledger l join l.ledgerDetails ld group by 1,2,3,4,5 order by l.timestamp desc")
    List<MiniLedgerDto> findInterfaceLedger();
}
