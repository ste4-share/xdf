package com.xdf.xd_f371.fatory;

import com.xdf.xd_f371.entity.LichsuXNK;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonFactory {

    protected static List<Tcn> tcnx_ls = new ArrayList<>();
    @Autowired
    protected LichsuService lichsuService;
    @Autowired
    protected LedgerService ledgerService;
    @Autowired
    protected MucgiaService mucgiaService;
    @Autowired
    protected NguonNxService nguonNxService;
    @Autowired
    protected TructhuocService tructhuocService;

    protected void createNewTransaction(LedgerDetails ledgerDetails, int tontruoc, int tonsau){
        LichsuXNK lichsuXNK = new LichsuXNK();
        lichsuXNK.setTen_xd(ledgerDetails.getTen_xd());
        lichsuXNK.setSoluong(ledgerDetails.getThuc_xuat());
        lichsuXNK.setTontruoc(tontruoc);
        lichsuXNK.setTonsau(tonsau);
        lichsuXNK.setMucgia(String.valueOf(ledgerDetails.getDon_gia()));
        lichsuService.save(lichsuXNK);
    }
}
