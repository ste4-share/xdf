package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Tcn {
    private int id;
    private int loaiphieu_id;
    private String name;
    private int concert;
    private String status;
    private String lp;

    public Tcn() {
    }

    public Tcn(int id, int loaiphieu_id, String name, int concert, String status) {
        this.id = id;
        this.loaiphieu_id = loaiphieu_id;
        this.name = name;
        this.concert = concert;
        this.status = status;
    }
}
