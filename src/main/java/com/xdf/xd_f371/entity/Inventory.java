package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
public class Inventory {
    private int stt;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "petro_id")
    private int petro_id;
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "tdk_nvdx")
    private int tdk_nvdx;
    @Column(name = "tdk_sscd")
    private int tdk_sscd;
    @Column(name = "pre_nvdx")
    private int pre_nvdx;
    @Column(name = "pre_sscd")
    private int pre_sscd;
    @Column(name = "tck_nvdx")
    private int tck_nvdx;
    @Column(name = "tck_sscd")
    private int tck_sscd;
    @Column(name = "import_total")
    private int import_total;
    @Column(name = "export_total")
    private int export_total;
    @Column(name = "total")
    private int total;
    @Column(name = "status")
    private String status;

    private String tdk_nvdx_str;
    private String tdk_sscd_str;
    private String tdk_sum_str;
    private String tcK_nvdx_str;
    private String tck_sscd_str;
    private String tck_sum_str;
    private String pre_nvdx_str;
    private String pre_sscd_str;
    private String pre_sum_str;
    private String petroleumName;
    private String chungloai;

    public Inventory(int stt, int id, int petro_id, int quarter_id, int tdk_nvdx, int tdk_sscd, int pre_nvdx, int pre_sscd, int tck_nvdx, int tck_sscd, int import_total, int export_total, int total, String status) {
        this.stt = stt;
        this.id = id;
        this.petro_id = petro_id;
        this.quarter_id = quarter_id;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.pre_nvdx = pre_nvdx;
        this.pre_sscd = pre_sscd;
        this.tck_nvdx = tck_nvdx;
        this.tck_sscd = tck_sscd;
        this.import_total = import_total;
        this.export_total = export_total;
        this.total = total;
        this.status = status;
    }
}
