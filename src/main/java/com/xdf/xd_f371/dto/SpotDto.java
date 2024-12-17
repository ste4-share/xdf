package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpotDto {
    private int lxd_id;
    private String maxd;
    private String tenxd;
    private int nvdx;
    private int sscd;
    private String nvdx_str;
    private String sscd_str;
    private String total;

    public SpotDto(int lxd_id, String maxd, String tenxd, int nvdx, int sscd) {
        this.lxd_id = lxd_id;
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.nvdx = nvdx;
        this.sscd = sscd;
        this.nvdx_str = TextToNumber.textToNum(String.valueOf(nvdx));
        this.sscd_str = TextToNumber.textToNum(String.valueOf(sscd));
        this.total = TextToNumber.textToNum(String.valueOf(sscd+nvdx));
    }
}
