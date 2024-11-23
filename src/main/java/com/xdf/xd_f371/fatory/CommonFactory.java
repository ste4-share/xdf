package com.xdf.xd_f371.fatory;

import com.xdf.xd_f371.dto.LichsuXNK;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.model.MucGiaEnum;
import com.xdf.xd_f371.repo.LedgerDetailRepo;
import com.xdf.xd_f371.repo.LedgersRepo;
import com.xdf.xd_f371.repo.MucGiaRepo;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommonFactory {
    protected LoaiPhieuService loaiPhieuService = new LoaiPhieuImp();
    protected CategoryService categoryService = new CategoryImp();
    protected InvReportDetailService invReportDetailService = new invReportDetailImp();
    protected MucgiaService mucgiaService = new MucgiaImp();
    protected LichsuNXKService lichsuNXKService = new LichsuNXKImp();
    protected static LoaiPhieu lp_id_pre = new LoaiPhieu();
    protected static List<Tcn> ls_tcn = new ArrayList<>();
    protected static Tcn pre_createNewTcn = new Tcn();
    protected NguonNXService nguonNXService = new NguonNXImp();
    protected TcnService tcnService = new TcnImp();

    protected TrucThuocService trucThuocService = new TrucThuocImp();
    @Autowired
    protected LedgerDetailRepo ledgerDetailRepo;
    @Autowired
    protected LedgersRepo ledgersRepo;
    @Autowired
    protected MucGiaRepo mucGiaRepo;

    protected void updateAllRowInv(LedgerDetails ledgerDetails){
//        Inventory inventory = tonKhoService.findByUniqueId(ledgerDetails.getLoaixd_id(), ledgerDetails.getQuarter_id());
//        Category category = categoryService.getTitleByttLpId(ledgerDetails.getNhiemvu_id(), ledgerDetails.getLoai_phieu());
//        updateTck_forReport(ChungLoaiModel.NVDX_a.getNameChungloai(),ChungLoaiModel.TCK_a.getNameChungloai(), ledgerDetails, inventory.getTcK_nvdx());
//        updateTck_forReport(ChungLoaiModel.SSCD_a.getNameChungloai(),ChungLoaiModel.TCK_a.getNameChungloai(), ledgerDetails, inventory.getTck_sscd());
//        if (category!=null){
//            InvReportDetail invReportDetail = invReportDetailService.findByIds(ledgerDetails.getLoaixd_id(), ledgerDetails.getQuarter_id(), category.getId());
//            if (Common.getInvCatalogField(category, inventory, invReportDetail)){
//                invReportDetailService.update(invReportDetail);
//            } else {
//                if (ledgerDetails.getLoai_phieu().equals("NHAP")){
//                    QuantityByTTDTO quantity = ledgerDetailsService.selectQuantityNguonnx(2,ledgerDetails.getLoai_phieu(),category.getTructhuoc_id(),ledgerDetails.getLoaixd_id());
//                    if (quantity==null){
//                        invReportDetail.setSoluong(0);
//                    }else{
//                        invReportDetail.setSoluong(quantity.getSum());
//                    }
//                    invReportDetailService.update(invReportDetail);
//                } else if (ledgerDetails.getLoai_phieu().equals("XUAT")){
//                    QuantityByTTDTO quantity = ledgerDetailsService.selectQuantityNguonnxImport(2,ledgerDetails.getLoai_phieu(),category.getTructhuoc_id(),ledgerDetails.getLoaixd_id());
//                    if (quantity==null){
//                        invReportDetail.setSoluong(0);
//                    }else{
//                        invReportDetail.setSoluong(quantity.getSum());
//                    }
//                    invReportDetailService.update(invReportDetail);
//                }
//            }
//        }else{
//            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//            alert.setContentText("Đơn vị không có nguồn trực thuộc.");
//            alert.showAndWait();
//        }
    }

    private void updateTck_forReport(String code, String type, LedgerDetails ledgerDetails, int soluong){
        //set tck sscd
        Category category = categoryService.findByCode(code,type);
        InvReportDetail invReportDetail1 = invReportDetailService.findByIds(ledgerDetails.getLoaixd_id(), ledgerDetails.getQuarter_id(), category.getId());
        invReportDetail1.setSoluong(soluong);
        invReportDetailService.update(invReportDetail1);
    }

    protected void createNewMucgia(LedgerDetails ledgerDetails, int quantity){
//        Mucgia mucgia = new Mucgia();
//        Inventory inventory = tonKhoService.findByUniqueId(ledgerDetails.getLoaixd_id(), ledgerDetails.getQuarter_id());
//        if (inventory==null){
//            inventory = createInventory(ledgerDetails);
//        }
//        mucgia.setPrice(ledgerDetails.getDon_gia());
//        mucgia.setAmount(quantity);
//        mucgia.setQuarter_id(ledgerDetails.getQuarter_id());
//        mucgia.setItem_id(ledgerDetails.getLoaixd_id());
//        mucgia.setStatus(MucGiaEnum.IN_STOCK.getStatus());
//        mucgia.setAssign_type_id(DashboardController.assignType.getId());
//        mucgia.setInventory_id(inventory.getId());
//        ledgerDetails.setTonkho_id(inventory.getId());
//        mucgiaService.createNew(mucgia);
    }

//    private Inventory createInventory(LedgerDetails ledgerDetails){
//        Inventory inventory = new Inventory();
//        inventory.setQuarter_id(ledgerDetails.getQuarter_id());
//        inventory.setPetro_id(ledgerDetails.getLoaixd_id());
//        inventory.setPetroleumName(ledgerDetails.getTen_xd());
//        inventory.setTdk_sscd(0);
//        inventory.setTdk_nvdx(0);
//        inventory.setPre_sscd(0);
//        inventory.setPre_nvdx(ledgerDetails.getThuc_xuat());
//        inventory.setTck_sscd(0);
//        inventory.setTcK_nvdx(ledgerDetails.getThuc_xuat());
//        tonKhoService.createNew(inventory);
//        return tonKhoService.findByUniqueId(ledgerDetails.getLoaixd_id(), ledgerDetails.getQuarter_id());
//    }

    protected void updateMucgia(int quantity, Mucgia mucgia_existed){
        if (quantity == 0){
            mucgia_existed.setStatus(MucGiaEnum.OUT_STOCK.getStatus());
            mucgia_existed.setAmount(quantity);
        } else if (quantity<0) {
            mucgia_existed.setStatus(MucGiaEnum.SUPER_OUT_STOCK.getStatus());
            mucgia_existed.setAmount(quantity);
        } else {
            mucgia_existed.setStatus(MucGiaEnum.IN_STOCK.getStatus());
            mucgia_existed.setAmount(quantity);
        }

        mucgiaService.updateMucGia(mucgia_existed);
    }

    protected void createNewTransaction(LedgerDetails ledgerDetails, int tontruoc, int tonsau){
        LichsuXNK lichsuXNK = new LichsuXNK();
        lichsuXNK.setTen_xd(ledgerDetails.getTen_xd());
        lichsuXNK.setLoai_phieu(ledgerDetails.getLoai_phieu());
        lichsuXNK.setSoluong(ledgerDetails.getThuc_xuat());
        lichsuXNK.setCreateTime(ledgerDetails.getNgay());
        lichsuXNK.setTontruoc(tontruoc);
        lichsuXNK.setTonsau(tonsau);
        lichsuXNK.setMucgia(String.valueOf(ledgerDetails.getDon_gia()));
        lichsuNXKService.createNew(lichsuXNK);
    }
}
