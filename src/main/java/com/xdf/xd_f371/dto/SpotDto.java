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
    private Long nvdx;
    private Long sscd;

    private String tdk_nvdx_str;
    private String tdk_sscd_str;
    private String tdk_total;

    private Long nhap_nvdx;
    private Long xuat_nvdx;
    private Long nhap_sscd;
    private Long xuat_sscd;

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

    public SpotDto(int lxd_id, String maxd, String tenxd,String chungloai,Long tdk_nvdx, Long tdk_sscd, Long nhap_nvdx,Long xuat_nvdx,Long nvdx,
                   Long nhap_sscd, Long xuat_sscd,Long sscd) {
        this.lxd_id = lxd_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.chungloai = chungloai;
        this.nhap_nvdx = (long) nhap_nvdx;
        this.xuat_nvdx = (long) xuat_nvdx;
        this.nhap_sscd = (long) nhap_sscd;
        this.xuat_sscd = (long) xuat_sscd;

        this.tdk_nvdx_str = TextToNumber.textToNum(String.valueOf(tdk_nvdx));
        this.tdk_sscd_str = TextToNumber.textToNum(String.valueOf(tdk_sscd));
        this.tdk_total = TextToNumber.textToNum(String.valueOf(tdk_sscd+tdk_nvdx));
        this.nhap_nvdx_str = TextToNumber.textToNum(String.valueOf(nhap_nvdx));
        this.xuat_nvdx_str = TextToNumber.textToNum(String.valueOf(xuat_nvdx));
        this.nvdx_str = TextToNumber.textToNum(String.valueOf(nvdx));
        this.nhap_sscd_str = TextToNumber.textToNum(String.valueOf(nhap_sscd));
        this.xuat_sscd_str = TextToNumber.textToNum(String.valueOf(xuat_sscd));
        this.sscd_str = TextToNumber.textToNum(String.valueOf(sscd));

        this.tck_nvdx_str = TextToNumber.textToNum(String.valueOf(nvdx));
        this.tck_sscd_str = TextToNumber.textToNum(String.valueOf(sscd));
        this.tck_total = TextToNumber.textToNum(String.valueOf(nvdx+sscd));
    }
}
