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
    private Long diezel;
    private Long daubay;
    private Long xang;
    private String tenNv;
    private String chitiet_nhiemvu;

    private String diezel_str;
    private String daubay_str;
    private String xang_str;
    private String cong;

    public HanmucNhiemvu2Dto(int id,int years, int dvi_id, int nhiemvu_id, Long diezel, Long daubay, Long xang, String tenNv, String chitiet_nhiemvu) {
        this.id = id;
        this.years = years;
        this.dvi_id = dvi_id;
        this.nhiemvu_id = nhiemvu_id;
        this.diezel = diezel;
        this.daubay = daubay;
        this.xang = xang;
        diezel_str = TextToNumber.textToNum(String.valueOf(diezel));
        daubay_str = TextToNumber.textToNum(String.valueOf(daubay));
        xang_str = TextToNumber.textToNum(String.valueOf(xang));
        cong = TextToNumber.textToNum(String.valueOf(xang+daubay+diezel));
        this.tenNv = tenNv;
        this.chitiet_nhiemvu = chitiet_nhiemvu;
    }
}
