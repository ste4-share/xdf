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
    private int ctnv_id;
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


}
