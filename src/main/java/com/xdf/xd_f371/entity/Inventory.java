package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.math3.stat.descriptive.summary.Product;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    @JoinColumn(name = "petro_id",referencedColumnName = "id", insertable = false, updatable = false)
    private LoaiXangDau loaiXangDau;
}
