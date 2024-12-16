package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.model.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
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
    private static Mucgia mucgia_id_selected_mucgia_cbb = new Mucgia();
    private static List<LedgerDetails> ls_socai;
    private static List<NhiemVuDto> chiTietNhiemVuDTO_list = new ArrayList<>();
    private static AutoCompletionBinding<String> acbLogin;
    private static DinhMuc dinhMuc;

    @FXML
    private TextField so,tcx,nguoinhan,lenhso,soxe,sokm,sogio, sophut,phaixuat,thucxuat,nhietdo,vcf,tytrong,nl_gio,nl_km;
    @FXML
    private DatePicker tungay,denngay;
    @FXML
    private RadioButton md_rd,tk_rd,may_rd, xe_rd, mb_rd;
    @FXML
    private ComboBox<PhuongTien> xmt_cbb;
    @FXML
    private Label inv_lb, dm_gio, dm_km,lb_dm_km,lb_dm_gio,chungloai_lb;
    @FXML
    private ComboBox<NguonNx> dvn_cbb,dvx_cbb;
    @FXML
    private ComboBox<String> loai_xuat_cbb;
    @FXML
    private ComboBox<LoaiXangDauDto> cbb_tenxd;
    @FXML
    private ComboBox<Mucgia> cbb_dongia;
    @FXML
    private TableView<LedgerDetails> tbXuat;
    @FXML
    private TableColumn<LedgerDetails, String> stt, tenxd, dongia,col_phaixuat,col_nhietdo,col_tytrong,col_vcf,col_thucxuat,col_thanhtien;
    @FXML
    private HBox lgb_hb,giohd,sokm_hb,xmt_hb,loaixemaytau,dvi_nhan,px_hbox;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        current_ledger_list = ledgerService.getAllByQuarter(DashboardController.findByTime.getId(),LoaiPhieuCons.PHIEU_XUAT.getName());
        ls_socai = new ArrayList<>();
        tcnx_ls = tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        initDefailtVar();
        initLoaiXuatCbb();
        searchCompleteTion(tcnx_ls.stream().map(Tcn::getName).collect(Collectors.toList()));
        mapXdForCombobox("ALL");

        hoverButton(addBtn, "#027a20");
        hoverButton(xuatButton, "#002db3");
        hoverButton(cancelBtn, "#595959");
    }
    @FXML
    public void dongiaSelected(ActionEvent actionEvent) {
        setInv_lb();
    }

    @FXML
    public void dvnSelectedAction(ActionEvent actionEvent) {
    }

    @FXML
    public void nvSelected(ActionEvent actionEvent) {
        if (xmt_cbb.getSelectionModel().getSelectedItem()!=null){
            dinhMuc = new DinhMuc();
            dinhMuc = dinhmucService.findDinhmucByPhuongtien(xmt_cbb.getSelectionModel().getSelectedItem().getId(), DashboardController.findByTime.getId()).orElse(null);
            if (mb_rd.isSelected()){
                lb_dm_gio.setText("Đ.mức TK:");
                dm_gio.setText(TextToNumber.textToNum(String.valueOf(dinhMuc.getDm_tk_gio())));
                lb_dm_km.setText("Đ.mức MĐ:");
                dm_km.setText(TextToNumber.textToNum(String.valueOf(dinhMuc.getDm_md_gio())));
            }else{
                lb_dm_gio.setText("Đ.mức Giờ:");
                dm_gio.setText(TextToNumber.textToNum(String.valueOf(dinhMuc.getDm_xm_gio())));
                lb_dm_km.setText("Đ.mức Km:");
                dm_km.setText(TextToNumber.textToNum(String.valueOf(dinhMuc.getDm_xm_km())));
            }
        }
    }
    @FXML
    public void loaixuatAction(ActionEvent actionEvent) {
        if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.X_K.getName())){
            disableFeature(true);
            mapItemsForDonViXuat(nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()));
            mapItemsForDonViNhap(nguonNxService.findAll());
            List<PhuongTien> pt= new ArrayList<>();
            mapItemsForXeMayTau(pt);
            tcnx_ls = tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_XUAT.getName());
            acbLogin.dispose();
            searchCompleteTion(tcnx_ls.stream().map(Tcn::getName).collect(Collectors.toList()));
            cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findAllOrderby()));
            cbb_tenxd.getSelectionModel().selectFirst();
            px_hbox.setDisable(false);
        } else if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.NV.getName())) {
            disableFeature(false);
            List<NguonNx> nxListw = new ArrayList<>();
            mapItemsForDonViNhap(nxListw);
            mapItemsForDonViXuat(nguonNxService.findByAllBy());
            mapItemsForXeMayTau(phuongtienService.findAll());
            chiTietNhiemVuDTO_list = chitietNhiemvuService.findAllDtoBy(3);
            acbLogin.dispose();
            searchCompleteTion(chiTietNhiemVuDTO_list.stream().map(NhiemVuDto::getChitiet).collect(Collectors.toList()));
            cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByType(LoaiXDCons.DAUBAY.getName(), LoaiXDCons.DAUHACAP.getName())));
            cbb_tenxd.getSelectionModel().selectFirst();
            px_hbox.setDisable(true);
        } else if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.HH.getName())) {
            disableFeature(true);
            mapItemsForDonViXuat(nguonNxService.findByAllBy());
            mapItemsForDonViNhap(new ArrayList<>());
            dvi_nhan.setDisable(true);
            List<PhuongTien> pt= new ArrayList<>();
            mapItemsForXeMayTau(pt);
            chiTietNhiemVuDTO_list = chitietNhiemvuService.findAllDtoById(3);
            acbLogin.dispose();
            searchCompleteTion(chiTietNhiemVuDTO_list.stream().map(NhiemVuDto::getChitiet).collect(Collectors.toList()));
            cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findAllOrderby()));
            cbb_tenxd.getSelectionModel().selectFirst();
            px_hbox.setDisable(false);
        }
    }

    @FXML
    public void mbRadioSel(ActionEvent actionEvent) {
        mapItemsForXeMayTau(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle()));
        lgb_hb.setDisable(false);
        System.out.println(loaiXdService.findByType(LoaiXDCons.DAUBAY.getName(),LoaiXDCons.DAUHACAP.getName()).size());
        cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByType(LoaiXDCons.DAUBAY.getName(),LoaiXDCons.DAUHACAP.getName())));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(true);
    }
    @FXML
    public void xeRadioSelec(ActionEvent actionEvent) {
        mapItemsForXeMayTau(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.XE.getNameVehicle()));
        md_rd.setSelected(true);
        lgb_hb.setDisable(true);
        cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByType(LoaiXDCons.XANG.getName(),LoaiXDCons.DIEZEL.getName())));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(false);
    }
    @FXML
    public void mayRadioSelec(ActionEvent actionEvent) {
        mapItemsForXeMayTau(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAY.getNameVehicle()));
        md_rd.setSelected(true);
        lgb_hb.setDisable(true);
        cbb_tenxd.setItems(FXCollections.observableList(loaiXdService.findByType(LoaiXDCons.XANG.getName(),LoaiXDCons.DIEZEL.getName())));
        cbb_tenxd.getSelectionModel().selectFirst();
        px_hbox.setDisable(false);
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        if (validateField(getLedgerDetails()).isEmpty()) {
            ls_socai.add(getLedgerDetails());
            setCellValueFactory();
            clearFields();
        }else{
            DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(getLedgerDetails()),
                    "Nhập sai định dạng.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void xuat(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("XUẤT", "TẠO PHIẾU XUẤT", "Xác nhận tạo phiếu XUẤT",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
            Ledger l = getLedger();
            if (validateField(l).isEmpty()) {
                ledgerService.saveLedgerWithDetails(l, ls_socai);
            }else{
                DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(l),
                        "Nhập sai định dạng.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        DashboardController.xuatStage.close();
    }
    @FXML
    public void edit(ActionEvent actionEvent) {
    }
    @FXML
    public void del(ActionEvent actionEvent) {
    }
    
    private int cal_phaixuat_km(int sokm, int dinhmuc){
        return (sokm*dinhmuc)/100;
    }
    private float cal_phaixuat_gio(float sogio, int dinhmuc){
        return (float) sogio*dinhmuc;
    }
    @FXML
    public void cal_nl_gio(ActionEvent actionEvent) {
        float phut = (float) Integer.parseInt(sophut.getText())/60;
        int du = (Integer.parseInt(sophut.getText()))/60;
        float gio = Integer.parseInt(sogio.getText()) + phut + du;
        if (mb_rd.isSelected()){
            if (tk_rd.isSelected()){
                nl_gio.setText(String.valueOf((int) cal_phaixuat_gio(gio, Objects.requireNonNull(dinhmucService.findDinhmucByPhuongtien(xmt_cbb.getValue().getId(), DashboardController.findByTime.getId()).orElse(null)).getDm_tk_gio())));
            } else{
                nl_gio.setText(String.valueOf((int) cal_phaixuat_gio(gio, Objects.requireNonNull(dinhmucService.findDinhmucByPhuongtien(xmt_cbb.getValue().getId(), DashboardController.findByTime.getId()).orElse(null)).getDm_md_gio())));
            }
        }else{
            nl_gio.setText(String.valueOf((int) cal_phaixuat_gio(gio, Objects.requireNonNull(dinhmucService.findDinhmucByPhuongtien(xmt_cbb.getValue().getId(), DashboardController.findByTime.getId()).orElse(null)).getDm_xm_gio())));
        }
    }
    @FXML
    public void selectxd(ActionEvent actionEvent) {
        if (cbb_tenxd.getSelectionModel().getSelectedItem()!=null){
            chungloai_lb.setText("Chủng loại: "+cbb_tenxd.getSelectionModel().getSelectedItem().getChungloai());
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
            if (current_ledger_list.stream().filter(i->i.getBill_id()==Integer.parseInt(so.getText())).findFirst().isPresent()){
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

    private void initLoaiXuatCbb() {
        loai_xuat_cbb.setItems(FXCollections.observableList(List.of(LoaiXuat.X_K.getName(),LoaiXuat.NV.getName(), LoaiXuat.HH.getName())));
        loai_xuat_cbb.getSelectionModel().selectFirst();
        mapItemsForDonViXuat(nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()));
        mapItemsForDonViNhap(nguonNxService.findAll());
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

    private void mapXdForCombobox(String type){
        cbb_tenxd.setConverter(new StringConverter<LoaiXangDauDto>() {
            @Override
            public String toString(LoaiXangDauDto object) {
                return object == null ? "": object.getTenxd();
            }
            @Override
            public LoaiXangDauDto fromString(String string) {
                return loaiXdService.findAllTenxdDto(string).orElse(null);
            }
        });
        cbb_tenxd.getItems().addAll(loaiXdService.findAllOrderby());
        cbb_tenxd.getSelectionModel().selectFirst();
        mapPrice(cbb_tenxd.getSelectionModel().getSelectedItem().getXd_id());
    }
    private void mapPrice(int xd_id){
        cbb_dongia.setConverter(new StringConverter<Mucgia>() {
            @Override
            public String toString(Mucgia object) {
                if (object!=null){
                    mucgia_id_selected_mucgia_cbb = object;
                }
                return object==null ? "": TextToNumber.textToNum(String.valueOf(object.getPrice()));
            }

            @Override
            public Mucgia fromString(String string) {
                return mucgiaService.findMucGiaByIdAndStatus(mucgia_id_selected_mucgia_cbb.getId(), MucGiaEnum.IN_STOCK.getStatus()).orElse(null);
            }
        });
        List<Mucgia> mucgials = mucgiaService.findAllMucgiaByItemID(xd_id,DashboardController.findByTime.getId());
        cbb_dongia.setItems(FXCollections.observableArrayList(mucgials));
        cbb_dongia.getSelectionModel().selectFirst();
        setInv_lb();
    }
    private void setCellValueFactory(){
        tbXuat.setItems(FXCollections.observableList(ls_socai));
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tbXuat.getItems().indexOf(column.getValue())+1).asString());
        tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        col_phaixuat.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phaixuat_str"));
        col_thucxuat.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thucxuat_str"));
        col_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        col_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        col_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        col_thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
        tbXuat.refresh();
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
        Ledger ledger = new Ledger();
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
        ledger.setDvi_nhan(dvn_cbb.getValue()==null ? "" : dvn_cbb.getValue().getTen());
        ledger.setDvi_xuat(dvx_cbb.getValue().getTen());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        ledger.setDvi_nhan_id(dvn_cbb.getValue()==null? 0 : dvn_cbb.getValue().getId());
        ledger.setDvi_xuat_id(dvx_cbb.getValue().getId());
        ledger.setNguoi_nhan(nguoinhan.getText());
        ledger.setSo_xe(soxe.getText());
        ledger.setLenh_so(lenhso.getText());
        if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.NV.getName())){
            ledger.setTructhuoc(TructhuocEnum.NV.getName());
            ledger.setLoaigiobay(tk_rd.isSelected() ? TypeCons.TREN_KHONG.getName() : TypeCons.MAT_DAT.getName());
            ledger.setNhiemvu(identifyNhiemvu().getNhiemvu());
            ledger.setNhiemvu_id(identifyNhiemvu().getId());
            ledger.setLpt(phuongtienService.findById(xmt_cbb.getValue().getId()).orElse(null).getLoaiPhuongTien().getTypeName());
            ledger.setLpt_2(phuongtienService.findById(xmt_cbb.getValue().getId()).orElse(null).getLoaiPhuongTien().getType());
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
        } else if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.HH.getName())){
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
            ledger.setTructhuoc(tructhuocService.findById(dvn_cbb.getValue().getTructhuoc_id()).orElseThrow().getType());
            ledger.setLoaigiobay("");
            ledger.setNhiemvu("");
            ledger.setNhiemvu_id(0);
            ledger.setTcn_id(tcnService.findByName(tcx.getText()).orElse(null)==null ? 0 : tcnService.findByName(tcx.getText()).orElse(null).getId());
            ledger.setSo_km(0);
            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
        }
        return ledger;
    }
    private LedgerDetails getLedgerDetails(){
        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setTen_xd(cbb_tenxd.getSelectionModel().getSelectedItem().getTenxd());
        ledgerDetails.setMa_xd(cbb_tenxd.getSelectionModel().getSelectedItem().getMaxd());
        ledgerDetails.setChung_loai(cbb_tenxd.getSelectionModel().getSelectedItem().getChungloai());
        ledgerDetails.setDon_gia(cbb_dongia.getValue().getPrice());
        ledgerDetails.setPhai_xuat(Integer.parseInt(phaixuat.getText().isEmpty() ? "0" : phaixuat.getText()));
        ledgerDetails.setThuc_xuat(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
        ledgerDetails.setNhiet_do_tt(Double.parseDouble(nhietdo.getText().isEmpty() ? "0" : nhietdo.getText()));
        ledgerDetails.setHe_so_vcf(Integer.parseInt(vcf.getText().isEmpty() ? "0" : vcf.getText()));
        ledgerDetails.setTy_trong(Double.parseDouble(tytrong.getText().isEmpty() ? "0" : tytrong.getText()));
        ledgerDetails.setLoaixd_id(cbb_tenxd.getSelectionModel().getSelectedItem().getXd_id());
        ledgerDetails.setSoluong(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
        ledgerDetails.setSoluong_px(Long.parseLong(phaixuat.getText().isEmpty() ? "0" : phaixuat.getText()));
        ledgerDetails.setThuc_nhap(0);
        ledgerDetails.setPhai_nhap(0);
        ledgerDetails.setNl_gio(Long.parseLong(nl_gio.getText().isEmpty() ? "0" : nl_gio.getText()));
        ledgerDetails.setNl_km(Long.parseLong(nl_km.getText().isEmpty() ? "0" : nl_km.getText()));
        if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.X_K.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setHaohut_sl(0);
            ledgerDetails.setThuc_xuat(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
        }else if (loai_xuat_cbb.getSelectionModel().getSelectedItem().equals(LoaiXuat.HH.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setThuc_xuat(0);
            ledgerDetails.setHaohut_sl(ledgerDetails.getSoluong());
        }else {
            ledgerDetails.setHaohut_sl(0);
            ledgerDetails.setPhuongtien_id(xmt_cbb.getValue().getId());

            if (tk_rd.isSelected()){
                ledgerDetails.setThuc_xuat_tk(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
                ledgerDetails.setThuc_xuat(0);
            } else if (md_rd.isSelected()) {
                ledgerDetails.setThuc_xuat_tk(0);
                ledgerDetails.setThuc_xuat(Integer.parseInt(thucxuat.getText().isEmpty() ? "0" : thucxuat.getText()));
            }
        }
        ledgerDetails.setThanhtien((long) (ledgerDetails.getSoluong() * ledgerDetails.getDon_gia()));
        ledgerDetails.setThanhtien_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getThanhtien())));
        ledgerDetails.setThucxuat_str(TextToNumber.textToNum(thucxuat.getText()));
        ledgerDetails.setPhaixuat_str(TextToNumber.textToNum(phaixuat.getText()));
        ledgerDetails.setDongia_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getDon_gia())));
        return ledgerDetails;
    }
    private void setInv_lb() {
        Mucgia mucgia = mucgiaService.findMucGiaByIdAndStatus(cbb_dongia.getSelectionModel().getSelectedItem().getId(), MucGiaEnum.IN_STOCK.getStatus()).orElseThrow();
        inv_lb.setText("Số lượng tồn: "+ TextToNumber.textToNum(String.valueOf(mucgia.getAmount())) + " (Lit)");
    }
    private void saveLichsuxnk(LedgerDetails soCaiDto) {
//        Inventory inventory = inventoryService.findByPetro_idAndQuarter_id(soCaiDto.getLoaixd_id(), DashboardController.findByTime.getId()).orElse(null);
//        int tonsau = inventory.getPre_nvdx() - soCaiDto.getSoluong();
//        int tontruoc = inventory.getPre_nvdx();
//        createNewTransaction(soCaiDto, tontruoc, tonsau);
    }
    private ChitietNhiemVu identifyNhiemvu(){
        String text = tcx.getText();
        return chitietNhiemvuService.findByNhiemvu(text).orElse(null);
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
        xmt_cbb.setItems(FXCollections.observableList(phuongTiens));
        xmt_cbb.setConverter(new StringConverter<PhuongTien>() {
            @Override
            public String toString(PhuongTien object) {
                return object==null ? "" : object.getName();
            }

            @Override
            public PhuongTien fromString(String string) {
                return phuongtienService.findById(xmt_cbb.getSelectionModel().getSelectedItem().getId()).orElse(null);
            }
        });
        xmt_cbb.getSelectionModel().selectFirst();
    }

    private void mapItemsForDonViXuat(List<NguonNx> nxList) {
        dvx_cbb.setItems(FXCollections.observableArrayList(nxList));
        dvx_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findByTen(string).orElse(null);
            }
        });
        dvx_cbb.getSelectionModel().selectLast();
    }

    private void mapItemsForDonViNhap(List<NguonNx> by) {
        dvn_cbb.setItems(FXCollections.observableArrayList(by));
        dvn_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findByTen(string).orElse(null);
            }
        });
        dvn_cbb.getSelectionModel().selectLast();
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
}
