package com.xdf.xd_f371.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;
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
    private int quarter_id;
    @Column(name = "bill_id")
    private int bill_id;
    @Column(name = "amount")
    private int amount;
    @Column(name = "from_date")
    private Date from_date;
    @Column(name = "end_date")
    private Date end_date;
    @Column(name = "status")
    private String status;
    @Column(name = "giohd_md")
    private String giohd_md;
    @Column(name = "giohd_tk")
    private String giohd_tk;
    @Column(name = "sl_tieuthu_md")
    private String sl_tieuthu_md;
    @Column(name = "sl_tieuthu_tk")
    private String sl_tieuthu_tk;

    @OneToMany(mappedBy = "ledger", cascade = CascadeType.ALL, orphanRemoval = true)
    List<LedgerDetails> ledgerDetails;
}
