package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.repo.HanmucNhiemvu2Repository;
import com.xdf.xd_f371.repo.HanmucNhiemvuTauBayRepo;
import com.xdf.xd_f371.util.DialogMessage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
    public List<HanmucNhiemvu2> findAllByYearHmnv(int y){
        return hanmucNhiemvu2Repository.findAllByYear(y);
    }
    public Optional<HanmucNhiemvu2> findByUnique(int y,int ctnv){
        return hanmucNhiemvu2Repository.findByUnique(y,ctnv);
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
    public List<NhiemvuTaubay> findAllByYear(int year){
        return hanmucNhiemvuTauBayRepo.findAllByYear(year);
    }
    public List<Integer> findAllYearByYear(){
        return hanmucNhiemvuTauBayRepo.findAllYearByYear();
    }
    public List<NguonNx> getAllDviTructhuocByTaubay(int taubayId, int y){
        return hanmucNhiemvuTauBayRepo.getAllDviTructhuocByTaubay(taubayId,y);
    }
    public Optional<HanmucNhiemvuTaubayDto> findHmUnique(int y,int pid,int nv_id,int dvi_id){
        return hanmucNhiemvuTauBayRepo.findHmUnique(y,pid,nv_id,dvi_id);
    }
    @Transactional
    public void switchNhiemvuTauBay(){
        List<NhiemvuTaubay> previousDm = findAllByYear((LocalDate.now().getYear()-1));
        if (!previousDm.isEmpty()){
            previousDm.forEach(x->{
                hanmucNhiemvuTauBayRepo.save(new NhiemvuTaubay(x.getDviXuatId(),x.getPt_id(),x.getCtnv_id(),x.getTk(),x.getMd(),x.getNhienlieu()));
            });
            DialogMessage.successShowing("Chuyen doi thanh cong");
        } else {
            DialogMessage.errorShowing("Khong co du lieu Nhiem vu tau bay tu nam "+(LocalDate.now().getYear()-1));
        }
    }
    @Transactional
    public void switchHanmucNhiemvu(){
        List<HanmucNhiemvu2> previousDm = findAllByYearHmnv((LocalDate.now().getYear()-1));
        if (!previousDm.isEmpty()){
            previousDm.forEach(x->{
                hanmucNhiemvu2Repository.save(new HanmucNhiemvu2(x.getDvi_id(),x.getNhiemvu_id(),x.getDiezel(),x.getDaubay(),x.getXang()));
            });
            DialogMessage.successShowing("Chuyen doi thanh cong");
        } else {
            DialogMessage.errorShowing("Khong co du lieu Han muc nhiem vu tu nam "+(LocalDate.now().getYear()-1));
        }
    }
}
