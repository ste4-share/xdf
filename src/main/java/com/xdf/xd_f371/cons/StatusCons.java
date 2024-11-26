package com.xdf.xd_f371.cons;

public enum StatusCons {
    ACTIVED("ACTIVE");

    public final String name;

    StatusCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
