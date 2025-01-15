package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.util.TextToNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "lichsuxnk")
@Getter
@Setter
@NoArgsConstructor
public class LichsuXNK {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ten_xd")
    private String ten_xd;
    @Column(name = "loai_phieu")
    private String loai_phieu;
    @Column(name = "tontruoc")
    private Long tontruoc;
    @Column(name = "soluong")
    private int soluong;
    @Column(name = "tonsau")
    private Long tonsau;
    @Column(name = "gia")
    private int gia;
    @Column(name = "timestamp",insertable = false,updatable = false)
    private LocalDateTime createTime;
    @Column(name = "type")
    private String type;
    @Column(name = "so")
    private int so;
    @Column(name = "dvn")
    private String dvn;
    @Column(name = "dvx")
    private String dvx;
    @Column(name = "chungloaixd")
    private String chungloaixd;
    @Column(name = "sd")
    private LocalDate sd;

    @Transient
    private String tontruoc_str;
    @Transient
    private String gia_str;
    @Transient
    private String soluong_str;
    @Transient
    private String tonsau_str;
    @Transient
    private String createtime_str;

    public LichsuXNK(String ten_xd, String loai_phieu, Long tontruoc, int soluong, Long tonsau, int mucgia, String type, int so,
                     String dvn, String dvx, String chungloaixd, LocalDate sd) {
        this.ten_xd = ten_xd;
        this.loai_phieu = loai_phieu;
        this.tontruoc = tontruoc;
        this.soluong = soluong;
        this.tonsau = tonsau;
        this.gia = mucgia;
        this.type = type;
        this.so = so;
        this.dvn = dvn;
        this.dvx = dvx;
        this.chungloaixd = chungloaixd;
        this.sd = sd;
        this.tontruoc_str = TextToNumber.textToNum(String.valueOf(tontruoc));
        this.tonsau_str = TextToNumber.textToNum(String.valueOf(tonsau));
        this.soluong_str = TextToNumber.textToNum(String.valueOf(soluong));
        this.gia_str = TextToNumber.textToNum(String.valueOf(mucgia));
    }
    public LichsuXNK(String ten_xd, String loai_phieu, Long tontruoc, int soluong, Long tonsau, int mucgia, String type, int so,
                     String dvn, String dvx, String chungloaixd, LocalDateTime createTime, LocalDate sd) {
        this.ten_xd = ten_xd;
        this.loai_phieu = loai_phieu;
        this.tontruoc = tontruoc;
        this.soluong = soluong;
        this.tonsau = tonsau;
        this.gia = mucgia;
        this.type = type;
        this.so = so;
        this.dvn = dvn;
        this.dvx = dvx;
        this.chungloaixd = chungloaixd;
        this.sd = sd;
        this.createTime = createTime;
        this.createtime_str = (createTime == null ? "" :createTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        this.tontruoc_str = TextToNumber.textToNum(String.valueOf(tontruoc));
        this.tonsau_str = TextToNumber.textToNum(String.valueOf(tonsau));
        this.soluong_str = TextToNumber.textToNum(String.valueOf(soluong));
        this.gia_str = TextToNumber.textToNum(String.valueOf(mucgia));
    }
    public LichsuXNK(LichsuXNK ls) {
        this.ten_xd = ls.getTen_xd();
        this.loai_phieu = ls.getLoai_phieu();
        this.tontruoc = ls.getTontruoc();
        this.soluong = ls.getSoluong();
        this.tonsau = ls.getTonsau();
        this.gia = ls.getGia();
        this.type = ls.getType();
        this.so = ls.getSo();
        this.dvn = ls.getDvn();
        this.dvx = ls.getDvx();
        this.chungloaixd = ls.getChungloaixd();
        this.sd = ls.getSd();
        this.createTime = ls.getCreateTime();
        this.createtime_str = (createTime == null ? "" :createTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
        this.tontruoc_str = TextToNumber.textToNum(String.valueOf(ls.getTontruoc()));
        this.tonsau_str = TextToNumber.textToNum(String.valueOf(ls.getTonsau()));
        this.soluong_str = TextToNumber.textToNum(String.valueOf(ls.getSoluong()));
        this.gia_str = TextToNumber.textToNum(String.valueOf(ls.getGia()));
    }
}
