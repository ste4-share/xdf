package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.DefaultVarCons;
import com.xdf.xd_f371.cons.LoaiPTEnum;
import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.XmtDto;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.entity.UnitXmt;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.repo.HanmucNhiemvuTauBayRepo;
import com.xdf.xd_f371.repo.LoaiPhuongTienRepo;
import com.xdf.xd_f371.repo.PhuongtienRepo;
import com.xdf.xd_f371.repo.UnitXmtRepo;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PhuongtienService {
    private final PhuongtienRepo phuongtienRepo;
    private final LoaiPhuongTienRepo loaiPhuongTienRepo;
    private final UnitXmtRepo unitXmtRepo;
    private final HanmucNhiemvuTauBayRepo hanmucNhiemvuTauBayRepo;

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
    }public LoaiPhuongTien findLptById(int tn){
        if (loaiPhuongTienRepo.findByLptId(tn).isPresent()){
            return loaiPhuongTienRepo.findByLptId(tn).get();
        }
        return null;
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
    public void createNewXmtUnit(UnitXmt xmt,String pt_name,int lpt_id,String lpt){
        try {
            PhuongTien pt= phuongtienRepo.save(new PhuongTien(pt_name,xmt.getUnit_id(),lpt_id,StatusCons.ACTIVED.getName(),lpt, CommonFactory.getTypeOFPhuongtien().get(lpt)));
            xmt.setXmt_id(pt.getId());
            UnitXmt u = unitXmtRepo.save(xmt);
            if (pt.getTinhchat().equals(LoaiPTEnum.MAYBAY.getNameVehicle())){
                DashboardController.ctnv_ls.forEach(ctnv->{
                    hanmucNhiemvuTauBayRepo.save(new NhiemvuTaubay(DashboardController.ref_Dv.getId(),xmt.getXmt_id(),ctnv.getId(),
                            DefaultVarCons.GIO_HD.getName(),DefaultVarCons.GIO_HD.getName(),0L, LocalDate.now().getYear(),u.getId()));
                });
            }
            DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
