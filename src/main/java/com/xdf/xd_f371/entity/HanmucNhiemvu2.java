package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "dvi_id")
    private int dvi_id;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "diezel")
    private Long diezel;
    @Column(name = "daubay")
    private Long daubay;
    @Column(name = "xang")
    private Long xang;

    @ManyToOne
    @JoinColumn(name = "nhiemvu_id",insertable = false, updatable = false)
    ChitietNhiemVu chitietNhiemVu;

    public HanmucNhiemvu2(int quarter_id, int dvi_id, int nhiemvu_id, Long diezel, Long daubay, Long xang) {
        this.quarter_id = quarter_id;
        this.dvi_id = dvi_id;
        this.nhiemvu_id = nhiemvu_id;
        this.diezel = diezel;
        this.daubay = daubay;
        this.xang = xang;
    }

    public HanmucNhiemvu2(HanmucNhiemvu2Dto hm) {
        this.id = hm.getId();
        this.quarter_id = hm.getQuarter_id();
        this.dvi_id = hm.getDvi_id();
        this.nhiemvu_id = hm.getNhiemvu_id();
        this.diezel = hm.getDiezel();
        this.daubay = hm.getDaubay();
        this.xang = hm.getXang();
    }
}
