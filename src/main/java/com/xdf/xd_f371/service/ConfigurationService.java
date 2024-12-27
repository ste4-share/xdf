package com.xdf.xd_f371.service;

import com.xdf.xd_f371.entity.Configuration;
import com.xdf.xd_f371.repo.ConfigurationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConfigurationService {
    private final ConfigurationRepo configurationRepo;
    public Optional<Configuration> findByParam(String p){
        return configurationRepo.findByParameter(p);
    }
}
