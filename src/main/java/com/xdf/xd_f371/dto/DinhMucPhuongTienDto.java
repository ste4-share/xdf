package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class DinhMucPhuongTienDto {
    private int dm_id;
    private int quarter_id;
    private int phuongtien_id;
    private int loaiphuongtien_id;
    private int dm_md_gio;
    private int dm_tk_gio;
    private int dm_xm_gio;
    private int dm_xm_km;
    private String name;
    private LocalDate start_date;
    private LocalDate end_date;
    private String name_pt;
    private int quantity;
    private String typeName;
    private String type;
    private int nnx_id;
    public DinhMucPhuongTienDto(int dm_id, int quarter_id, int phuongtien_id, int loaiphuongtien_id, int dm_md_gio, int dm_tk_gio, int dm_xm_gio, int dm_xm_km, String name, LocalDate start_date, LocalDate end_date, String name_pt, int quantity, String typeName, String type) {
        this.dm_id = dm_id;
        this.quarter_id = quarter_id;
        this.phuongtien_id = phuongtien_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.dm_md_gio = dm_md_gio;
        this.dm_tk_gio = dm_tk_gio;
        this.dm_xm_gio = dm_xm_gio;
        this.dm_xm_km = dm_xm_km;
        this.name = name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.name_pt = name_pt;
        this.quantity = quantity;
        this.typeName = typeName;
        this.type = type;
    }
}
