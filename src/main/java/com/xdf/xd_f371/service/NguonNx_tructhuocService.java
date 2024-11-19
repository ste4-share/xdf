package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.NguonNx_tructhuoc;
import com.xdf.xd_f371.entity.TrucThuoc;

import java.util.List;

public interface NguonNx_tructhuocService {
    List<TrucThuoc> findAllTrucThuocByNGuonNxID(int nguonnx_id);
    NguonNx_tructhuoc findNguonnx_tructhuocByNnx_lp(int nguonnx_id, int loaiPhieu);
    NguonNx_tructhuoc createNew(NguonNx_tructhuoc nguonNxTructhuoc);
    NguonNx_tructhuoc update(NguonNx_tructhuoc nguonNxTructhuoc);
    int delete(NguonNx_tructhuoc nguonNxTructhuoc);
}
