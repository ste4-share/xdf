package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.model.*;
import com.xdf.xd_f371.repo.*;
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
import javafx.scene.layout.HBox;
import javafx.util.StringConverter;
import lombok.extern.slf4j.Slf4j;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Slf4j
@Component
public class XuatController extends CommonFactory implements Initializable {

    private static Mucgia mucgia_id_selected_mucgia_cbb = new Mucgia();
    private boolean addedBySelection_lstb = false;
    private static List<LedgerDetails> ls_socai;
    private static List<NhiemVuDto> chiTietNhiemVuDTO_list = new ArrayList<>();
    private static NhiemVuDto nhiemVu_selected=  new NhiemVuDto();


    @FXML
    private TextField so,tcx,nguoinhan,lenhso,soxe,sokm,sogio, sophut,phaixuat,thucxuat,nhietdo,vcf,tytrong;
    @FXML
    private DatePicker tungay,denngay;
    @FXML
    private RadioButton md_rd,tk_rd,may_rd, xe_rd, mb_rd;
    @FXML
    private ComboBox<PhuongTien> xmt_cbb;
    @FXML
    private Label inv_lb;
    @FXML
    private ComboBox<NguonNx> dvn_cbb,dvx_cbb;
    @FXML
    private ComboBox<String> loai_xuat;
    @FXML
    private ComboBox<LoaiXangDau> cbb_tenxd;
    @FXML
    private ComboBox<Mucgia> cbb_dongia;
    @FXML
    private TableView<LedgerDetails> tbXuat;
    @FXML
    private TableColumn<LedgerDetails, String> stt, tenxd, dongia,col_phaixuat,col_nhietdo,col_tytrong,col_vcf,col_thucxuat,col_thanhtien;
    @FXML
    private HBox lgb_hb,giohd,sokm_hb,xmt_hb,loaixemaytau,dvi_nhan;

