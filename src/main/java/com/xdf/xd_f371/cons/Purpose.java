package com.xdf.xd_f371.cons;

public enum Purpose {
    SSCD("SSCD"),
    NVDX("NVDX");

    public final String name;

    Purpose(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
