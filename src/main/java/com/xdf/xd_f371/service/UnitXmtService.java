package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.UnitXmt;
import com.xdf.xd_f371.repo.UnitXmtRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UnitXmtService {
    private final UnitXmtRepo unitXmtRepo;

    public UnitXmt save(UnitXmt u){
        return unitXmtRepo.save(u);
    }

    public List<UnitXmt> getAll(){
        return unitXmtRepo.findAll();
    }
    public List<UnitXmt> findByUnitId(int unit_id){
        return unitXmtRepo.findByUnitId(unit_id);
    }
    public List<UnitXmt> findAllByMaybay(int unit_id){
        return unitXmtRepo.findAllByMaybay(unit_id);
    }
    public List<UnitXmt> findByUnitIdAndPtId(int unit_id, int pt_id){
        return unitXmtRepo.findByUnitIdPtId(unit_id,pt_id);
    }public List<String> findXmtIdList(){
        return unitXmtRepo.findXmtIdList();
    }
    public UnitXmt findByLicensePlate(String license){
        if (unitXmtRepo.findByLicensePlate(license).isPresent()){
            return unitXmtRepo.findByLicensePlate(license).get();
        }
        return null;
    }
}
