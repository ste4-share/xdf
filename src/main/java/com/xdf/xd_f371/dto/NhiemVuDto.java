package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NhiemVuDto {
    private int team_id;
    private int lnv_id;
    private int nv_id;
    private int ctnv_id;
    private int priority;
    private String ten_nv;
    private String chitiet;
    private String khoi;
    private String ten_loai_nv;
}
