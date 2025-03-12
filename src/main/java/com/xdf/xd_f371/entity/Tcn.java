package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tcn")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tcn {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "loaiphieu")
    private String loaiphieu;
    @Column(name = "name")
    private String name;
    @Column(name = "ma_tcn")
    private String ma_tcn;

    public Tcn(String loaiphieu, String name) {
        this.loaiphieu = loaiphieu;
        this.name = name;
    }
}
