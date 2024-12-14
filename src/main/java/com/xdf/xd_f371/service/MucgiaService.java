package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.repo.MucGiaRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MucgiaService {
    private final MucGiaRepo mucGiaRepo;

    public Optional<Mucgia> findMucGiaByIdAndStatus(int id, String status){
        return mucGiaRepo.findMucGiaByIdAndStatus(id,status);
    }
    public List<Mucgia> findAllMucgiaByItemID(String purpose,int itemID,int quarter_id){
        return mucGiaRepo.findAllMucgiaByItemID(purpose,itemID,quarter_id);
    }
    public Optional<Mucgia> findAllMucgiaUnique(@Param("pur") String purpose,@Param("petroId") int itemID,@Param("qId") int quarter_id, @Param("price") int price){
        return mucGiaRepo.findAllMucgiaUnique(purpose,itemID,quarter_id,price);
    }
    public List<SpotDto> getAllSpots(int quarter_id){
        return mucGiaRepo.getAllSpots(quarter_id);
    }

    public Mucgia save(Mucgia mucgia){
        return mucGiaRepo.save(mucgia);
    }
}
