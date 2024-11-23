package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Table(name = "loaixd2")
@Entity
@Getter
@Setter
public class LoaiXangDau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "maxd")
    private String maxd;
    @Column(name = "tenxd")
    private String tenxd;
    @Column(name = "chungloai")
    private String chungloai;
    @Column(name = "ut")
    private int priority;
    @Column(name = "type")
    private String type;
    @Column(name = "r_type")
    private String rType;
    @Column(name = "ut2")
    private int ut2;
    @Column(name = "petroleum_type_id")
    private int petroleum_type_id;

    public LoaiXangDau() {
    }

}
