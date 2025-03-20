package com.xdf.xd_f371.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistory {
    @Id
    private String id;
    @Column(name = "xd_id")
    private int xd_id;
    @Column(name = "create_at")
    private LocalDateTime create_at;
    @Column(name = "loaiphieu")
    private String loaiphieu;
    @Column(name = "date")
    private Date date;
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
}
