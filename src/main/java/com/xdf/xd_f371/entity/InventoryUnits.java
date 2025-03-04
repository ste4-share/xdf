package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.cons.StatusCons;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    @Transient
    private String created_at_str;

    public InventoryUnits(LedgerDetails ld,Long rootID) {
        this.root_unit_id = rootID;
        this.petro_id = ld.getLoaixd_id();
        this.price = ld.getDon_gia();
        this.status = StatusCons.ACTIVED.getName();
        if (ld.getSscd_nvdx().equals(Purpose.NVDX.getName())){
            this.nvdx_quantity = ld.getSoluong();
            this.sscd_quantity = 0;
        }else{
            this.nvdx_quantity = 0;
            this.sscd_quantity = ld.getSoluong();
        }
    }

    public InventoryUnits(Long root_unit_id, int petro_id, double price, double nvdx_quantity, double sscd_quantity, String status, LocalDateTime created_at) {
        this.root_unit_id = root_unit_id;
        this.petro_id = petro_id;
        this.price = price;
        this.nvdx_quantity = nvdx_quantity;
        this.sscd_quantity = sscd_quantity;
        this.status = status;
        this.created_at = created_at;
        this.created_at_str = (created_at == null ? "" : created_at.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
    }
}
