package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.*;

import java.util.List;

public interface LedgerDetailsService {
    QuantityByTTDTO selectQuantityNguonnx(int groupid, String loaiphieu, int tructhuoc_id,int xdid);
    QuantityByTTDTO selectQuantityNguonnxImport(int group_id, String loaiphieu, int tructhuoc_id, int loaixdId);
}
