package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.service.TcnService;
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
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
@Component
public class NhapController extends CommonFactory implements Initializable {
    private static List<LedgerDetails> ls_socai;
    private static List<Ledger> current_ledger_list = new ArrayList<>();
    private static int dvvc_id =0;
    @FXML
    private TextField soTf, recvTf,tcNhap,lenhKHso,soXe,
            donGiaTf, thucNhap,phaiNhap,tThucTe, vcf,tyTrong;
    @FXML
    private Label lb_tontheoxd, notification,chungloai_lb;
    @FXML
    private DatePicker tungay, denngay;
    @FXML
    private Button addbtn,importbtn,cancelbtn;
    @FXML
    private TableColumn<LedgerDetails, String> tbTT,tbTenXD,tbDonGia, tbPx,tbNhietDo, tbTyTrong, tbVCf, tbTx, tbThanhTien;
    @FXML
    private TableView<LedgerDetails> tableView;
    @FXML
    private ComboBox<NguonNx> cmb_dvvc, cmb_dvn;
    @FXML
    private ComboBox<LoaiXangDauDto> cmb_tenxd;

    @Autowired
    private LoaiXdService loaiXdService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private TcnService tcnService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        current_ledger_list = ledgerService.getAllByQuarter(DashboardController.findByTime.getId(),LoaiPhieuCons.PHIEU_NHAP.getName());
        ls_socai = new ArrayList<>();
        tableView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        tcnx_ls = tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_NHAP.getName());
        notification.setText("");

        hoverButton(addbtn ,"#027a20");
        hoverButton(importbtn,"#0000b3");
        hoverButton(cancelbtn,"#595959");

        setTenXDToCombobox();
        setDvvcCombobox();
        setDvnCombobox();

        setUpForSearchCompleteTion();
        Inventory i = inventoryService.findByPetro_idAndQuarter_id(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id(), DashboardController.findByTime.getId()).orElseThrow();
        setTonKhoLabel(i.getTdk_nvdx() + i.getNhap_nvdx()-i.getXuat_nvdx());
    }
    private void setUpForSearchCompleteTion(){
        List<String> search_arr = new ArrayList<>();

        for(int i = 0; i< tcnx_ls.size(); i++){
            search_arr.add(tcnx_ls.get(i).getName());
        }
        TextFields.bindAutoCompletion(tcNhap, t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });
    }
    private void setTenXDToCombobox(){
        cmb_tenxd.setConverter(new StringConverter<LoaiXangDauDto>() {
            @Override
            public String toString(LoaiXangDauDto loaiXangDau) {
                return loaiXangDau==null ? "" : loaiXangDau.getTenxd();
            }

            @Override
            public LoaiXangDauDto fromString(String s) {
                return loaiXdService.findById(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id()).orElse(null);
            }
        });
        cmb_tenxd.setItems(FXCollections.observableList(loaiXdService.findAllBy()));
        cmb_tenxd.getSelectionModel().selectFirst();
        chungloai_lb.setText("Chủng loại: "+cmb_tenxd.getSelectionModel().getSelectedItem().getLoai());
    }

    private void setDvvcCombobox(){
        cmb_dvvc.setItems(FXCollections.observableList(nguonNxService.findByStatus("NORMAL")));
        cmb_dvvc.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }
            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findById(dvvc_id).orElse(null);
            }
        });

        cmb_dvvc.getSelectionModel().selectFirst();
    }

    private void setDvnCombobox() {
        cmb_dvn.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findById(cmb_dvn.getValue().getId()).orElse(null);
            }
        });
        cmb_dvn.setItems(FXCollections.observableList(nguonNxService.findByStatus("ROOT")));
        cmb_dvn.getSelectionModel().selectFirst();
    }

    private LedgerDetails getLedgerDetails(){

        int tn = thucNhap.getText().isEmpty() ? 0 : Integer.parseInt(thucNhap.getText());
        int pn = phaiNhap.getText().isEmpty() ? 0 : Integer.parseInt(phaiNhap.getText());
        int p = donGiaTf.getText().trim().isEmpty() ? 0 : Integer.parseInt(donGiaTf.getText());

        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setMa_xd(cmb_tenxd.getSelectionModel().getSelectedItem().getMaxd());
        ledgerDetails.setTen_xd(cmb_tenxd.getSelectionModel().getSelectedItem().getTenxd());
        ledgerDetails.setLoaixd_id(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id());
        ledgerDetails.setDon_gia(p);
        ledgerDetails.setPhai_nhap(pn);
        ledgerDetails.setThuc_nhap(tn);
        ledgerDetails.setNhiet_do_tt(tThucTe.getText().isEmpty() ? 0 : Double.parseDouble(tThucTe.getText()));
        ledgerDetails.setHe_so_vcf(vcf.getText().isEmpty() ? 0 : Integer.parseInt(vcf.getText()));
        ledgerDetails.setTy_trong(tyTrong.getText().isEmpty() ? 0 : Double.parseDouble(tyTrong.getText()));
        ledgerDetails.setSoluong(tn);
        ledgerDetails.setThanhtien((long) tn * p);
        ledgerDetails.setSoluong_px((long)pn);

        ledgerDetails.setThanhtien_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getThanhtien())));
        ledgerDetails.setThucnhap_str(TextToNumber.textToNum(String.valueOf(tn)));
        ledgerDetails.setPhainhap_str(TextToNumber.textToNum(String.valueOf(pn)));
        ledgerDetails.setDongia_str(TextToNumber.textToNum(String.valueOf(p)));
        return ledgerDetails;
    }

    private void setcellFactory(){
        tbTT.setSortable(false);
        tbTT.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(column.getValue())+1).asString());
        tbTenXD.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        tbDonGia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        tbPx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phainhap_str"));
        tbTx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thucnhap_str"));
        tbNhietDo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        tbVCf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        tbTyTrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        tbThanhTien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
        tableView.setItems(FXCollections.observableList(ls_socai));
    }

    @FXML
    private void btnInsert(ActionEvent event){
        LedgerDetails ld = getLedgerDetails();
        if (!outfieldValid(tcNhap, "tinh chat nhap khoong duoc de trong.")){
            if (validateField(ld).isEmpty()) {
                ls_socai.add(ld);
                setcellFactory();
                setTonKhoLabel(inventory_quantity+ld.getSoluong());
                clearHH();
            }else{
                DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(ld),
                        "Nhập sai định dạng.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void btnImport(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("NHẬP", "TẠO PHIẾU NHẬP", "Xác nhận tạo phiếu nhập",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
            Ledger l = getLedger();
            if (validateField(l).isEmpty()) {
                ledgerService.saveLedgerWithDetails(l, ls_socai);
                DialogMessage.message("Thong bao", "Them phieu NHAP thanh cong..",
                        "Thanh cong", Alert.AlertType.INFORMATION);
                DashboardController.primaryStage.close();
            }else{
                DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(l),
                        "Nhập sai định dạng.", Alert.AlertType.ERROR);
            }
        }
    }
    private String changeStyleTextFieldByValidation(Object o){
        List<String> ls = validateField(o);
        if (!ls.isEmpty()){
            if (ls.get(0).equals("soluong_px")){
                phaiNhap.setStyle(styleErrorField);
                return "phai nhap phai lon hon 0";
            }else if (ls.get(0).equals("nhiet_do_tt")){
                tThucTe.setStyle(styleErrorField);
                return "Nhiet do phai lon hon 0";
            }else if (ls.get(0).equals("ty_trong")){
                tyTrong.setStyle(styleErrorField);
                return "Ty trong phai lon hon 0";
            }else if (ls.get(0).equals("don_gia")){
                donGiaTf.setStyle(styleErrorField);
                return "don gia phai lon hon 0";
            } else if (ls.get(0).equals("soluong")){
                thucNhap.setStyle(styleErrorField);
                return "thuc nhap phai lon hon 0";
            }else if (ls.get(0).equals("bill_id")){
                soTf.setStyle(styleErrorField);
                return "so phai lon hon 0";
            }else if (ls.get(0).equals("tcn_id")){
                tcNhap.setStyle(styleErrorField);
                return "Tinh chat nhap khong xac dinh.";
            }
        }
        return null;
    }
    private Ledger getLedger() {
        Ledger ledger = new Ledger();
        ledger.setBill_id(Integer.parseInt(soTf.getText()));
        ledger.setQuarter_id(DashboardController.findByTime.getId());
        ledger.setAmount(ls_socai.stream().mapToLong(x->(x.getThuc_nhap()*x.getDon_gia())).sum());
        ledger.setFrom_date(tungay.getValue());
        ledger.setEnd_date(denngay.getValue());
        ledger.setStatus(StatusCons.ACTIVED.getName());
        ledger.setDvi_nhan(cmb_dvn.getValue().getTen());
        ledger.setDvi_xuat(cmb_dvvc.getValue().getTen());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_NHAP.getName());
        ledger.setDvi_nhan_id(cmb_dvn.getValue().getId());
        ledger.setDvi_xuat_id(cmb_dvvc.getValue().getId());
        ledger.setNguoi_nhan(recvTf.getText());
        ledger.setSo_xe(soXe.getText());
        ledger.setLenh_so(lenhKHso.getText());
        Tcn t = tcnService.findByName(tcNhap.getText().trim()).orElse(null);

        if (t != null) {
            ledger.setTcn_id(t.getId());
        }else{
            ledger.setTcn_id(tcnService.save(new Tcn(LoaiPhieuCons.PHIEU_XUAT.getName(), tcNhap.getText())).getId());
        }
        ledger.setTructhuoc(tructhuocService.findById(cmb_dvvc.getSelectionModel().getSelectedItem().getTructhuoc_id()).orElseThrow().getType());
        return ledger;
    }
    private void clearHH(){
        donGiaTf.setText("0");
        phaiNhap.setText("0");
        thucNhap.setText("0");
        tThucTe.setText("0");
        vcf.setText("0");
        tyTrong.setText("0");
    }
    private void setTonKhoLabel(int i){
        inventory_quantity = i;
        lb_tontheoxd.setText("Số lượng tồn: "+ TextToNumber.textToNum(String.valueOf(inventory_quantity)) +" (Lit)");
    }
    @FXML
    public void changedItemLoaiXd(ActionEvent actionEvent) {
        Inventory i = inventoryService.findByPetro_idAndQuarter_id(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id(), DashboardController.findByTime.getId()).orElseThrow();
        int tk = i.getTdk_nvdx() + i.getNhap_nvdx()-i.getXuat_nvdx();
        LoaiXangDauDto lxd = cmb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            LedgerDetails ld = ls_socai.stream().filter(x->x.getLoaixd_id()==lxd.getXd_id()).findFirst().orElse(null);
            if (ld!=null){
                setTonKhoLabel(tk+ld.getSoluong());
            }else{
                setTonKhoLabel(tk);
            }
            chungloai_lb.setText("Chủng loại: "+lxd.getLoai());
        }
    }
    @FXML
    public void select_item(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            if (DialogMessage.callAlertWithMessage("Delete", "Xoa", "Xác nhận xoa",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
                LedgerDetails ld = tableView.getSelectionModel().getSelectedItem();
                if (ld!=null){
                    ls_socai.remove(ld);
                    setTonKhoLabel(inventory_quantity-ld.getSoluong());
                    tableView.setItems(FXCollections.observableList(ls_socai));
                    tableView.refresh();
                }
            }
        }
    }
    @FXML
    public void btnCancel(ActionEvent actionEvent) {
        DashboardController.primaryStage.close();
    }
    @FXML
    public void soValid(KeyEvent keyEvent) {
        if(!soTf.getText().isEmpty()){
            validateToSettingStyle(soTf);
            if (current_ledger_list.stream().filter(i->i.getBill_id()==Integer.parseInt(soTf.getText())).findFirst().isPresent()){
                soTf.setStyle(styleErrorField);
                notification.setText("Số đã được thêm vào sổ cái, vui lòng nhập số phiếu khác.");
            }
        }
    }
    @FXML
    public void validate_dongia(KeyEvent keyEvent) {
        validateToSettingStyle(donGiaTf);
    }
    @FXML
    public void validate_phaixuat(KeyEvent keyEvent) {
        validateToSettingStyle(phaiNhap);
    }
    @FXML
    public void validate_thucxuat(KeyEvent keyEvent) {
        validateToSettingStyle(thucNhap);
    }
    @FXML
    public void validate_nhietdo(KeyEvent keyEvent) {
        validateToSettingStyle(tThucTe);
    }
    @FXML
    public void validate_tytrong(KeyEvent keyEvent) {
        validateToSettingStyle(tyTrong);
    }
    @FXML
    public void validate_vcf(KeyEvent keyEvent) {
        validateToSettingStyle(vcf);
    }

    @FXML
    public void so_clicked(MouseEvent mouseEvent) {
        cleanErrorField(soTf);
        current_ledger_list = ledgerService.getAllByQuarter(DashboardController.findByTime.getId(), LoaiPhieuCons.PHIEU_NHAP.getName());
        notification.setText("");
    }
    @FXML
    public void nguoinhan_clicked(MouseEvent mouseEvent) {
        cleanErrorField(recvTf);
    }
    @FXML
    public void tcn_clicked(MouseEvent mouseEvent) {
        cleanErrorField(tcNhap);
    }
    @FXML
    public void lenhso_clicked(MouseEvent mouseEvent) {
        cleanErrorField(lenhKHso);
    }
    @FXML
    public void soxe_clicked(MouseEvent mouseEvent) {
        cleanErrorField(soXe);
    }
    @FXML
    public void dongia_clicked(MouseEvent mouseEvent) {
        cleanErrorField(donGiaTf);
    }
    @FXML
    public void phainhap_clicked(MouseEvent mouseEvent) {
        cleanErrorField(phaiNhap);
    }
    @FXML
    public void thucnhap_clicked(MouseEvent mouseEvent) {
        cleanErrorField(thucNhap);
    }
    @FXML
    public void nhietdo_clicked(MouseEvent mouseEvent) {
        cleanErrorField(tThucTe);
    }
    @FXML
    public void vcf_clicked(MouseEvent mouseEvent) {
        cleanErrorField(vcf);
    }
    @FXML
    public void tytrong_clicked(MouseEvent mouseEvent) {
        cleanErrorField(tyTrong);
    }
}
