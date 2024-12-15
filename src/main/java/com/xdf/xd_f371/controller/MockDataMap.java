package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.service.MucgiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockDataMap {
    @Autowired
    private LoaiXdService loaiXdService;
    @Autowired
    private MucgiaService mucgiaService;

    public void mockInventoryData(){
        for (LoaiXangDau loaiXangDau : loaiXdService.findAll()) {
            Mucgia mucgia = new Mucgia();
            mucgia.setQuarter_id(DashboardController.findByTime.getId());
            mucgia.setAmount(40000);
            mucgia.setPrice(142857);
            mucgia.setItem_id(loaiXangDau.getId());
            mucgia.setNvdx(20000L);
            mucgia.setSscd(20000L);
            mucgia.setStatus("IN_STOCK");
            mucgiaService.save(mucgia);
        }
    }

}
