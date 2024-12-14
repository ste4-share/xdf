package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.LichsuXNK;
import com.xdf.xd_f371.repo.LichsuRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LichsuService {
    private final LichsuRepo lichsuRepo;

    public List<LichsuXNK> findAll(){
        return lichsuRepo.findAll();
    }
    public LichsuXNK save(LichsuXNK lichsuXNK){
        return lichsuRepo.save(lichsuXNK);
    }
}
