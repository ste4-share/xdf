package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.repo.DinhMucRepo;
import com.xdf.xd_f371.repo.LoaiPhuongTienRepo;
import com.xdf.xd_f371.repo.PhuongtienRepo;
import jakarta.persistence.Column;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhuongtienService {
    private final PhuongtienRepo phuongtienRepo;
    private final LoaiPhuongTienRepo loaiPhuongTienRepo;
    private final DinhMucRepo dinhMucRepo;

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
    public Optional<PhuongTien> findPhuongTienByName(String name) {
        return phuongtienRepo.findPhuongTienByName(name);
    }
    @Transactional
    public DinhMuc savePt_DM(DinhMucPhuongTienDto pt, int nnx_id){
        PhuongTien p = phuongtienRepo.save(new PhuongTien(pt.getName_pt(),pt.getQuantity(),nnx_id,pt.getLoaiphuongtien_id(), StatusCons.ACTIVED.getName()));
        return dinhMucRepo.save(new DinhMuc(pt.getDm_md_gio(), pt.getDm_tk_gio(), pt.getDm_xm_gio(),pt.getDm_xm_km(),p.getId(),pt.getQuarter_id()));
    }
}
