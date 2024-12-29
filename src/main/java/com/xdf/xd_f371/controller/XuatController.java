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
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class XuatController extends CommonFactory implements Initializable {
    private static List<Ledger> current_ledger_list = new ArrayList<>();
    private static long inv_price;
    private static AutoCompletionBinding<String> acbLogin;
    private static DinhMuc dinhMuc;

    @FXML
    private TextField so,tcx,nguoinhan,lenhso,soxe,sokm,sogio, sophut,phaixuat,thucxuat,nhietdo,vcf,tytrong,nl_gio,nl_km;
    @FXML
    private DatePicker tungay,denngay;
    @FXML
    private RadioButton md_rd,tk_rd, mb_rd,nvdx_rd;
    @FXML
    private ComboBox<PhuongTien> xmt_cbb;
    @FXML
    private Label inv_lb, dm_gio, dm_km,lb_dm_km,lb_dm_gio,chungloai_lb,loai_xmt;
    @FXML
    private ComboBox<NguonNx> dvn_cbb,dvx_cbb;
    @FXML
    private ComboBox<String> loai_xuat_cbb;
    @FXML
    private ComboBox<LoaiXangDauDto> cbb_tenxd;
    @FXML
    private ComboBox<Integer> cbb_dongia;
    @FXML
    private HBox lgb_hb,giohd,sokm_hb,xmt_hb,loaixemaytau,dvi_nhan,px_hbox,nl_km_hb,nl_gio_hb;
    @FXML
    private Button addBtn,xuatButton,cancelBtn;

    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private LoaiXdService loaiXdService;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private TcnService tcnService;
    @Autowired
    private HanmucNhiemvuService hanmucNhiemvuService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        current_ledger_list = ledgerService.getAllByQuarter(DashboardController.findByTime.getId(),LoaiPhieuCons.PHIEU_XUAT.getName());
        ls_socai = new ArrayList<>();
        tcnx_ls = tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        nvdx_rd.setSelected(true);
        initDefailtVar();
        initLoaiXuatCbb();
        searchCompleteTion(tcnx_ls.stream().map(Tcn::getName).collect(Collectors.toList()));
        mapXdForCombobox();
        Common.hoverButton(addBtn, "#027a20");
        Common.hoverButton(xuatButton, "#002db3");
        Common.hoverButton(cancelBtn, "#595959");
    }
    @FXML
    public void dongiaSelected(ActionEvent actionEvent) {
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        Integer gia = cbb_dongia.getSelectionModel().getSelectedItem();
        if (lxd != null && gia != null) {
            Optional<Inventory> in = inventoryService.findByUnique(lxd.getXd_id(),DashboardController.findByTime.getId(),MucGiaEnum.IN_STOCK.getStatus(),gia);
            in.ifPresent(inventory -> setInv_lb(inventory.getNhap_nvdx() - inventory.getXuat_nvdx()));
        }
    }
    @FXML
    public void dvnSelectedAction(ActionEvent actionEvent) {
    }
    @FXML
    public void nvSelected(ActionEvent actionEvent) {
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();
        setlabelDinhmuc(pt);
        if (pt!=null){
            List<NguonNx> nguonNx = hanmucNhiemvuService.getAllDviTructhuocByTaubay(pt.getId(),DashboardController.findByTime.getId());
            if (!nguonNx.isEmpty()){
                mapItemsForDonvi(nguonNx,dvx_cbb);
            }else{
                mapItemsForDonvi(nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()), dvx_cbb);
            }
            mapLoaixdByList(pt);
            loai_xmt.setText(pt.getLoaiPhuongTien().getTypeName());
        }
    }

    private void setlabelDinhmuc(PhuongTien pt){
        if (pt!=null){
            dinhMuc = dinhmucService.findDinhmucByPhuongtien(pt.getId(), DashboardController.findByTime.getId()).orElse(null);
            if (dinhMuc!=null){
                if (mb_rd.isSelected()){
                    setDinhmucToLabel("Đ.mức TK:", "Đ.mức MD:",dinhMuc.getDm_tk_gio(),dinhMuc.getDm_md_gio());
                }else{
                    setDinhmucToLabel("Đ.mức Giờ:", "Đ.mức Km:",dinhMuc.getDm_xm_gio(),dinhMuc.getDm_xm_km());
                }
            }else {
                setDinhmucToLabel("--- Chưa đặt Đ.mức cho phương tiện--", "",0,0);
            }
        }
    }
    @FXML
    public void loaixuatAction(ActionEvent actionEvent) {
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
        if (lx.equals(LoaiXuat.X_K.getName())){

            initValueForLoaiXuatCbb(tcnx_ls.stream().map(Tcn::getName).collect(Collectors.toList()),
                    new ArrayList<>(),nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()),nguonNxService.findAll(),loaiXdService.findAllOrderby()
                    ,true);
            nl_km_hb.setDisable(true);
            nl_gio_hb.setDisable(true);
        } else if (lx.equals(LoaiXuat.NV.getName())) {
            List<NhiemVuDto> ls = chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName());
            List<String> str = new ArrayList<>();
            ls.forEach(x->str.add(x.getTen_nv()  +" - "+ x.getChitiet()));
            initValueForLoaiXuatCbb(str, phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle()),
                    nguonNxService.findByAllBy(),new ArrayList<>(),loaiXdService.findByType(LoaiXDCons.DAUBAY.getName(), LoaiXDCons.DAUHACAP.getName()),false);
            px_hbox.setDisable(false);
        } else if (lx.equals(LoaiXuat.HH.getName())) {
            List<NhiemVuDto> ls = chitietNhiemvuService.findAllDtoById(LoaiNVCons.HAOHUT.getName());
            List<String> str = new ArrayList<>();
            ls.forEach(x->str.add(x.getTen_nv()  +" - "+ x.getChitiet()));
            initValueForLoaiXuatCbb(str, new ArrayList<>(),nguonNxService.findByAllBy(),new ArrayList<>(),loaiXdService.findAllOrderby(),true);
            dvi_nhan.setDisable(true);
        }
    }
    @FXML
    public void mbRadioSel(ActionEvent actionEvent) {
        setLoaiXangDauByRadio(LoaiPTEnum.MAYBAY.getNameVehicle(), true,LoaiXDCons.DAUBAY.getName(),LoaiXDCons.DAUHACAP.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName()));
        px_hbox.setDisable(false);
    }
    @FXML
    public void xeRadioSelec(ActionEvent actionEvent) {
        setLoaiXangDauByRadio(LoaiPTEnum.XE.getNameVehicle(), false,LoaiXDCons.XANG.getName(),LoaiXDCons.DIEZEL.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoBy(LoaiNVCons.NV_BAY.getName()));
    }
    @FXML
    public void mayRadioSelec(ActionEvent actionEvent) {
        setLoaiXangDauByRadio(LoaiPTEnum.MAY.getNameVehicle(), false,LoaiXDCons.XANG.getName(),LoaiXDCons.DIEZEL.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoBy(LoaiNVCons.NV_BAY.getName()));
    }
    @FXML
    public void add(ActionEvent actionEvent) {
        LedgerDetails ld = getLedgerDetails();
        if (!outfieldValid(tcx, "tinh chat xuat khong duoc de trong.")) {
            if (inv_price < ld.getSoluong()){
                DialogMessage.message("Error", "so luong xuat > so luong ton kho","Co loi xay ra", Alert.AlertType.WARNING);
            } else {
                if (validateField(ld).isEmpty()) {
                    if (isNotDuplicate(ld.getLoaixd_id(),ld.getDon_gia(),ld.getThuc_xuat(),ld.getPhai_xuat(),LoaiPhieuCons.PHIEU_XUAT.getName())) {
                        ls_socai.add(ld);
                    }
                    setInv_lb(inv_price-ld.getSoluong());
                    setCellValueFactoryXuat();
                    clearFields();
                } else {
                    DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(ld),
                            "Nhập sai định dạng.", Alert.AlertType.WARNING);
                }
            }
        }
    }
    @FXML
    public void xuat(ActionEvent actionEvent) {
        if (!ls_socai.isEmpty()) {
            if (DialogMessage.callAlertWithMessage("XUẤT", "TẠO PHIẾU XUẤT", "Xác nhận tạo phiếu XUẤT", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
                Ledger l = getLedger();
                if (validateField(l).isEmpty()) {
                    Ledger res = ledgerService.saveLedgerWithDetails(l, ls_socai);
                    DialogMessage.message("Thong bao", "Them phieu XUAT thanh cong.. so: " + res.getBill_id(),
                            "Thanh cong", Alert.AlertType.INFORMATION);
                    DashboardController.xuatStage.close();
                } else {
                    DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(l),
                            "Nhập sai định dạng.", Alert.AlertType.ERROR);
                }
            }
        }else{
            DialogMessage.message(null, "Phiếu trống!!!",
                    null, Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        DashboardController.xuatStage.close();
    }
    private int cal_phaixuat_km(int sokm, int dinhmuc){
        return (sokm*dinhmuc)/100;
    }
    private float cal_phaixuat_gio(float sogio, int dinhmuc){
        return sogio *dinhmuc;
    }
    @FXML
    public void cal_nl_gio(ActionEvent actionEvent) {
        calNlTheoGio();
    }
    private void calNlTheoGio(){
        float phut = (float) Integer.parseInt(sophut.getText())/60;
        int du = (Integer.parseInt(sophut.getText()))/60;
        float gio = Integer.parseInt(sogio.getText()) + phut + du;
        PhuongTien pt =xmt_cbb.getSelectionModel().getSelectedItem();
        if (pt!=null){
            DinhMuc dm = dinhmucService.findDinhmucByPhuongtien(pt.getId(), DashboardController.findByTime.getId()).orElse(null);
            if (dm!=null){
                if (mb_rd.isSelected()){
                    if (tk_rd.isSelected()){
                        nl_gio.setText(String.valueOf((int) cal_phaixuat_gio(gio, dm.getDm_tk_gio())));
                    } else{
                        nl_gio.setText(String.valueOf((int) cal_phaixuat_gio(gio, dm.getDm_md_gio())));
                    }
                }else{
                    nl_gio.setText(String.valueOf((int) cal_phaixuat_gio(gio, dm.getDm_xm_gio())));
                }
            }else{
                DialogMessage.errorShowing("Không tìm thấy định mức cho phương tiện " + pt.getName());
            }
        }
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
    public void km_keyrealese(KeyEvent keyEvent) {
        if (!nl_km.getText().isEmpty() && isNumber(nl_km.getText())){
            try{
                nl_km.setText(String.valueOf(cal_phaixuat_km(Integer.parseInt(sokm.getText()), Objects.requireNonNull(dinhmucService.findDinhmucByPhuongtien(xmt_cbb.getValue().getId(), DashboardController.findByTime.getId()).orElse(null)).getDm_xm_km())));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    public void soKeyRealed(KeyEvent keyEvent) {
        if(!so.getText().isEmpty()){
            validateToSettingStyle(so);
            if (isNumber(so.getText())){
                if (current_ledger_list.stream().filter(i->i.getBill_id()==Integer.parseInt(so.getText())).findFirst().isPresent()){
                    so.setStyle(styleErrorField);
                }
            }else{
                so.setStyle(styleErrorField);
            }
        }
    }
    @FXML
    public void giohd_key(KeyEvent keyEvent) {
        validateToSettingStyle(sogio);
    }
    @FXML
    public void phut_key(KeyEvent keyEvent) {
        if (Integer.parseInt(sogio.getText()) <60 && Integer.parseInt(sogio.getText()) >=0){
            validateToSettingStyle(sophut);
        }
    }
    @FXML
    public void px_kr(KeyEvent keyEvent) {
        validateToSettingStyle(phaixuat);
    }
    @FXML
    public void tx_kr(KeyEvent keyEvent) {
        validateToSettingStyle(thucxuat);
    }
    @FXML
    public void nl_km_kr(KeyEvent keyEvent) {
        validateToSettingStyle(nl_km);
    }
    @FXML
    public void nl_gio_kr(KeyEvent keyEvent) {
        validateToSettingStyle(nl_gio);
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
    public void so_clicked(MouseEvent mouseEvent) {
        cleanErrorField(so);
        current_ledger_list = ledgerService.getAllByQuarter(DashboardController.findByTime.getId(),LoaiPhieuCons.PHIEU_XUAT.getName());
    }
    @FXML
    public void so_km_clicked(MouseEvent mouseEvent) {
        cleanErrorField(sokm);
    }
    @FXML
    public void giohd_clicked(MouseEvent mouseEvent) {
        cleanErrorField(sogio);
    }
    @FXML
    public void phuthd_clicked(MouseEvent mouseEvent) {
        cleanErrorField(sophut);
    }
    @FXML
    public void tcx_clicked(MouseEvent mouseEvent) {
        cleanErrorField(tcx);
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
    public void nl_km_clicked(MouseEvent mouseEvent) {
        cleanErrorField(nl_km);
    }
    @FXML
    public void nlgio__clicked(MouseEvent mouseEvent) {
        cleanErrorField(nl_gio);
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
    private void initDefailtVar() {
        sokm.setText("0");
        sogio.setText("0");
        sophut.setText("0");
        phaixuat.setText("0");
        thucxuat.setText("0");
        nhietdo.setText("0");
        tytrong.setText("0");
        vcf.setText("0");
        nl_gio.setText("0");
        nl_km.setText("0");
    }
    private void setDinhmucToLabel(String l1, String l2, int dm1,int dm2){
        lb_dm_gio.setText(l1);
        dm_gio.setText(TextToNumber.textToNum(String.valueOf(dm1)));
        lb_dm_km.setText(l2);
        dm_km.setText(TextToNumber.textToNum(String.valueOf(dm2)));
    }
    private void setLoaiXangDauByRadio(String lpt,boolean pxhb, String lxd1,String lxd2){
        mapItemsForXeMayTau(phuongtienService.findPhuongTienByLoaiPhuongTien(lpt));
        md_rd.setSelected(!pxhb);
        lgb_hb.setDisable(!pxhb);
        cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByType(lxd1, lxd2)));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(pxhb);
    }
    private void mapLoaixdByList(PhuongTien pt){
        if (pt.getLoaiPhuongTien().getTypeName().equals(LoaiPTEnum.MAY_CHAY_XANG.getNameVehicle()) || pt.getLoaiPhuongTien().getTypeName().equals(LoaiPTEnum.XE_CHAY_XANG.getNameVehicle())){
            cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByTypeNAme(LoaiXDCons.XANG.getName())));
            cbb_tenxd.getSelectionModel().selectFirst();
        } else if (pt.getLoaiPhuongTien().getTypeName().equals(LoaiPTEnum.MAY_CHAY_DIEZEL.getNameVehicle()) || pt.getLoaiPhuongTien().getTypeName().equals(LoaiPTEnum.XE_CHAY_DIEZEL.getNameVehicle())) {
            cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByTypeNAme(LoaiXDCons.DIEZEL.getName())));
            cbb_tenxd.getSelectionModel().selectFirst();
        }
    }
    private void setNhiemvuForField(List<NhiemVuDto> nhiemVuDtos){
        acbLogin.dispose();
        List<String> ls = new ArrayList<>();
        nhiemVuDtos.forEach(i->{
            ls.add(i.getTen_nv()+ " - " + i.getChitiet());
        });
        searchCompleteTion(ls);
    }
    private void initValueForLoaiXuatCbb(List<String> nhiemVuDtos, List<PhuongTien> pt,List<NguonNx> dvx,List<NguonNx> dvn,List<LoaiXangDauDto> lxd,boolean pxhbox) {
        disableFeature(pxhbox);
        mapItemsForDonvi(dvx, dvx_cbb);
        mapItemsForDonvi(dvn,dvn_cbb);
        mapItemsForXeMayTau(pt);
        acbLogin.dispose();
        searchCompleteTion(nhiemVuDtos);
        cbb_tenxd.setItems(FXCollections.observableList(lxd));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(!pxhbox);
    }
    private void initLoaiXuatCbb() {
        loai_xuat_cbb.setItems(FXCollections.observableList(List.of(LoaiXuat.X_K.getName(),LoaiXuat.NV.getName(), LoaiXuat.HH.getName())));
        loai_xuat_cbb.getSelectionModel().selectFirst();
        mapItemsForDonvi(nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()), dvx_cbb);
        mapItemsForDonvi(nguonNxService.findAll(),dvn_cbb);
        List<PhuongTien> pt= new ArrayList<>();
        mapItemsForXeMayTau(pt);
        disableFeature(true);
    }
    private void searchCompleteTion(List<String> search_arr){
        acbLogin = TextFields.bindAutoCompletion(tcx, t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });

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
        List<Inventory> inventoryList = inventoryService.findByPetro_idAndQuarter_id(xd_id,DashboardController.findByTime.getId(),MucGiaEnum.IN_STOCK.getStatus());
        if (!inventoryList.isEmpty()){
            cbb_dongia.setItems(FXCollections.observableList(inventoryList.stream().map(Inventory::getPrice).toList()));
            cbb_dongia.getSelectionModel().selectFirst();
            Inventory i = inventoryList.stream().filter(x->x.getPrice()==cbb_dongia.getSelectionModel().getSelectedItem()).findFirst().orElse(null);
            if (i != null){
                setInv_lb(i.getNhap_nvdx()-i.getXuat_nvdx());
            }
        }else{
            DialogMessage.message("Thong bao", loaiXdService.findById(xd_id).get().getTenxd()+" da het, vui long nhap them.","Het hang", Alert.AlertType.WARNING);
            setXangDauCombobox(cbb_tenxd, loaiXdService);
            cbb_dongia.setItems(FXCollections.observableList(new ArrayList<>()));
        }
    }
    private void setCellValueFactoryXuat(){
        setcellFactory("phaixuat_str","thucxuat_str");
        tbView.setItems(FXCollections.observableList(ls_socai));
        tbView.refresh();
    }
    private void clearFields(){
        phaixuat.setText("0");
        thucxuat.setText("0");
        nhietdo.setText("0");
        vcf.setText("0");
        tytrong.setText("0");
        nl_gio.setText("0");
        nl_km.setText("0");
    }
    private Ledger getLedger() {
        NguonNx dvx = dvx_cbb.getSelectionModel().getSelectedItem();
        NguonNx dvn = dvn_cbb.getSelectionModel().getSelectedItem();
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();

        Ledger ledger = new Ledger();
        ledger.setCreate_by(ConnectLan.pre_acc.getId());
        ledger.setBill_id(Integer.parseInt(so.getText().isEmpty() ? "0" : so.getText()));
        ledger.setQuarter_id(DashboardController.findByTime.getId());
        ledger.setAmount(ls_socai.stream().mapToLong(x-> ((long) x.getSoluong() *x.getDon_gia())).sum());
        ledger.setFrom_date(tungay.getValue());
        ledger.setEnd_date(denngay.getValue());
        ledger.setStatus("ACTIVE");

        if (tk_rd.isSelected()){
            ledger.setSl_tieuthu_md(0);
            ledger.setSl_tieuthu_tk(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
        }else if (md_rd.isSelected()){
            ledger.setSl_tieuthu_md(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
            ledger.setSl_tieuthu_tk(0);
        }else{
            ledger.setSl_tieuthu_md(0);
            ledger.setSl_tieuthu_tk(0);
        }
        ledger.setDvi_nhan(dvn==null ? "" : dvn.getTen());
        ledger.setDvi_xuat(dvx.getTen());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        ledger.setDvi_nhan_id(dvn==null? 0 : dvn.getId());
        ledger.setDvi_xuat_id(dvx.getId());
        ledger.setRoot_id(Integer.parseInt(configurationService.findByParam(ConfigCons.ROOT_ID.getName()).orElse(null).getValue()));
        ledger.setNguoi_nhan(nguoinhan.getText());
        ledger.setSo_xe(soxe.getText());
        ledger.setLenh_so(lenhso.getText());
        if (lx.equals(LoaiXuat.NV.getName())){
            ledger.setTructhuoc(TructhuocEnum.NV.getName());
            ledger.setLoaigiobay(tk_rd.isSelected() ? TypeCons.TREN_KHONG.getName() : TypeCons.MAT_DAT.getName());
            ledger.setNhiemvu(identifyNhiemvu().getNhiemvu());
            ledger.setNhiemvu_id(identifyNhiemvu().getId());
            if (pt!=null){
                ledger.setPt_id(pt.getId());
                ledger.setLpt(phuongtienService.findById(pt.getId()).orElse(null).getLoaiPhuongTien().getTypeName());
                ledger.setLpt_2(phuongtienService.findById(pt.getId()).orElse(null).getLoaiPhuongTien().getType());
            }
            if (tk_rd.isSelected()){
                ledger.setSo_km(0);
                ledger.setGiohd_tk(getStrInterval());
                ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
            } else if (md_rd.isSelected()) {
                ledger.setSo_km(Integer.parseInt(sokm.getText().isEmpty() ? "0" : sokm.getText()));
                ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
                ledger.setGiohd_md(getStrInterval());
            }
            ledger.setTcn_id(0);
        } else if (lx.equals(LoaiXuat.HH.getName())){
            ledger.setTructhuoc(TructhuocEnum.HH.getName());
            ledger.setLoaigiobay("");
            ledger.setNhiemvu("");
            ledger.setNhiemvu_id(0);
            ledger.setTcn_id(0);
            ledger.setSo_km(0);
            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
            ledger.setLoainv(identifyNhiemvu().getNhiemvu());
        } else {
            if (dvn!=null){
                ledger.setTructhuoc(tructhuocService.findById(dvn.getTructhuoc_id()).orElseThrow().getType());
            }
            ledger.setLoaigiobay("");
            ledger.setNhiemvu("");
            ledger.setNhiemvu_id(0);
            Tcn t = tcnService.findByName(tcx.getText().trim()).orElse(null);
            if (t!=null){
                ledger.setTcn_id(t.getId());
            }else{
                ledger.setTcn_id(tcnService.save(new Tcn(LoaiPhieuCons.PHIEU_XUAT.getName(), tcx.getText())).getId());
            }
            ledger.setSo_km(0);
            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
        }
        return ledger;
    }
    private LedgerDetails getLedgerDetails(){
        LedgerDetails ledgerDetails = new LedgerDetails();

        int txuat = thucxuat.getText().isEmpty() ? 0 : Integer.parseInt(thucxuat.getText());
        int pxuat = phaixuat.getText().isEmpty() ? 0 : Integer.parseInt(phaixuat.getText());

        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd ==null){
            cbb_tenxd.setStyle(styleErrorField);
            DialogMessage.message("Error", "ten xang dau khong xac dinh","Co loi xay ra", Alert.AlertType.ERROR);
            throw new RuntimeException("error");
        }
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();

        ledgerDetails.setTen_xd(lxd.getTenxd());
        ledgerDetails.setMa_xd(lxd.getMaxd());
        ledgerDetails.setSscd_nvdx(nvdx_rd.isSelected() ? Purpose.NVDX.getName():Purpose.SSCD.getName());
        ledgerDetails.setChung_loai(lxd.getChungloai());
        ledgerDetails.setDon_gia(Integer.parseInt(String.valueOf(cbb_dongia.getSelectionModel().getSelectedItem())));
        ledgerDetails.setPhai_xuat(pxuat);
        ledgerDetails.setThuc_xuat(txuat);
        ledgerDetails.setNhiet_do_tt(Double.parseDouble(nhietdo.getText().isEmpty() ? "0" : nhietdo.getText()));
        ledgerDetails.setHe_so_vcf(Double.parseDouble(vcf.getText().isEmpty() ? "0" : vcf.getText()));
        ledgerDetails.setTy_trong(Double.parseDouble(tytrong.getText().isEmpty() ? "0" : tytrong.getText()));
        ledgerDetails.setLoaixd_id(lxd.getXd_id());
        ledgerDetails.setSoluong(txuat);
        ledgerDetails.setSoluong_px((long) pxuat);
        ledgerDetails.setThuc_nhap(0);
        ledgerDetails.setPhai_nhap(0);
        ledgerDetails.setNl_gio(Long.parseLong(nl_gio.getText().isEmpty() ? "0" : nl_gio.getText()));
        ledgerDetails.setNl_km(Long.parseLong(nl_km.getText().isEmpty() ? "0" : nl_km.getText()));
        if (lx.equals(LoaiXuat.X_K.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setHaohut_sl(0);
            ledgerDetails.setThuc_xuat(txuat);
        }else if (lx.equals(LoaiXuat.HH.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setThuc_xuat(0);
            ledgerDetails.setHaohut_sl(ledgerDetails.getSoluong());
        }else {
            ledgerDetails.setHaohut_sl(0);
            ledgerDetails.setPhuongtien_id(pt.getId());

            if (tk_rd.isSelected()){
                ledgerDetails.setThuc_xuat_tk(txuat);
                ledgerDetails.setThuc_xuat(0);
            } else if (md_rd.isSelected()) {
                ledgerDetails.setThuc_xuat_tk(0);
                ledgerDetails.setThuc_xuat(txuat);
            }
        }
        ledgerDetails.setThanhtien((long) ((long) ledgerDetails.getSoluong() * ledgerDetails.getDon_gia()));
        ledgerDetails.setThanhtien_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getThanhtien())));
        ledgerDetails.setThucxuat_str(TextToNumber.textToNum(String.valueOf(txuat)));
        ledgerDetails.setPhaixuat_str(TextToNumber.textToNum(String.valueOf(pxuat)));
        ledgerDetails.setDongia_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getDon_gia())));
        return ledgerDetails;
    }
    private void setInv_lb(long inv) {
        inv_price = inv;
        inv_lb.setText("Số lượng tồn: "+ TextToNumber.textToNum(String.valueOf(inv)) + " (Lit)");
    }
    private ChitietNhiemVu identifyNhiemvu(){
        String text = tcx.getText();
        String nv = text.substring(0,text.indexOf("-")).trim();
        String ct = text.substring(text.indexOf("-")+1).trim();
        Optional<NhiemVu> n = chitietNhiemvuService.findByName(nv,StatusCons.ACTIVED.getName());
        return n.flatMap(nhiemVu -> chitietNhiemvuService.findByNhiemvu(ct, nhiemVu.getId())).orElse(null);
    }
    private String getStrInterval(){
        String hour_str = sogio.getText().trim();
        String m_str = sophut.getText().trim();

        int hour1 = getHourMinute(Integer.parseInt(m_str)).get(0);
        int minute = getHourMinute(Integer.parseInt(m_str)).get(1);
        int sum_hour = Integer.parseInt(hour_str) + hour1;

        if (minute <10){
            return sum_hour+":0"+minute+":00";
        }
        if (sum_hour<10){
            return "0"+sum_hour+":"+minute+":00";
        }
        return sum_hour+":"+minute+":00";
    }

    private List<Integer> getHourMinute(int minute){
        if (minute>=0 && minute <60){
            return List.of(0,minute);
        }
        else if (minute>=60){
            int hour = minute/60;
            int remainder_minute = minute%60;
            return List.of(hour, remainder_minute);
        }
        return List.of();
    }

    private void mapItemsForXeMayTau(List<PhuongTien> phuongTiens) {
        ComponentUtil.setItemsToComboBox(xmt_cbb,phuongTiens,PhuongTien::getName, input -> phuongtienService.findPhuongTienByName(input).orElse(null));
        xmt_cbb.getSelectionModel().selectFirst();
    }
    private void mapItemsForDonvi(List<NguonNx> by,ComboBox<NguonNx> cbb) {
        setNguonnxCombobox(cbb, by);
        cbb.getSelectionModel().selectLast();
    }
    private void disableFeature(boolean ex) {
        lgb_hb.setDisable(ex);
        giohd.setDisable(ex);
        sokm_hb.setDisable(ex);
        xmt_hb.setDisable(ex);
        loaixemaytau.setDisable(ex);
        dvi_nhan.setDisable(!ex);
        tk_rd.setSelected(!ex);
        mb_rd.setSelected(!ex);
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
            }else if (ls.get(0).equals("bill_id")){
                so.setStyle(styleErrorField);
                return "so phai lon hon 0";
            }else if (ls.get(0).equals("tcn_id")){
                tcx.setStyle(styleErrorField);
                return "Tinh chat xuat khong xac dinh.";
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
                    setInv_lb(inv_price+ld.getSoluong());
                    tbView.setItems(FXCollections.observableList(ls_socai));
                    tbView.refresh();
                }
            }
        }
    }
}
