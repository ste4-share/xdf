package com.xdf.xd_f371.cons;

public enum ConfigCons {
    ROOT_ID("ROOT_ID"),
    START_DATE("REF_START_DATE"),
    END_DATE("REF_END_DATE"),
    NVDX("DT cho NV đột xuất"),
    SSCD("DT SSCD"),
    CONG("Cộng"),
    NBN("NBN"),
    XBN("XBN"),
    REPORT_PATH("PATH_EXPORT_REPORTERS"),
    FORMAT_DATE("dd/MM/yyyy");

    public final String name;

    ConfigCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
