package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.SpotDto;

import java.util.List;

public interface MucgiaService {
    List<SpotDto> getAllSpots(int quarter_id);
}
