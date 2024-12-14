package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.TructhuocDto;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.TructhuocRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TructhuocService {
    private final TructhuocRepo tructhuocRepo;
    public List<TrucThuoc> findAll(){
        return tructhuocRepo.findAll();
    }
    public Optional<TrucThuoc> findById(Integer id){
        return tructhuocRepo.findById(id);
    }

    public List<TructhuocDto> findAllBy(){
        return tructhuocRepo.findAllBy();
    }
}
