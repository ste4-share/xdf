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

    public List<DinhMucPhuongTienDto> findAllBy(@Param("qid") int quarter_id){
        return dinhMucRepo.findAllBy(quarter_id);
    }
    public DinhMuc save(DinhMuc dinhMuc){
        return dinhMucRepo.save(dinhMuc);
    }
    public Optional<DinhMuc> findDinhmucByPhuongtien(@Param("pt_id") int pt_id, @Param("quarter_id") int quarter_id){
        return dinhMucRepo.findDinhmucByPhuongtien(pt_id, quarter_id);
    }
}
