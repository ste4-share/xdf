package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.TructhuocRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NguonNxService {
    private final NguonNxRepo nguonNxRepo;
    private final LedgersRepo ledgersRepo;
    private final TructhuocRepo tructhuocRepo;

    public List<NguonNx> findAllById(int id){
        return nguonNxRepo.findAllById(id);
    }
    public List<NguonNx> findAllByDifrentId(int id){
        return nguonNxRepo.findAllByDifrentId(id);
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
        return nguonNxRepo.findById(id);
    }
    public NguonNx save(NguonNx nguonNx){
        return nguonNxRepo.save(nguonNx);
    }
    @Transactional
    public void saveNnxAndLedger(NguonNx n, TrucThuoc tt){
        try {
            nguonNxRepo.save(new NguonNx(n.getId(),n.getTen(),n.getStatus(),
                    n.getCode(),n.getTructhuoc_id()));
            tructhuocRepo.save(tt);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
