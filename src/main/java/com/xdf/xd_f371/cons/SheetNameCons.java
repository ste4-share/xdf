package com.xdf.xd_f371.cons;

public enum SheetNameCons {
    PHIEU_NHAP("phieu_nhap"),
    PHIEU_XUAT("phieu_xuat"),
    NXT("NXT"),
    NXT2("beta_nxt"),
    TTXD("TTXD"),
    NL_BAY_THEO_KH("NL_BAY_THEO_KH_BETA"),
    TTXD_XMT_BETA("ttxd_xmt_beta"),
    TTXD_XMT("TTXD_XMT"),
    LCV("luan_chuyenvon_data"),
    PT_TONKHO("PT_TONKHO");

    public final String name;

    SheetNameCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