    @Autowired
    private ChitietNhiemvuRepo chitietNhiemvuRepo;
    @Autowired
    private HanmucNhiemvuRepo hanmucNhiemvuRepo;
    @Autowired
    private NguonNxRepo nguonNxRepo;
    @Autowired
    private LoaiXangDauRepo loaiXangDauRepo;
    @Autowired
    private PhuongtienRepo phuongtienRepo;
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private TcnRepo tcnRepo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ls_socai = new ArrayList<>();
        chiTietNhiemVuDTO_list = chitietNhiemvuRepo.findAllDtoBy();
        tcnx_ls = tcnRepo.findByLoaiphieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        initLoaiXuatCbb();
        searchCompleteTion(tcnx_ls.stream().map(Tcn::getName).collect(Collectors.toList()));
        mapXdForCombobox("ALL");
    }

    private void initLoaiXuatCbb() {
        loai_xuat.setItems(FXCollections.observableList(List.of(LoaiXuat.X_K.getName(),LoaiXuat.NV.getName())));
        loai_xuat.getSelectionModel().selectFirst();
        mapItemsForDonViXuat(nguonNxRepo.findByStatus(StatusEnum.ROOT_STATUS.getName()));
        mapItemsForDonViNhap(nguonNxRepo.findAll());
        List<PhuongTien> pt= new ArrayList<>();
        mapItemsForXeMayTau(pt);
        disableFeature(true);
    }
    private void searchCompleteTion(List<String> search_arr){
        TextFields.bindAutoCompletion(tcx, t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });
    }

    private void mapXdForCombobox(String type){
        cbb_tenxd.setConverter(new StringConverter<LoaiXangDau>() {
            @Override
            public String toString(LoaiXangDau object) {
                return object == null ? "": object.getTenxd();
            }
            @Override
            public LoaiXangDau fromString(String string) {
                return loaiXangDauRepo.findByTenxd(string);
            }
        });
        cbb_tenxd.getItems().addAll(loaiXangDauRepo.findAll());
        cbb_tenxd.getSelectionModel().selectFirst();
        mapPrice(cbb_tenxd.getSelectionModel().getSelectedItem().getId());
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
                return mucGiaRepo.findMucGiaByIdAndStatus(mucgia_id_selected_mucgia_cbb.getId(), MucGiaEnum.IN_STOCK.getStatus()).orElse(null);
            }
        });
        List<Mucgia> mucgials = mucGiaRepo.findAllMucgiaByItemID(Purpose.NVDX.getName(),xd_id,DashboardController.findByTime.getId());
        cbb_dongia.setItems(FXCollections.observableArrayList(mucgials));
        cbb_dongia.getSelectionModel().selectFirst();
        setInv_lb();
    }
    private void fillDataToTextField_tab_k(LedgerDetails ledgerDetails){
        cbb_tenxd.getSelectionModel().select(loaiXangDauRepo.findById(ledgerDetails.getLoaixd_id()).orElse(null));
        phaixuat.setText(String.valueOf(ledgerDetails.getPhai_xuat()));
        thucxuat.setText(String.valueOf(ledgerDetails.getThuc_xuat()));
        nhietdo.setText(String.valueOf(ledgerDetails.getNhiet_do_tt()));
        vcf.setText(String.valueOf(ledgerDetails.getHe_so_vcf()));
        tytrong.setText(String.valueOf(ledgerDetails.getTy_trong()));
    }

    private void setCellValueFactory(LedgerDetails l){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tbXuat.getItems().indexOf(column.getValue())+1).asString());
        tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("don_gia"));
        col_phaixuat.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phai_xuat"));
        col_thucxuat.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("so_luong"));
        col_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        col_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        col_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        col_thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien"));
    }
    private void clearFields(){
        phaixuat.clear();
        thucxuat.clear();
        nhietdo.clear();
        vcf.clear();
        tytrong.clear();
    }

    private LedgerDetails getLedgerDetails(){
        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setTen_xd(cbb_tenxd.getSelectionModel().getSelectedItem().getTenxd());
        ledgerDetails.setMa_xd(cbb_tenxd.getSelectionModel().getSelectedItem().getMaxd());
        ledgerDetails.setChung_loai(cbb_tenxd.getSelectionModel().getSelectedItem().getChungLoaiXd().getChungloai());
        ledgerDetails.setDon_gia(Integer.parseInt(dongia.getText()));
        ledgerDetails.setPhai_xuat(Integer.parseInt(phaixuat.getText()));
        ledgerDetails.setThuc_xuat(Integer.parseInt(thucxuat.getText()));
        ledgerDetails.setNhiet_do_tt(Double.parseDouble(nhietdo.getText()));
        ledgerDetails.setHe_so_vcf(Integer.parseInt(vcf.getText()));
        ledgerDetails.setTy_trong(Double.parseDouble(tytrong.getText()));
        ledgerDetails.setLoaixd_id(cbb_tenxd.getSelectionModel().getSelectedItem().getId());
        ledgerDetails.setSoluong(Integer.parseInt(thucxuat.getText()));
        ledgerDetails.setThuc_nhap(0);
        ledgerDetails.setPhai_nhap(0);

        if (loai_xuat.getSelectionModel().getSelectedItem().equals(LoaiXuat.X_K.getName())){
            ledgerDetails.setPhuongtien_id(0);
            ledgerDetails.setThuc_xuat_tk(0);
            ledgerDetails.setNhiemvu_hanmuc_id(0);
            ledgerDetails.setPx_soluong(Integer.parseInt(thucxuat.getText()));
        }else {
            ledgerDetails.setPhuongtien_id(xmt_cbb.getValue().getId());
            ledgerDetails.setNhiemvu_hanmuc_id(hanmucNhiemvuRepo.findByUniqueIds(dvx_cbb.getValue().getId(),nhiemVu_selected.getCtnv_id(),DashboardController.findByTime.getId()).get().getId());
            if (tk_rd.isSelected()){
                ledgerDetails.setThuc_xuat_tk(Integer.parseInt(thucxuat.getText()));
                ledgerDetails.setThuc_xuat(0);
            } else if (md_rd.isSelected()) {
                ledgerDetails.setThuc_xuat_tk(0);
                ledgerDetails.setThuc_xuat(Integer.parseInt(thucxuat.getText()));
            }
        }
        ledgerDetails.setThanhtien(ledgerDetails.getSoluong() * Integer.parseInt(dongia.getText()));
        return ledgerDetails;
    }
    private void setInv_lb() {
        Mucgia mucgia = mucGiaRepo.findMucGiaByIdAndStatus(cbb_dongia.getSelectionModel().getSelectedItem().getId(), MucGiaEnum.IN_STOCK.getStatus()).orElseThrow();
        inv_lb.setText("Số lượng tồn: "+ TextToNumber.textToNum(String.valueOf(mucgia.getAmount())) + " (Lit)");
    }

    private void saveLichsuxnk(LedgerDetails soCaiDto) {
        Inventory inventory = inventoryRepo.findByPetro_idAndQuarter_id(soCaiDto.getLoaixd_id(), DashboardController.findByTime.getId()).orElse(null);
        int tonsau = inventory.getPre_nvdx() - soCaiDto.getSoluong();
        int tontruoc = inventory.getPre_nvdx();
        createNewTransaction(soCaiDto, tontruoc, tonsau);
    }

    private Mucgia saveMucgia(LedgerDetails ld, Ledger l){
        Mucgia m = mucGiaRepo.findAllMucgiaUnique(Purpose.NVDX.getName(), ld.getLoaixd_id(), DashboardController.findByTime.getId(), ld.getDon_gia()).orElse(null);
        if (m == null){
            return mucGiaRepo.save(new Mucgia(ld.getDon_gia(), ld.getSoluong(),l.getQuarter_id(),ld.getLoaixd_id(),l.getInventoryId(),Purpose.NVDX.getName(),MucGiaEnum.IN_STOCK.getStatus()));
        }
        m.setAmount(m.getAmount() - ld.getSoluong());
        return mucGiaRepo.save(m);
    }
    private void updateInventory(Ledger l, LedgerDetails ld) {
        Inventory i = inventoryRepo.findById(l.getInventoryId()).orElseThrow();
        i.setPre_nvdx(i.getPre_nvdx() - ld.getSoluong());
        inventoryRepo.save(i);
    }

    private Ledger createNewLedger() {
        Ledger ledger = new Ledger();
        ledger.setBill_id(Integer.parseInt(so.getText()));
        ledger.setQuarter_id(DashboardController.findByTime.getId());
        ledger.setAmount(ls_socai.stream().mapToLong(x->(x.getThuc_xuat()*x.getDon_gia())).sum());
        ledger.setFrom_date(java.sql.Date.valueOf(tungay.getValue()));
        ledger.setEnd_date(java.sql.Date.valueOf(denngay.getValue()));
        ledger.setStatus("ACTIVE");
        if (tk_rd.isSelected()){
            ledger.setSl_tieuthu_md(0);
            ledger.setSl_tieuthu_tk(Integer.parseInt(thucxuat.getText()));
        }else if (md_rd.isSelected()){
            ledger.setSl_tieuthu_md(Integer.parseInt(thucxuat.getText()));
            ledger.setSl_tieuthu_tk(0);
        }else{
            ledger.setSl_tieuthu_md(0);
            ledger.setSl_tieuthu_tk(0);
        }
        ledger.setInventoryId(inventoryRepo.findByPetro_idAndQuarter_id(cbb_tenxd.getSelectionModel().getSelectedItem().getId(), DashboardController.findByTime.getId()).orElseThrow().getId());
        ledger.setDvi_nhan(dvn_cbb.getValue().getTen());
        ledger.setDvi_xuat(dvx_cbb.getValue().getTen());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        ledger.setDvi_nhan_id(dvn_cbb.getValue().getId());
        ledger.setDvi_xuat_id(dvx_cbb.getValue().getId());
        ledger.setNguoi_nhan(nguoinhan.getText());
        ledger.setSo_xe(soxe.getText());
        ledger.setLenh_so(lenhso.getText());
        ledger.setTcn_id(tcnRepo.findByName(tcx.getText()).orElseThrow().getId());
        if (loai_xuat.getSelectionModel().getSelectedItem().equals(LoaiXuat.NV.getName())){
            ledger.setLoaigiobay(tk_rd.isSelected() ? TypeCons.TREN_KHONG.getName() : TypeCons.MAT_DAT.getName());
            ledger.setNhiemvu(nhiemVu_selected.getTen_nv() + " | " + nhiemVu_selected.getChitiet());
            ledger.setNhiemvu_id(nhiemVu_selected.getCtnv_id());
            if (tk_rd.isSelected()){
                ledger.setSo_km(0);
                ledger.setGiohd_tk(getStrInterval());
                ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
            } else if (md_rd.isSelected()) {
                ledger.setSo_km(Integer.parseInt(sokm.getText()));
                ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
                ledger.setGiohd_md(getStrInterval());
            }
            ledger.setTcn_id(0);
        }else {
            ledger.setLoaigiobay("");
            ledger.setNhiemvu("");
            ledger.setNhiemvu_id(0);
            ledger.setTcn_id(tcnRepo.findByName(tcx.getText()).orElseThrow().getId());
            ledger.setSo_km(0);
            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
        }
        return ledgersRepo.save(ledger);
    }

    private void setUpForSearchCompleteTion(){
        TextFields.bindAutoCompletion(tcx, t -> {
            return chiTietNhiemVuDTO_list.stream().map(NhiemVuDto::getChitiet).toList().stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });
        tcx.setOnKeyPressed(e -> {
            addedBySelection_lstb = false;
        });
        tcx.setOnKeyReleased(e -> {
            addedBySelection_lstb = true;
        });
        tcx.textProperty().addListener(e -> {
            if (addedBySelection_lstb) {
                identifyNhiemvu();
                addedBySelection_lstb = false;
            }
        });
    }
    private boolean identifyNhiemvu(){
        String text = tcx.getText();
        String[] arr = splitAssignment(text);
        if (arr[1]==null){
            for (int i = 0; i< chiTietNhiemVuDTO_list.size(); i++){
                NhiemVuDto chiTietNhiemVuDTO = chiTietNhiemVuDTO_list.get(i);
                if (arr[0].equals(chiTietNhiemVuDTO.getTen_nv())){
                    nhiemVu_selected = chiTietNhiemVuDTO;
                    return true;
                }
            }
        }else {
            if (arr[1].contains("-")){
                throw new RuntimeException("Chitietnv contains dash  - ");
            }else {
                for (int i = 0; i< chiTietNhiemVuDTO_list.size(); i++){
                    NhiemVuDto chiTietNhiemVuDTO = chiTietNhiemVuDTO_list.get(i);
                    if (arr[0].equals(chiTietNhiemVuDTO.getTen_nv())){
                        if (arr[1].equals(chiTietNhiemVuDTO.getChitiet())){
                            nhiemVu_selected = chiTietNhiemVuDTO;
                            return true;
                        }
                    }
                }
                return false;
            }
        }
        return false;
    }

    private String[] splitAssignment(String text) {
        if (text.contains("-")){
            String nhiemvu = text.substring(0, text.indexOf("-")).trim();
            String ctnv = text.substring(text.indexOf("-") + 1).trim();
            String[] arr = new String[]{nhiemvu, ctnv};
            return arr;
        }
        return new String[]{text.trim(), null};
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
                return phuongtienRepo.findById(xmt_cbb.getSelectionModel().getSelectedItem().getId()).orElse(null);
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
                return nguonNxRepo.findByTen(string).orElse(null);
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
                return nguonNxRepo.findByTen(string).orElse(null);
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
    @FXML
    public void dongiaSelected(ActionEvent actionEvent) {
        setInv_lb();
    }

    @FXML
    public void dvnSelectedAction(ActionEvent actionEvent) {
    }
    @FXML
    public void soKeyRealed(KeyEvent keyEvent) {
        try {
            if (!so.getText().isEmpty()){
                if (DashboardController.ledgerList.stream().filter(x->x.getBill_id()==Integer.parseInt(so.getText())).findFirst().isPresent()){
                    so.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
                } else {
                    so.setStyle(null);
                }
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void nvSelected(ActionEvent actionEvent) {
        
    }
    @FXML
    public void loaixuatAction(ActionEvent actionEvent) {
        if (loai_xuat.getSelectionModel().getSelectedItem().equals(LoaiXuat.X_K.getName())){
            disableFeature(true);
            mapItemsForDonViXuat(nguonNxRepo.findByStatus(StatusEnum.ROOT_STATUS.getName()));
            mapItemsForDonViNhap(nguonNxRepo.findAll());
            List<PhuongTien> pt= new ArrayList<>();
            mapItemsForXeMayTau(pt);
        } else if (loai_xuat.getSelectionModel().getSelectedItem().equals(LoaiXuat.NV.getName())) {
            disableFeature(false);
            List<NguonNx> nxListw = new ArrayList<>();
            mapItemsForDonViNhap(nxListw);
            mapItemsForDonViXuat(nguonNxRepo.findByAllBy());
            mapItemsForXeMayTau(phuongtienRepo.findAll());
        }
    }

    @FXML
    public void mbRadioSel(ActionEvent actionEvent) {
        mapItemsForXeMayTau(phuongtienRepo.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle()));
    }
    @FXML
    public void xeRadioSelec(ActionEvent actionEvent) {
        mapItemsForXeMayTau(phuongtienRepo.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.XE.getNameVehicle()));
    }
    @FXML
    public void mayRadioSelec(ActionEvent actionEvent) {
        mapItemsForXeMayTau(phuongtienRepo.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAY.getNameVehicle()));
    }

    @FXML
    public void add(ActionEvent actionEvent) {
        setCellValueFactory(getLedgerDetails());
        clearFields();
    }

    @FXML
    public void xuat(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("NHẬP", "TẠO PHIẾU NHẬP", "Xác nhận tạo phiếu nhập",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
            Ledger l = createNewLedger();
            ls_socai.forEach(ld -> {
                ld.setLedger_id(l.getId());
//                saveLichsunxk(soCaiDto);
                saveMucgia(ld, l);
                updateInventory(l, ld);
                ledgerDetailRepo.save(ld);
            });
            if (DialogMessage.callAlertWithMessage("THÔNG BÁO", "Thành công", "Thêm phiếu nhập thành công",Alert.AlertType.INFORMATION) == ButtonType.OK){
                DashboardController.primaryStage.close();
            }
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
    }
    @FXML
    public void edit(ActionEvent actionEvent) {
    }
    @FXML
    public void del(ActionEvent actionEvent) {
    }
}
