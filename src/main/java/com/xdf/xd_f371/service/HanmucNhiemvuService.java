package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.HanmucNhiemvu;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.repo.HanmucNhiemvuRepo;
import com.xdf.xd_f371.repo.NhiemvuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HanmucNhiemvuService {

    @Autowired
    private HanmucNhiemvuRepo hanmucNhiemvuRepo;
    @Autowired
    private NhiemvuRepository nhiemvuRepository;

    public List<HanmucNhiemvu> getAll(){
        return hanmucNhiemvuRepo.findAll();
    }
}
