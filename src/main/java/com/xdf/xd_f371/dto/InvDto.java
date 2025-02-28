package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InvDto {
    private int petro_id;
    private String maxd;
    private String tenxd;
    private int petroleum_type_id;
    private double don_gia;
    private double nhap_nvdx;
    private double nhap_sscd;
    private double xuat_nvdx;
    private double xuat_sscd;
    private LocalDate sd;
    private LocalDate ed;

    public InvDto(int petro_id, String maxd, String tenxd, int petroleum_type_id, double don_gia, double nhap_nvdx, double nhap_sscd, double xuat_nvdx, double xuat_sscd,LocalDate sd,LocalDate ed) {
        this.petro_id = petro_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.petroleum_type_id = petroleum_type_id;
        this.don_gia = don_gia;
        this.nhap_nvdx = nhap_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_nvdx = xuat_nvdx;
        this.xuat_sscd = xuat_sscd;
        this.sd = sd;
        this.ed = ed;
    }

    public InvDto(int petro_id, double don_gia, double nhap_nvdx, double nhap_sscd, double xuat_nvdx, double xuat_sscd, LocalDate sd, LocalDate ed) {
        this.petro_id = petro_id;
        this.don_gia = don_gia;
        this.nhap_nvdx = nhap_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_nvdx = xuat_nvdx;
        this.xuat_sscd = xuat_sscd;
        this.sd = sd;
        this.ed = ed;
    }
}
