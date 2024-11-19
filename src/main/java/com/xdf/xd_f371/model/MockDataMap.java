package com.xdf.xd_f371.model;

import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.QuantityByTTDTO;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import com.xdf.xd_f371.util.Common;

import java.util.List;
import java.util.Map;

public class MockDataMap {

    private static LoaiXdService loaiXdService = new LoaiXdImp();
    private static CategoryService categoryService = new CategoryImp();
    private static TonKhoService tonKhoService = new TonkhoImp();
    private static InvReportDetailService invReportDetailService = new invReportDetailImp();
    private static MucgiaService mucgiaService = new MucgiaImp();
    private static LedgerDetailsService ledgerDetailsService = new LedgerDetailsImp();

    public static void initInventoryMap(){
        invReportDetailService.deleteAll();
        List<LoaiXangDau> loaiXangDauList = loaiXdService.getAll();
        List<Category> categories = categoryService.getAll();
        try {
            int quarter_id = DashboardController.findByTime.getId();
            for (int i =0; i< loaiXangDauList.size(); i++){
                Inventory inventory = tonKhoService.findByUniqueId(loaiXangDauList.get(i).getId(), quarter_id);
                for (int j=0; j< categories.size(); j++){
                    Category catelos = categories.get(j);
                    InvReportDetail invReportDetail = new InvReportDetail();
                    Common.getInvCatalogField(catelos, inventory, invReportDetail);
                    if (catelos.getCode().equals("NHAP")){
                        QuantityByTTDTO quantity = ledgerDetailsService.selectQuantityNguonnx(2,"NHAP",catelos.getTructhuoc_id(),loaiXangDauList.get(i).getId());
                        if (quantity==null){
                            invReportDetail.setSoluong(0);
                        }else{
                            invReportDetail.setSoluong(quantity.getSum());
                        }

                    } else if(catelos.getCode().equals("XUAT")) {
                        QuantityByTTDTO quantity = ledgerDetailsService.selectQuantityNguonnxImport(2,"XUAT",catelos.getTructhuoc_id(),loaiXangDauList.get(i).getId());
                        if (quantity==null){
                            invReportDetail.setSoluong(0);
                        }else{
                            invReportDetail.setSoluong(quantity.getSum());
                        }
                    }
                    //inventory detail
                    invReportDetail.setLoaixd(loaiXangDauList.get(i).getTenxd());
                    invReportDetail.setTitle_lv1(catelos.getHeader_lv1());
                    invReportDetail.setTitle_lv2(catelos.getHeader_lv2());
                    invReportDetail.setTitle_lv3(catelos.getHeader_lv3());
                    Map<String, String> titleMap = ChungloaiMap.getMapChungloai();
                    invReportDetail.setTitle_lxd_lv1(titleMap.get(loaiXangDauList.get(i).getChungloai()));
                    invReportDetail.setTitle_lxd_lv2(titleMap.get(loaiXangDauList.get(i).getType()));
                    invReportDetail.setTitle_lxd_lv3(titleMap.get(loaiXangDauList.get(i).getRtype()));
                    invReportDetail.setXd_id(loaiXangDauList.get(i).getId());
                    invReportDetail.setQuarter_id(quarter_id);
                    invReportDetail.setTitle_id(catelos.getId());
                    invReportDetailService.createNew(invReportDetail);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void mockInventoryData(){
        int tondk_nvdx_mock = 20000;
        int tondk_sscd_mock = 20000;
        int tondk_sum_mock = tondk_sscd_mock+tondk_nvdx_mock;
        for (LoaiXangDau loaiXangDau : loaiXdService.getAll()) {

            Inventory inventory = new Inventory();
            inventory.setPetro_id(loaiXangDau.getId());
            inventory.setQuarter_id(DashboardController.findByTime.getId());
            inventory.setTotal(tondk_sum_mock);
            inventory.setTdk_nvdx(tondk_nvdx_mock);
            inventory.setTdk_sscd(tondk_sscd_mock);
            inventory.setPre_nvdx(tondk_nvdx_mock);
            inventory.setPre_sscd(tondk_sscd_mock);
            inventory.setTcK_nvdx(0);
            inventory.setTck_sscd(0);
            inventory.setImport_total(0);
            inventory.setExport_total(0);

            inventory.setTdk_sscd(tondk_sscd_mock);
            inventory.setStatus("RECORDING");
            tonKhoService.createNew(inventory);

            Inventory inventory1 = tonKhoService.findByUniqueId(loaiXangDau.getId(), DashboardController.findByTime.getId());
            //mock mucgia
            Mucgia mucgia = new Mucgia();
            mucgia.setQuarter_id(DashboardController.findByTime.getId());
            mucgia.setAmount(tondk_nvdx_mock);
            mucgia.setPrice(142857);
            mucgia.setItem_id(loaiXangDau.getId());
            mucgia.setAssign_type_id(mucgiaService.findByName(AssignTypeEnum.NVDX.getName()).getId());
            mucgia.setStatus("IN_STOCK");
            mucgia.setInventory_id(inventory1.getId());
            mucgiaService.createNew(mucgia);
            Mucgia mucgia2 = new Mucgia();
            mucgia2.setQuarter_id(DashboardController.findByTime.getId());
            mucgia2.setAmount(tondk_nvdx_mock);
            mucgia2.setPrice(142857);
            mucgia2.setItem_id(loaiXangDau.getId());
            mucgia2.setAssign_type_id(mucgiaService.findByName(AssignTypeEnum.SSCD.getName()).getId());
            mucgia2.setStatus("IN_STOCK");
            mucgia2.setInventory_id(inventory1.getId());
            mucgiaService.createNew(mucgia2);

        }
    }

}
