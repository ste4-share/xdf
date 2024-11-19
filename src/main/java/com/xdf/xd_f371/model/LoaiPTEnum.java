package com.xdf.xd_f371.model;

public enum LoaiPTEnum {
    MAYBAY_CHIENDAU("MB-CD"),
    MAYBAY("MB"),
    TRUCTHANG("MB-TT"),
    CHAY("CHAY"),
    MAYCHAY("MAY_CHAY"),
    XECHAY("XE_CHAY"),
    XE_CHAY_XANG("XE_CHAY_XANG"),
    MAY_CHAY_XANG("MAY_CHAY_XANG"),
    XE_CHAY_DIEZEL("XE_CHAY_DIEZEL"),
    XE("XE"),
    MAY("MAY"),
    MAYBAY_a("MAYBAY"),
    MAY_CHAY_DIEZEL("MAY_CHAY_DIEZEL");

    public final String name;

    LoaiPTEnum(String name) {
        this.name = name;
    }

    public String getNameVehicle(){
        return name;
    }
}
