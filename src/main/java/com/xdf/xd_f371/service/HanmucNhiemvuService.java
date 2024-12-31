package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.repo.HanmucNhiemvu2Repository;
import com.xdf.xd_f371.repo.HanmucNhiemvuTauBayRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class HanmucNhiemvuService {

    private final HanmucNhiemvu2Repository hanmucNhiemvu2Repository;
    private final HanmucNhiemvuTauBayRepo hanmucNhiemvuTauBayRepo;

    public List<HanmucNhiemvu2Dto> findAllDto(int y){
        return hanmucNhiemvu2Repository.findAllDto(y);
    }
    public Optional<HanmucNhiemvu2> findByUnique(int q,int ctnv){
        return hanmucNhiemvu2Repository.findByUnique(q,ctnv);
    }
    public HanmucNhiemvu2 save(HanmucNhiemvu2 hanmucNhiemvu2){
        return hanmucNhiemvu2Repository.save(hanmucNhiemvu2);
    }
    public List<HanmucNhiemvuTaubayDto> getAllByYear(int y){
        return hanmucNhiemvuTauBayRepo.getAllByYear(y);
    }
    public NhiemvuTaubay save(NhiemvuTaubay nhiemvuTaubay){
        return hanmucNhiemvuTauBayRepo.save(nhiemvuTaubay);
    }

    public List<NguonNx> getAllDviTructhuocByTaubay(int taubayId, int quyID){
        return hanmucNhiemvuTauBayRepo.getAllDviTructhuocByTaubay(taubayId,quyID);
    }
    public Optional<HanmucNhiemvuTaubayDto> findHmUnique(int y,int pid,int nv_id,int dvi_id){
        return hanmucNhiemvuTauBayRepo.findHmUnique(y,pid,nv_id,dvi_id);
    }
}
