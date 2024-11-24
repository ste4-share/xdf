package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.postgresql.util.PGInterval;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LedgerDto {
    private int quarter_id;
    private int bill_id;
    private int amount;
    private Date from_date;
    private Date end_date;
    private String status;
    private String giohd_md;
    private String giohd_tk;
    private String sl_tieuthu_md;
    private String sl_tieuthu_tk;
    private int ledgerDetailId;
    private String dvi;
    private String dvvc;
    private String ngay;
    private String ma_xd;
    private String ten_xd;
    private String chung_loai;
    private String loai_phieu;
    private String so;
    private String theo_lenh_so;
    private String nhiem_vu;
    private String nguoi_nhan_hang;
    private String so_xe;
    private String chat_luong;
    private int phai_xuat;
    private double nhiet_do_tt;
    private double ty_trong;
    private int he_so_vcf;
    private int don_gia;
    private Long thanh_tien;
    private int so_km;
    private String denngay;
    private int loaixd_id;
    private int nhiemvu_id;
    private int tcn_id;
    private int phuongtien_id;
    private int ledger_id;
    private int import_unit_id;
    private int export_unit_id;
    private String loaigiobay;
    private int thuc_xuat;
    private int thuc_xuat_tk;
    private PGInterval dur_text_md2;
    private PGInterval dur_text_tk2;
    private int nhiemvu_hanmuc_id;
    private int soluong;
    private int thuc_nhap;
    private int phai_nhap;

}
