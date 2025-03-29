package com.xdf.xd_f371.entity;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.DefaultVarCons;
import com.xdf.xd_f371.cons.TypeCons;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "ledgers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ledger extends BaseObject{
    @Id
    private String id;
    @Column(name = "bill_id")
    private String bill_id;
    @Column(name = "amount")
    private Double amount;
    @Column(name = "from_date")
    private LocalDate from_date;
    @Column(name = "end_date")
    private LocalDate end_date;
    @Column(name = "status")
    private String status;
    @Column(name = "so_km")
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
    @Column(name = "xmt_id")
    private String xmt_id;
    @Column(name = "note")
    private String note;
    @Column(name = "year")
    private int year;
    @Column(name = "dvi_baono")
    private int dvi_baono;
    @Column(name = "bienso_ds")
    private String bienso_ds;
    @Column(name = "bill_id2")
    private String bill_id2;

    @Transient
    private String from_date_str;
    @Transient
    private String end_date_str;
    @Transient
    private String timestamp_str;
    @Transient
    private String billnumber;

    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LedgerDetails> ledgerDetails;
    @ManyToOne
    @JoinColumn(name = "create_by",nullable = false,insertable = false,updatable = false)
    private Accounts accounts;

    public Ledger(Ledger l) {
        this.id = l.getId();
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
        this.year = l.getYear();
        this.dvi_baono = l.getDvi_baono();
        this.bienso_ds = l.getBienso_ds();
        this.xmt_id=l.getXmt_id();
        this.from_date_str = l.getFrom_date()==null ? "" : l.getFrom_date().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName()));
        this.end_date_str = l.getEnd_date()==null ? "" : l.getEnd_date().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName()));
        this.billnumber = l.getBill_id().concat(l.getBill_id2());
        this.timestamp_str = timestamp.format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE_TIME.getName()));
    }
}
