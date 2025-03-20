package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.InventoryUnits;
import com.xdf.xd_f371.repo.InventoryUnitsRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    @Transactional
    public List<InventoryUnits> getInventoryByUnitByPetro(Long root_id,int petroId) {
        int current_year = LocalDate.now().getYear();

        if (inventoryUnitsRepo.getInventoryByUnitByPetro(root_id,petroId,current_year).isEmpty()){
            List<InventoryUnits> lastY_inv = inventoryUnitsRepo.getInventoryByUnitByPetro(root_id,petroId,current_year-1);
            if (lastY_inv.isEmpty()){
                return new ArrayList<>();
            }
            lastY_inv.stream().forEach(x->{
                x.setYear(current_year);

            });
        }
        return new ArrayList<>();
    }
    public Optional<InventoryUnits> getInventoryByUnitByPetroByPrice(Long root_id, int petroId, double price){
        return inventoryUnitsRepo.getInventoryByUnitByPetroByPrice(root_id,petroId,price);
    }
    public void updateQuantityForPetro(double nvdx,double sscd,Long root_id,int petro_id,double price){
        inventoryUnitsRepo.updateQuantityForPetro(nvdx,sscd,root_id,petro_id,price);
    }
}
