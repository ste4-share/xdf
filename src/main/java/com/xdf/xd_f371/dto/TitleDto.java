package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TitleDto {

    private String lv1;
    private String lv2;
    private String lv3;

    public TitleDto() {
    }

    public TitleDto(String lv1, String lv2, String lv3) {
        this.lv1 = lv1;
        this.lv2 = lv2;
        this.lv3 = lv3;
    }
}
