package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.XmtDto;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.entity.UnitXmt;
import com.xdf.xd_f371.repo.DinhMucRepo;
import com.xdf.xd_f371.repo.LoaiPhuongTienRepo;
import com.xdf.xd_f371.repo.PhuongtienRepo;
import com.xdf.xd_f371.repo.UnitXmtRepo;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhuongtienService {
    private final PhuongtienRepo phuongtienRepo;
    private final LoaiPhuongTienRepo loaiPhuongTienRepo;
    private final UnitXmtRepo unitXmtRepo;

    public List<PhuongTien> findPhuongTienByLoaiPhuongTien(String loaiPhuongTien,int dvi_id){
        return phuongtienRepo.findPhuongTienByLoaiPhuongTien(loaiPhuongTien,dvi_id);
    }
    public List<XmtDto> findXmtByType(String lpt,int dvid){
        return mapXmt(phuongtienRepo.findXmtByType(lpt,dvid));
    }
    public List<XmtDto> findAllXmtByType(String lpt){
        return mapXmt(phuongtienRepo.findXmtByTypeAll(lpt));
    }
    public List<XmtDto> mapXmt(List<Object[]> results) {
        return results.stream()
                .map(row -> new XmtDto((int) row[0],(String)row[1],(String)row[2],(Timestamp) row[3],(Integer) row[4]))
                .toList();
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

    @Transactional
    public void updateXmtUnit(UnitXmt xmt){
        try {
            unitXmtRepo.updateUnitXmtByPtAndUnit(xmt.getNote(),xmt.getUnit_id(),xmt.getXmt_id(),xmt.getDm_hours(),xmt.getDm_km(),xmt.getDm_md(),xmt.getDm_tk(),xmt.getLicence_plate_number(),xmt.getStatus());
            DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Transactional
    public void createNewXmtUnit(UnitXmt xmt,String pt_name,int lpt_id){
        try {
            PhuongTien pt= phuongtienRepo.save(new PhuongTien(pt_name,1,xmt.getUnit_id(),lpt_id,StatusCons.ACTIVED.getName()));
            xmt.setXmt_id(pt.getId());
            unitXmtRepo.save(xmt);
            DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
