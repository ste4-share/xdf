package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.LichsuXNK;

import java.util.List;

public interface LichsuNXKService {
    List<LichsuXNK> getAll();
    LichsuXNK createNew(LichsuXNK lichsuXNK);
}
