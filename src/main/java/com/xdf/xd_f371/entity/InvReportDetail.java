package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvReportDetail {
    private int id;
    private String loaixd;
    private int soluong;
    private String title_lv1;
    private String title_lv2;
    private String title_lv3;
    private String title_lxd_lv1;
    private String title_lxd_lv2;
    private String title_lxd_lv3;
    private int xd_id;
    private int title_id;
    private int quarter_id;

    public InvReportDetail() {
    }
}
