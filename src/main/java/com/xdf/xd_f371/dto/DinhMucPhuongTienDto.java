package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DinhMucPhuongTienDto {
    private int dm_id;
    private int phuongtien_id;
    private int loaiphuongtien_id;
    private double dm_md_gio;
    private double dm_tk_gio;
    private double dm_xm_gio;
    private double dm_xm_km;
    private String nameDv;
    private String name_pt;
    private double quantity;
    private String typeName;
    private String type;
    private int nnx_id;

    public DinhMucPhuongTienDto(int dm_id, int phuongtien_id, int loaiphuongtien_id, double dm_md_gio,
                                double dm_tk_gio, double dm_xm_gio, double dm_xm_km, String nameDv, String name_pt, double quantity, String typeName, String type) {
        this.dm_id = dm_id;
        this.phuongtien_id = phuongtien_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.dm_md_gio = dm_md_gio;
        this.dm_tk_gio = dm_tk_gio;
        this.dm_xm_gio = dm_xm_gio;
        this.dm_xm_km = dm_xm_km;
        this.nameDv = nameDv;
        this.name_pt = name_pt;
        this.quantity = quantity;
        this.typeName = typeName;
        this.type = type;
    }
}
