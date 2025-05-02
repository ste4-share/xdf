package com.xdf.xd_f371.service;

import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class InventoryService {
    private final InventoryRepo inventoryRepo;

    public Inventory save(Inventory inventory){
        return inventoryRepo.save(inventory);
    }
    public Inventory findById(int id){
        return inventoryRepo.findById(id).orElse(null);
    }
    public List<InventoryDto> mapPreInventoryPetro(List<Object[]> results) {
        return results.stream()
                .map(row -> new InventoryDto((int) row[0],(String) row[1],(double) row[2],(double) row[3],
                        (double) row[4],(double) row[5],(double) row[6]))
                .toList();
    }
    public List<PttkDto> mapPttkPetro() {
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> pttk = reportDAO.findByWhatEver(SubQuery.bc_pttk_q());
        return pttk.stream()
                .map(row -> new PttkDto((String) row[1],(String) row[2],(double) row[3],(double) row[4],
                        (double) row[5],(double) row[6],(double) row[7],(double) row[8],(double) row[9],
                        (double) row[10],(double) row[11]))
                .toList();
    }

    public List<InventoryDto> findPreInventoryPetroFollowUnit(int petro_id, int id){
        if (!inventoryRepo.findPreInventoryPetro(petro_id,id).isEmpty()){
            return mapPreInventoryPetro(inventoryRepo.findPreInventoryPetro(petro_id,id));
        }
        return null;
    }
}
