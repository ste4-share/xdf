package com.xdf.xd_f371.service;

import com.xdf.xd_f371.dto.QuarterDto;
import com.xdf.xd_f371.entity.Configuration;
import com.xdf.xd_f371.repo.ConfigurationRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigurationService {
    private final ConfigurationRepo configurationRepo;
    public Optional<Configuration> findByParam(String p){
        return configurationRepo.findByParameter(p);
    }
    public List<QuarterDto> getListQuarter(int y){
        return mapQuarter(this.configurationRepo.getListQuarter(y));
    }
    private List<QuarterDto> mapQuarter(List<Object[]> ls){
        List<QuarterDto> quarterDtos = new ArrayList<>();
        ls.forEach(x-> {
            quarterDtos.add(new QuarterDto((int) x[0],(int) x[1],(Timestamp) x[2],(Timestamp) x[3]));
        });
        return quarterDtos;
    }
    @Transactional
    public void updateValueByParam(String p,String val){
        configurationRepo.updateConfigByParam(p,val);
    }
}
