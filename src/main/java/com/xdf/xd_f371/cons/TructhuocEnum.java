package com.xdf.xd_f371.cons;

public enum TructhuocEnum {
    NV("TT_XM"),
    HH("HH");

    public final String name;

    TructhuocEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
