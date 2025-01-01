package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "hanmuc_nhiemvu_taubay")
@Getter
@Setter
@NoArgsConstructor
public class NhiemvuTaubay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "dvi_xuat_id")
    private int dviXuatId;
    @Column(name = "pt_id")
    private int pt_id;
    @Column(name = "ctnv_id")
    private int ctnv_id;
    @Column(name = "tk")
    private String tk;
    @Column(name = "md")
    private String md;
    @Column(name = "nhienlieu")
    private Long nhienlieu;
    @Column(name = "years")
    private int years = LocalDate.now().getYear();
    public NhiemvuTaubay(int dviXuatId, int pt_id, int ctnv_id, String tk, String md, Long nhienlieu) {
        this.dviXuatId = dviXuatId;
        this.pt_id = pt_id;
        this.ctnv_id = ctnv_id;
        this.tk = tk;
        this.md = md;
        this.nhienlieu = nhienlieu;
    }
    public NhiemvuTaubay(int id, int dviXuatId, int pt_id, int ctnv_id, String tk, String md, Long nhienlieu) {
        this.id = id;
        this.dviXuatId = dviXuatId;
        this.pt_id = pt_id;
        this.ctnv_id = ctnv_id;
        this.tk = tk;
        this.md = md;
        this.nhienlieu = nhienlieu;
    }
    @ManyToOne
    @JoinColumn(name = "ctnv_id", referencedColumnName = "id", insertable = false, updatable = false)
    private ChitietNhiemVu chitietNhiemVu;
    @ManyToOne
    @JoinColumn(name = "pt_id",referencedColumnName = "id",insertable = false,updatable = false)
    private PhuongTien phuongTien;
    @ManyToOne
    @JoinColumn(name = "dvi_xuat_id",referencedColumnName = "id",insertable = false,updatable = false)
    private NguonNx nguonNx;
}
