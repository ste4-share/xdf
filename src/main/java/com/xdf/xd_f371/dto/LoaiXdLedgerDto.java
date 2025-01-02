package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class LoaiXdLedgerDto {
    private int xd_id;
    private String tenxd;
    private Long nhap_nvdx;
    private Long nhap_sscd;
    private Long xuat_nvdx;
    private Long xuat_sscd;
    private Long price;
    private LocalDate sd;
    private LocalDate ed;
}
