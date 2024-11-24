package com.xdf.xd_f371.repo;

import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.entity.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface LedgersRepo extends JpaRepository<Ledger, Integer> {
    @Query(value = "Select * from ledgers where quarter_id=:qId and bill_id=:bId", nativeQuery = true)
    Ledger findLedgerByBillIdAndQuarter_id(@Param("bId") Integer billId,@Param("qId") Integer quarterId);

    @Query("select new com.xdf.xd_f371.dto.LedgerDto(l.quarter_id, l.bill_id,l.amount,l.from_date,l.end_date,l.status,l.giohd_md,l.giohd_tk," +
            "l.sl_tieuthu_md,l.sl_tieuthu_tk, ld.id,ld.dvi,ld.dvvc,ld.ngay,ld.ma_xd,ld.ten_xd,ld.chung_loai,ld.loai_phieu," +
            "ld.so,ld.theo_lenh_so,ld.nhiem_vu,ld.nguoi_nhan_hang,ld.so_xe,ld.chat_luong,ld.phai_nhap,ld.nhiet_do_tt,ld.ty_trong," +
            "ld.he_so_vcf,ld.don_gia,ld.thanh_tien,ld.so_km,ld.denngay, ld.loaixd_id, ld.nhiemvu_id,ld.tcn_id,ld.phuongtien_id," +
            "ld.ledger_id,ld.import_unit_id, ld.export_unit_id,ld.loaigiobay,ld.thuc_xuat,ld.thuc_xuat_tk, ld.dur_text_md2, " +
            "ld.dur_text_tk2,ld.nhiemvu_hanmuc_id,ld.soluong,ld.thuc_nhap,ld.phai_nhap) from Ledger l join l.ledgerDetails ld")
    List<LedgerDto> findAllDtoBy();
}
