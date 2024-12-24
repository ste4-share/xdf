package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.ChitietNhiemVuDto;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.dto.NhiemvuTeamDto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.LoaiNhiemVu;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.entity.Team;
import com.xdf.xd_f371.repo.ChitietNhiemvuRepo;
import com.xdf.xd_f371.repo.LoaiNhiemvuRepo;
import com.xdf.xd_f371.repo.NhiemvuRepository;
import com.xdf.xd_f371.repo.TeamRepo;
import lombok.RequiredArgsConstructor;
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

    public List<NhiemVuDto> findAllDtoBy(String lnv){
        return chitietNhiemvuRepo.findAllDtoBy(lnv);
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
    public Optional<NhiemVuDto> findAllByChitietNhiemvu(String nv, String chitiet){
        return chitietNhiemvuRepo.findAllByChitietNhiemvu(nv,chitiet);
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
    }public Optional<NhiemVu> findByName(String n){
        return nhiemvuRepository.findByName(n);
    }
    public Optional<NhiemVu> findByIdNhiemvu(int id){
        return nhiemvuRepository.findById(id);
    }
    public List<NhiemvuTeamDto> findByTeam(){
        return nhiemvuRepository.findByTeam();
    }
    public void saveNhiemvu(int team_id, String nv, String lnv,String ct){
        Optional<LoaiNhiemVu> l = findLoaiNvByName(lnv);
        if (l.isPresent()){
            Optional<NhiemVu> n_v = nhiemvuRepository.findByName(nv);
            if (n_v.isPresent()){
                chitietNhiemvuRepo.save(new ChitietNhiemVu(n_v.get().getId(),n_v.get().getTenNv()));
            } else {
                NhiemVu n = nhiemvuRepository.save(new NhiemVu(nv, StatusCons.ACTIVED.getName(),team_id,l.get().getId(),99,99));
                chitietNhiemvuRepo.save(new ChitietNhiemVu(n.getId(),n.getTenNv()));
            }
        } else {
            LoaiNhiemVu new_lnv = loaiNhiemvuRepo.save(new LoaiNhiemVu(lnv));
            NhiemVu n = nhiemvuRepository.save(new NhiemVu(nv, StatusCons.ACTIVED.getName(),team_id,new_lnv.getId(),99,99));
            chitietNhiemvuRepo.save(new ChitietNhiemVu(n.getId(),n.getTenNv()));
        }
    }
}
