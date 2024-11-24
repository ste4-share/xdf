package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LedgerDto {
    private int ledger_id;
    private int ledger_detail_id;
    private int quarter_id;
    private int bill_id;
    private int amount;
    private Date from_date;
    private Date end_date;
    private String status;
    private int so_km;
    private String giohd_md;
    private String giohd_tk;
    private int sl_tieuthu_md;
    private int sl_tieuthu_tk;
    private int inventoryId;
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
    private String nhiemvu_id;
    private String tcn_id;

    private String ma_xd;
    private String ten_xd;
    private String chung_loai;
    private String chat_luong;
    private int phai_xuat;
    private double nhiet_do_tt;
    private double ty_trong;
    private int he_so_vcf;
    private int don_gia;
    private int loaixd_id;
    private int phuongtien_id;
    private int thuc_xuat;
    private int thuc_xuat_tk;
    private int nhiemvu_hanmuc_id;
    private int soluong;
    private int thuc_nhap;
    private int phai_nhap;
    private int px_soluong;

}
