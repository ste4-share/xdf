package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
public class LoaiXangDau {
    private int id;
    private String maxd;
    private String tenxd;
    private String chungloai;
    private Date createtime;
    private String status;
    private String type;
    private String Rtype;

    public LoaiXangDau() {
    }

}
