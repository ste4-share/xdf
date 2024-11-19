package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TTPhieuDto {
    private String so;
    private String loai_phieu;
    private String dvn;
    private String dvvc;
    private String ngaytao;
    private String tcn;
    private String hang_hoa;
    private Long tong;

    public TTPhieuDto() {
    }
}
