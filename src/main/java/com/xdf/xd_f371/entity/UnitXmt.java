package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "unit_xmt")
@Getter
@Setter
@NoArgsConstructor
public class UnitXmt {
    @Id
    private String id;
    @Column(name = "unit_id")
    private int unit_id;
    @Column(name = "xmt_id")
    private int xmt_id;
    @Column(name = "note")
    private String note;
    @Column(name = "dm_hours")
    private double dm_hours;
    @Column(name = "dm_km")
    private double dm_km;
    @Column(name = "dm_md")
    private double dm_md;
    @Column(name = "dm_tk")
    private double dm_tk;
    @Column(name = "licence_plate_number")
    private String licence_plate_number;
    @Column(name = "status")
    private String status;
    @Column(name = "chitieu_giobaymd")
    private String chitieu_giobaymd;
    @Column(name = "chitieu_giobaytk")
    private String chitieu_giobaytk;
    @Column(name = "nltieuthu")
    private double nltieuthu;

    public UnitXmt(String id, int unit_id, int xmt_id, String note, double dm_hours, double dm_km, double dm_md, double dm_tk,
                   String licence_plate_number, String status,String chitieu_giobaymd,String chitieu_giobaytk,double nltieuthu) {
        this.id = id;
        this.unit_id = unit_id;
        this.xmt_id = xmt_id;
        this.note = note;
        this.dm_hours = dm_hours;
        this.dm_km = dm_km;
        this.dm_md = dm_md;
        this.dm_tk = dm_tk;
        this.licence_plate_number = licence_plate_number;
        this.status = status;
        this.chitieu_giobaymd = chitieu_giobaymd;
        this.chitieu_giobaytk = chitieu_giobaytk;
        this.nltieuthu = nltieuthu;
    }
}
