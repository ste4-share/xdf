package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.DviNvu;

import java.util.List;

public interface DvNvService {
    List<DviNvu> getAll();
    DviNvu create(DviNvu dviNvu);
    DviNvu update(DviNvu dviNvu);
}
