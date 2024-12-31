package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.repo.DinhMucRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DinhmucService {
    private final DinhMucRepo dinhMucRepo;

    public List<DinhMucPhuongTienDto> findAllBy(int y,String lpt){
        return dinhMucRepo.findAllBy(y,lpt);
    }
    public List<DinhMuc> findAllByYear(int y){
        return dinhMucRepo.findAllByYear(y);
    }
    public DinhMuc save(DinhMuc dinhMuc){
        return dinhMucRepo.save(dinhMuc);
    }
    public Optional<DinhMuc> findDinhmucByPhuongtien(int pt_id, int y){
        return dinhMucRepo.findDinhmucByPhuongtien(pt_id, y);
    }
}
