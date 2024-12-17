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
    private static long inv_price;
    private static List<LedgerDetails> ls_socai;
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
        initDefailtVar();
        initLoaiXuatCbb();
        searchCompleteTion(tcnx_ls.stream().map(Tcn::getName).collect(Collectors.toList()));
        mapXdForCombobox();

        hoverButton(addBtn, "#027a20");
        hoverButton(xuatButton, "#002db3");
        hoverButton(cancelBtn, "#595959");
    }
    @FXML
    public void dongiaSelected(ActionEvent actionEvent) {
        setInvenPrice();
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
                mapItemsForDonViXuat(nguonNx);
            }else{
                mapItemsForDonViXuat(nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()));
            }
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
                    new ArrayList<>(),nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()),nguonNxService.findAll(),loaiXdService.findAllOrderby()
                    ,true);
            nl_km_hb.setDisable(true);
            nl_gio_hb.setDisable(true);
        } else if (lx.equals(LoaiXuat.NV.getName())) {
            initValueForLoaiXuatCbb(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName()).stream().map(NhiemVuDto::getChitiet).collect(Collectors.toList()),
                    phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle()),nguonNxService.findByAllBy(),new ArrayList<>(),loaiXdService.findByType(LoaiXDCons.DAUBAY.getName(), LoaiXDCons.DAUHACAP.getName())
                    ,false);
        } else if (lx.equals(LoaiXuat.HH.getName())) {
            initValueForLoaiXuatCbb(chitietNhiemvuService.findAllDtoById(LoaiNVCons.HAOHUT.getName()).stream().map(NhiemVuDto::getChitiet).collect(Collectors.toList()),
                    new ArrayList<>(),nguonNxService.findByAllBy(),new ArrayList<>(),loaiXdService.findAllOrderby(),true);
            dvi_nhan.setDisable(true);
        }
    }

    @FXML
    public void mbRadioSel(ActionEvent actionEvent) {
        setLoaiXangDauByRadio(LoaiPTEnum.MAYBAY.getNameVehicle(), true,LoaiXDCons.DAUBAY.getName(),LoaiXDCons.DAUHACAP.getName());
        setNhiemvuForField(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName()));
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
        if (!outfieldValid(tcx, "tinh chat xuat khong duoc de trong.")){
            if (inv_price < ld.getSoluong()){
                DialogMessage.message("Error", "so luong xuat > so luong ton kho","Co loi xay ra", Alert.AlertType.ERROR);
            } else {
                if (validateField(ld).isEmpty()) {
                    if (!isDuplicate(ld.getLoaixd_id(),ld.getThuc_xuat(),ld.getPhai_xuat())){
                        ls_socai.add(ld);
                    }
                    setCellValueFactory();
                    clearFields();
                } else {
                    DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(ld),
                            "Nhập sai định dạng.", Alert.AlertType.ERROR);
                }
            }
        }

    }
    private boolean isDuplicate(int xd_id,int tx, int px){
        for (int i =0; i< ls_socai.size(); i++){
            LedgerDetails ld = ls_socai.get(i);
            if (ld.getLoaixd_id() == xd_id){
                ld.setThuc_xuat(ld.getThuc_xuat() + tx);
                ld.setPhai_xuat(ld.getPhai_xuat() + px);
                ld.setSoluong(ld.getThuc_xuat() + tx);
                ld.setSoluong_px((long) ld.getPhai_xuat() + px);
                ld.setThucxuat_str(TextToNumber.textToNum(String.valueOf(ld.getThuc_xuat())));
                ld.setPhaixuat_str(TextToNumber.textToNum(String.valueOf(ld.getPhai_xuat())));
                ld.setThanhtien_str(TextToNumber.textToNum(String.valueOf((long) ld.getThuc_xuat()*ld.getDon_gia())));
                ls_socai.set(i, ld);
                return true;
            }
        }
        return false;
    }
    @FXML
    public void xuat(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("XUẤT", "TẠO PHIẾU XUẤT", "Xác nhận tạo phiếu XUẤT",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
            Ledger l = getLedger();
            if (validateField(l).isEmpty()) {
                ledgerService.saveLedgerWithDetails(l, ls_socai);
                DialogMessage.message("Thong bao", "Them phieu XUAT thanh cong..",
                        "Thanh cong", Alert.AlertType.INFORMATION);
                DashboardController.xuatStage.close();
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

    private int cal_phaixuat_km(int sokm, int dinhmuc){
        return (sokm*dinhmuc)/100;
    }

    private float cal_phaixuat_gio(float sogio, int dinhmuc){
        return sogio *dinhmuc;
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
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            mapPrice(lxd.getXd_id());
            chungloai_lb.setText("Chủng loại: "+lxd.getChungloai());
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
    private void setNhiemvuForField(List<NhiemVuDto> nhiemVuDtos){
        acbLogin.dispose();
        searchCompleteTion(nhiemVuDtos.stream().map(NhiemVuDto::getChitiet).collect(Collectors.toList()));
    }
    private void initValueForLoaiXuatCbb(List<String> nhiemVuDtos, List<PhuongTien> pt,List<NguonNx> dvx,List<NguonNx> dvn,List<LoaiXangDauDto> lxd,boolean pxhbox) {
        disableFeature(pxhbox);
        mapItemsForDonViXuat(dvx);
        mapItemsForDonViNhap(dvn);
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

    private void mapXdForCombobox(){
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
        int quarter_id = DashboardController.findByTime.getId();
        cbb_dongia.setConverter(new StringConverter<Mucgia>() {
            @Override
            public String toString(Mucgia object) {
                return object==null ? "": TextToNumber.textToNum(String.valueOf(object.getPrice()));
            }

            @Override
            public Mucgia fromString(String string) {
                return mucgiaService.findAllMucgiaUnique(xd_id,quarter_id,Integer.parseInt(string)).orElse(null);
            }
        });
        List<Mucgia> mucgials = mucgiaService.findAllMucgiaByItemID(xd_id,DashboardController.findByTime.getId(),MucGiaEnum.IN_STOCK.getStatus());
        cbb_dongia.setItems(FXCollections.observableArrayList(mucgials));
        cbb_dongia.getSelectionModel().selectFirst();
        setInvenPrice();
    }

    private void setInvenPrice(){
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        Mucgia m = cbb_dongia.getSelectionModel().getSelectedItem();
        if (lxd != null && m!=null) {
            Optional<Mucgia> mucgia = mucgiaService.findAllMucgiaUnique(lxd.getXd_id(),DashboardController.findByTime.getId(),m.getPrice());
            if (mucgia.isPresent()){
                setInv_lb(mucgia.get().getNvdx());
            }
        }
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

        NguonNx dvx = dvx_cbb.getSelectionModel().getSelectedItem();
        NguonNx dvn = dvn_cbb.getSelectionModel().getSelectedItem();
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();

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
        ledger.setDvi_nhan(dvn==null ? "" : dvn.getTen());
        ledger.setDvi_xuat(dvx.getTen());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        ledger.setDvi_nhan_id(dvn==null? 0 : dvn.getId());
        ledger.setDvi_xuat_id(dvx.getId());
        ledger.setNguoi_nhan(nguoinhan.getText());
        ledger.setSo_xe(soxe.getText());
        ledger.setLenh_so(lenhso.getText());
        if (lx.equals(LoaiXuat.NV.getName())){
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
        Mucgia m = cbb_dongia.getSelectionModel().getSelectedItem();
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();

        ledgerDetails.setTen_xd(lxd.getTenxd());
        ledgerDetails.setMa_xd(lxd.getMaxd());
        ledgerDetails.setChung_loai(lxd.getChungloai());
        ledgerDetails.setDon_gia(m.getPrice());
        ledgerDetails.setPhai_xuat(pxuat);
        ledgerDetails.setThuc_xuat(txuat);
        ledgerDetails.setNhiet_do_tt(Double.parseDouble(nhietdo.getText().isEmpty() ? "0" : nhietdo.getText()));
        ledgerDetails.setHe_so_vcf(Integer.parseInt(vcf.getText().isEmpty() ? "0" : vcf.getText()));
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
    @FXML
    public void selected_item(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            if (DialogMessage.callAlertWithMessage("Delete", "Xoa", "Xác nhận xoa",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
                LedgerDetails ld = tbXuat.getSelectionModel().getSelectedItem();
                if (ld!=null){
                    ls_socai.remove(ld);
                    setInv_lb(inv_price-ld.getSoluong());
                    tbXuat.setItems(FXCollections.observableList(ls_socai));
                    tbXuat.refresh();
                }
            }
        }
    }
}
