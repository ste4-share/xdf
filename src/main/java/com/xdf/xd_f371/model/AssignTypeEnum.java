package com.xdf.xd_f371.model;

public enum AssignTypeEnum {
    NVDX("NVDX"),
    HH("HH"),
    NLTT_MD("NLTT_MD"),
    NLTT_TK("NLTT_TK"),
    NLTT_SUM("NLTT_SUM"),
    HD_MD("HD_MD"),
    HD_TK("HD_TK"),
    HD_SUM("HD_SUM"),
    NL("NL"),
    GB_MD("GB_MD"),
    GB_TK("GB_TK"),
    GB_SUM("GB_SUM"),
    SSCD("SSCD");

    public final String name;

    AssignTypeEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
