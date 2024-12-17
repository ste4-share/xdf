package com.xdf.xd_f371.cons;

public enum LoaiNVCons {
    NV_BAY("NV_BAY"),
    NV_KHAC("KHAC"),
    HAOHUT("HAOHUT");

    public final String name;

    LoaiNVCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
