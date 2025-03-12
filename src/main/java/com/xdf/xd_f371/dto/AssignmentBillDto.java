package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignmentBillDto extends InformationBill{
    private PhuongTien xmt;
    private LoaiPhuongTien lpt;
    private ChitietNhiemVu ctnv;
    private int sokm;
    private String lgb;
    private String hours_act;

    public AssignmentBillDto(PhuongTien xmt, LoaiPhuongTien lpt, ChitietNhiemVu ctnv, int sokm, String lgb, String hours_act) {
        this.xmt = xmt;
        this.lpt = lpt;
        this.ctnv = ctnv;
        this.sokm = sokm;
        this.lgb = lgb;
        this.hours_act = hours_act;
    }
}
