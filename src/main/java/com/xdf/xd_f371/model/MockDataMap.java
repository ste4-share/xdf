package com.xdf.xd_f371.model;

import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.QuantityByTTDTO;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.InventoryRepo;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import com.xdf.xd_f371.repo.MucGiaRepo;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import com.xdf.xd_f371.util.Common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MockDataMap {

    private static InvReportDetailService invReportDetailService = new invReportDetailImp();

    @Autowired
    private static LoaiXangDauRepo loaiXangDauRepo;

    @Autowired
    private static MucGiaRepo mucGiaRepo;
    @Autowired
    private static InventoryRepo inventoryRepo;

    public static void initInventoryMap(){
        invReportDetailService.deleteAll();
        List<LoaiXangDau> loaiXangDauList = loaiXangDauRepo.findAll();
//        try {
//            int quarter_id = DashboardController.findByTime.getId();
//            for (int i =0; i< loaiXangDauList.size(); i++){
//                Inventory inventory = inventoryRepo.findByPetro_idAndQuarter_id(loaiXangDauList.get(i).getId(), quarter_id).orElse(null);
//                for (int j=0; j< categories.size(); j++){
//                    Category catelos = categories.get(j);
//                    InvReportDetail invReportDetail = new InvReportDetail();
//                    Common.getInvCatalogField(catelos, inventory, invReportDetail);
//                    if (catelos.getCode().equals("NHAP")){
//                        QuantityByTTDTO quantity = ledgerDetailsService.selectQuantityNguonnx(2,"NHAP",catelos.getTructhuoc_id(),loaiXangDauList.get(i).getId());
//                        if (quantity==null){
//                            invReportDetail.setSoluong(0);
//                        }else{
//                            invReportDetail.setSoluong(quantity.getSum());
//                        }
//
//                    } else if(catelos.getCode().equals("XUAT")) {
//                        QuantityByTTDTO quantity = ledgerDetailsService.selectQuantityNguonnxImport(2,"XUAT",catelos.getTructhuoc_id(),loaiXangDauList.get(i).getId());
//                        if (quantity==null){
//                            invReportDetail.setSoluong(0);
//                        }else{
//                            invReportDetail.setSoluong(quantity.getSum());
//                        }
//                    }
//                    //inventory detail
//                    invReportDetail.setLoaixd(loaiXangDauList.get(i).getTenxd());
//                    invReportDetail.setTitle_lv1(catelos.getHeader_lv1());
//                    invReportDetail.setTitle_lv2(catelos.getHeader_lv2());
//                    invReportDetail.setTitle_lv3(catelos.getHeader_lv3());
//
//                    invReportDetail.setXd_id(loaiXangDauList.get(i).getId());
//                    invReportDetail.setQuarter_id(quarter_id);
//                    invReportDetail.setTitle_id(catelos.getId());
//                    invReportDetailService.createNew(invReportDetail);
//                }
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
    }

    public static void mockInventoryData(){
        int tondk_nvdx_mock = 20000;
        int tondk_sscd_mock = 20000;
        int tondk_sum_mock = tondk_sscd_mock+tondk_nvdx_mock;
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
