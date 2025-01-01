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
    private int tdk_nvdx=0;
    @Column(name = "tdk_sscd")
    private int tdk_sscd=0;
    @Column(name = "nhap_nvdx")
    private int nhap_nvdx=0;
    @Column(name = "nhap_sscd")
    private int nhap_sscd=0;
    @Column(name = "xuat_nvdx")
    private int xuat_nvdx=0;
    @Column(name = "xuat_sscd")
    private int xuat_sscd=0;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private int price=0;
    @Column(name = "create_at", insertable = false,updatable = false)
    private LocalDate create_at;
    @Column(name = "sd")
    private LocalDate sd;
    @Column(name = "ed")
    private LocalDate ed;

    @ManyToOne
    @JoinColumn(name = "petro_id",referencedColumnName = "id", insertable = false, updatable = false)
    private LoaiXangDau loaiXangDau;

    public Inventory(int petro_id, int tdk_nvdx, int tdk_sscd, int nhap_nvdx, int nhap_sscd, int xuat_nvdx, int xuat_sscd, String status, int price,LocalDate sd, LocalDate ed) {
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

    public Inventory(int petro_id, int quarter_id, String status) {
        this.petro_id = petro_id;
        this.status = status;
    }
}
