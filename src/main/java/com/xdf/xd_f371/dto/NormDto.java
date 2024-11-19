package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NormDto {
    private int pt_id;
    private String namePt;
    private String typePt;
    private int quantity;
    private int dm_md_gio;
    private int dm_tk_gio;
    private int dm_xm_gio;
    private int dm_xm_km;
    private String createtime;
    private String status;
    private int loaiphuongtien_id;
    private int quarter_id;

    public NormDto(int pt_id, String namePt, String typePt, int quantity, int dm_md_gio, int dm_tk_gio, int dm_xm_gio, int dm_xm_km, String createtime, int loaiphuongtien_id,String status) {
        this.pt_id = pt_id;
        this.namePt = namePt;
        this.typePt = typePt;
        this.quantity = quantity;
        this.dm_md_gio = dm_md_gio;
        this.dm_tk_gio = dm_tk_gio;
        this.dm_xm_gio = dm_xm_gio;
        this.dm_xm_km = dm_xm_km;
        this.createtime = createtime;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.status = status;
    }
}
