package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhuongTien {
    private int id;
    private String name;
    private String type;
    private int han_muc;
    private int dm_tk;
    private int dm_md;
    private int dm_xm_km;
    private int dm_xm_gio;
    private int quantity;
    private int nguonnx_id;
    private int loaiphuongtien_id;
    private String status;

    public PhuongTien() {
    }

}
