package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
public class TonkhoDto {
    private int petro_id;
    private String maxd;
    private String tenxd;
    private String loai;
    private double tdk_nvdx;
    private double tdk_sscd;
    private double nhap_nvdx;
    private double xuat_nvdx;
    private double nvdx;
    private double nhap_sscd;
    private double xuat_sscd;
    private double sscd;

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

    public TonkhoDto(int petro_id, String maxd, String tenxd, String loai, double tdk_nvdx, double tdk_sscd,
                     double nhap_nvdx, double xuat_nvdx, double nvdx, double nhap_sscd, double xuat_sscd, double sscd) {
        this.petro_id = petro_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.loai = loai;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.nhap_nvdx = nhap_nvdx;
        this.xuat_nvdx = xuat_nvdx;
        this.nvdx = nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_sscd = xuat_sscd;
        this.sscd = sscd;
        this.tdk_nvdx_str = TextToNumber.textToNum_2digits(tdk_nvdx);
        this.tdk_sscd_str = TextToNumber.textToNum_2digits(tdk_sscd);
        this.tdk_total = TextToNumber.textToNum_2digits(tdk_sscd+tdk_nvdx);
        this.nhap_nvdx_str = TextToNumber.textToNum_2digits(nhap_nvdx);
        this.xuat_nvdx_str = TextToNumber.textToNum_2digits(xuat_nvdx);
        this.nvdx_str = TextToNumber.textToNum_2digits(nvdx);
        this.nhap_sscd_str = TextToNumber.textToNum_2digits(nhap_sscd);
        this.xuat_sscd_str = TextToNumber.textToNum_2digits(xuat_sscd);
        this.sscd_str = TextToNumber.textToNum_2digits(sscd);
        this.tck_nvdx_str = TextToNumber.textToNum_2digits(tdk_nvdx+nvdx);
        this.tck_sscd_str = TextToNumber.textToNum_2digits(tdk_sscd+sscd);
        this.tck_total = TextToNumber.textToNum_2digits(tdk_nvdx+nvdx+tdk_sscd+sscd);
    }

    public TonkhoDto() {
    }
}
