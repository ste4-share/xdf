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
    public Optional<Quarter> findPreviousTime(){
        return quarterRepository.findPreviousTime();
    }
    public Optional<Quarter> findByIndex(String in){
        return quarterRepository.findByIndex(in);
    }
    public List<Quarter> findAllByYear(String year){
        return quarterRepository.findByYear(year);
    }
    public List<Quarter> findAll(){
        return quarterRepository.findAll();
    }
    public List<Quarter> findAllDescSD(){
        return quarterRepository.findAllDescSD();
    }
    public Quarter save(Quarter quarter){
        return quarterRepository.save(quarter);
    }
    public Optional<Quarter> findByUnique(String year,String i){
        return quarterRepository.findByUnique(year,i);
    }
}
