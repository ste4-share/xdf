package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
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
}
