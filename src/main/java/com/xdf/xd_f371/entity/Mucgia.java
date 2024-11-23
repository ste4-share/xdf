package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "inventory_id")
    private int inventory_id;
    @Column(name = "assign_type_id")
    private int assign_type_id;
    @Column(name = "status")
    private String status;
    @Column(name = "timestamp")
    private String timestamp;
}
