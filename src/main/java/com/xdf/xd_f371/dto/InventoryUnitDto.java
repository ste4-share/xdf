package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class InventoryUnitDto {
    private int petro_id;
    private String maxd;
    private String tenxd;
    private String loai;
    private double tdk_nvdx;
    private double tdk_sscd;
    private double pre_nvdx;
    private double pre_sscd;

    private String tdk_nvdx_str;
    private String tdk_sscd_str;
    private String tdk_str;
    private String pre_nvdx_str;
    private String pre_sscd_str;
    private String pre_str;
    private String datt_nvdx_str;
    private String datt_sscd_str;
    private String datt_str;

    public InventoryUnitDto(int petro_id, String maxd, String tenxd, String loai, double tdk_nvdx, double tdk_sscd, double pre_nvdx, double pre_sscd) {
        this.petro_id = petro_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.loai = loai;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.pre_nvdx = pre_nvdx;
        this.pre_sscd = pre_sscd;

        this.tdk_nvdx_str=TextToNumber.textToNum_2digits(tdk_nvdx);
        this.tdk_sscd_str=TextToNumber.textToNum_2digits(tdk_sscd);
        this.tdk_str = TextToNumber.textToNum_2digits(tdk_nvdx+tdk_sscd);
        this.pre_str = TextToNumber.textToNum_2digits(pre_sscd+pre_nvdx);
        this.pre_nvdx_str = TextToNumber.textToNum_2digits(pre_nvdx);
        this.pre_sscd_str = TextToNumber.textToNum_2digits(pre_sscd);
        this.datt_nvdx_str = TextToNumber.textToNum_2digits(pre_nvdx-tdk_nvdx);
        this.datt_sscd_str = TextToNumber.textToNum_2digits(pre_sscd-tdk_sscd);
        this.datt_str = TextToNumber.textToNum_2digits(pre_sscd+pre_nvdx-tdk_nvdx-tdk_sscd);
    }
}
