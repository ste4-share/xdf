package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.ChitietNhiemvuRepo;
import com.xdf.xd_f371.repo.LoaiNhiemvuRepo;
import com.xdf.xd_f371.repo.NhiemvuRepository;
import com.xdf.xd_f371.repo.TeamRepo;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public List<NhiemVuDto> findAllDtoBy(String lnv){
        return chitietNhiemvuRepo.findAllDtoBy(lnv);
    }
    public NhiemVuDto findAllDtoByTenNv(String ten){
        if (chitietNhiemvuRepo.findAllDtoByTenNv(ten).isPresent()){
            chitietNhiemvuRepo.findAllDtoByTenNv(ten).get();
        }
        return null;
    }

    public List<NhiemVuDto> findAllBy(){
        return chitietNhiemvuRepo.findAllBy();
    }
    public Optional<NhiemVuDto> findByTenNhiemvuDto(String tennv){
        return chitietNhiemvuRepo.findByTenNv(tennv);
    }
    public List<NhiemVuDto> findAllDtoById(String lnv){
        return chitietNhiemvuRepo.findAllDtoById(lnv);
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
    public Optional<NhiemVu> findByName(String n,String status){
        return nhiemvuRepository.findByName(n,status);
    }
    public void saveNhiemvu(int ctnv_id,int team_id, String nv, LoaiNhiemVu lnv,String ct,NguonNx nx,String x,String diezel_tf,String d,String hacap){
        Optional<NhiemVu> n_v = nhiemvuRepository.findByName(nv,StatusCons.ACTIVED.getName());
        if (n_v.isPresent()){
            Optional<ChitietNhiemVu> ctnv = chitietNhiemvuRepo.findByNhiemvu(ct,n_v.get().getId());
            if (ctnv.isPresent()){
                DialogMessage.errorShowing("Nhiem vu da ton tai.");
            }else{
                ChitietNhiemVu ct2 = chitietNhiemvuRepo.save(new ChitietNhiemVu(ctnv_id,n_v.get().getId(),ct));
                hanmucNhiemvuService.save(new HanmucNhiemvu2(nx.getId(),ct2.getId(),
                        Double.parseDouble(diezel_tf),Double.parseDouble(d),Double.parseDouble(x),Double.parseDouble(hacap)));
                DialogMessage.message(null, "Them thanh cong",
                        null, Alert.AlertType.INFORMATION);
            }
        }else{
            NhiemVu n = nhiemvuRepository.save(new NhiemVu(nv, StatusCons.ACTIVED.getName(),team_id,lnv.getId(),99,99));
            ChitietNhiemVu ct2 = chitietNhiemvuRepo.save(new ChitietNhiemVu(ctnv_id,n.getId(),ct));
            hanmucNhiemvuService.save(new HanmucNhiemvu2(nx.getId(),ct2.getId(),
                    Double.parseDouble(diezel_tf),Double.parseDouble(d),Double.parseDouble(x),Double.parseDouble(hacap)));
            DialogMessage.message(null, "Them thanh cong",
                    null, Alert.AlertType.INFORMATION);
        }

    }
}
