package com.xdf.xd_f371.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoaiXangDauDto {
    private int xd_id;
    private int loaixd_id;
    private String maxd;
    private String tenxd;
    private int priority;
    private int ut2;
    private String loai;
    private String chungloai;
    private String tinhchat;
    private String code;
    private String status;
}
