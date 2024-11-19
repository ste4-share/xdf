package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TonKho {
    private int id;
    private int stt;
    private String loai_xd;
    private int soluong;
    private int mucgia;
    private String createtime;
    private String status;
    private int quarter_id;
    private int loaixd_id;
    private int mucgia_id;


    public TonKho() {
    }

}