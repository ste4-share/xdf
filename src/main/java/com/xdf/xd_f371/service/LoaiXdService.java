package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.ChungLoaiXd;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.repo.ChungLoaiXdRepo;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoaiXdService {
    private final ChungLoaiXdRepo chungLoaiXdRepo;
    private final LoaiXangDauRepo loaiXangDauRepo;
    private final InventoryService inventoryService;

    public List<LoaiXangDauDto> findAllBy(){
        return loaiXangDauRepo.findAllBy();
    }
    public Optional<LoaiXangDauDto> findById(int id){
        return loaiXangDauRepo.findById(id);
    }
    public Optional<LoaiXangDau> findByMaxd(String mxd){
        return loaiXangDauRepo.findByMaxd(mxd);
    }
    public List<LoaiXangDauDto> findAllOrderby(){
        return loaiXangDauRepo.findAllOrderby();
    }
    public Optional<LoaiXangDauDto> findAllTenxdDto(String name){
        return loaiXangDauRepo.findAllTenxdDto(name);
    }
    public List<LoaiXangDauDto> findByType(String code1,String code2){
        return loaiXangDauRepo.findByType(code1,code2);
    }public List<LoaiXangDauDto> findByTypeNAme(String code){
        return loaiXangDauRepo.findByTypeName(code);
    }
    public List<LoaiXangDau> findAll(){
        return loaiXangDauRepo.findAll();
    }
    public Optional<LoaiXangDau> save(LoaiXangDau loaiXangDau){
        return Optional.of(loaiXangDauRepo.save(loaiXangDau));
    }

    public List<ChungLoaiXd> findAllChungLoaiXd(){
        return chungLoaiXdRepo.findAll();
    }
    public Optional<ChungLoaiXd> findByCode(String code){
        return chungLoaiXdRepo.findByCode(code);
    }

    @Transactional
    public boolean saveLxdAndInventory(LoaiXangDau loaiXangDau,int tdknvdx,int tdk_sscd){
        Optional<LoaiXangDau> lxd = this.save(loaiXangDau);
        if (lxd.isPresent()){
            inventoryService.save(new Inventory(lxd.get().getId(),tdknvdx,tdk_sscd,0,0,0,0, MucGiaEnum.IN_STOCK.getStatus(), 0,
                    DashboardController.findByTime.getStart_date(),DashboardController.findByTime.getEnd_date()));
            return true;
        }
        return false;
    }
}
