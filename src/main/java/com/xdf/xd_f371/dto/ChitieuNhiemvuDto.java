package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChitieuNhiemvuDto {
    private int nv_id;
    private int dvi_id;
    private int ctnv_id;
    private String ten_nv;
    private String chitiet;
    private String ct_tk;
    private String ct_md;
    private int consumpt;
    private String consumpt_str;

    public ChitieuNhiemvuDto(int nv_id, int dvi_id, int ctnv_id, String ten_nv, String chitiet, String ct_tk, String ct_md, int consumpt) {
        this.nv_id = nv_id;
        this.dvi_id = dvi_id;
        this.ctnv_id = ctnv_id;
        this.ten_nv = ten_nv;
        this.chitiet = chitiet;
        this.ct_tk = ct_tk;
        this.ct_md = ct_md;
        this.consumpt = consumpt;
        this.consumpt_str = TextToNumber.textToNum(String.valueOf(consumpt));
    }
}
