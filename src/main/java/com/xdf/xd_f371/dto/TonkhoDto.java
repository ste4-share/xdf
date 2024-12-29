package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TonkhoDto {
    private int petro_id;
    private String maxd;
    private String tenxd;
    private String loai;
    private Long tdk_nvdx;
    private Long tdk_sscd;
    private Long nhap_nvdx;
    private Long xuat_nvdx;
    private Long nvdx;
    private Long nhap_sscd;
    private Long xuat_sscd;
    private Long sscd;

    private String tdk_nvdx_str;
    private String tdk_sscd_str;
    private String tdk_total;

    private String nhap_nvdx_str;
    private String xuat_nvdx_str;
    private String nhap_sscd_str;
    private String xuat_sscd_str;

    private String tck_nvdx_str;
    private String tck_sscd_str;
    private String tck_total;

    private String nvdx_str;
    private String sscd_str;
    private String total;

    public TonkhoDto(int petro_id, String maxd, String tenxd, String loai, Long tdk_nvdx, Long tdk_sscd,
                     String nhap_nvdx, String xuat_nvdx, String nvdx, String nhap_sscd, String xuat_sscd, String sscd) {
        this.petro_id = petro_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.loai = loai;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.nhap_nvdx = Long.parseLong(nhap_nvdx);
        this.xuat_nvdx = Long.parseLong(xuat_nvdx);
        this.nvdx = Long.parseLong(nvdx);
        this.nhap_sscd = Long.parseLong(nhap_sscd);
        this.xuat_sscd = Long.parseLong(xuat_sscd);
        this.sscd = Long.parseLong(sscd);
        this.tdk_nvdx_str = TextToNumber.textToNum(String.valueOf(tdk_nvdx));
        this.tdk_sscd_str = TextToNumber.textToNum(String.valueOf(tdk_sscd));
        this.tdk_total = TextToNumber.textToNum(String.valueOf(tdk_sscd+tdk_nvdx));
        this.nhap_nvdx_str = TextToNumber.textToNum(nhap_nvdx);
        this.xuat_nvdx_str = TextToNumber.textToNum(xuat_nvdx);
        this.nvdx_str = TextToNumber.textToNum(nvdx);
        this.nhap_sscd_str = TextToNumber.textToNum(nhap_sscd);
        this.xuat_sscd_str = TextToNumber.textToNum(xuat_sscd);
        this.sscd_str = TextToNumber.textToNum(sscd);
        this.tck_nvdx_str = TextToNumber.textToNum(nvdx);
        this.tck_sscd_str = TextToNumber.textToNum(sscd);
        this.tck_total = TextToNumber.textToNum(nvdx+sscd);
    }

    public TonkhoDto() {
    }
}
