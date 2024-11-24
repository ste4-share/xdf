package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inventory")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "petro_id")
    private int petro_id;
    @Column(name = "quarter_id")
    private int quarter_id;
    @Column(name = "tdk_nvdx")
    private int tdk_nvdx;
    @Column(name = "tdk_sscd")
    private int tdk_sscd;
    @Column(name = "pre_nvdx")
    private int pre_nvdx;
    @Column(name = "pre_sscd")
    private int pre_sscd;
    @Column(name = "status")
    private String status;
}
