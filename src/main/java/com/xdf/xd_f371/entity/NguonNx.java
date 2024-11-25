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
    @Column(name = "status")
    private String status;
    @Column(name = "tructhuoc_id")
    private int tructhuoc_id;

    @OneToMany(mappedBy = "nguonNx", cascade = CascadeType.ALL)
    List<HanmucNhiemvu> hanmucNhiemvuList;

    @OneToMany(mappedBy = "nguonNx", cascade = CascadeType.ALL)
    List<DonViTrucThuoc> donViTrucThuocs;

    @OneToMany(mappedBy = "nguonNx", cascade = CascadeType.ALL)
    List<PhuongTien> phuongTiens;

    @ManyToOne
    @JoinColumn(name = "tructhuoc_id",referencedColumnName = "id", insertable = false, updatable = false)
    TrucThuoc trucThuoc;

    public NguonNx(int id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public NguonNx(int id, String ten,String status, int tructhuoc_id) {
        this.id = id;
        this.ten = ten;
        this.status = status;
        this.tructhuoc_id = tructhuoc_id;
    }

    public NguonNx(String ten) {
        this.ten = ten;
    }
}
