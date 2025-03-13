package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.entity.UnitXmt;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentBillDto extends InformationBill{
    private static AssignmentBillDto instance;
    private PhuongTien xmt;
    private LoaiPhuongTien lpt;
    private UnitXmt unit;
    private NhiemVuDto ctnv;
    private int sokm;
    private String lgb;
    private String hours_act;

    public AssignmentBillDto(PhuongTien xmt,LoaiPhuongTien lpt, UnitXmt unit, NhiemVuDto ctnv, int sokm, String lgb, String hours_act,String so,String lenhso,String nguoinhan) {
        this.xmt = xmt;
        this.unit = unit;
        this.lpt = lpt;
        this.ctnv = ctnv;
        this.sokm = sokm;
        this.lgb = lgb;
        this.hours_act = hours_act;
        this.so = so;
        this.lenhso = lenhso;
        this.nguoinhan = nguoinhan;
    }
    public static AssignmentBillDto getInstance(){
        if (instance==null){
            instance = new AssignmentBillDto();
        }
        return instance;
    }
}
