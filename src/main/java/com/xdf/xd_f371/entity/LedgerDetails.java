package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.postgresql.util.PGInterval;
@Entity
@Table(name = "ledger_details")
@Getter
@Setter
@NoArgsConstructor
public class LedgerDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "dvi")
    private String dvi;
    @Column(name = "dvvc")
    private String dvvc;
    @Column(name = "ngay")
    private String ngay;
    @Column(name = "ma_xd")
    private String ma_xd;
    @Column(name = "ten_xd")
    private String ten_xd;
    @Column(name = "chung_loai")
    private String chung_loai;
    @Column(name = "loai_phieu")
    private String loai_phieu;
    @Column(name = "so")
    private String so;
    @Column(name = "theo_lenh_so")
    private String theo_lenh_so;
    @Column(name = "nhiem_vu")
    private String nhiem_vu;
    @Column(name = "nguoi_nhan_hang")
    private String nguoi_nhan_hang;
    @Column(name = "so_xe")
    private String so_xe;
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
    @Column(name = "thanh_tien")
    private Long thanh_tien;
    @Column(name = "so_km")
    private int so_km;
    @Column(name = "denngay")
    private String denngay;

    @Column(name = "loaixd_id")
    private int loaixd_id;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "tcn_id")
    private int tcn_id;
    @Column(name = "phuongtien_id")
    private int phuongtien_id;
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "ledger_id")
    private int ledger_id;
    @Column(name = "import_unit_id")
    private int import_unit_id;
    @Column(name = "export_unit_id")
    private int export_unit_id;
    @Column(name = "loaigiobay")
    private String loaigiobay;
    @Column(name = "thuc_xuat")
    private int thuc_xuat;
    @Column(name = "thuc_xuat_tk")
    private int thuc_xuat_tk;
    @Column(name = "dur_text_md2")
    private PGInterval dur_text_md2;
    @Column(name = "dur_text_tk2")
    private PGInterval dur_text_tk2;
    @Column(name = "nhiemvu_hanmuc_id")
    private int nhiemvu_hanmuc_id;
    @Column(name = "so_luong")
    private int soluong;
    @Column(name = "thuc_nhap")
    private int thuc_nhap;
    @Column(name = "phai_nhap")
    private int phai_nhap;

    @ManyToOne
    @JoinColumn(name = "ledger_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Ledger ledger;
}
