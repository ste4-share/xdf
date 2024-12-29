package com.xdf.xd_f371.cons;

public enum MucGiaEnum {
    OUT_STOCK_NVDX("OUT_OF_STOCK_NVDX"),
    OUT_STOCK_SSCD("OUT_OF_STOCK_SSCD"),
    OUT_STOCK_ALL("OUT_OF_STOCK_ALL"),
    SUPER_OUT_STOCK("SUPER_OUT_OF_STOCK"),
    IN_STOCK("IN_STOCK");

    public final String name;

    MucGiaEnum(String name) {
        this.name = name;
    }

    public String getStatus(){
        return name;
    }
}
