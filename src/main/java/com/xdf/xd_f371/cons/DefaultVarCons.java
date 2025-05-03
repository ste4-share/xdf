package com.xdf.xd_f371.cons;

public enum DefaultVarCons {
    GIO_HD("00:00:00"),
    IP("127.0.0.1"),
    PORT("5432");

    public final String name;

    DefaultVarCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
