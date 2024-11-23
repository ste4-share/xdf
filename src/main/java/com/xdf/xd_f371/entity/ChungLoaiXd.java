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

    @OneToMany(mappedBy = "chungLoaiXd", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LoaiXangDau> loaiXangDau;
}
