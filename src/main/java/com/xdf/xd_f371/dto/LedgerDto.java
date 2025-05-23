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
    private String ledger_id;
    private String ledger_detail_id;
    private String bill_id;
    private double amount;
    private LocalDate from_date;
    private LocalDate end_date;
    private String status;
    private int so_km;
    private String giohd_md;
    private String giohd_tk;
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
    private double phai_xuat;
    private double nhiet_do_tt;
    private double ty_trong;
    private double he_so_vcf;
    private double don_gia;
    private int loaixd_id;
    private int phuongtien_id;
    private double thuc_xuat;
    private double thuc_xuat_tk;
    private double soluong;
    private double thuc_nhap;
    private double phai_nhap;
    private int haohut_sl;
    private String loainv;
    private Long nl_gio;
    private Long nl_km;
    private double soluong_px;

    private String soluongpx_str;
    private String soluong_str;
    private String amount_str;
    private String dongia_str;
    private String thanhtien;

    public LedgerDto(String ledger_id, String ledger_detail_id, String bill_id,
                     double amount, LocalDate from_date, LocalDate end_date, String status,
                     int so_km, String giohd_md, String giohd_tk,
                     int dvi_nhan_id, int dvi_xuat_id,
                     String loai_phieu, String dvi_nhan, String dvi_xuat, String loaigiobay, String nguoi_nhan, String so_xe, String lenh_so, String nhiemvu, int nhiemvu_id,
                     int tcn_id, String ma_xd, String ten_xd, String chung_loai, String chat_luong, double phai_xuat, double nhiet_do_tt, double ty_trong,
                     double he_so_vcf, double don_gia, int loaixd_id, int phuongtien_id, double thuc_xuat, double thuc_xuat_tk,
                     double soluong, double thuc_nhap, double phai_nhap, int haohut_sl, String loainv, Long nl_gio, Long nl_km, double soluong_px) {
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

        soluongpx_str = TextToNumber.textToNum_2digits(soluong_px);
        soluong_str = TextToNumber.textToNum_2digits(soluong);
        dongia_str = TextToNumber.textToNum_2digits(don_gia);
        amount_str = TextToNumber.textToNum_2digits(amount);
        thanhtien = TextToNumber.textToNum(String.valueOf((soluong*don_gia)));
    }
}
