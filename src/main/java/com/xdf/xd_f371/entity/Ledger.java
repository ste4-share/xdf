package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
@Entity
@Table(name = "ledgers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "bill_id")
    private int bill_id;
    @Column(name = "bill_type_id")
    private int billType_id;
    @Column(name = "amount")
    private int amount;
    @Column(name = "from_date")
    private Date from_date;
    @Column(name = "end_date")
    private Date end_date;
    @Column(name = "status")
    private String status;

}
