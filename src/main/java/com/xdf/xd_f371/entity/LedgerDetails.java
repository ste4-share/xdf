package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
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
    private double nhiet_do_tt;
    @Column(name = "ty_trong")
    private double ty_trong;
    @Column(name = "he_so_vcf")
    private int he_so_vcf;
    @Column(name = "don_gia")
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
    @Column(name = "nhiemvu_hanmuc_id")
    private int nhiemvu_hanmuc_id;
    @Column(name = "so_luong")
    private int soluong;
    @Column(name = "thuc_nhap")
    private int thuc_nhap;
    @Column(name = "phai_nhap")
    private int phai_nhap;
    @Column(name = "thanhtien")
    private int thanhtien;
    @Column(name = "haohut_sl")
    private int haohut_sl;
    @Column(name = "nl_gio")
    private Long nl_gio;
    @Column(name = "nl_km")
    private Long nl_km;

    @Column(insertable = false, updatable = false)
    private String thanhtien_str;
    @Column(insertable = false, updatable = false)
    private String thucxuat_str;
    @Column(insertable = false, updatable = false)
    private String phaixuat_str;
    @Column(insertable = false, updatable = false)
    private String dongia_str;


    @ManyToOne
    @JoinColumn(name = "ledger_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ledger ledger;
}
