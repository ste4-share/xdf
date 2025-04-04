package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "hanmuc_nhiemvu2")
@Getter
@Setter
@NoArgsConstructor
public class HanmucNhiemvu2 {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "dvi_id")
    private int dvi_id;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "diezel")
    private double diezel;
    @Column(name = "daubay")
    private double daubay;
    @Column(name = "xang")
    private double xang;
    @Column(name = "hacap")
    private double hacap;
    @Column(name = "hm_md")
    private String hm_md;
    @Column(name = "hm_tk")
    private String hm_tk;
    @Column(name = "years")
    private Integer years= LocalDate.now().getYear();

    @ManyToOne
    @JoinColumn(name = "nhiemvu_id",insertable = false, updatable = false)
    ChitietNhiemVu chitietNhiemVu;

    public HanmucNhiemvu2(int dvi_id, int nhiemvu_id, double diezel, double daubay, double xang,double hacap) {
        this.dvi_id = dvi_id;
        this.nhiemvu_id = nhiemvu_id;
        this.diezel = diezel;
        this.daubay = daubay;
        this.xang = xang;
        this.hacap = hacap;
    }

    public HanmucNhiemvu2(HanmucNhiemvu2Dto hm) {
        this.id = hm.getId();
        this.dvi_id = hm.getDvi_id();
        this.years = hm.getYears();
        this.nhiemvu_id = hm.getNhiemvu_id();
        this.diezel = hm.getDiezel();
        this.daubay = hm.getDaubay();
        this.xang = hm.getXang();
        this.hacap = hm.getHacap();
    }
}
