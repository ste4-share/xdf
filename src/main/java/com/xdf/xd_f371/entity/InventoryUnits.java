package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory_units")
@Getter
@Setter
@NoArgsConstructor
public class InventoryUnits {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "root_unit_id")
    private Long root_unit_id;
    @Column(name = "petro_id")
    private Long petro_id;
    @Column(name = "price")
    private double price;
    @Column(name = "nvdx_quantity")
    private double nvdx_quantity;
    @Column(name = "sscd_quantity")
    private double sscd_quantity;
    @Column(name = "status")
    private String status;
}
