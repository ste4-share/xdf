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
    @Column(name = "nguonnx_id")
    private int nguonnx_id;
    @Column(name = "loaiphuongtien_id")
    private int loaiphuongtien_id;
    @Column(name = "status")
    private String status;
    @Column(name = "loaipt")
    private String loaipt;
    @Column(name = "tinhchat")
    private String tinhchat;

    public PhuongTien(String name, int nguonnx_id, int loaiphuongtien_id, String status) {
        this.name = name;
        this.nguonnx_id = nguonnx_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.status = status;
    }

    public PhuongTien(int id, String name, int nguonnx_id, int loaiphuongtien_id, String status) {
        this.id = id;
        this.name = name;
        this.nguonnx_id = nguonnx_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.status = status;
    }

    public PhuongTien(String name, int nguonnx_id, int loaiphuongtien_id, String status, String loaipt, String tinhchat) {
        this.name = name;
        this.nguonnx_id = nguonnx_id;
        this.loaiphuongtien_id = loaiphuongtien_id;
        this.status = status;
        this.loaipt = loaipt;
        this.tinhchat = tinhchat;
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
