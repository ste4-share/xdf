package com.xdf.xd_f371.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Category {
    private int id;
    private String header_lv1;
    private String header_lv2;
    private String header_lv3;
    private String type_title;
    private int tructhuoc_id;
    private String code;

    public Category() {
    }

    public Category(int id, String header_lv1, String header_lv2, String header_lv3, String type_title, int tructhuoc_id, String code) {
        this.id = id;
        this.header_lv1 = header_lv1;
        this.header_lv2 = header_lv2;
        this.header_lv3 = header_lv3;
        this.type_title = type_title;
        this.tructhuoc_id = tructhuoc_id;
        this.code = code;
    }

    public Category(String header_lv1, String header_lv2, String header_lv3, String type_title, int tructhuoc_id, String code) {
        this.header_lv1 = header_lv1;
        this.header_lv2 = header_lv2;
        this.header_lv3 = header_lv3;
        this.type_title = type_title;
        this.tructhuoc_id = tructhuoc_id;
        this.code = code;
    }

}
