package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "loai_phuongtien")
@Getter
@Setter
public class LoaiPhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "type_name")
    private String typeName;
    @Column(name = "type")
    private String type;

    public LoaiPhuongTien() {
    }

    public LoaiPhuongTien(String type, String typeName, int id) {
        this.type = type;
        this.typeName = typeName;
        this.id = id;
    }

    @OneToMany(mappedBy = "loaiPhuongTien",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PhuongTien> phuongTiens;
}
