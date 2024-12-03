package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "category")
@Table
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "header_lv1")
    private String header_lv1;
    @Column(name = "header_lv2")
    private String header_lv2;
    @Column(name = "header_lv3")
    private String header_lv3;
    @Column(name = "type_title")
    private String type_title;
    @Column(name = "tructhuoc_id")
    private int tructhuoc_id;
    @Column(name = "code")
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
