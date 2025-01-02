package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LoaiXdLedgerDto {
    private int xd_id;
    private String tenxd;
    private Long nhap_nvdx;
    private Long nhap_sscd;
    private Long xuat_nvdx;
    private Long xuat_sscd;
    private int price;
    private LocalDate sd;
    private LocalDate ed;

    public LoaiXdLedgerDto(int xd_id, String tenxd, Long nhap_nvdx, Long nhap_sscd, Long xuat_nvdx, Long xuat_sscd, int price, LocalDate sd, LocalDate ed) {
        this.xd_id = xd_id;
        this.tenxd = tenxd;
        this.nhap_nvdx = nhap_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_nvdx = xuat_nvdx;
        this.xuat_sscd = xuat_sscd;
        this.price = price;
        this.sd = sd;
        this.ed = ed;
    }
}
