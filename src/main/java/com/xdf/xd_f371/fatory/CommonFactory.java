package com.xdf.xd_f371.fatory;

import com.xdf.xd_f371.dto.LichsuXNK;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.model.MucGiaEnum;
import com.xdf.xd_f371.repo.LedgerDetailRepo;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.MucGiaRepo;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonFactory {
    protected LichsuNXKService lichsuNXKService = new LichsuNXKImp();
    protected static Tcn txnx = new Tcn();
    protected static List<Tcn> tcnx_ls = new ArrayList<>();

    @Autowired
    protected LedgerDetailRepo ledgerDetailRepo;
    @Autowired
    protected LedgersRepo ledgersRepo;
    @Autowired
    protected MucGiaRepo mucGiaRepo;

    protected void createNewTransaction(LedgerDetails ledgerDetails, int tontruoc, int tonsau){
        LichsuXNK lichsuXNK = new LichsuXNK();
        lichsuXNK.setTen_xd(ledgerDetails.getTen_xd());
        lichsuXNK.setSoluong(ledgerDetails.getThuc_xuat());
        lichsuXNK.setTontruoc(tontruoc);
        lichsuXNK.setTonsau(tonsau);
        lichsuXNK.setMucgia(String.valueOf(ledgerDetails.getDon_gia()));
        lichsuNXKService.createNew(lichsuXNK);
    }
}
