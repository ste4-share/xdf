package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "petro_id")
    private int petro_id;
    @Column(name = "tdk_nvdx")
    private double tdk_nvdx=0;
    @Column(name = "tdk_sscd")
    private double tdk_sscd=0;
    @Column(name = "nhap_nvdx")
    private double nhap_nvdx=0;
    @Column(name = "nhap_sscd")
    private double nhap_sscd=0;
    @Column(name = "xuat_nvdx")
    private double xuat_nvdx=0;
    @Column(name = "xuat_sscd")
    private double xuat_sscd=0;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private double price=0;
    @Column(name = "create_at", insertable = false,updatable = false)
    private LocalDate create_at;
    @Column(name = "sd")
    private LocalDate sd;
    @Column(name = "ed")
    private LocalDate ed;
    @Column(name = "dvi_id")
    private Integer dvi_id=0;

    @ManyToOne
    @JoinColumn(name = "petro_id",referencedColumnName = "id", insertable = false, updatable = false)
    private LoaiXangDau loaiXangDau;

    public Inventory(int petro_id, double tdk_nvdx, double tdk_sscd, double nhap_nvdx, double nhap_sscd, double xuat_nvdx, double xuat_sscd, String status, double price,
                     LocalDate sd, LocalDate ed) {
        this.petro_id = petro_id;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.nhap_nvdx = nhap_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_nvdx = xuat_nvdx;
        this.xuat_sscd = xuat_sscd;
        this.status = status;
        this.price = price;
        this.sd = sd;
        this.ed = ed;
    }

    public Inventory(int petro_id, String status) {
        this.petro_id = petro_id;
        this.status = status;
    }
}
