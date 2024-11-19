package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.ChitietNhiemVu;

import java.util.List;

public interface ChiTietNhiemVuService {
    List<ChitietNhiemVu> getAll();
    ChitietNhiemVu create(ChitietNhiemVu nhiemVu);
    ChitietNhiemVu update(ChitietNhiemVu nhiemVu);
    ChitietNhiemVu findById(int id);
    List<ChitietNhiemVu> findByTenNv(int nv_id);
}
