package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvDto2 {
    private int xd_id;
    private int gia;
    private String loai;
    private String tenxd;
    private Long sl_ton;

    public InvDto2(int xd_id, String loai, String tenxd, Long sl_ton) {
        this.xd_id = xd_id;
        this.loai = loai;
        this.tenxd = tenxd;
        this.sl_ton = sl_ton;
    }

    public InvDto2(int xd_id,int gia, String tenxd, Long sl_ton) {
        this.xd_id = xd_id;
        this.tenxd = tenxd;
        this.sl_ton = sl_ton;
        this.gia = gia;
    }
}
