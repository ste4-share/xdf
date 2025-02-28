package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    private int petro_id;
    private Long ledger_id;
    private double don_gia;
    private double pre_nvdx;
    private double nhap_nvdx;
    private double xuat_nvdx;
    private double pre_sscd;
    private double nhap_sscd;
    private double xuat_sscd;

    public InventoryDto(int petro_id, Long ledger_id, double don_gia, double nhap_nvdx, double xuat_nvdx, double nhap_sscd, double xuat_sscd) {
        this.petro_id = petro_id;
        this.ledger_id = ledger_id;
        this.don_gia = don_gia;
        this.nhap_nvdx = nhap_nvdx;
        this.xuat_nvdx = xuat_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_sscd = xuat_sscd;
    }
}