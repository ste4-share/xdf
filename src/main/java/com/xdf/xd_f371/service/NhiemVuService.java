package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.HanmucNhiemvu;
import com.xdf.xd_f371.entity.NhiemVu;

import java.util.List;

public interface NhiemVuService {
    List<NhiemVu> getAll();
    List<CtnvDto> getAllNvByType(int loainv_id);
    List<KhoiDto> getAllKhoi();
    KhoiDto findKhoiById(int id);
    List<NhiemVuDto> getAllNVDTO(int khoi);
    NhiemVu create(NhiemVu nhiemVu);
    int createHanmucNhiemVu(HanmucNhiemvu hanmucNhiemvu);
    HanmucNhiemvu findHanmucNhiemVu(int quarter_id, int unit_id, int nhiemvu_id);
    HanmucNhiemvu getHanmucNhiemvu(int unit_id, int nhiemvu_id, int quarter_id);
    int create(NhiemVuReport nhiemVuReport);
    NhiemVu update(NhiemVu nhiemVu);
    NhiemVu findCtnvByID(String chiTietNhiemVu);
    NhiemVu findById(int id);
    NhiemVu findByTenNv(String tennv, String ct);
    List<ChiTietNhiemVuDTO> getNvAndCtnv();
    List<ChiTietNhiemVuDTO> getAllCtnvByType(int type);
}
