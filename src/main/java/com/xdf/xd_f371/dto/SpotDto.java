package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpotDto {
    private int lxd_id;
    private String maxd;
    private String tenxd;
    private String chungloai;
    private int tdk_nvdx;
    private int tdk_sscd;
    private int nvdx;
    private int sscd;

    private String tdk_nvdx_str;
    private String tdk_sscd_str;
    private String tdk_total;

    private int nhap_nvdx;
    private int xuat_nvdx;
    private int nhap_sscd;
    private int xuat_sscd;

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

    public SpotDto(int lxd_id, String maxd,String chungloai, String tenxd, int nvdx, int sscd) {
        this.lxd_id = lxd_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.nvdx = nvdx;
        this.chungloai = chungloai;
        this.sscd = sscd;
        this.nvdx_str = TextToNumber.textToNum(String.valueOf(nvdx));
        this.sscd_str = TextToNumber.textToNum(String.valueOf(sscd));
        this.total = TextToNumber.textToNum(String.valueOf(sscd+nvdx));
    }

    public SpotDto(int lxd_id, String maxd, String tenxd,String chungloai,int tdk_nvdx, int tdk_sscd, int nhap_nvdx,int xuat_nvdx, int nhap_sscd, int xuat_sscd) {
        this.lxd_id = lxd_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.chungloai = chungloai;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.nhap_nvdx = nhap_nvdx;
        this.xuat_nvdx = xuat_nvdx;
        this.nhap_sscd = nhap_sscd;
        this.xuat_sscd = xuat_sscd;
        this.tdk_nvdx_str = TextToNumber.textToNum(String.valueOf(tdk_nvdx));
        this.tdk_sscd_str = TextToNumber.textToNum(String.valueOf(tdk_sscd));
        this.tdk_total = TextToNumber.textToNum(String.valueOf(tdk_sscd+tdk_nvdx));
        this.nhap_nvdx_str = TextToNumber.textToNum(String.valueOf(nhap_nvdx));
        this.xuat_nvdx_str = TextToNumber.textToNum(String.valueOf(xuat_nvdx));
        this.xuat_nvdx_str = TextToNumber.textToNum(String.valueOf(xuat_nvdx));
        this.nhap_sscd_str = TextToNumber.textToNum(String.valueOf(nhap_sscd));
        this.tck_nvdx_str = TextToNumber.textToNum(String.valueOf(tdk_nvdx + nhap_nvdx - xuat_nvdx));
        this.tck_sscd_str = TextToNumber.textToNum(String.valueOf(tdk_sscd + nhap_sscd - xuat_sscd));
        this.tck_total = TextToNumber.textToNum(String.valueOf((tdk_sscd +tdk_nvdx + nhap_nvdx+nhap_sscd) - (xuat_sscd+xuat_nvdx)));
    }
}
