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
    @Column(name = "mucgia")
    private String mucgia;
    @Column(name = "createtime")
    private LocalDate createTime;
    @Column(name = "type")
    private String type;
    @Column(name = "so")
    private int so;
    @Column(name = "dvn")
    private String dvn;
    @Column(name = "dvx")
    private String dvx;
    @Column(name = "tinhchat")
    private String tinhchat;
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

    public LichsuXNK(String ten_xd, String loai_phieu, int tontruoc, int soluong, int tonsau, String mucgia, LocalDate createTime, String type, int so, String dvn, String dvx, String tinhchat, String chungloaixd,int quyId) {
        this.ten_xd = ten_xd;
        this.loai_phieu = loai_phieu;
        this.tontruoc = tontruoc;
        this.soluong = soluong;
        this.tonsau = tonsau;
        this.mucgia = mucgia;
        this.createTime = createTime;
        this.type = type;
        this.so = so;
        this.dvn = dvn;
        this.dvx = dvx;
        this.tinhchat = tinhchat;
        this.chungloaixd = chungloaixd;
        this.quyId = quyId;
        this.tontruoc_str = TextToNumber.textToNum(String.valueOf(tontruoc));
        this.tonsau_str = TextToNumber.textToNum(String.valueOf(tonsau));
        this.soluong_str = TextToNumber.textToNum(String.valueOf(soluong));
    }
}
