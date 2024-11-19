package com.xdf.xd_f371.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Inventory {
    private int stt;
    @Id
    private int id;
    private int petro_id;
    private int quarter_id;
    private int tdk_nvdx;
    private int tdk_sscd;
    private int pre_nvdx;
    private int pre_sscd;
    private int tcK_nvdx;
    private int tck_sscd;
    private int import_total;
    private int export_total;
    private int total;
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

    public Inventory() {
    }

    public Inventory(int id, int petro_id, int quarter_id, int tdk_nvdx, int tdk_sscd, int tcK_nvdx, int tck_sscd, int total, String status, int import_total, int export_total, int pre_nvdx, int pre_sscd) {
        this.id = id;
        this.petro_id = petro_id;
        this.quarter_id = quarter_id;
        this.tdk_nvdx = tdk_nvdx;
        this.tdk_sscd = tdk_sscd;
        this.tcK_nvdx = tcK_nvdx;
        this.tck_sscd = tck_sscd;
        this.total = total;
        this.status = status;
        this.export_total = export_total;
        this.import_total = import_total;
        this.pre_sscd = pre_sscd;
        this.pre_nvdx = pre_nvdx;
    }

}
