package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "loaixd2")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class LoaiXangDau {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "maxd")
    private String maxd;
    @Column(name = "tenxd")
    private String tenxd;
    @Column(name = "petroleum_type_id")
    private int petroleum_type_id;
    @Column(name = "status")
    private String status;
    @ManyToOne
    @JoinColumn(name = "petroleum_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ChungLoaiXd chungLoaiXd;

    @OneToMany(mappedBy = "loaiXangDau")
    private List<Inventory> inventory;

    public LoaiXangDau(String maxd, String tenxd, int petroleum_type_id, String status) {
        this.maxd = maxd;
        this.tenxd = tenxd;
        this.petroleum_type_id = petroleum_type_id;
        this.status = status;
    }
}
