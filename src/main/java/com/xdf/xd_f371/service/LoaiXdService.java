package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.repo.ChungLoaiXdRepo;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoaiXdService {
    private final ChungLoaiXdRepo chungLoaiXdRepo;
    private final LoaiXangDauRepo loaiXangDauRepo;

    public List<LoaiXangDauDto> findAllBy(){
        return loaiXangDauRepo.findAllBy();
    }
    public Optional<LoaiXangDauDto> findById(int id){
        return loaiXangDauRepo.findById(id);
    }
    public List<LoaiXangDauDto> findAllOrderby(){
        return loaiXangDauRepo.findAllOrderby();
    }
    public Optional<LoaiXangDauDto> findAllTenxdDto(String name){
        return loaiXangDauRepo.findAllTenxdDto(name);
    }
    public List<LoaiXangDauDto> findByType(String code1,String code2){
        return loaiXangDauRepo.findByType(code1,code2);
    }
    public List<LoaiXangDau> findAll(){
        return loaiXangDauRepo.findAll();
    }
}
