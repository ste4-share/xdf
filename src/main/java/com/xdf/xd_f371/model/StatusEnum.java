package com.xdf.xd_f371.model;

public enum StatusEnum {
    NORMAL_STATUS("NORMAL"),
    ROOT_STATUS("ROOT");

    public final String name;

    StatusEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
