package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inv_records")
@Getter
@Setter
@NoArgsConstructor
public class InvRecords {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dvi_nhan_id")
    private Long dvi_nhan_id;
    @Column(name = "dvi_xuat_id")
    private Long dvi_xuat_id;
    @Column(name = "ledger_details_id")
    private Long ledger_details_id;
    @Column(name = "sscd")
    private double sscd;
    @Column(name = "nvdx")
    private double nvdx;
    @Column(name = "status")
    private String status;
    @Column(name = "price")
    private double price;
    @Column(name = "loaixd_id")
    private Long loaixd_id;
    @Column(name = "created_at")
    private LocalDate created_at;
}
