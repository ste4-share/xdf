package com.xdf.xd_f371.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LichsuXNK {
    private int id;
    private int stt;
    private String ten_xd;
    private String loai_phieu;
    private int tontruoc;
    private int soluong;
    private int tonsau;
    private String mucgia;

    private String createTime;
    private String tontruoc_str;
    private String soluong_str;
    private String tonsau_str;

    public LichsuXNK() {
    }
}
