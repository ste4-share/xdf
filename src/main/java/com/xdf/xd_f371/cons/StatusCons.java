package com.xdf.xd_f371.cons;

public enum StatusCons {
    ACTIVED("ACTIVE"),
    IN_ACTIVED("IN_ACTIVE"),
    DONE("DONE"),
    RECORDING("RECORDING"),
    GOOD("GOOD"),
    NORMAL_STATUS("NORMAL"),
    ROOT_STATUS("ROOT");

    public final String name;

    StatusCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
