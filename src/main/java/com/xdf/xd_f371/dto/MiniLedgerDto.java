package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
@NoArgsConstructor
@Getter
@Setter
public class MiniLedgerDto {
    private int id;
    private int so;
    private String so_str;
    private String loai_phieu;
    private String dvi_nhap;
    private String dvi_xuat;
    private LocalDateTime timestamp;
    private String timestamp_str;
    private Long count;
    private String count_str;
    private Long tong;
    private String tong_str;
    private String nhiemvu;
    private String username;

    public MiniLedgerDto(int id, int so, String loai_phieu, String dvi_nhap, String dvi_xuat, LocalDate timestamp, String nhiemvu, String username, Long count, Long tong) {
        this.id = id;
        this.so = so;
        this.so_str = String.valueOf(so);
        this.loai_phieu = loai_phieu;
        this.dvi_nhap = dvi_nhap;
        this.dvi_xuat = dvi_xuat;
        this.timestamp = timestamp.atStartOfDay();
        this.timestamp_str = timestamp.atStartOfDay().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.count = count;
        this.nhiemvu = nhiemvu;
        this.count_str = String.valueOf(count);
        this.tong = tong;
        this.tong_str = TextToNumber.textToNum(String.valueOf(tong));
        this.username = username;
    }

    @Override
    public String toString() {
        return "{object: " + so + " - " + loai_phieu+ " - "+dvi_nhap + " - " +dvi_xuat + " - "+ timestamp + " - "  +count_str  + " - " + tong_str + "}\n";
    }
}
