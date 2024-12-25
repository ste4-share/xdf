package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.repo.QuarterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuarterService {
    private final QuarterRepository quarterRepository;

    public Optional<Quarter> findByCurrentTime(LocalDate currentTime){
        return quarterRepository.findByCurrentTime(currentTime);
    }
    public Optional<Quarter> findByName(String name){
        return quarterRepository.findByName(name);
    }
    public List<Quarter> findAllByYear(String year){
        return quarterRepository.findByYear(year);
    }
    public List<Quarter> findAll(){
        return quarterRepository.findAll();
    }
    public Quarter save(Quarter quarter){
        return quarterRepository.save(quarter);
    }
    public Optional<Quarter> findByUnique(String year,int i){
        return quarterRepository.findByUnique(year,i);
    }
}
