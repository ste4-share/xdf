package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @Column(name = "ut")
    private int priority;
    @Column(name = "ut2")
    private int ut2;
    @Column(name = "petroleum_type_id")
    private int petroleum_type_id;
    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "petroleum_type_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ChungLoaiXd chungLoaiXd;
}
