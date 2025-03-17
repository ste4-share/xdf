package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.cons.StatusCons;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "inventory_units")
@Getter
@Setter
@NoArgsConstructor
public class InventoryUnits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "root_unit_id")
    private Long root_unit_id;
    @Column(name = "petro_id")
    private int petro_id;
    @Column(name = "price")
    private double price;
    @Column(name = "nvdx_quantity")
    private double nvdx_quantity;
    @Column(name = "sscd_quantity")
    private double sscd_quantity;
    @Column(name = "status")
    private String status;
    @Column(name = "created_at",insertable = false,updatable = false)
    private LocalDateTime created_at;
    @Column(name = "tructhuoc")
    private String tructhuoc;
    @Column(name = "st_time",insertable = false,updatable = false)
    private LocalDate st_time;
    @Column(name = "year")
    private int year;
    @Column(name = "bill_type")
    private String bill_type;
    @Column(name = "petro_type")
    private String petro_type;

    @Transient
    private String created_at_str;

    public InventoryUnits(Ledger l,LedgerDetails ld,Long rootID) {
        this.root_unit_id = rootID;
        this.petro_id = ld.getLoaixd_id();
        this.price = ld.getDon_gia();
        this.status = StatusCons.ACTIVED.getName();
        this.tructhuoc = l.getTructhuoc();
        this.st_time = l.getFrom_date();
        this.year= l.getYear();
        this.bill_type = l.getLoai_phieu();
        this.petro_type = ld.getChung_loai();
        if (ld.getSscd_nvdx().equals(Purpose.NVDX.getName())){
            this.nvdx_quantity = ld.getSoluong();
            this.sscd_quantity = 0;
        }else{
            this.nvdx_quantity = 0;
            this.sscd_quantity = ld.getSoluong();
        }
    }
}
