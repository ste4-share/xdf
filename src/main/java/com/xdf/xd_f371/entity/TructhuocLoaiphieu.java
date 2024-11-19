package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TructhuocLoaiphieu {
    private int id;
    private int tructhuoc_id;
    private int loaiphieu_id;

    public TructhuocLoaiphieu(int id, int tructhuoc_id, int loaiphieu_id) {
        this.id = id;
        this.tructhuoc_id = tructhuoc_id;
        this.loaiphieu_id = loaiphieu_id;
    }
}
