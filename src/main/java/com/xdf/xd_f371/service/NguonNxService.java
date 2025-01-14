package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.NguonNxRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
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
    public List<NguonNx> findByStatusUnlessTructhuoc(){
        return nguonNxRepo.findByStatusUnlessTructhuoc();
    }
    public Optional<NguonNx> findByTen(String ten){
        return nguonNxRepo.findByTen(ten);
    }
    public List<NguonNx> findByAllBy(){
        return nguonNxRepo.findByAllBy();
    }
    public Optional<NguonNx> findAllByNguonnxId(int id){
        return nguonNxRepo.findAllByNguonnxId(id);
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
    @Transactional
    public void saveNnxAndLedger(NguonNx n, TrucThuoc tt){
        try {
            nguonNxRepo.save(new NguonNx(n.getId(),n.getTen(),n.getStatus(),
                    n.getCode(),n.getTructhuoc_id()));
            ledgersRepo.updateTrucThuocFromNxx(n.getId(),tt.getType());
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
