package com.xdf.xd_f371.cons;

public enum LoaiPTEnum {
    XE("XE"),
    MB_CD("MB-CD"),
    XE_CHAY_DIEZEL("XE_CHAY_DIEZEL"),
    XE_CHAY_XANG("XE_CHAY_XANG"),
    MAY_CHAY_DIEZEL("MAY_CHAY_DIEZEL"),
    MAY_CHAY_XANG("MAY_CHAY_XANG"),
    MB_TT("MB-TT"),
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
