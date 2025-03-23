package com.xdf.xd_f371.cons;

public enum LoaiXDCons {
    NHIENLIEU("NHIEN_LIEU"),
    XANG("XANG_O_TO"),
    DIEZEL("DIEZEL"),
    DAUHACAP("HA_CAP"),
    DMN("DMN"),
    TK_MN("TK_MN"),
    TK_DTL("TK_DTL"),
    TK_DM("TK_DM"),
    TK_DK("TK_DK"),
    TK_DDC("TK_DDC"),
    MD_MGMS("MD_MGMS"),
    MD_DTD("MD_DTD"),
    MD_DK("MD_DK"),
    MD_DCOTO("MD_DCOTO"),
    DAUBAY("DAU_BAY");

    public final String name;

    LoaiXDCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
