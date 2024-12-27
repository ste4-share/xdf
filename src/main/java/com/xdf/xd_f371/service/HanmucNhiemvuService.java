package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.NhiemvuController;
import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.repo.HanmucNhiemvu2Repository;
import com.xdf.xd_f371.repo.HanmucNhiemvuTauBayRepo;
import com.xdf.xd_f371.util.DialogMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HanmucNhiemvuService {

    private final HanmucNhiemvu2Repository hanmucNhiemvu2Repository;
    private final HanmucNhiemvuTauBayRepo hanmucNhiemvuTauBayRepo;

    public List<HanmucNhiemvu2Dto> findAllDto(int q){
        return hanmucNhiemvu2Repository.findAllDto(q);
    }
    public Optional<HanmucNhiemvu2> findByUnique(int q,int ctnv){
        return hanmucNhiemvu2Repository.findByUnique(q,ctnv);
    }
    public HanmucNhiemvu2 save(HanmucNhiemvu2 hanmucNhiemvu2){
        return hanmucNhiemvu2Repository.save(hanmucNhiemvu2);
    }
    public List<HanmucNhiemvuTaubayDto> getAllBy(){
        return hanmucNhiemvuTauBayRepo.getAllBy();
    }
    public NhiemvuTaubay save(NhiemvuTaubay nhiemvuTaubay){
        return hanmucNhiemvuTauBayRepo.save(nhiemvuTaubay);
    }

    public List<NguonNx> getAllDviTructhuocByTaubay(int taubayId, int quyID){
        return hanmucNhiemvuTauBayRepo.getAllDviTructhuocByTaubay(taubayId,quyID);
    }

}
