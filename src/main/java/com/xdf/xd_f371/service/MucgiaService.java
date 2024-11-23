package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.AssignTypePriceDto;
import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;

import java.util.List;

public interface MucgiaService {
    Mucgia updateMucGia(Mucgia mucgia);
    List<SpotDto> getAllSpots(int quarter_id);
}
