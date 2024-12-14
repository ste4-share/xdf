package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "ledgers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Ledger {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "quarter_id")
    @NotNull(message = "Quarter can not be null")
    private int quarter_id;
    @Column(name = "bill_id")
    @NotNull(message = "Bill Id can not be null")
    @Min(value = 0, message = "bill must be at least 0")
    private int bill_id;
    @Column(name = "amount")
    @NotNull(message = "amount can not be null")
    @Min(value = 0, message = "bill must be at least 0")
    private Long amount;
    @Column(name = "from_date")
    @NotNull(message = "Event date cannot be null")
    @FutureOrPresent(message = "Event date must be today or in the future")
    private LocalDate from_date;
    @Column(name = "end_date")
    @NotNull(message = "Event date cannot be null")
    @FutureOrPresent(message = "Event date must be today or in the future")
    private LocalDate end_date;
    @Column(name = "status")
    private String status;
    @Column(name = "so_km")
    @Min(value = 0, message = "km must be at least 0")
    private int so_km;
    @Column(name = "giohd_md")
    private String giohd_md;
    @Column(name = "giohd_tk")
    private String giohd_tk;
    @Column(name = "sl_tieuthu_md")
    @Min(value = 0, message = "md must be at least 0")
    private int sl_tieuthu_md;
    @Column(name = "sl_tieuthu_tk")
    @Min(value = 0, message = "tk must be at least 0")
    private int sl_tieuthu_tk;
    @Column(name = "inventory_id")
    @NotNull(message = "inventory Id can not be null")
    @Min(value = 0, message = "id must be at least 0")
    private int inventoryId;
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
    @Column(name = "timestamp",insertable = false)
    private LocalDate timestamp;
    @Column(name = "loainv")
    private String loainv;
    @Column(name = "tructhuoc")
    private String tructhuoc;
    @Column(name = "lpt")
    private String lpt;
    @Column(name = "lpt_2")
    private String lpt_2;
    @Version
    private int version;

    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LedgerDetails> ledgerDetails;
}
