package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HanmucNhiemvu2Dto {
    private int id;
    private int quarter_id;
    private int dvi_id;
    private int nhiemvu_id;
    private Long diezel;
    private Long daubay;
    private Long xang;
    private String tenNv;
    private String chitiet_nhiemvu;

    public HanmucNhiemvu2Dto(int quarter_id, int dvi_id, int nhiemvu_id, Long diezel, Long daubay, Long xang, String tenNv, String chitiet_nhiemvu) {
        this.quarter_id = quarter_id;
        this.dvi_id = dvi_id;
        this.nhiemvu_id = nhiemvu_id;
        this.diezel = diezel;
        this.daubay = daubay;
        this.xang = xang;
        this.tenNv = tenNv;
        this.chitiet_nhiemvu = chitiet_nhiemvu;
    }
}
