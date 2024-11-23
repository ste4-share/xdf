package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.ChitieuDTO;
import com.xdf.xd_f371.dto.NormDto;
import com.xdf.xd_f371.dto.StatusActive;
import com.xdf.xd_f371.entity.*;

import java.util.List;

public interface PhuongTienService {
    int createNewNorm(DinhMuc dinhMuc);
    List<NguonNx> getIdNguonnx();
    List<String> getTypeName();
    int createNew(PhuongTien phuongTien);
    PhuongTien findPhuongTienById(int id);
    List<PhuongTien> findPhuongTienByType(String type);
}
