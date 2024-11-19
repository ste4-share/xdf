package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NhiemVuDto {
    private int id;
    private int dv_id;
    private int team_id;
    private int lnv_id;
    private int nv_id;
    private int ctnv_id;
    private String ten_dvi;
    private String loai;
    private String loai_tt;
    private String ten_nv;
    private String ten_loai_nv;
    private String chitiet;

    public NhiemVuDto() {
    }

}
