package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "nguon_nx")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NguonNx {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "ten")
    private String ten;
    @Column(name = "createtime")
    private String createtime;

    @OneToMany(mappedBy = "nguonNx", cascade = CascadeType.ALL,orphanRemoval = true)
    List<HanmucNhiemvu> hanmucNhiemvuList;

}
