package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NguonNx {
    private int id;
    private int tructhuoc_id;
    private String ten;
    private String tructhuoc;
    private String loaiphieu;
    private String createtime;

    public NguonNx() {
    }

    public NguonNx(String ten) {
        this.ten = ten;
    }

    public NguonNx(int id, String ten, String createtime) {
        this.id = id;
        this.ten = ten;
        this.createtime = createtime;
    }

    public NguonNx(int id, String ten, String tructhuoc, String loaiphieu, String createtime) {
        this.id = id;
        this.ten = ten;
        this.tructhuoc = tructhuoc;
        this.loaiphieu = loaiphieu;
        this.createtime = createtime;
    }
}
