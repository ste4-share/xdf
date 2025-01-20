package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class LedgerDto {
    private Long ledger_id;
    private Long ledger_detail_id;
    private int bill_id;
    private Long amount;
    private LocalDate from_date;
    private LocalDate end_date;
    private String status;
    private int so_km;
    private String giohd_md;
    private String giohd_tk;
    private int sl_tieuthu_md;
    private int sl_tieuthu_tk;
    private int dvi_nhan_id;
    private int dvi_xuat_id;
    private String loai_phieu;
    private String dvi_nhan;
    private String dvi_xuat;
    private String loaigiobay;
    private String nguoi_nhan;
    private String so_xe;
    private String lenh_so;
    private String nhiemvu;
    private int nhiemvu_id;
    private int tcn_id;

    private String ma_xd;
    private String ten_xd;
    private String chung_loai;
    private String chat_luong;
    private int phai_xuat;
    private double nhiet_do_tt;
    private double ty_trong;
    private double he_so_vcf;
    private int don_gia;
    private int loaixd_id;
    private int phuongtien_id;
    private int thuc_xuat;
    private int thuc_xuat_tk;
    private int soluong;
    private int thuc_nhap;
    private int phai_nhap;
    private int haohut_sl;
    private String loainv;
    private Long nl_gio;
    private Long nl_km;
    private Long soluong_px;

    private String soluongpx_str;
    private String soluong_str;
    private String amount_str;
    private String dongia_str;
    private String thanhtien;

    public LedgerDto(Long ledger_id, Long ledger_detail_id, int bill_id,
                     Long amount, LocalDate from_date, LocalDate end_date, String status,
                     int so_km, String giohd_md, String giohd_tk,
                     int sl_tieuthu_md, int sl_tieuthu_tk, int dvi_nhan_id, int dvi_xuat_id,
                     String loai_phieu, String dvi_nhan, String dvi_xuat, String loaigiobay, String nguoi_nhan, String so_xe, String lenh_so, String nhiemvu, int nhiemvu_id,
                     int tcn_id, String ma_xd, String ten_xd, String chung_loai, String chat_luong, int phai_xuat, double nhiet_do_tt, double ty_trong,
                     double he_so_vcf, int don_gia, int loaixd_id, int phuongtien_id, int thuc_xuat, int thuc_xuat_tk,
                     int soluong, int thuc_nhap, int phai_nhap, int haohut_sl, String loainv, Long nl_gio, Long nl_km, Long soluong_px) {
        this.ledger_id = ledger_id;
        this.ledger_detail_id = ledger_detail_id;
        this.bill_id = bill_id;
        this.amount = amount;
        this.from_date = from_date;
        this.end_date = end_date;
        this.status = status;
        this.so_km = so_km;
        this.giohd_md = giohd_md;
        this.giohd_tk = giohd_tk;
        this.sl_tieuthu_md = sl_tieuthu_md;
        this.sl_tieuthu_tk = sl_tieuthu_tk;
        this.dvi_nhan_id = dvi_nhan_id;
        this.dvi_xuat_id = dvi_xuat_id;
        this.loai_phieu = loai_phieu;
        this.dvi_nhan = dvi_nhan;
        this.dvi_xuat = dvi_xuat;
        this.loaigiobay = loaigiobay;
        this.nguoi_nhan = nguoi_nhan;
        this.so_xe = so_xe;
        this.lenh_so = lenh_so;
        this.nhiemvu = nhiemvu;
        this.nhiemvu_id = nhiemvu_id;
        this.tcn_id = tcn_id;
        this.ma_xd = ma_xd;
        this.ten_xd = ten_xd;
        this.chung_loai = chung_loai;
        this.chat_luong = chat_luong;
        this.phai_xuat = phai_xuat;
        this.nhiet_do_tt = nhiet_do_tt;
        this.ty_trong = ty_trong;
        this.he_so_vcf = he_so_vcf;
        this.don_gia = don_gia;
        this.loaixd_id = loaixd_id;
        this.phuongtien_id = phuongtien_id;
        this.thuc_xuat = thuc_xuat;
        this.thuc_xuat_tk = thuc_xuat_tk;
        this.soluong = soluong;
        this.thuc_nhap = thuc_nhap;
        this.phai_nhap = phai_nhap;
        this.haohut_sl = haohut_sl;
        this.loainv = loainv;
        this.nl_gio = nl_gio;
        this.nl_km = nl_km;
        this.soluong_px = soluong_px;

        soluongpx_str = TextToNumber.textToNum(String.valueOf(soluong_px));
        soluong_str = TextToNumber.textToNum(String.valueOf(soluong));
        dongia_str = TextToNumber.textToNum(String.valueOf(don_gia));
        amount_str = TextToNumber.textToNum(String.valueOf(amount));
        thanhtien = TextToNumber.textToNum(String.valueOf(((long) soluong*don_gia)));
    }
}
