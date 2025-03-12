package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.Tcn;
import com.xdf.xd_f371.repo.TcnRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TcnService {
    private final TcnRepo tcnRepo;

    public Optional<Tcn> findByName(String name){
        return tcnRepo.findByName(name);
    }
    public Tcn findByMaTcn(String name){
        if (tcnRepo.findByMaTcn(name).isPresent()){
            return tcnRepo.findByMaTcn(name).get();
        }
        return null;
    }
    public List<Tcn> findByLoaiphieu(String loaiphieu){
        return tcnRepo.findByLoaiphieu(loaiphieu);
    }
    public Tcn save(Tcn tcn){
        return tcnRepo.save(tcn);
    }
    public void delete(Tcn tcn){
        tcnRepo.delete(tcn);
    }
    public Optional<Tcn> findById(int id){
        return tcnRepo.findById(id);
    }
    public List<Tcn> findAll(){
        return tcnRepo.findAll();
    }
}
