package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HanmucNhiemvu2Dto {
    private int id;
    private int years;
    private int dvi_id;
    private int nhiemvu_id;
    private double diezel;
    private double daubay;
    private double xang;
    private double hacap;
    private String tenNv;
    private String chitiet_nhiemvu;

    private String diezel_str;
    private String daubay_str;
    private String xang_str;
    private String hacap_str;
    private String cong;

    public HanmucNhiemvu2Dto(int id,int years, int dvi_id, int nhiemvu_id, double diezel, double daubay, double xang,double hacap, String tenNv, String chitiet_nhiemvu) {
        this.id = id;
        this.years = years;
        this.dvi_id = dvi_id;
        this.nhiemvu_id = nhiemvu_id;
        this.diezel = diezel;
        this.daubay = daubay;
        this.xang = xang;
        this.hacap = hacap;
        diezel_str = TextToNumber.textToNum_2digits(diezel);
        daubay_str = TextToNumber.textToNum_2digits(daubay);
        xang_str = TextToNumber.textToNum_2digits(xang);
        hacap_str = TextToNumber.textToNum_2digits(hacap);
        cong = TextToNumber.textToNum_2digits(xang+daubay+diezel+hacap);
        this.tenNv = tenNv;
        this.chitiet_nhiemvu = chitiet_nhiemvu;
    }
}
