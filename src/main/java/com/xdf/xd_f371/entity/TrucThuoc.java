package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tructhuoc")
@Getter
@Setter
public class TrucThuoc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "type")
    private String type;
    @Column(name = "nhom_tructhuoc")
    private String nhom_tructhuoc;
    @Column(name = "tennhom_tructhuoc")
    private String tennhom_tructhuoc;
    @Column(name = "timestamp")
    private String timestamp;

    @OneToMany(mappedBy = "trucThuoc", cascade = CascadeType.ALL, orphanRemoval = true)
    List<NguonNx> nxList;

    public TrucThuoc() {
    }
}
