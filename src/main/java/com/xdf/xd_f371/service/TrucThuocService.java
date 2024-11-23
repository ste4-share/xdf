package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.TrucThuoc;

import java.util.List;

public interface TrucThuocService {
    List<TrucThuoc> getAll();
    List<TrucThuoc> getAllByType(String t);
    TrucThuoc createNew(TrucThuoc trucThuoc);
    TrucThuoc udpate_n(TrucThuoc trucThuoc);
    TrucThuoc delById(TrucThuoc trucThuoc);
    TrucThuoc findById(int id);
    TrucThuoc findByNguonnx(int nguonnxId, int titleId);
    List<TrucThuoc> findByName(String name);
}
