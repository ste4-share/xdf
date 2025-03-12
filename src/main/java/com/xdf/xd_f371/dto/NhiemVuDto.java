package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
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

    public NhiemVuDto(int team_id, int lnv_id, int nv_id, int ctnv_id, int priority, String ten_nv, String chitiet, String khoi, String ten_loai_nv) {
        this.team_id = team_id;
        this.lnv_id = lnv_id;
        this.nv_id = nv_id;
        this.ctnv_id = ctnv_id;
        this.priority = priority;
        this.ten_nv = ten_nv;
        this.chitiet = chitiet;
        this.khoi = khoi;
        this.ten_loai_nv = ten_loai_nv;
    }
}
