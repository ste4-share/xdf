package com.xdf.xd_f371.model;

public enum LoaiGB {
    TK("TK"),
    MD("MD");

    public final String name;

    LoaiGB(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
