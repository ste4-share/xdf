package com.xdf.xd_f371.cons;

public enum LoaiXuat {
    X_K("Xuất đơn vị"),
    HH("Hao hụt"),
    NV("Nhiệm vụ");

    public final String name;

    LoaiXuat(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
