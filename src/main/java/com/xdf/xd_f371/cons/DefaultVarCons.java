package com.xdf.xd_f371.cons;

public enum DefaultVarCons {
    GIO_HD("00:00:00");

    public final String name;

    DefaultVarCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
