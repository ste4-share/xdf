package com.xdf.xd_f371.service;

import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.NguonNxRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NguonNxService {
    private final NguonNxRepo nguonNxRepo;
    private final LedgersRepo ledgersRepo;

    public List<NguonNx> findByStatus(String status){
        return nguonNxRepo.findByStatus(status);
    }
    public Optional<NguonNx> findByTen(String ten){
        return nguonNxRepo.findByTen(ten);
    }
    public List<NguonNx> findByAllBy(){
        return nguonNxRepo.findByAllBy();
    }

    public List<NguonNx> findAll(){
        return nguonNxRepo.findAll();
    }
    public Optional<NguonNx> findById(int id){
        return Optional.ofNullable(nguonNxRepo.findById(id).orElse(null));
    }
    public NguonNx save(NguonNx nguonNx){
        return nguonNxRepo.save(nguonNx);
    }

    public void saveNnxAndLedger(NguonNx n, TrucThuoc tt){
        try {
            nguonNxRepo.save(new NguonNx(n.getId(),n.getTen(),n.getStatus(),
                    n.getCode(),n.getTructhuoc_id()));
            ledgersRepo.updateTrucThuocFromNxx(n.getId(),tt.getType(), DashboardController.findByTime.getStart_date(),DashboardController.findByTime.getEnd_date());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
