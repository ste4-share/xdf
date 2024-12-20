package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "chungloaixd")
@Getter
@Setter
@NoArgsConstructor
public class ChungLoaiXd {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "loai")
    private String loai;
    @Column(name = "chungloai")
    private String chungloai;
    @Column(name = "tinhchat")
    private String tinhchat;
    @Column(name = "code")
    private String code;
    @Column(name = "code2")
    private String code2;
    @Column(name = "priority_1")
    private int priority_1;
    @Column(name = "priority_2")
    private int priority_2;
    @Column(name = "priority_3")
    private int priority_3;

    @OneToMany(mappedBy = "chungLoaiXd", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LoaiXangDau> loaiXangDau;
}
