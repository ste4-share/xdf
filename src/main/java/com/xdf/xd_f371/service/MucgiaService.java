package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.repo.MucGiaRepo;
import lombok.RequiredArgsConstructor;
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
    public List<Mucgia> findAllMucgiaByItemID(int itemID,int quarter_id,String s){
        return mucGiaRepo.findAllMucgiaByItemID(itemID,quarter_id,s);
    }
    public Optional<Mucgia> findAllMucgiaUnique(int itemID,int quarter_id,int price){
        return mucGiaRepo.findAllMucgiaUnique(itemID,quarter_id,price);
    }


    public Mucgia save(Mucgia mucgia){
        return mucGiaRepo.save(mucgia);
    }
}
