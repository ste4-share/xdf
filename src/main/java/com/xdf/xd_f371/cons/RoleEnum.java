package com.xdf.xd_f371.cons;

public enum RoleEnum {
    ADMIN("ADMIN"),
    USER("USER");

    public final String name;

    RoleEnum(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
