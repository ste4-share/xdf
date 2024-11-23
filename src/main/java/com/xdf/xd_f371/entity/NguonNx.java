package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "nguon_nx")
@Getter
@Setter
@NoArgsConstructor
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

    @OneToMany(mappedBy = "nguonNx", cascade = CascadeType.ALL,orphanRemoval = true)
    List<DonViTrucThuoc> donViTrucThuocs;

    public NguonNx(int id, String ten, String createtime) {
        this.id = id;
        this.ten = ten;
        this.createtime = createtime;
    }
    public NguonNx(String ten) {
        this.ten = ten;
    }
}
