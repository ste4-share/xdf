package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.LoaiPhieu;

import java.util.List;

public interface LoaiPhieuService {
    List<LoaiPhieu> getAll();
    LoaiPhieu findLoaiPhieuByID(int id);
    LoaiPhieu findLoaiPhieuByType(String type);
}
