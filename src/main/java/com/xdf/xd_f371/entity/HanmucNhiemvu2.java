package com.xdf.xd_f371.entity;

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
    @JoinColumn(name = "nhiemvu_id", referencedColumnName = "id",insertable = false, updatable = false)
    NhiemVu nhiemVu;

    public HanmucNhiemvu2(int quarter_id, int dvi_id, int nhiemvu_id, Long diezel, Long daubay, Long xang) {
        this.quarter_id = quarter_id;
        this.dvi_id = dvi_id;
        this.nhiemvu_id = nhiemvu_id;
        this.diezel = diezel;
        this.daubay = daubay;
        this.xang = xang;
    }
}
