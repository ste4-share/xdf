package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvDto2 {
    private int xd_id;
    private double gia;
    private String loai;
    private String tenxd;
    private double sl_ton;

    public InvDto2(int xd_id, String loai, String tenxd, double sl_ton) {
        this.xd_id = xd_id;
        this.loai = loai;
        this.tenxd = tenxd;
        this.sl_ton = sl_ton;
    }

    public InvDto2(int xd_id,double gia, String tenxd, double sl_ton) {
        this.xd_id = xd_id;
        this.tenxd = tenxd;
        this.sl_ton = sl_ton;
        this.gia = gia;
    }
}
