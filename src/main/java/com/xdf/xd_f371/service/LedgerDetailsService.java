package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.LedgerDetails;

import java.util.List;
import java.util.Map;

public interface LedgerDetailsService {
    List<LedgerDetails> getAll();
    LedgerDetails create(LedgerDetails ledgerDetails);
    LedgerDetails update(LedgerDetails ledgerDetails);
    boolean delete_f(String so);
    List<TTPhieuDto> getTTPhieu();
    List<TTPhieuDto> getTTPhieu_ByLoaiPhieu(String loaiPhieu);
    List<LedgerDetails> getChiTietSoCai(String so);
    QuantityByTTDTO selectQuantityNguonnx(int groupid, String loaiphieu, int tructhuoc_id,int xdid);
    QuantityByTTDTO selectQuantityNguonnxImport(int group_id, String loaiphieu, int tructhuoc_id, int loaixdId);

    GioBay getSumofWorkingTime(int pt_id, int quarterId, String lgb);
    Map<String, Integer> getSumofconsumption(int pt_id, int quarterID);
}
