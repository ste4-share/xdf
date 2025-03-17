package com.xdf.xd_f371.cons;

public enum LoaiPhieuCons {
    PHIEU_NHAP("NHAP"),
    X("X"),
    N("N"),
    PHIEU_XUAT("XUAT");

    public final String name;

    LoaiPhieuCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
