package com.xdf.xd_f371.model;

public enum LoaiPTEnum {
    XE("XE"),
    MAY("MAY"),
    MAYBAY("MAYBAY");

    public final String name;

    LoaiPTEnum(String name) {
        this.name = name;
    }

    public String getNameVehicle(){
        return name;
    }
}
