package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.DefaultVarCons;
import com.xdf.xd_f371.cons.LoaiNVCons;
import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.ChitietNhiemvuRepo;
import com.xdf.xd_f371.repo.LoaiNhiemvuRepo;
import com.xdf.xd_f371.repo.NhiemvuRepository;
import com.xdf.xd_f371.repo.TeamRepo;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChitietNhiemvuService {
    private final ChitietNhiemvuRepo chitietNhiemvuRepo;
    private final NhiemvuRepository nhiemvuRepository;
    private final LoaiNhiemvuRepo loaiNhiemvuRepo;
    private final TeamRepo teamRepo;
    @Autowired
    private HanmucNhiemvuService hanmucNhiemvuService;

    public Optional<NhiemVuDto> findByTenNhiemvuDto(String tennv){
        return chitietNhiemvuRepo.findByTenNv(tennv);
    }
    public List<NhiemVuDto> findAllDtoById(String lnv){
        return chitietNhiemvuRepo.findAllDtoById(lnv);
    }
    public List<NhiemVuDto> findAllDtoById(){
        return chitietNhiemvuRepo.findAllDtoById();
    }
    public List<Team> findAllTeam(){
        return teamRepo.findAll();
    }
    public Team findByCode(String code){
        return teamRepo.findByTeam_code(code);
    }
    public List<LoaiNhiemVu> getAllLoaiNv(){
        return loaiNhiemvuRepo.findAll();
    }
    public Optional<LoaiNhiemVu> findLoaiNvByName(String n){
        return loaiNhiemvuRepo.findLoaiNvByName(n);
    }
    public List<ChitietNhiemVu> findByNhiemvuId(int id){
        return chitietNhiemvuRepo.findByNhiemvuId(id);
    }
    public List<ChitietNhiemVu> findAllCtnvByTypeMaybay(){
        return chitietNhiemvuRepo.findAllCtnv();
    }
    public List<ChitietNhiemVu> findAllCtnv(){
        return chitietNhiemvuRepo.findAll();
    }
    public Optional<ChitietNhiemVu> findByNhiemvu(String tennv, int nvid){
        return chitietNhiemvuRepo.findByNhiemvu(tennv,nvid);
    }

    public Optional<ChitietNhiemVu> findById(int id){
        return chitietNhiemvuRepo.findById(id);
    }

    public List<NhiemVu> findAll(){
        return nhiemvuRepository.findAll();
    }
    public NhiemVu savenv(NhiemVu nv){
        return nhiemvuRepository.save(nv);
    }
    public Optional<NhiemVu> findByName(String n,String status){
        return nhiemvuRepository.findByName(n,status);
    }
    @Autowired
    private UnitXmtService unitXmtService;

    @Transactional
    public void saveNhiemvu(int ctnv_id,NhiemVu nv,String ct,NguonNx nx,String x,String diezel_tf,String d,String hacap){
        if (DashboardController.ctnv_ls_all.stream().anyMatch(y->y.getChitiet().equals(ct))){
            DialogMessage.successShowing("Nhiệm vụ đã được khởi tạo.");
        } else {
            ChitietNhiemVu ct2 = chitietNhiemvuRepo.save(new ChitietNhiemVu(ctnv_id,nv.getId(),ct));
            if (nv.getLoainv().equals(LoaiNVCons.NV_BAY.getName())){
                DashboardController.unitxmt_ls.forEach(xmt -> {
                    hanmucNhiemvuService.save(new NhiemvuTaubay(DashboardController.ref_Dv.getId(),xmt.getXmt_id(),ct2.getId(),
                            DefaultVarCons.GIO_HD.getName(),DefaultVarCons.GIO_HD.getName(),0L, LocalDate.now().getYear(),xmt.getId()));
                });
            }
            hanmucNhiemvuService.save(new HanmucNhiemvu2(nx.getId(),ct2.getId(),
                    Double.parseDouble(diezel_tf),Double.parseDouble(d),Double.parseDouble(x),Double.parseDouble(hacap)));
            DashboardController.unitxmt_ls = unitXmtService.findAllByMaybay(DashboardController.ref_Dv.getId());
            DialogMessage.message(null, MessageCons.THANH_CONG.getName(),
                    null, Alert.AlertType.INFORMATION);
        }
    }
}
