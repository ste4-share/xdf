package com.xdf.xd_f371.cons;

public enum TypeCons {
    TREN_KHONG("TK"),
    MAT_DAT("MD");

    public final String name;

    TypeCons(String name) {
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
