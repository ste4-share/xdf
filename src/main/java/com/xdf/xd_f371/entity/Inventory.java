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
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "tdk_nvdx")
    private int tdk_nvdx;
    @Column(name = "tdk_sscd")
    private int tdk_sscd;
    @Column(name = "nhap_nvdx")
    private int nhap_nvdx;
    @Column(name = "nhap_sscd")
    private int nhap_sscd;
    @Column(name = "xuat_nvdx")
    private int xuat_nvdx;
    @Column(name = "xuat_sscd")
    private int xuat_sscd;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private int price;
    @Column(name = "create_at")
    private LocalDate create_at;

    @ManyToOne
    @JoinColumn(name = "petro_id",referencedColumnName = "id", insertable = false, updatable = false)
    private LoaiXangDau loaiXangDau;

    public Inventory(int petro_id, int quarter_id, int tdk_nvdx, int tdk_sscd, int nhap_nvdx, int nhap_sscd, int xuat_nvdx, int xuat_sscd, String status, int price) {
        this.petro_id = petro_id;
        this.quarter_id = quarter_id;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.nhap_nvdx = nhap_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_nvdx = xuat_nvdx;
        this.xuat_sscd = xuat_sscd;
        this.status = status;
        this.price = price;
    }
}
