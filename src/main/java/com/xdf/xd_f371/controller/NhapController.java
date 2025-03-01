package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.InvDto2;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
@Component
public class NhapController extends CommonFactory implements Initializable {
    @FXML
    private TextField soTf, recvTf,tcNhap,lenhKHso,soXe,
            donGiaTf, thucNhap,phaiNhap,tThucTe, vcf,tyTrong;
    @FXML
    private Label lb_tontheoxd, notification,chungloai_lb,text_dongia,text_phainhap,text_thucnhap;
    @FXML
    private RadioButton nvdx_rd;
    @FXML
    private Button addbtn,importbtn,cancelbtn;

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
        setVi_DatePicker(tungay);
        setVi_DatePicker(denngay);
        ls_socai = new ArrayList<>();
        tbView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        tcnx_ls = tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_NHAP.getName());
        notification.setText("");
        text_dongia.setText("0 (VND/Lit)");
        text_phainhap.setText("0 Lit 15 độ C");
        text_thucnhap.setText("0 Lit 15 độ C");
        tungay.setValue(LocalDate.now());
        nvdx_rd.setSelected(true);

        Common.hoverButton(addbtn ,"#027a20");
        Common.hoverButton(importbtn,"#0000b3");
        Common.hoverButton(cancelbtn,"#595959");

        setTenXDToCombobox();
        setDvvcCombobox();
        setDvnCombobox();

        setUpForSearchCompleteTion();
        setInvLabel();
    }

    private void setInvLabel(){
        LoaiXangDauDto lxd = cmb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            NguonNx dvn = cmb_dvn.getSelectionModel().getSelectedItem();
            if (dvn!=null){
                InvDto2 i = inventoryService.getPreInvWithDvi(lxd.getXd_id(),dvn.getId());
                if (i!=null){
                    setTonKhoLabel(i.getSl_ton());
                } else {
                    setTonKhoLabel(0L);
                }
            }
        }
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
        setXangDauCombobox(cmb_tenxd, loaiXdService);
    }

    private void setDvvcCombobox(){
        setNguonnxCombobox(cmb_dvvc, nguonNxService.findByStatusUnlessTructhuoc());
        cmb_dvvc.getSelectionModel().selectFirst();
    }
    private void setDvnCombobox() {
        setNguonnxCombobox(cmb_dvn, nguonNxService.findByAllBy());
        cmb_dvn.getSelectionModel().selectFirst();
    }
    private LedgerDetails getLedgerDetails(LoaiXangDauDto lxd){
        double tn = thucNhap.getText().isEmpty() ? 0 : Double.parseDouble(thucNhap.getText());
        double pn = phaiNhap.getText().isEmpty() ? 0 : Double.parseDouble(phaiNhap.getText());
        double p = donGiaTf.getText().trim().isEmpty() ? 0 : Double.parseDouble(donGiaTf.getText());

        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setMa_xd(lxd.getMaxd());
        ledgerDetails.setTen_xd(lxd.getTenxd());
        ledgerDetails.setChung_loai(lxd.getChungloai());
        ledgerDetails.setSscd_nvdx(nvdx_rd.isSelected() ? Purpose.NVDX.getName():Purpose.SSCD.getName());
        ledgerDetails.setLoaixd_id(lxd.getXd_id());
        ledgerDetails.setDon_gia(p);
        ledgerDetails.setPhai_nhap(pn);
        ledgerDetails.setThuc_nhap(tn);
        ledgerDetails.setNhiet_do_tt(tThucTe.getText().isEmpty() ? 0 : Double.parseDouble(tThucTe.getText()));
        ledgerDetails.setHe_so_vcf(vcf.getText().isEmpty() ? 0 : Double.parseDouble(vcf.getText()));
        ledgerDetails.setTy_trong(tyTrong.getText().isEmpty() ? 0 : Double.parseDouble(tyTrong.getText()));
        ledgerDetails.setSoluong(tn);
        ledgerDetails.setThanhtien(tn * p);
        ledgerDetails.setSoluong_px(pn);
        ledgerDetails.setThanhtien_str(TextToNumber.textToNum_2digits(ledgerDetails.getThanhtien()));
        ledgerDetails.setThucnhap_str(TextToNumber.textToNum_2digits(tn));
        ledgerDetails.setPhainhap_str(TextToNumber.textToNum_2digits(pn));
        ledgerDetails.setDongia_str(TextToNumber.textToNum_2digits(p));
        return ledgerDetails;
    }
    private void setcellFactoryNhap(){
        setcellFactory("phainhap_str","thucnhap_str");
        tbView.setItems(FXCollections.observableList(ls_socai));
        tbView.refresh();
    }
    @FXML
    private void btnInsert(ActionEvent event){
        LoaiXangDauDto lxd = cmb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null) {
            LedgerDetails ld = getLedgerDetails(lxd);
            if (!outfieldValid(tcNhap, "tinh chat nhap khoong duoc de trong.")){
                cmb_tenxd.setStyle(null);
                if (validateField(ld).isEmpty()) {
                    if (isNotDuplicate(ld.getLoaixd_id(),ld.getDon_gia(),ld.getThuc_nhap(),ld.getPhai_nhap(),LoaiPhieuCons.PHIEU_NHAP.getName())){
                        ls_socai.add(ld);
                    }
                    setcellFactoryNhap();
                    setTonKhoLabel(inventory_quantity+ld.getSoluong());
                    clearHH();
                }else{
                    DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(ld),
                            "Nhập sai định dạng.", Alert.AlertType.ERROR);
                }
            }
        }else{
            cmb_tenxd.setStyle(styleErrorField);
            DialogMessage.message("Lỗi", "...",
                    "Ten xang dau khong xac dinh.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void btnImport(ActionEvent actionEvent) {
        if (!ls_socai.isEmpty()){
            if (DialogMessage.callAlertWithMessage("NHẬP", "TẠO PHIẾU NHẬP", "Xác nhận tạo phiếu nhập",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
                try {
                    NguonNx dvx = cmb_dvvc.getSelectionModel().getSelectedItem();
                    if (dvx!=null){
                        NguonNx dvn = cmb_dvn.getSelectionModel().getSelectedItem();
                        if (dvn!=null) {
                            Ledger l = getLedger();
                            if (validateField(l).isEmpty()) {
                                Ledger res = ledgerService.saveLedgerWithDetails(l, ls_socai);
                                DialogMessage.message("Thong bao", "Them phieu NHAP thanh cong.. so: " + res.getBill_id(),
                                        "Thanh cong", Alert.AlertType.INFORMATION);
                                DashboardController.primaryStage.close();
                            } else {
                                DialogMessage.message("Lỗi", changeStyleTextFieldByValidation(l),
                                        "Nhập sai định dạng.", Alert.AlertType.WARNING);
                            }
                        }else{
                            cmb_dvn.setStyle(styleErrorField);
                            DialogMessage.errorShowing("Không tìm thấy nguồn nhập xuất, vui lòng thử lại.");
                        }
                    }else{
                        cmb_dvvc.setStyle(styleErrorField);
                        DialogMessage.errorShowing("Không tìm thấy nguồn nhập xuất, vui lòng thử lại.");
                    }
                }catch (NumberFormatException e){
                    DialogMessage.errorShowing("Số sai định dạng, vui lòng thử lại");
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    DialogMessage.errorShowing("có lỗi xảy ra, vui lòng thử lại sau.");
                    throw new RuntimeException(e);
                }
            }
        }else{
            DialogMessage.message(null, "Phiếu trống!!!",
                    null, Alert.AlertType.WARNING);
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
        NguonNx dvx = cmb_dvvc.getSelectionModel().getSelectedItem();
        NguonNx dvn = cmb_dvn.getSelectionModel().getSelectedItem();
        Ledger ledger = new Ledger();
        ledger.setCreate_by(ConnectLan.pre_acc.getId());
        ledger.setBill_id(Integer.parseInt(soTf.getText().trim().isEmpty() ? "0" : soTf.getText()));
        ledger.setAmount(ls_socai.stream().mapToDouble(x->(x.getThuc_nhap()*x.getDon_gia())).sum());
        ledger.setFrom_date(tungay.getValue());
        ledger.setEnd_date(denngay.getValue());
        ledger.setStatus(StatusCons.ACTIVED.getName());
        ledger.setDvi_nhan(dvn.getTen());
        ledger.setDvi_xuat(dvx.getTen());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_NHAP.getName());
        ledger.setDvi_nhan_id(dvn.getId());
        ledger.setDvi_xuat_id(dvx.getId());
        ledger.setRoot_id(Integer.parseInt(configurationService.findByParam(ConfigCons.ROOT_ID.getName()).orElse(null).getValue()));
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
    private void setTonKhoLabel(double i){
        inventory_quantity = i;
        lb_tontheoxd.setText("Số lượng tồn: "+ TextToNumber.textToNum_2digits(inventory_quantity) +" (Lit)");
    }
    @FXML
    public void changedItemLoaiXd(ActionEvent actionEvent) {
        LoaiXangDauDto lxd = cmb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            NguonNx dvn = cmb_dvn.getSelectionModel().getSelectedItem();
            if (dvn!=null){
                InvDto2 i = inventoryService.getPreInvWithDvi(lxd.getXd_id(),dvn.getId());
                if (i!=null){
                    setTonKhoLabel(i.getSl_ton());
                    LedgerDetails ld = ls_socai.stream().filter(x->x.getLoaixd_id()==lxd.getXd_id()).findFirst().orElse(null);
                    if (ld!=null){
                        setTonKhoLabel(i.getSl_ton()+ld.getSoluong());
                    }else{
                        setTonKhoLabel(i.getSl_ton());
                    }
                }else{
                    setTonKhoLabel(0L);
                }
            }
            chungloai_lb.setText("Chủng loại: "+lxd.getLoai());
        }else{
            chungloai_lb.setText("Chủng loại: ---");
        }
    }
    @FXML
    public void select_item(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            if (DialogMessage.callAlertWithMessage("Delete", "Xoa", "Xác nhận xoa",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
                LedgerDetails ld = tbView.getSelectionModel().getSelectedItem();
                if (ld!=null){
                    ls_socai.remove(ld);
                    setTonKhoLabel(inventory_quantity-ld.getSoluong());
                    tbView.setItems(FXCollections.observableList(ls_socai));
                    tbView.refresh();
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
            if (Common.isNumber(soTf.getText())){
                soTf.setStyle(null);
            }else{
                soTf.setStyle(styleErrorField);
            }
        }
    }
    @FXML
    public void validate_dongia(KeyEvent keyEvent) {
        validateToSettingStyle(donGiaTf);
        try {
            if (!donGiaTf.getText().isEmpty()){
                text_dongia.setText(TextToNumber.textToNum_2digits(Double.parseDouble(donGiaTf.getText()))+ " (VND/Lit)");
            }else{
                text_dongia.setText("0 (VND/Lit)");
            }
        }catch (Exception e){
            text_dongia.setText("0 (VND/Lit)");
        }
    }
    @FXML
    public void validate_phaixuat(KeyEvent keyEvent) {
        validateToSettingStyle(phaiNhap);
        thucNhap.setText(phaiNhap.getText());
        try {
            if (!phaiNhap.getText().isEmpty()){
                text_phainhap.setText(TextToNumber.textToNum_2digits(Double.parseDouble(phaiNhap.getText())) + " lit 15 độ C");
            }else{
                text_phainhap.setText("0 (Lit)");
            }
        }catch (Exception e){
            text_phainhap.setText("0 (Lit)");
        }
    }
    @FXML
    public void validate_thucxuat(KeyEvent keyEvent) {
        validateToSettingStyle(thucNhap);
        try {
            if (!thucNhap.getText().isEmpty()){
                text_thucnhap.setText(TextToNumber.textToNum_2digits(Double.parseDouble(thucNhap.getText())) + " lit 15 độ C");
            }else{
                text_thucnhap.setText("0 (Lit)");
            }
        }catch (Exception e){
            text_thucnhap.setText("0 (Lit)");
        }
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
    @FXML
    public void dvnAction(ActionEvent actionEvent) {
        cmb_dvn.setStyle(null);
        cmb_tenxd.getSelectionModel().selectFirst();
        setInvLabel();
    }
    @FXML
    public void cmb_dvvcAction(ActionEvent actionEvent) {
        cmb_dvvc.setStyle(null);
    }
}
