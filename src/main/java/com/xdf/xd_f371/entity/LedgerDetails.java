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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "ma_xd")
    private String ma_xd;
    @Column(name = "ten_xd")
    private String ten_xd;
    @Column(name = "chung_loai")
    private String chung_loai;
    @Column(name = "chat_luong")
    private String chat_luong;
    @Column(name = "phai_xuat")
    private int phai_xuat;
    @Column(name = "nhiet_do_tt")
    @NotNull
    private double nhiet_do_tt;
    @Column(name = "ty_trong")
    @NotNull
    private double ty_trong;
    @Column(name = "he_so_vcf")
    @NotNull
    private int he_so_vcf;
    @Column(name = "don_gia")
    @NotNull
    private int don_gia;
    @Column(name = "loaixd_id")
    private int loaixd_id;
    @Column(name = "phuongtien_id")
    private int phuongtien_id;
    @Column(name = "ledger_id")
    private int ledger_id;
    @Column(name = "thuc_xuat")
    private int thuc_xuat;
    @Column(name = "thuc_xuat_tk")
    private int thuc_xuat_tk;
    @Column(name = "so_luong")
    @NotNull
    @Min(value = 0)
    @Positive
    private int soluong;
    @Column(name = "thuc_nhap")
    private int thuc_nhap;
    @Column(name = "phai_nhap")
    private int phai_nhap;
    @Column(name = "thanhtien")
    private Long thanhtien;
    @Column(name = "haohut_sl")
    private int haohut_sl;
    @Column(name = "nl_gio")
    @NotNull
    private Long nl_gio;
    @Column(name = "nl_km")
    @NotNull
    private Long nl_km;
    @Column(name = "so_luong_px")
    @NotNull(message = "Phai xuat can not be null")
    @Min(value = 0)
    @Positive
    private Long soluong_px;
    @Column(insertable = false, updatable = false)
    private String thanhtien_str;
    @Column(insertable = false, updatable = false)
    private String thucxuat_str;
    @Column(insertable = false, updatable = false)
    private String phaixuat_str;
    @Column(insertable = false, updatable = false)
    private String dongia_str;


    @ManyToOne
    @JoinColumn(name = "ledger_id", nullable = false,insertable = false,updatable = false)
    private Ledger ledger;
}
