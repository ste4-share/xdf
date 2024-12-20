package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "mucgia")
@Getter
@Setter
@NoArgsConstructor
public class Mucgia {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "price")
    private int price;
    @Column(name = "amount")
    private int amount;
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "item_id")
    private int item_id;
    @Column(name = "status")
    private String status;
    @Column(name = "timestamp", insertable = false)
    private LocalDate timestamp;
    @Column(name = "nvdx")
    private Long nvdx;
    @Column(name = "sscd")
    private Long sscd;

    public Mucgia(int price, int amount, int quarter_id, int item_id, String status, Long nvdx, Long sscd) {
        this.price = price;
        this.amount = amount;
        this.quarter_id = quarter_id;
        this.item_id = item_id;
        this.status = status;
        this.nvdx = nvdx;
        this.sscd = sscd;
    }
}
