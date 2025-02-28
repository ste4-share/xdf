package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Table(name = "dinhmuc")
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DinhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "dm_md_gio")
    private double dm_md_gio;
    @Column(name = "dm_tk_gio")
    private double dm_tk_gio;
    @Column(name = "dm_xm_gio")
    private double dm_xm_gio;
    @Column(name = "dm_xm_km")
    private double dm_xm_km;
    @Column(name = "phuongtien_id")
    private double phuongtien_id;
    @Column(name = "years")
    private int years= LocalDate.now().getYear();

    @ManyToOne
    @JoinColumn(name = "phuongtien_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PhuongTien phuongTien;

    public DinhMuc(double dm_md_gio, double dm_tk_gio, double dm_xm_gio, double dm_xm_km, int phuongtien_id) {
        this.dm_md_gio = dm_md_gio;
        this.dm_tk_gio = dm_tk_gio;
        this.dm_xm_gio = dm_xm_gio;
        this.dm_xm_km = dm_xm_km;
        this.phuongtien_id = phuongtien_id;
    }

    public DinhMuc(int id, double dm_md_gio, double dm_tk_gio, double dm_xm_gio, double dm_xm_km, int phuongtien_id) {
        this.id = id;
        this.dm_md_gio = dm_md_gio;
        this.dm_tk_gio = dm_tk_gio;
        this.dm_xm_gio = dm_xm_gio;
        this.dm_xm_km = dm_xm_km;
        this.phuongtien_id = phuongtien_id;
    }
}
