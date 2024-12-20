package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.repo.InventoryRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public List<Inventory> findByQuarter_id(int quarter_id){
        return inventoryRepo.findByQuarter_id(quarter_id);
    }
    public Optional<Inventory> findByPetro_idAndQuarter_id( int petro_id, int quarter_id){
        return inventoryRepo.findByPetro_idAndQuarter_id(petro_id, quarter_id);
    }
    public Inventory save(Inventory inventory){
        return inventoryRepo.save(inventory);
    }
    public Inventory findById(int id){
        return inventoryRepo.findById(id).orElse(null);
    }
    public List<SpotDto> getAllSpots(int quarter_id){
        return inventoryRepo.getAllSpots(quarter_id);
    }
}
