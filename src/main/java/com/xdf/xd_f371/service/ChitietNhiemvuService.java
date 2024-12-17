package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.ChitietNhiemVuDto;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.dto.NhiemvuTeamDto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.repo.ChitietNhiemvuRepo;
import com.xdf.xd_f371.repo.NhiemvuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChitietNhiemvuService {
    private final ChitietNhiemvuRepo chitietNhiemvuRepo;
    private final NhiemvuRepository nhiemvuRepository;

    public List<NhiemVuDto> findAllDtoBy(String lnv){
        return chitietNhiemvuRepo.findAllDtoBy(lnv);
    }

    public List<NhiemVuDto> findAllBy(){
        return chitietNhiemvuRepo.findAllBy();
    }

    public List<NhiemVuDto> findAllDtoById(String lnv){
        return chitietNhiemvuRepo.findAllDtoById(lnv);
    }
    public Optional<NhiemVuDto> findAllByChitietNhiemvu(String nv, String chitiet){
        return chitietNhiemvuRepo.findAllByChitietNhiemvu(nv,chitiet);
    }
    public List<ChitietNhiemVu> findByNhiemvuId(int id){
        return chitietNhiemvuRepo.findByNhiemvuId(id);
    }
    public List<ChitietNhiemVuDto> findAllByLoaiNv(int loainv_id, int loainv_id1){
        return chitietNhiemvuRepo.findAllByLoaiNv(loainv_id,loainv_id1);
    }
    public Optional<ChitietNhiemVuDto> findByTenNhiemvu(String tennv){
        return chitietNhiemvuRepo.findByTenNhiemvu(tennv);
    }
    public Optional<ChitietNhiemVu> findByNhiemvu(String tennv){
        return chitietNhiemvuRepo.findByNhiemvu(tennv);
    }

    public Optional<ChitietNhiemVu> findById(int id){
        return chitietNhiemvuRepo.findById(id);
    }

    public List<NhiemVu> findAll(){
        return nhiemvuRepository.findAll();
    }
    public Optional<NhiemVu> findByIdNhiemvu(int id){
        return nhiemvuRepository.findById(id);
    }
    public List<NhiemvuTeamDto> findByTeam(){
        return nhiemvuRepository.findByTeam();
    }
}
