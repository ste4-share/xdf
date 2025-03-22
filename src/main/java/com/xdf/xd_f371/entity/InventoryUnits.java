package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "inventory_units")
@Getter
@Setter
@NoArgsConstructor
public class InventoryUnits extends BaseObject{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "xd_id")
    private int xd_id;
    @Column(name = "mucgia")
    private double mucgia;
    @Column(name = "loaiphieu")
    private String loaiphieu;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "soluong")
    private double soluong;
    @Column(name = "tructhuoc")
    private String tructhuoc;
    @Column(name = "tonkhotong")
    private double tonkhotong;
    @Column(name = "tonkho_gia")
    private double tonkho_gia;
    @Column(name = "index")
    private int index;
    @Column(name = "soluong_tt")
    private double soluong_tt;
    @Column(name = "tonkh_sscd")
    private double tonkh_sscd;
    @Column(name = "tonkh_gia_sscd")
    private double tonkh_gia_sscd;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "xmt_unit_id")
    private String xmt_unit_id;
    @Column(name = "root_id")
    private int root_id;
    @Column(name = "ledger_id")
    private String ledger_id;
    @Column(name = "year")
    private int year;

    @Transient
    private String created_at_str;

    public InventoryUnits(Ledger l,LedgerDetails ld,Long rootID) {

    }
}
