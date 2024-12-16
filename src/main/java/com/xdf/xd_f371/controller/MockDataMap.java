package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.service.MucgiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockDataMap{
    @Autowired
    private static LoaiXdService loaiXdService;
    @Autowired
    private static MucgiaService mucgiaService;

    public static void mockInventoryData(){

    }

}
