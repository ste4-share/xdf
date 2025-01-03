package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InventoryDto {
    private int petro_id;
    private int don_gia;
    private Long pre_nvdx;
    private Long pre_sscd;
    public InventoryDto(int petro_id, int don_gia, Long pre_nvdx, Long pre_sscd) {
        this.petro_id = petro_id;
        this.don_gia = don_gia;
        this.pre_nvdx = pre_nvdx;
        this.pre_sscd = pre_sscd;
    }
    public InventoryDto(int petro_id, Long pre_nvdx, Long pre_sscd) {
        this.petro_id = petro_id;
        this.pre_nvdx = pre_nvdx;
        this.pre_sscd = pre_sscd;
    }
}