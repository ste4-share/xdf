package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HanmucNhiemvuTaubayDto {
    private int nvtb_id;
    private int dvid;
    private int ctnv_id;
    private int pt_id;
    private String donvi;
    private String nhiemvu;
    private String ct_nhiemvu;
    private String tenpt;
    private String tk;
    private String md;
    private Long nhienlieu;
    private String nhienlieu_str;

    public HanmucNhiemvuTaubayDto(int nvtb_id, int dvid, int ctnv_id, int pt_id, String donvi, String nhiemvu, String ct_nhiemvu, String tenpt, String tk, String md, Long nhienlieu) {
        this.nvtb_id = nvtb_id;
        this.dvid = dvid;
        this.ctnv_id = ctnv_id;
        this.pt_id = pt_id;
        this.donvi = donvi;
        this.nhiemvu = nhiemvu;
        this.ct_nhiemvu = ct_nhiemvu;
        this.tenpt = tenpt;
        this.tk = tk;
        this.md = md;
        this.nhienlieu = nhienlieu;
        this.nhienlieu_str = TextToNumber.textToNum(String.valueOf(nhienlieu));
    }
}
