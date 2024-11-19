package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoaiPhuongTien {
    private int id;
    private String typeName;
    private String type;

    public LoaiPhuongTien() {
    }

    public LoaiPhuongTien(String type, String typeName, int id) {
        this.type = type;
        this.typeName = typeName;
        this.id = id;
    }
}
