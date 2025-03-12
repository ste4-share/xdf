package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Component
public class XuatController extends CommonFactory implements Initializable {
    private static AutoCompletionBinding<String> acbLogin;
    private XuatDVController xuatDVController;
    @FXML
    private TextField phaixuat,thucxuat,nhietdo,vcf,tytrong;

    @FXML
    private ComboBox<PhuongTien> xmt_cbb;
    @FXML
    private Label chungloai_lb,loai_xmt,gia_vnd;
    @FXML
    private ComboBox<String> loai_xuat_cbb;

    @FXML
    private ComboBox<LoaiXangDauDto> cbb_tenxd;
    @FXML
    private ComboBox<Double> cbb_dongia;
    @FXML
    private HBox lgb_hb,giohd,sokm_hb,xmt_hb,loaixemaytau,dvi_nhan,px_hbox;
    @FXML
    private Button addBtn,xuatButton,cancelBtn;
    @FXML
    private Label px_lb,tx_lb;
    @FXML
    private GridPane grid_parent;
    private VBox dv,nv;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private PhuongtienService phuongtienService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
//        initLabel();
        initLoaderFxml();
        initLoaiXuatCbb();
//        mapXdForCombobox();
    }
    private void initLoaderFxml(){
        try {
            nv = (VBox) DashboardController.getNodeBySource("xuat_nv.fxml");

            FXMLLoader loader =DashboardController.getFXLoadderBySource("xuat_dv.fxml");
            dv = (VBox) loader.load();
            xuatDVController = loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initLabel() {
        tcnx_ls = tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        gia_vnd.setText("(VND/Lit)");
        Common.hoverButton(addBtn, "#027a20");
        Common.hoverButton(xuatButton, "#002db3");
        Common.hoverButton(cancelBtn, "#595959");
    }

    @FXML
    public void dongiaSelected(ActionEvent actionEvent) {
        Double gia = cbb_dongia.getSelectionModel().getSelectedItem();
        if (gia!=null){
            mapLabelPrice(gia);
            gia_vnd.setText(TextToNumber.textToNum_2digits(gia)+" (VND/Lit)");
        }
    }

    @FXML
    public void loaixuatAction(ActionEvent actionEvent) {
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
        removeNode();

        if (lx.equals(LoaiXuat.X_K.getName())){
            addNewNode(dv);
        } else if (lx.equals(LoaiXuat.NV.getName())){
            addNewNode(nv);
//            tcx.setText(null);
//            List<NhiemVuDto> ls = chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName());
//            List<String> str = new ArrayList<>();
//            ls.forEach(x->str.add(x.getTen_nv()  +" - "+ x.getChitiet()));
//            initValueForLoaiXuatCbb(str, phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle(),DashboardController.ref_Dv.getId()),
//                    nguonNxService.findAllById(Integer.parseInt(configurationService.findByParam(ConfigCons.ROOT_ID.getName()).get().getValue())),
//                    new ArrayList<>(),loaiXdService.findByType(LoaiXDCons.DAUBAY.getName(), LoaiXDCons.DAUHACAP.getName()),false);
//            px_hbox.setDisable(false);
//            sokm_hb.setDisable(true);
//            LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
//            mapPrice(lxd.getXd_id());
        } else if (lx.equals(LoaiXuat.HH.getName())) {
//            tcx.setText(null);
//            List<NhiemVuDto> ls = chitietNhiemvuService.findAllDtoById(LoaiNVCons.HAOHUT.getName());
//            List<String> str = new ArrayList<>();
//            ls.forEach(x->str.add(x.getTen_nv()  +" - "+ x.getChitiet()));
//            initValueForLoaiXuatCbb(str, new ArrayList<>(),
//                    nguonNxService.findAllById(Integer.parseInt(configurationService.findByParam(ConfigCons.ROOT_ID.getName()).get().getValue())),
//                    new ArrayList<>(),loaiXdService.findAllOrderby(),true);
//            dvi_nhan.setDisable(true);
//            sokm_hb.setDisable(true);
        }
    }
    private void addNewNode(VBox v) {
        GridPane.setRowIndex(v, 0);
        GridPane.setColumnIndex(v, 0);
        grid_parent.add(v,0,0);
    }
    private void removeNode(){
        grid_parent.getChildren().removeIf(node -> {
            Integer nodeCol = GridPane.getColumnIndex(node);
            Integer nodeRow = GridPane.getRowIndex(node);

            int nodeColIndex = (nodeCol == null) ? 0 : nodeCol;
            int nodeRowIndex = (nodeRow == null) ? 0 : nodeRow;

            return nodeColIndex == 0 && nodeRowIndex == 0;
        });
    }
    @FXML
    public void mbRadioSel(ActionEvent actionEvent) {
        clearTb();
        setLoaiXangDauByRadio(LoaiPTEnum.MAYBAY.getNameVehicle(), true,LoaiXDCons.DAUBAY.getName(),LoaiXDCons.DAUHACAP.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName()));
        px_hbox.setDisable(false);
        sokm_hb.setDisable(true);
    }
    private void clearTb(){
        ls_socai.clear();
        setCellValueFactoryXuat();
    }
    @FXML
    public void xeRadioSelec(ActionEvent actionEvent) {
        clearTb();
        setLoaiXangDauByRadio(LoaiPTEnum.XE.getNameVehicle(), false,LoaiXDCons.XANG.getName(),LoaiXDCons.DIEZEL.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoBy(LoaiNVCons.NV_BAY.getName()));
        sokm_hb.setDisable(false);
    }
    @FXML
    public void mayRadioSelec(ActionEvent actionEvent) {
        clearTb();
        setLoaiXangDauByRadio(LoaiPTEnum.MAY.getNameVehicle(), false,LoaiXDCons.XANG.getName(),LoaiXDCons.DIEZEL.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoBy(LoaiNVCons.NV_BAY.getName()));
        sokm_hb.setDisable(false);
    }

    private boolean isCbb(LoaiXangDauDto lxd,Double gia){
        if (lxd!=null){
            if (gia!=null){
                return true;
            }else{
                cbb_dongia.setStyle(styleErrorField);
                DialogMessage.message(null, lxd.getTenxd() + " - Het hang","Mặt hàng này đã hết, vui lòng nhập thêm.", Alert.AlertType.WARNING);
            }
        }else{
            cbb_tenxd.setStyle(styleErrorField);
            DialogMessage.message(null, null,MessageCons.CO_LOI_XAY_RA.getName(), Alert.AlertType.WARNING);
        }
        return false;
    }
    @FXML
    public void add(ActionEvent actionEvent) {
        UnitBillDto unitBillDto1 = xuatDVController.getInfo();
        if (unitBillDto1!=null){
            System.out.println("so is:" + unitBillDto1.getSo());
        }
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        Double gia = cbb_dongia.getSelectionModel().getSelectedItem();
        if (isCbb(lxd,gia)){
            PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();
            if (pt!=null) {
            LedgerDetails ld = getLedgerDetails(lxd, gia);
//                if (!outfieldValid(lenhso,MessageCons.NOT_EMPTY_lenhKH.getName())) {
//                    if (inventory_quantity < ld.getSoluong()) {
//                        DialogMessage.message("Error", "so luong xuat > so luong ton kho", "Co loi xay ra", Alert.AlertType.WARNING);
//                    } else {
//                        if (validateField(ld).isEmpty()) {
//                            if (isNotDuplicate(ld.getLoaixd_id(), ld.getDon_gia(), ld.getThuc_xuat(), ld.getPhai_xuat(), LoaiPhieuCons.PHIEU_XUAT.getName())) {
//                                ls_socai.add(ld);
//                            }
//                            setTonKhoLabel(inventory_quantity - ld.getSoluong());
//                            setCellValueFactoryXuat();
//                            clearFields();
//                        } else {
//                            DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(ld),
//                                    "Nhập sai định dạng.", Alert.AlertType.WARNING);
//                        }
//                    }
//                }
            }else{
                xmt_cbb.setStyle(CommonFactory.styleErrorField);
                DialogMessage.errorShowing("Xe máy tàu trống, vui lòng thử lại.");
            }
        }
    }
    @FXML
    public void xuat(ActionEvent actionEvent) {
        if (!ls_socai.isEmpty()) {
            if (DialogMessage.callAlertWithMessage("XUẤT", "TẠO PHIẾU XUẤT", "Xác nhận tạo phiếu XUẤT", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
                try {
//                    NguonNx dvx = dvx_cbb.getSelectionModel().getSelectedItem();
//                    if (dvx!=null) {
//                        Ledger l = getLedger();
//                        if (validateField(l).isEmpty()) {
//                            Ledger res = ledgerService.saveLedgerWithDetails(l, ls_socai);
//                            DialogMessage.message("Thong bao", "Them phieu XUAT thanh cong.. so: " + res.getBill_id(),
//                                    "Thanh cong", Alert.AlertType.INFORMATION);
//                            LedgerController.primaryStage.close();
//                        } else {
//                            DialogMessage.message(null, changeStyleTextFieldByValidation(l),
//                                    MessageCons.SAI_DINH_DANG.getName(), Alert.AlertType.ERROR);
//                        }
//                    }else{
//                        dvx_cbb.setStyle(styleErrorField);
//                        DialogMessage.errorShowing("Không tìm thấy nguồn nhập xuất, vui lòng thử lại.");
//                    }
                }catch (NumberFormatException e){
                    DialogMessage.errorShowing(MessageCons.SAI_DINH_DANG.getName());
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
                    throw new RuntimeException(e);
                }
            }
        }else{
            DialogMessage.message(null, "Phiếu trống!!!",
                    null, Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        LedgerController.primaryStage.close();
    }
    @FXML
    public void selectxd(ActionEvent actionEvent) {
        LoaiXangDauDto lxd = FxUtilTest.getComboBoxValue(cbb_tenxd);
        if (lxd!=null){
            cbb_tenxd.setStyle(null);
            chungloai_lb.setText("Chủng loại: "+lxd.getLoai());
            mapPrice(lxd.getXd_id());
        }
    }

    @FXML
    public void px_kr(KeyEvent keyEvent) {
        validateToSettingStyle(phaixuat);
        if (!phaixuat.getText().isBlank()){
            thucxuat.setText(phaixuat.getText());
            try {
                px_lb.setText(TextToNumber.textToNum_2digits(Double.parseDouble(phaixuat.getText())));
                tx_lb.setText(TextToNumber.textToNum_2digits(Double.parseDouble(phaixuat.getText())));
            } catch (NumberFormatException e) {
                phaixuat.setStyle(CommonFactory.styleErrorField);
            }
        }
    }
    @FXML
    public void tx_kr(KeyEvent keyEvent) {
        validateToSettingStyle(thucxuat);
        if (!phaixuat.getText().isBlank()){
            try {
                tx_lb.setText(TextToNumber.textToNum_2digits(Double.parseDouble(thucxuat.getText())));
            } catch (NumberFormatException e) {
                thucxuat.setStyle(CommonFactory.styleErrorField);
            }
        }
    }
    @FXML
    public void nhietdo_kr(KeyEvent keyEvent) {
        validateToSettingStyle(nhietdo);
    }
    @FXML
    public void vcf_kr(KeyEvent keyEvent) {
        validateToSettingStyle(vcf);
    }
    @FXML
    public void tytrong_kr(KeyEvent keyEvent) {
        validateToSettingStyle(tytrong);
    }
    @FXML
    public void px_clicked(MouseEvent mouseEvent) {
        cleanErrorField(phaixuat);
    }
    @FXML
    public void tx_clicked(MouseEvent mouseEvent) {
        cleanErrorField(thucxuat);
    }
    @FXML
    public void nhietdo_clicked(MouseEvent mouseEvent) {
        cleanErrorField(nhietdo);
    }
    @FXML
    public void cvf_clicked(MouseEvent mouseEvent) {
        cleanErrorField(vcf);
    }
    @FXML
    public void tytrong_clicked(MouseEvent mouseEvent) {
        cleanErrorField(tytrong);
    }

    private void setLoaiXangDauByRadio(String lpt,boolean pxhb, String lxd1,String lxd2){
        mapItemsForXeMayTau(phuongtienService.findPhuongTienByLoaiPhuongTien(lpt,DashboardController.ref_Dv.getId()));
        lgb_hb.setDisable(!pxhb);
        cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByType(lxd1, lxd2)));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(pxhb);
    }

    private void setNhiemvuForField(List<NhiemVuDto> nhiemVuDtos){
        acbLogin.dispose();
        List<String> ls = new ArrayList<>();
        nhiemVuDtos.forEach(i->{
            ls.add(i.getTen_nv()+ " - " + i.getChitiet());
        });
    }
    private void initValueForLoaiXuatCbb(List<String> nhiemVuDtos, List<PhuongTien> pt,List<NguonNx> dvx,List<NguonNx> dvn,List<LoaiXangDauDto> lxd,boolean pxhbox) {
        disableFeature(pxhbox);
        mapItemsForXeMayTau(pt);
        acbLogin.dispose();
        cbb_tenxd.setItems(FXCollections.observableList(lxd));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(!pxhbox);
    }
    private void initLoaiXuatCbb() {
        loai_xuat_cbb.setItems(FXCollections.observableList(List.of(LoaiXuat.X_K.getName(),LoaiXuat.NV.getName(), LoaiXuat.HH.getName())));
        loai_xuat_cbb.getSelectionModel().selectFirst();
        addNewNode(dv);
    }
    private void mapXdForCombobox(){
        setXangDauCombobox(cbb_tenxd, loaiXdService);
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            chungloai_lb.setText("Chủng loại: "+lxd.getLoai());
            mapPrice(lxd.getXd_id());
        }
    }
    private void mapPrice(int xd_id){
        i = inventoryUnitService.getInventoryByUnitByPetro(Long.parseLong(config.getValue()),xd_id);
        i = i.stream().filter(x->x.getNvdx_quantity()>0).toList();
        if (!i.isEmpty()){
            List<Double> priceList = i.stream().map(InventoryUnits::getPrice).toList();
            setGiaCbbItems(priceList);
            Double pre_price = cbb_dongia.getSelectionModel().getSelectedItem();
            mapLabelPrice(pre_price);
        }else{
            setTonKhoLabel(0);
            setGiaCbbItems(new ArrayList<>());
        }
    }
    private void mapLabelPrice(Double pre_price){
        if (pre_price!=null){
            cbb_dongia.setStyle(null);
            InventoryUnits preInventoryUnit = i.stream().filter(x->x.getPrice()==pre_price).findFirst().orElse(null);
            if (preInventoryUnit!=null){
                setTonKhoLabel(preInventoryUnit.getNvdx_quantity());
            }else{
                setTonKhoLabel(0);
            }
        }else{
            setTonKhoLabel(0);
        }
    }
    private void setGiaCbbItems(List<Double> ls){
        cbb_dongia.setItems(FXCollections.observableList(ls));
        cbb_dongia.getSelectionModel().selectFirst();
    }
    private void setCellValueFactoryXuat(){
        tbView.setItems(FXCollections.observableList(ls_socai));
        tbView.refresh();
    }
    private void clearFields(){
        phaixuat.setText("0");
        thucxuat.setText("0");
        nhietdo.setText("0");
        vcf.setText("0");
        tytrong.setText("0");
    }
    private Ledger getLedger() {
//        NguonNx dvx = dvx_cbb.getSelectionModel().getSelectedItem();
//        NguonNx dvn = dvn_cbb.getSelectionModel().getSelectedItem();
//        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
//        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();
//
//        Ledger ledger = new Ledger();
//        ledger.setCreate_by(ConnectLan.pre_acc.getId());
//        ledger.setBill_id(so.getText());
//        ledger.setAmount(ls_socai.stream().mapToDouble(x-> ((double) x.getSoluong() *x.getDon_gia())).sum());
//        ledger.setFrom_date(tungay.getValue());
//        ledger.setEnd_date(denngay.getValue());
//        ledger.setStatus(StatusCons.ACTIVED.getName());
//
//        ledger.setDvi_nhan(dvn==null ? "" : dvn.getTen());
//        ledger.setDvi_xuat(dvx.getTen());
//        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
//        ledger.setDvi_nhan_id(dvn==null? 0 : dvn.getId());
//        ledger.setDvi_xuat_id(dvx.getId());
//        ledger.setRoot_id(Integer.parseInt(configurationService.findByParam(ConfigCons.ROOT_ID.getName()).orElse(null).getValue()));
//        ledger.setNguoi_nhan(nguoinhan.getText());
//        ledger.setSo_xe(soxe.getText());
//        ledger.setLenh_so(lenhso.getText());
//        if (lx.equals(LoaiXuat.NV.getName())){
//            ledger.setTructhuoc(TructhuocEnum.NV.getName());
//            ledger.setLoaigiobay(tk_rd.isSelected() ? TypeCons.TREN_KHONG.getName() : TypeCons.MAT_DAT.getName());
////            ledger.setNhiemvu(identifyNhiemvu().getNhiemvu());
////            ledger.setNhiemvu_id(identifyNhiemvu().getId());
//            if (pt!=null){
//                ledger.setPt_id(pt.getId());
//                ledger.setLpt(phuongtienService.findById(pt.getId()).orElse(null).getLoaiPhuongTien().getTypeName());
//                ledger.setLpt_2(phuongtienService.findById(pt.getId()).orElse(null).getLoaiPhuongTien().getType());
//            }
//            if (tk_rd.isSelected()){
//                ledger.setSo_km(0);
//                ledger.setGiohd_tk(getStrInterval());
//                ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
//            } else if (md_rd.isSelected()) {
//                ledger.setSo_km(Integer.parseInt(sokm.getText().isBlank() ? "0" : sokm.getText()));
//                ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
//                ledger.setGiohd_md(getStrInterval());
//            }
//            ledger.setTcn_id(0);
//        } else if (lx.equals(LoaiXuat.HH.getName())){
////            ledger.setTructhuoc(TructhuocEnum.HH.getName());
////            ledger.setLoaigiobay("");
////            ledger.setNhiemvu(identifyNhiemvu().getNhiemvu());
////            ledger.setNhiemvu_id(identifyNhiemvu().getId());
////            ledger.setTcn_id(0);
////            ledger.setSo_km(0);
////            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
////            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
////            ledger.setLoainv(identifyNhiemvu().getNhiemvu());
//        } else {
//            if (dvn!=null){
//                ledger.setTructhuoc(tructhuocService.findById(dvn.getTructhuoc_id()).orElseThrow().getType());
//            }
//            ledger.setLoaigiobay("");
//            ledger.setNhiemvu("");
//            ledger.setNhiemvu_id(0);
////            Tcn t = tcnService.findByName(tcx.getText().trim()).orElse(null);
////            if (t!=null){
////                ledger.setTcn_id(t.getId());
////            }else{
////                ledger.setTcn_id(tcnService.save(new Tcn(LoaiPhieuCons.PHIEU_XUAT.getName(), tcx.getText())).getId());
////            }
//            ledger.setSo_km(0);
//            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
//            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
//            ledger.setNote(note.getText());
//        }
//        ledger.setYear(tungay.getValue().getYear());
//        ledger.setId(generateId(ledger.getYear(),ledger.getRoot_id(),lenhso.getText(),LoaiPhieuCons.PHIEU_XUAT.getName()));
        return new Ledger();
    }
    private LedgerDetails getLedgerDetails(LoaiXangDauDto lxd, Double gia){
        LedgerDetails ledgerDetails = new LedgerDetails();

        double txuat = thucxuat.getText().isBlank() ? 0 : Double.parseDouble(thucxuat.getText());
        double pxuat = phaixuat.getText().isBlank() ? 0 : Double.parseDouble(phaixuat.getText());

        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();

        ledgerDetails.setTen_xd(lxd.getTenxd());
        ledgerDetails.setMa_xd(lxd.getMaxd());
        ledgerDetails.setSscd_nvdx(Purpose.NVDX.getName());
        ledgerDetails.setChung_loai(lxd.getChungloai());
        ledgerDetails.setDon_gia(gia);
        ledgerDetails.setPhai_xuat(pxuat);
        ledgerDetails.setThuc_xuat(txuat);
        ledgerDetails.setNhiet_do_tt(Double.parseDouble(nhietdo.getText().isBlank() ? "0" : nhietdo.getText()));
        ledgerDetails.setHe_so_vcf(Double.parseDouble(vcf.getText().isBlank() ? "0" : vcf.getText()));
        ledgerDetails.setTy_trong(Double.parseDouble(tytrong.getText().isBlank() ? "0" : tytrong.getText()));
        ledgerDetails.setLoaixd_id(lxd.getXd_id());
        ledgerDetails.setSoluong(txuat);
        ledgerDetails.setSoluong_px(pxuat);
        ledgerDetails.setThuc_nhap(0);
        ledgerDetails.setPhai_nhap(0);
        if (lx.equals(LoaiXuat.X_K.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setHaohut_sl(0);
            ledgerDetails.setThuc_xuat(txuat);
        }else if (lx.equals(LoaiXuat.HH.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setThuc_xuat(0);
            ledgerDetails.setHaohut_sl((int)ledgerDetails.getSoluong());
        }else {
            ledgerDetails.setHaohut_sl(0);
            ledgerDetails.setPhuongtien_id(pt.getId());
//
//            if (tk_rd.isSelected()){
//                ledgerDetails.setThuc_xuat_tk(txuat);
//                ledgerDetails.setThuc_xuat(0);
//            } else if (md_rd.isSelected()) {
//                ledgerDetails.setThuc_xuat_tk(0);
//                ledgerDetails.setThuc_xuat(txuat);
//            }
        }
        ledgerDetails.setThanhtien(ledgerDetails.getSoluong() * ledgerDetails.getDon_gia());
        ledgerDetails.setThanhtien_str(TextToNumber.textToNum_2digits(ledgerDetails.getThanhtien()));
        ledgerDetails.setThucxuat_str(TextToNumber.textToNum_2digits(txuat));
        ledgerDetails.setPhaixuat_str(TextToNumber.textToNum_2digits(pxuat));
        ledgerDetails.setSoluongpx_str(TextToNumber.textToNum_2digits(ledgerDetails.getSoluong_px()));
        ledgerDetails.setSoluong_str(TextToNumber.textToNum_2digits(ledgerDetails.getSoluong()));
        ledgerDetails.setDongia_str(TextToNumber.textToNum_2digits(ledgerDetails.getDon_gia()));
        return ledgerDetails;
    }

    private void mapItemsForXeMayTau(List<PhuongTien> phuongTiens) {
        ComponentUtil.setItemsToComboBox(xmt_cbb,phuongTiens,PhuongTien::getName, input -> phuongtienService.findPhuongTienByName(input).orElse(null));
        xmt_cbb.getSelectionModel().selectFirst();
    }
    private void mapItemsForDonvi(List<NguonNx> by,ComboBox<NguonNx> cbb) {
        setNguonnxCombobox(cbb, by);
        cbb.getSelectionModel().selectFirst();
    }
    private void disableFeature(boolean ex) {
        lgb_hb.setDisable(ex);
        giohd.setDisable(ex);
        sokm_hb.setDisable(ex);
        xmt_hb.setDisable(ex);
        loaixemaytau.setDisable(ex);
        dvi_nhan.setDisable(!ex);
    }
    private String changeStyleTextFieldByValidation(Object o){
        List<String> ls = validateField(o);
        if (!ls.isEmpty()){
            if (ls.get(0).equals("soluong_px")){
                phaixuat.setStyle(styleErrorField);
                return "phai xuat phai lon hon 0";
            }else if (ls.get(0).equals("nhiet_do_tt")){
                nhietdo.setStyle(styleErrorField);
                return "Nhiet do phai lon hon 0";
            }else if (ls.get(0).equals("ty_trong")){
                tytrong.setStyle(styleErrorField);
                return "Ty trong phai lon hon 0";
            }else if (ls.get(0).equals("don_gia")){
                dongia.setStyle(styleErrorField);
                return "don gia phai lon hon 0";
            } else if (ls.get(0).equals("soluong")){
                thucxuat.setStyle(styleErrorField);
                return "thuc xuat phai lon hon 0";
            }
        }
        return null;
    }
    @FXML
    public void selected_item(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            if (DialogMessage.callAlertWithMessage("Delete", "Xoa", "Xác nhận xoa",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
                LedgerDetails ld = tbView.getSelectionModel().getSelectedItem();
                if (ld!=null){
                    ls_socai.remove(ld);
                    setTonKhoLabel(inventory_quantity+ld.getSoluong());
                    tbView.setItems(FXCollections.observableList(ls_socai));
                    tbView.refresh();
                }
            }
        }
    }
    @FXML
    public void dvxAction(ActionEvent actionEvent) {
//        dvx_cbb.setStyle(null);
//        mapPrice2();
    }
    private void mapPrice2(){
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        mapPrice(lxd.getXd_id());
    }
    @FXML
    public void chitietExited(MouseEvent mouseEvent) {
    }
    @FXML
    public void chitietEnter(MouseEvent mouseEvent) {
    }

}
