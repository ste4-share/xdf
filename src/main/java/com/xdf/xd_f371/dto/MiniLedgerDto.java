package com.xdf.xd_f371.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


@NoArgsConstructor
@Getter
@Setter
public class MiniLedgerDto {

    private int so;
    private String so_str;
    private String loai_phieu;
    private String dvi_nhap;
    private String dvi_xuat;
    private LocalDate timestamp;
    private String timestamp_str;
    private Long count;
    private String count_str;
    private Long tong;
    private String tong_str;

    public MiniLedgerDto(int so, String loai_phieu, String dvi_nhap, String dvi_xuat, LocalDate timestamp, Long count, Long tong) {
        this.so = so;
        this.so_str = String.valueOf(so);
        this.loai_phieu = loai_phieu;
        this.dvi_nhap = dvi_nhap;
        this.dvi_xuat = dvi_xuat;
        this.timestamp = timestamp;
        this.timestamp_str = timestamp.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        this.count = count;
        this.count_str = String.valueOf(count);
        this.tong = tong;
        this.tong_str = String.valueOf(tong);
    }
}
