package com.xdf.xd_f371.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Norm {
    private int id;
    private int dm_md_gio;
    private int dm_tk_gio;
    private int dm_xm_gio;
    private int dm_xm_km;
    private int phuongtien_id;
    private int quarter_id;

    public Norm(int dm_md_gio, int dm_tk_gio, int dm_xm_gio, int dm_xm_km, int phuongtien_id, int quarter_id) {
        this.dm_md_gio = dm_md_gio;
        this.dm_tk_gio = dm_tk_gio;
        this.dm_xm_gio = dm_xm_gio;
        this.dm_xm_km = dm_xm_km;
        this.phuongtien_id = phuongtien_id;
        this.quarter_id = quarter_id;
    }
}
