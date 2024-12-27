package com.xdf.xd_f371.cons;

public enum ConfigCons {
    ROOT_ID("ROOT_ID");

    public final String name;

    ConfigCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
