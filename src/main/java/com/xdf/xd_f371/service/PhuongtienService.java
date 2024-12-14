package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.repo.LoaiPhuongTienRepo;
import com.xdf.xd_f371.repo.PhuongtienRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhuongtienService {
    private final PhuongtienRepo phuongtienRepo;
    private final LoaiPhuongTienRepo loaiPhuongTienRepo;

    public List<PhuongTien> findPhuongTienByLoaiPhuongTien(String loaiPhuongTien){
        return phuongtienRepo.findPhuongTienByLoaiPhuongTien(loaiPhuongTien);
    }
    public PhuongTien save(PhuongTien phuongTien){
        return phuongtienRepo.save(phuongTien);
    }
    public LoaiPhuongTien save(LoaiPhuongTien phuongTien){
        return loaiPhuongTienRepo.save(phuongTien);
    }
    public Optional<PhuongTien> findById(int id){
        return phuongtienRepo.findById(id);
    }
    public List<PhuongTien> findAll(){
        return phuongtienRepo.findAll();
    }
}
