package com.xdf.xd_f371.cons;

public enum LoaiXDCons {
    XANG("XANG_O_TO"),
    DIEZEL("DIEZEL"),
    DAUHACAP("HA_CAP"),
    DAUBAY("DAU_BAY");

    public final String name;

    LoaiXDCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
