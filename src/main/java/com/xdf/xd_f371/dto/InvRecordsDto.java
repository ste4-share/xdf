package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvRecordsDto {
    private Long loaixd_id;
    private double price;
    private double nvdx;
    private double sscd;

    public InvRecordsDto(Long loaixd_id, double price, double nvdx, double sscd) {
        this.loaixd_id = loaixd_id;
        this.price = price;
        this.nvdx = nvdx;
        this.sscd = sscd;
    }

    public InvRecordsDto(Long loaixd_id, double nvdx, double sscd) {
        this.loaixd_id = loaixd_id;
        this.nvdx = nvdx;
        this.sscd = sscd;
    }
}
