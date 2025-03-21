package com.xdf.xd_f371.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "transaction_history")
@Getter
@Setter
@NoArgsConstructor
public class TransactionHistory {
    @Id
    private String id;
    @Column(name = "xd_id")
    private int xd_id;
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
    @Column(name = "index")
    private long index;

    public TransactionHistory(String id, int xd_id, String loaiphieu, LocalDate date, double mucgia, double soluong, String tructhuoc, double tonkhotong, double tonkho_gia, long index) {
        this.id = id;
        this.xd_id = xd_id;
        this.loaiphieu = loaiphieu;
        this.date = date;
        this.mucgia = mucgia;
        this.soluong = soluong;
        this.tructhuoc = tructhuoc;
        this.tonkhotong = tonkhotong;
        this.tonkho_gia = tonkho_gia;
        this.index = index;
    }
}
