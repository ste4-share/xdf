package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;




@Entity
@Table(name = "ledger_details")
@Getter
@Setter
@NoArgsConstructor
public class LedgerDetails {
    @Id
    private Long id;
    @Column(name = "ma_xd")
    private String ma_xd;
    @Column(name = "ten_xd")
    private String ten_xd;
    @Column(name = "chung_loai")
    private String chung_loai;
    @Column(name = "chat_luong")
    private String chat_luong;
    @Column(name = "phai_xuat")
    private double phai_xuat;
    @Column(name = "nhiet_do_tt")
    @NotNull
    private double nhiet_do_tt;
    @Column(name = "ty_trong")
    @NotNull
    private double ty_trong;
    @Column(name = "he_so_vcf")
    @NotNull
    private double he_so_vcf;
    @Column(name = "don_gia")
    @NotNull
    @Min(value = 0)
    @Positive
    private double don_gia;
    @Column(name = "loaixd_id")
    private int loaixd_id;
    @Column(name = "phuongtien_id")
    private int phuongtien_id;
    @Column(name = "ledger_id")
    private Long ledger_id;
    @Column(name = "thuc_xuat")
    private double thuc_xuat;
    @Column(name = "thuc_xuat_tk")
    private double thuc_xuat_tk;
    @Column(name = "so_luong")
    @Min(value = 0)
    @Positive
    private double soluong;
    @Column(name = "thuc_nhap")
    private double thuc_nhap;
    @Column(name = "phai_nhap")
    private double phai_nhap;
    @Column(name = "thanhtien")
    private double thanhtien;
    @Column(name = "haohut_sl")
    private int haohut_sl;
    @Column(name = "nl_gio")
    private Long nl_gio;
    @Column(name = "nl_km")
    private Long nl_km;
    @Column(name = "sscd_nvdx")
    private String sscd_nvdx;
    @Column(name = "so_luong_px")
    @NotNull(message = "Phai xuat can not be null")
    @Min(value = 0)
    @Positive
    private double soluong_px;
    @Column(name = "nhap_nvdx")
    private double nhap_nvdx = 0;
    @Column(name = "nhap_sscd")
    private double nhap_sscd = 0;
    @Column(name = "xuat_nvdx")
    private double xuat_nvdx = 0;
    @Column(name = "xuat_sscd")
    private double xuat_sscd = 0;

    @Transient
    private String thanhtien_str;
    @Transient
    private String thucxuat_str;
    @Transient
    private String phaixuat_str;
    @Transient
    private String dongia_str;
    @Transient
    private String thucnhap_str;
    @Transient
    private String phainhap_str;

    @ManyToOne
    @JoinColumn(name = "ledger_id", nullable = false,insertable = false,updatable = false)
    private Ledger ledger;

    public LedgerDetails(LedgerDetails ld) {
        this.ma_xd = ld.ma_xd;
        this.ten_xd = ld.ten_xd;
        this.chung_loai = ld.chung_loai;
        this.chat_luong = ld.chat_luong;
        this.phai_xuat = ld.phai_xuat;
        this.nhiet_do_tt = ld.nhiet_do_tt;
        this.ty_trong = ld.ty_trong;
        this.he_so_vcf = ld.he_so_vcf;
        this.don_gia = ld.don_gia;
        this.loaixd_id = ld.loaixd_id;
        this.phuongtien_id = ld.phuongtien_id;
        this.ledger_id = ld.ledger_id;
        this.thuc_xuat = ld.thuc_xuat;
        this.thuc_xuat_tk = ld.thuc_xuat_tk;
        this.soluong = ld.soluong;
        this.thuc_nhap = ld.thuc_nhap;
        this.phai_nhap = ld.phai_nhap;
        this.thanhtien = ld.thanhtien;
        this.haohut_sl = ld.haohut_sl;
        this.nl_gio = ld.nl_gio;
        this.nl_km = ld.nl_km;
        this.sscd_nvdx = ld.sscd_nvdx;
        this.soluong_px = ld.soluong_px;
    }
}
