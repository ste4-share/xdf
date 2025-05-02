package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@NoArgsConstructor
public class TransactionHistory extends BaseObject{
    @Id
    private String id;
    @Column(name = "xd_id")
    private int xd_id;
    @Column(name = "root_id")
    private int root_id;
    @Column(name = "loaiphieu")
    private String loaiphieu;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "mucgia")
    private double mucgia;
    @Column(name = "soluong")
    private double soluong;
    @Column(name = "tructhuoc")
    private String tructhuoc;
    @Column(name = "tonkhotong")
    private double tonkhotong;
    @Column(name = "tonkho_gia")
    private double tonkho_gia;
    @Column(name = "soluong_tt")
    private double soluong_tt;
    @Column(name = "tonkh_sscd")
    private double tonkh_sscd;
    @Column(name = "tonkh_gia_sscd")
    private double tonkh_gia_sscd;
    @Column(name = "index")
    private long index;
    @Column(name = "ledger_id")
    private String ledger_id;
    @Transient
    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Transient
    private String soluong_str;
    @Transient
    private String mucgia_str;
    @Transient
    private String ton;
    @Transient
    private String created_at_str;

    public TransactionHistory(String id, int xd_id,int root_id, String loaiphieu, LocalDate date, double mucgia,
                              double soluong, String tructhuoc, double tonkhotong, double tonkhogia_nvdx, long index,double soluong_tt,String ledger_id,double tonkh_sscd,double tonkh_gia_sscd) {
        this.id = id;
        this.xd_id = xd_id;
        this.root_id = root_id;
        this.loaiphieu = loaiphieu;
        this.date = date;
        this.mucgia = mucgia;
        this.soluong = soluong;
        this.tructhuoc = tructhuoc;
        this.tonkhotong = tonkhotong;
        this.tonkho_gia = tonkhogia_nvdx;
        this.index = index;
        this.soluong_tt = soluong_tt;
        this.ledger_id = ledger_id;
        this.tonkh_sscd = tonkh_sscd;
        this.tonkh_gia_sscd = tonkh_gia_sscd;
    }
}
