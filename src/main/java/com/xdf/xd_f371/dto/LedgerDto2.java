package com.xdf.xd_f371.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
public class LedgerDto2 {
    private Long ledger_id;
    private Long ledger_detail_id;
    private int dvn_id;
    private int dvx_id;
    private Long ctnv_id;
    private int xmt_id;
    private int so;
    private LocalDate from_date;
    private LocalDate end_date;
    private String lenh_so;
    private String phieu;
    private String dvn;
    private String dvx;
    private String xmt;
    private String chitiet_nhiemvu;
    private String ghichu;
    private String thoigiannhap;

    public LedgerDto2(long ledger_id, long ledger_detail_id, int dvn_id, int dvx_id, long ctnv_id, int xmt_id,
                      int so, LocalDate from_date, LocalDate end_date, String lenh_so, String phieu, String dvn,
                      String dvx, String xmt, String chitiet_nhiemvu, String ghichu, String thoigiannhap) {
        this.ledger_id = ledger_id;
        this.ledger_detail_id = ledger_detail_id;
        this.dvn_id = dvn_id;
        this.dvx_id = dvx_id;
        this.ctnv_id = ctnv_id;
        this.xmt_id = xmt_id;
        this.so = so;
        this.from_date = from_date;
        this.end_date = end_date;
        this.lenh_so = lenh_so;
        this.phieu = phieu;
        this.dvn = dvn;
        this.dvx = dvx;
        this.xmt = xmt;
        this.chitiet_nhiemvu = chitiet_nhiemvu;
        this.ghichu = ghichu;
        this.thoigiannhap = thoigiannhap;
    }
}
