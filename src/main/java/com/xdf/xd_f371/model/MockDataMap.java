package com.xdf.xd_f371.model;

import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.InventoryRepo;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import com.xdf.xd_f371.repo.MucGiaRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MockDataMap {


    @Autowired
    private static LoaiXangDauRepo loaiXangDauRepo;

    @Autowired
    private static MucGiaRepo mucGiaRepo;
    @Autowired
    private static InventoryRepo inventoryRepo;
    public static void mockInventoryData(){
        int tondk_nvdx_mock = 20000;
        int tondk_sscd_mock = 20000;
        for (LoaiXangDau loaiXangDau : loaiXangDauRepo.findAll()) {

            Inventory inventory = new Inventory();
            inventory.setPetro_id(loaiXangDau.getId());
            inventory.setQuarter_id(DashboardController.findByTime.getId());
            inventory.setTdk_nvdx(tondk_nvdx_mock);
            inventory.setTdk_sscd(tondk_sscd_mock);
            inventory.setPre_nvdx(tondk_nvdx_mock);
            inventory.setPre_sscd(tondk_sscd_mock);

            inventory.setTdk_sscd(tondk_sscd_mock);
            inventory.setStatus("RECORDING");
            inventoryRepo.save(inventory);

            Inventory inventory1 = inventoryRepo.findByPetro_idAndQuarter_id(loaiXangDau.getId(), DashboardController.findByTime.getId()).orElse(null);
            //mock mucgia
            Mucgia mucgia = new Mucgia();
            mucgia.setQuarter_id(DashboardController.findByTime.getId());
            mucgia.setAmount(tondk_nvdx_mock);
            mucgia.setPrice(142857);
            mucgia.setItem_id(loaiXangDau.getId());
            mucgia.setPurpose(Purpose.NVDX.getName());
            mucgia.setStatus("IN_STOCK");
            mucgia.setInventory_id(inventory1.getId());
            mucGiaRepo.save(mucgia);
            Mucgia mucgia2 = new Mucgia();
            mucgia2.setQuarter_id(DashboardController.findByTime.getId());
            mucgia2.setAmount(tondk_nvdx_mock);
            mucgia2.setPrice(142857);
            mucgia2.setItem_id(loaiXangDau.getId());
            mucgia2.setPurpose(Purpose.SSCD.getName());
            mucgia2.setStatus("IN_STOCK");
            mucgia2.setInventory_id(inventory1.getId());
            mucGiaRepo.save(mucgia2);

        }
    }

}
