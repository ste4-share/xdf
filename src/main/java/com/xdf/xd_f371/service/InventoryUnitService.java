package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.InventoryUnits;
import com.xdf.xd_f371.repo.InventoryUnitsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryUnitService {
    private final InventoryUnitsRepo inventoryUnitsRepo;
    public InventoryUnits save(InventoryUnits inv){
        return this.inventoryUnitsRepo.save(inv);
    }
    public List<InventoryUnits> getInventoryByUnit(Long root_id){
        return inventoryUnitsRepo.getInventoryByUnit(root_id);
    }
    public List<InventoryUnits> getInventoryByUnitByPetro(Long root_id,int petroId){
        return inventoryUnitsRepo.getInventoryByUnitByPetro(root_id,petroId);
    }
    public Optional<InventoryUnits> getInventoryByUnitByPetroByPrice(Long root_id, int petroId, double price){
        return inventoryUnitsRepo.getInventoryByUnitByPetroByPrice(root_id,petroId,price);
    }
    public void updateQuantityForPetro(double nvdx,double sscd,Long root_id,int petro_id,double price){
        inventoryUnitsRepo.updateQuantityForPetro(nvdx,sscd,root_id,petro_id,price);
    }
}
