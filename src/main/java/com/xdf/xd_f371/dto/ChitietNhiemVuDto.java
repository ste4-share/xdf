package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChitietNhiemVuDto {
    private int nv_id;
    private int ct_id;
    private String nv_name;
    private String ctnv;

    public ChitietNhiemVuDto(int nv_id, int ct_id, String nv_name, String ctnv) {
        this.nv_id = nv_id;
        this.ct_id = ct_id;
        this.nv_name = nv_name;
        this.ctnv = ctnv;
    }
}
