package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.util.*;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NhapController extends CommonFactory implements Initializable {
    @FXML
    private TextField soTf, recvTf,lenhKHso,soXe,
            donGiaTf, thucNhap,phaiNhap,tThucTe, vcf,tyTrong;
    @FXML
    private Label notification,chungloai_lb,text_dongia,text_phainhap,text_thucnhap,predict_billid;
    @FXML
    private Button addbtn,importbtn,cancelbtn;
    @FXML
    private ComboBox<NguonNx> cmb_dvvc, cmb_dvn;
    @FXML
    private ComboBox<Tcn> cmb_tcn;
    @FXML
    private ComboBox<LoaiXangDauDto> cmb_tenxd;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        initLabelValue();
        setTenXDToCombobox();
        setItemForTcnCbb();

        setDvCombobox(cmb_dvvc,dvvcLs);
        setDvCombobox(cmb_dvn,dvnLs);
        setPreInv();
        last_ledger =ledgerService.findLastLedgerByBillId(LoaiPhieuCons.PHIEU_NHAP.getName());
        if (last_ledger!=null){
            String num = "";
            String letter = "";
            if (last_ledger.getBill_id()!=null){
                num = last_ledger.getBill_id();
            }if (last_ledger.getBill_id2()!=null){
                letter = last_ledger.getBill_id2();
            }
            initPredictValue(getNextInSequence(num.concat(letter)));
        }else{
            initPredictValue("1");
        }
    }

    private void initLabelValue() {
        tbView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        notification.setText("");
        text_dongia.setText("0 (VND/Lit)");
        text_phainhap.setText("0 Lit 15 độ C");
        text_thucnhap.setText("0 Lit 15 độ C");
        Common.hoverButton(addbtn ,"#027a20");
        Common.hoverButton(importbtn,"#0000b3");
        Common.hoverButton(cancelbtn,"#595959");
    }

    private void setPreInv() {
        LoaiXangDauDto lxd = cmb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            setInvLabel(lxd);
            chungloai_lb.setText("Chủng loại: "+lxd.getLoai());
        }else{
            chungloai_lb.setText("Chủng loại: ---");
        }
    }
    private void setItemForTcnCbb(){
        ComponentUtil.setItemsToComboBox(cmb_tcn,tcnx_ls,Tcn::getName, input -> tcnx_ls.stream().filter(x->x.getName().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(cmb_tcn, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        cmb_tcn.getSelectionModel().selectFirst();
    }
    private void setTenXDToCombobox(){
        setXangDauCombobox(cmb_tenxd);
    }

    private void setDvCombobox(ComboBox<NguonNx> cmb_dv, List<NguonNx> nguonNxList){
        setNguonnxCombobox(cmb_dv, nguonNxList);
        cmb_dv.getSelectionModel().selectFirst();
    }
    private LedgerDetails getLedgerDetails(LoaiXangDauDto lxd){
        double tn = thucNhap.getText().isBlank() ? 0 : Double.parseDouble(thucNhap.getText());
        double pn = phaiNhap.getText().isBlank() ? 0 : Double.parseDouble(phaiNhap.getText());
        double p = donGiaTf.getText().trim().isBlank() ? 0 : Double.parseDouble(donGiaTf.getText());

        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setMa_xd(lxd.getMaxd());
        ledgerDetails.setTen_xd(lxd.getTenxd());
        ledgerDetails.setChung_loai(lxd.getChungloai());
        ledgerDetails.setSscd_nvdx(Purpose.NVDX.getName());
        ledgerDetails.setLoaixd_id(lxd.getXd_id());
        ledgerDetails.setDon_gia(p);
        ledgerDetails.setPhai_nhap(pn);
        ledgerDetails.setThuc_nhap(tn);
        ledgerDetails.setNhiet_do_tt(tThucTe.getText().isBlank() ? 0 : Double.parseDouble(tThucTe.getText()));
        ledgerDetails.setHe_so_vcf(vcf.getText().isBlank() ? 0 : Double.parseDouble(vcf.getText()));
        ledgerDetails.setTy_trong(tyTrong.getText().isBlank() ? 0 : Double.parseDouble(tyTrong.getText()));
        ledgerDetails.setSoluong(tn);
        ledgerDetails.setThanhtien(tn * p);
        ledgerDetails.setSoluong_px(pn);
        ledgerDetails.setThanhtien_str(TextToNumber.textToNum_2digits(ledgerDetails.getThanhtien()));
        ledgerDetails.setThucnhap_str(TextToNumber.textToNum_2digits(tn));
        ledgerDetails.setPhainhap_str(TextToNumber.textToNum_2digits(pn));
        ledgerDetails.setSoluongpx_str(TextToNumber.textToNum_2digits(pn));
        ledgerDetails.setSoluong_str(TextToNumber.textToNum_2digits(tn));
        ledgerDetails.setDongia_str(TextToNumber.textToNum_2digits(p));
        return ledgerDetails;
    }
    private void setcellFactoryNhap(){
        tbView.setItems(FXCollections.observableList(ls_socai));
        tbView.refresh();
    }
    @FXML
    private void btnInsert(ActionEvent event){
        LoaiXangDauDto lxd = cmb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null) {
            LedgerDetails ld = getLedgerDetails(lxd);
            if (!outfieldValid(lenhKHso, MessageCons.NOT_EMPTY_lenhKH.getName())){
                if (!outfieldValid(soTf, MessageCons.NOT_EMPTY_so.getName())){
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
            }
        }else{
            cmb_tenxd.setStyle(styleErrorField);
            DialogMessage.message(null, null,
                    MessageCons.CO_LOI_XAY_RA.getName(), Alert.AlertType.ERROR);
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
                                if (duplicateBillNumber(soTf.getText(),LoaiPhieuCons.PHIEU_NHAP.getName())){
                                    if (DialogMessage.callAlertWithMessage(MessageCons.THONGBAO.getName(), "Số "+l.getBill_id().concat(l.getBill_id2())+" đã được tạo, số phiếu hiện tại sẽ dời sang 1 đơn vị. Bạn có muốn tiếp tục tạo phiếu?",
                                            null, Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                                        ledgerService.updateBillNumber(l,ledgers);
                                        Ledger res = ledgerService.saveLedgerWithDetails(l, ls_socai);
                                        DialogMessage.message(MessageCons.THONGBAO.getName(), "Them phieu NHAP thanh cong.. so: " + res.getBill_id(),
                                                MessageCons.THANH_CONG.getName(), Alert.AlertType.INFORMATION);
                                        LedgerController.primaryStage.close();
                                    }
                                }else{
                                    Ledger res = ledgerService.saveLedgerWithDetails(l, ls_socai);
                                    DialogMessage.message(MessageCons.THONGBAO.getName(), "Them phieu NHAP thanh cong.. so: " + res.getBill_id(),
                                            MessageCons.THANH_CONG.getName(), Alert.AlertType.INFORMATION);
                                    LedgerController.primaryStage.close();
                                }
                            } else {
                                DialogMessage.message(MessageCons.LOI.getName(), changeStyleTextFieldByValidation(l),
                                        MessageCons.SAI_DINH_DANG.getName(), Alert.AlertType.WARNING);
                            }
                        } else {
                            cmb_dvn.setStyle(styleErrorField);
                            DialogMessage.errorShowing("Không tìm thấy nguồn nhập xuất, vui lòng thử lại.");
                        }
                    }else{
                        cmb_dvvc.setStyle(styleErrorField);
                        DialogMessage.errorShowing("Không tìm thấy nguồn nhập xuất, vui lòng thử lại.");
                    }
                }catch (NumberFormatException e){
                    DialogMessage.errorShowing(MessageCons.SAI_DINH_DANG.getName());
                    e.printStackTrace();
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
            }
        }
        return null;
    }
    private Ledger getLedger() {
        NguonNx dvx = cmb_dvvc.getSelectionModel().getSelectedItem();
        NguonNx dvn = cmb_dvn.getSelectionModel().getSelectedItem();
        Ledger ledger = new Ledger();
        ledger.setCreate_by(ConnectLan.pre_acc.getId());
        splitBillNumber(soTf.getText(),ledger);
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
        ledger.setNote(note.getText());
        Tcn t = cmb_tcn.getSelectionModel().getSelectedItem();
        if (t!=null){
            ledger.setTcn_id(t.getId());
            if (t.getMa_tcn().equals(ConfigCons.NBN.getName())){
                ledger.setDvi_baono(dvx.getId());
            }
        }else {
            ledger.setTcn_id(1);
        }
        ledger.setTructhuoc(tructhuocService.findById(cmb_dvvc.getSelectionModel().getSelectedItem().getTructhuoc_id()).orElseThrow().getType());
        ledger.setYear(tungay.getValue().getYear());
        ledger.setId(generateId(ledger.getYear(),ledger.getRoot_id(),lenhKHso.getText(),LoaiPhieuCons.PHIEU_NHAP.getName()));
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

    @FXML
    public void changedItemLoaiXd(ActionEvent actionEvent) {
        setPreInv();
    }
    @FXML
    public void select_item(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            if (DialogMessage.callAlertWithMessage(null, null, "Xác nhận xoa",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
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
        LedgerController.primaryStage.close();
    }
    @FXML
    public void soValid(KeyEvent keyEvent) {
    }
    @FXML
    public void validate_dongia(KeyEvent keyEvent) {
        validateToSettingStyle(donGiaTf);
        try {
            if (!donGiaTf.getText().isBlank()){
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
            if (!phaiNhap.getText().isBlank()){
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
            if (!thucNhap.getText().isBlank()){
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
        setPreInv();
    }
    @FXML
    public void cmb_dvvcAction(ActionEvent actionEvent) {
        cmb_dvvc.setStyle(null);
    }
    @FXML
    public void chitietExited(MouseEvent mouseEvent) {
        primaryStage = new Stage();
        Common.openNewStage("price_view.fxml", primaryStage,null, StageStyle.TRANSPARENT);
    }
    @FXML
    public void chitietEntered(MouseEvent mouseEvent) {
        primaryStage.close();
    }
    @FXML
    public void lenhsoKR(KeyEvent keyEvent) {
        lenhKHso.setStyle(null);
        if (lenhKHso.getText().isBlank()){
            if (ledgers.stream().anyMatch(l->l.getLenh_so().equals(lenhKHso.getText().trim()))){
                lenhKHso.setStyle(CommonFactory.styleErrorField);
            }
        }
    }
    @FXML
    public void cmb_tcnAction(ActionEvent actionEvent) {
    }
}
