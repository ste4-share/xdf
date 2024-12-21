package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.util.TextToNumber;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private int tontruoc;
    @Column(name = "soluong")
    private int soluong;
    @Column(name = "tonsau")
    private int tonsau;
    @Column(name = "gia")
    private int gia;
    @Column(name = "timestamp")
    private LocalDate createTime;
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
    @Column(name = "quy_id")
    private int quyId;

    @Column(insertable = false, updatable = false)
    private String tontruoc_str;
    @Column(insertable = false, updatable = false)
    private String soluong_str;
    @Column(insertable = false, updatable = false)
    private String tonsau_str;

    public LichsuXNK(String ten_xd, String loai_phieu, int tontruoc, int soluong, int tonsau, int mucgia, String type, int so,
                     String dvn, String dvx, String chungloaixd,int quyId) {
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
        this.quyId = quyId;
        this.tontruoc_str = TextToNumber.textToNum(String.valueOf(tontruoc));
        this.tonsau_str = TextToNumber.textToNum(String.valueOf(tonsau));
        this.soluong_str = TextToNumber.textToNum(String.valueOf(soluong));
    }
    public LichsuXNK(String ten_xd, String loai_phieu, int tontruoc, int soluong, int tonsau, int mucgia, String type, int so,
                     String dvn, String dvx, String chungloaixd,int quyId,LocalDate createTime) {
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
        this.quyId = quyId;
        this.createTime = createTime;
        this.tontruoc_str = TextToNumber.textToNum(String.valueOf(tontruoc));
        this.tonsau_str = TextToNumber.textToNum(String.valueOf(tonsau));
        this.soluong_str = TextToNumber.textToNum(String.valueOf(soluong));
    }
}
