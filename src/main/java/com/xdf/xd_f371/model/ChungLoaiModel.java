package com.xdf.xd_f371.model;

public enum ChungLoaiModel {
    SSCD_a("SSCD"),
    NVDX_a("NVDX"),
    TDK_a("TDK"),
    TCK_a("TCK");
    public final String name;
    ChungLoaiModel(String name) {
        this.name = name;
    }
    public String getNameChungloai(){
        return name;
    }
}
