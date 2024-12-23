package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Table(name = "phuongtien")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class PhuongTien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "quantity")
    private int quantity;
    @Column(name = "nguonnx_id")
    private int nguonnx_id;
    @Column(name = "loaiphuongtien_id")
    private int loaiphuongtien_id;
    @Column(name = "status")
    private String status;

    public PhuongTien(String name, int quantity, int nguonnx_id, int loaiphuongtien_id, String status) {
        this.name = name;
        this.quantity = quantity;
        this.nguonnx_id = nguonnx_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.status = status;
    }

    public PhuongTien(int id, String name, int quantity, int nguonnx_id, int loaiphuongtien_id, String status) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.nguonnx_id = nguonnx_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.status = status;
    }

    @OneToMany(mappedBy = "phuongTien", cascade = CascadeType.ALL)
    private List<DinhMuc> dinhmuc;
    @OneToMany(mappedBy = "phuongTien", cascade = CascadeType.ALL)
    private List<NhiemvuTaubay> nhiemvuTaubays;

    @ManyToOne
    @JoinColumn(name = "loaiphuongtien_id" , referencedColumnName = "id", insertable = false, updatable = false)
    private LoaiPhuongTien loaiPhuongTien;

    @ManyToOne
    @JoinColumn(name = "nguonnx_id" , referencedColumnName = "id", insertable = false, updatable = false)
    private NguonNx nguonNx;
}
