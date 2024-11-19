package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PetroleumType {
    private String name;
    private String type;
    private String r_type;

    public PetroleumType(String name, String type, String r_type) {
        this.name = name;
        this.type = type;
        this.r_type = r_type;
    }
}
