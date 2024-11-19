package com.xdf.xd_f371.dto;

public class ChiTietNhiemVuDTO {
    private int id;
    private String nhiemvu;
    private String chiTietNhiemVu;
    private int ctnv_id;

    public ChiTietNhiemVuDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNhiemvu() {
        return nhiemvu;
    }

    public void setNhiemvu(String nhiemvu) {
        this.nhiemvu = nhiemvu;
    }

    public String getChiTietNhiemVu() {
        return chiTietNhiemVu;
    }

    public void setChiTietNhiemVu(String chiTietNhiemVu) {
        this.chiTietNhiemVu = chiTietNhiemVu;
    }

    public int getCtnv_id() {
        return ctnv_id;
    }

    public void setCtnv_id(int ctnv_id) {
        this.ctnv_id = ctnv_id;
    }
}
