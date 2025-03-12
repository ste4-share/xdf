package com.xdf.xd_f371.dto;

import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.Tcn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UnitBillDto extends InformationBill{
    private static UnitBillDto instance;

    private NguonNx dvi_nhan;
    private NguonNx dvi_xuat;
    private Tcn tcn;

    public UnitBillDto(NguonNx dvi_nhan, NguonNx dvi_xuat, Tcn tcn,String so,String lenhso,String nguoinhan,String soxe) {
        this.dvi_nhan = dvi_nhan;
        this.dvi_xuat = dvi_xuat;
        this.tcn = tcn;
        this.so = so;
        this.lenhso = lenhso;
        this.nguoinhan = nguoinhan;
        this.so_xe = soxe;
    }

    public static UnitBillDto getInstance(){
        if (instance==null){
            instance = new UnitBillDto();
        }
        return instance;
    }
}
