package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.HanmucNhiemvu;
import com.xdf.xd_f371.repo.HanmucNhiemvuRepo;
import com.xdf.xd_f371.repo.NhiemvuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HanmucNhiemvuService {

    private final HanmucNhiemvuRepo hanmucNhiemvuRepo;
    private final NhiemvuRepository nhiemvuRepository;

    public List<HanmucNhiemvu> getAll(){
        return hanmucNhiemvuRepo.findAll();
    }
}
