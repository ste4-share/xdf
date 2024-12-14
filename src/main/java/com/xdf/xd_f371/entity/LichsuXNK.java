package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "lichsuxnk")
@Getter
@Setter
public class LichsuXNK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ten_xd")
    private String ten_xd;
    @Column(name = "loai_phieu")
    private String loai_phieu;
    @Column(name = "tontruoc")
    private int tontruoc;
    @Column(name = "soluong")
    private int soluong;
    @Column(name = "tonsau")
    private int tonsau;
    @Column(name = "mucgia")
    private String mucgia;
    @Column(name = "createtime")
    private String createTime;

    private String tontruoc_str;
    private String soluong_str;
    private String tonsau_str;

    public LichsuXNK() {
    }
}
