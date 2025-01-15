package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.repo.DinhMucRepo;
import com.xdf.xd_f371.repo.LoaiPhuongTienRepo;
import com.xdf.xd_f371.repo.PhuongtienRepo;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<LoaiPhuongTien> findAllLpt(){
        return loaiPhuongTienRepo.findAll();
    }
    public LoaiPhuongTien findLptByName(String tn){
        return loaiPhuongTienRepo.findLptByName(tn);
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
    public void savePt_DM(DinhMucPhuongTienDto pt, int nnx_id){
        try {
            if (pt.getPhuongtien_id()==0){
                PhuongTien p = phuongtienRepo.save(new PhuongTien(pt.getName_pt(),pt.getQuantity(),nnx_id,pt.getLoaiphuongtien_id(), StatusCons.ACTIVED.getName()));
                dinhMucRepo.save(new DinhMuc(pt.getDm_md_gio(), pt.getDm_tk_gio(), pt.getDm_xm_gio(),pt.getDm_xm_km(),p.getId()));
            }else{
                PhuongTien p = phuongtienRepo.save(new PhuongTien(pt.getPhuongtien_id(),pt.getName_pt(),pt.getQuantity(),nnx_id,pt.getLoaiphuongtien_id(), StatusCons.ACTIVED.getName()));
                DinhMuc dm = dinhMucRepo.findDinhmucByPhuongtien(p.getId(), LocalDate.now().getYear()).orElse(null);
                if (dm!=null){
                    dinhMucRepo.save(new DinhMuc(dm.getId(),pt.getDm_md_gio(), pt.getDm_tk_gio(), pt.getDm_xm_gio(),pt.getDm_xm_km(),p.getId()));
                } else {
                    dinhMucRepo.save(new DinhMuc(pt.getDm_md_gio(), pt.getDm_tk_gio(), pt.getDm_xm_gio(),pt.getDm_xm_km(),p.getId()));
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            DialogMessage.message(null,null,"An error has occurred", Alert.AlertType.ERROR);
        }
    }
}
