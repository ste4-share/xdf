package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.entity.PetroleumType;

import java.util.List;
import java.util.Map;

public interface LoaiXdService {
    List<LoaiXangDau> getAll();
    List<String> getAllChungLoai();
    LoaiXangDau create(LoaiXangDau importDto);
    PetroleumType create(PetroleumType importDto);
    LoaiXangDau update(LoaiXangDau importDto);
    boolean delete_f(String so);
    LoaiXangDau findLoaiXdByID(String tenxd);
    List<LoaiXangDau> findLoaiXdByChungLoai(String loai);
    List<LoaiXangDau> findLoaiXdByType(String loai);
    List<LoaiXangDau> findLoaiXdByChungloai(String chungloai);
    List<String> getAllType();
    List<LoaiXangDau> findLoaiXdByChungLoai_PER(String loai1);
    LoaiXangDau findLoaiXdByID_non(int id);
    Map<String, String> getChungLoaiCount();
}
