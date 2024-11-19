package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoaiPhieu {
    private int id;
    private String type;

    public LoaiPhieu() {
    }

    public LoaiPhieu(int id, String type) {
        this.id = id;
        this.type = type;
    }
}
