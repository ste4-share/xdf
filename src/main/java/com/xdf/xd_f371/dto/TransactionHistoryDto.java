package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.util.TextToNumber;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class TransactionHistoryDto {
    private String sophieu;
    private String loaiphieu;
    private String dvx;
    private String dvn;
    private String tructhuoc;
    private String tenxd;
    private String loaixd;
    private String gia;
    private String soluong;
    private String created_at;
    private String tontheogia;
    private String tontheoloaixd;
    private String tenxmt;
    private String loaixmt;
    private String chitietnhiemvu;

    public TransactionHistoryDto(String sophieu, String loaiphieu, String dvx, String dvn, String tructhuoc, String tenxd, String loaixd, double gia, double soluong, LocalDateTime created_at, double tontheogia, double tontheoloaixd, String tenxmt, String loaixmt, String chitietnhiemvu) {
        this.sophieu = sophieu;
        this.loaiphieu = loaiphieu;
        this.dvx = dvx;
        this.dvn = dvn;
        this.tructhuoc = tructhuoc;
        this.tenxd = tenxd;
        this.loaixd = loaixd;
        this.gia = TextToNumber.textToNum_2digits(gia);
        this.soluong = TextToNumber.textToNum_2digits(soluong);
        this.created_at = created_at==null ? "" : created_at.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
        this.tontheogia = TextToNumber.textToNum_2digits(tontheogia);
        this.tontheoloaixd = TextToNumber.textToNum_2digits(tontheoloaixd);
        this.tenxmt = tenxmt;
        this.loaixmt = loaixmt;
        this.chitietnhiemvu = chitietnhiemvu;
    }
}
