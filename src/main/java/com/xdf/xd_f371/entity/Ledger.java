package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "ledgers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {
    @Id
    private Long id;
    @Column(name = "bill_id")
    @NotNull
    @Min(1)
    private int bill_id;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "from_date")
    private LocalDate from_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Column(name = "status")
    private String status;
    @Column(name = "so_km")
    @Min(value = 0)
    private int so_km;
    @Column(name = "giohd_md")
    private String giohd_md;
    @Column(name = "giohd_tk")
    private String giohd_tk;
    @Column(name = "dvi_nhan_id")
    private int dvi_nhan_id;
    @Column(name = "dvi_xuat_id")
    private int dvi_xuat_id;
    @Column(name = "loai_phieu")
    private String loai_phieu;
    @Column(name = "dvi_nhan")
    private String dvi_nhan;
    @Column(name = "dvi_xuat")
    private String dvi_xuat;
    @Column(name = "loaigiobay")
    private String loaigiobay;
    @Column(name = "nguoi_nhan")
    private String nguoi_nhan;
    @Column(name = "so_xe")
    private String so_xe;
    @Column(name = "lenh_so")
    private String lenh_so;
    @Column(name = "nhiemvu")
    private String nhiemvu;
    @Column(name = "nhiemvu_id")
    private int nhiemvu_id;
    @Column(name = "tcn_id")
    private int tcn_id;
    @Column(name = "timestamp",insertable = false,updatable = false)
    private LocalDateTime timestamp;
    @Column(name = "loainv")
    private String loainv;
    @Column(name = "tructhuoc")
    private String tructhuoc;
    @Column(name = "lpt")
    private String lpt;
    @Column(name = "lpt_2")
    private String lpt_2;
    @Column(name = "create_by")
    private int create_by;
    @Column(name = "root_id")
    private int root_id;
    @Column(name = "pt_id")
    private int pt_id;
    @Column(name = "note")
    private String note;
    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LedgerDetails> ledgerDetails;
    @ManyToOne
    @JoinColumn(name = "create_by",nullable = false,insertable = false,updatable = false)
    private Accounts accounts;

    public Ledger(Ledger l) {
        this.bill_id = l.bill_id;
        this.amount = l.amount;
        this.from_date = l.from_date;
        this.end_date = l.end_date;
        this.status = l.status;
        this.so_km = l.so_km;
        this.giohd_md = l.giohd_md;
        this.giohd_tk = l.giohd_tk;
        this.dvi_nhan_id = l.dvi_nhan_id;
        this.dvi_xuat_id = l.dvi_xuat_id;
        this.loai_phieu = l.loai_phieu;
        this.dvi_nhan = l.dvi_nhan;
        this.dvi_xuat = l.dvi_xuat;
        this.loaigiobay = l.loaigiobay;
        this.nguoi_nhan = l.nguoi_nhan;
        this.so_xe = l.so_xe;
        this.lenh_so = l.lenh_so;
        this.nhiemvu = l.nhiemvu;
        this.nhiemvu_id = l.nhiemvu_id;
        this.tcn_id = l.tcn_id;
        this.timestamp = l.timestamp;
        this.loainv = l.loainv;
        this.tructhuoc = l.tructhuoc;
        this.lpt = l.lpt;
        this.lpt_2 = l.lpt_2;
        this.create_by = l.create_by;
        this.root_id = l.root_id;
        this.pt_id = l.pt_id;
        this.note = l.note;
    }
}
