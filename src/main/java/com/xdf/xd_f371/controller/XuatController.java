package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
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
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Slf4j
@Component
public class XuatController extends CommonFactory implements Initializable {
    private XuatDVController xuatDVController;
    private XuatNVController xuatNVController;
    @FXML
    private TextField phaixuat,thucxuat,nhietdo,vcf,tytrong;

    @FXML
    private Label chungloai_lb,gia_vnd;
    @FXML
    private ComboBox<String> loai_xuat_cbb;

    @FXML
    private ComboBox<LoaiXangDauDto> cbb_tenxd;
    @FXML
    private ComboBox<Double> cbb_dongia;
    @FXML
    private Button addBtn,xuatButton,cancelBtn,editBtn;
    @FXML
    private Label px_lb,tx_lb;
    @FXML
    private GridPane grid_parent;
    private VBox dv,nv;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        super.initialize(url,resourceBundle);
        initLabel();
        ledger_id = generateId(LoaiPhieuCons.PHIEU_XUAT.getName());
        initLoaderFxml();
        initLoaiXuatCbb();
        mapXdForCombobox();
        initEditValue();
        if (LedgerController.status.equals(StatusCons.ADD.getName())){
            editBtn.setVisible(false);
            addBtn.setVisible(true);
        }else{
            editBtn.setVisible(true);
            addBtn.setVisible(false);
            xuatButton.setText("Lưu thay đổi");
        }
    }

    private void initEditValue() {
        if (LedgerController.ledger_edit!=null){
            l = LedgerController.ledger_edit;
            tungay.setValue(l.getFrom_date());
            denngay.setValue(l.getEnd_date());
            setItemToList(l.getLedgerDetails());

            removeNode();
            try{
                if (l.getNhiemvu_id()==0){
                    loai_xuat_cbb.getSelectionModel().select(LoaiXuat.X_K.getName());
                    loai_xuat_cbb.setDisable(true);
                    FXMLLoader loader =DashboardController.getFXLoadderBySource("xuat_dv.fxml");
                    dv = (VBox) loader.load();
                    xuatDVController = loader.getController();
                    addNewNode(dv);
                }else{
                    loai_xuat_cbb.getSelectionModel().select(LoaiXuat.NV.getName());
                    loai_xuat_cbb.setDisable(true);
                    FXMLLoader loader_nv =DashboardController.getFXLoadderBySource("xuat_nv.fxml");
                    nv = (VBox) loader_nv.load();
                    xuatNVController = loader_nv.getController();
                    addNewNode(nv);
                }
            }catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private void setItemToList(List<LedgerDetails> ldLs) {
        for (LedgerDetails x : ldLs) {
            x.setPhainhap_str(TextToNumber.textToNum_2digits(x.getPhai_nhap()));
            x.setThucnhap_str(TextToNumber.textToNum_2digits(x.getThuc_nhap()));
            x.setPhaixuat_str(TextToNumber.textToNum_2digits(x.getPhai_xuat()));
            x.setSoluong_str(TextToNumber.textToNum_2digits(x.getSoluong()));
            x.setSoluongpx_str(TextToNumber.textToNum_2digits(x.getSoluong_px()));
            x.setThucxuat_str(TextToNumber.textToNum_2digits(x.getThuc_xuat()));
            x.setDongia_str(TextToNumber.textToNum_2digits(x.getDon_gia()));
            x.setThanhtien_str(TextToNumber.textToNum_2digits(x.getThanhtien()));
        }
        setCellValueFactoryXuat(l.getLedgerDetails());
    }
    private void initLoaderFxml(){
        try {
            if (LedgerController.status.equals(StatusCons.ADD.getName())){
                FXMLLoader loader_nv =DashboardController.getFXLoadderBySource("xuat_nv.fxml");
                nv = (VBox) loader_nv.load();
                xuatNVController = loader_nv.getController();
                FXMLLoader loader =DashboardController.getFXLoadderBySource("xuat_dv.fxml");
                dv = (VBox) loader.load();
                xuatDVController = loader.getController();
                unitBillDto = xuatDVController.getInfo();
            }
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
    private boolean validField(){
        if (!phaixuat.getText().isBlank()){
            if (!thucxuat.getText().isBlank()){
                return true;
            }else{
                thucxuat.setStyle(CommonFactory.styleErrorField);
            }
        }else{
            phaixuat.setStyle(CommonFactory.styleErrorField);
        }
        return false;
    }
    @FXML
    public void add(ActionEvent actionEvent) {
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        Double gia = cbb_dongia.getSelectionModel().getSelectedItem();
        if (isCbb(lxd,gia)){
            if (validField()){
                LedgerDetails ld = getLedgerDetails(lxd, gia);
                if (ld!=null){
                    if (inventory_quantity < ld.getSoluong()) {
                        DialogMessage.message(null, "so luong xuat > so luong ton kho", MessageCons.CO_LOI_XAY_RA.getName(), Alert.AlertType.WARNING);
                    }else{
                        if (l.getLedgerDetails().isEmpty()){
                            l.addDetail(ld);
                        }else if (isNotDuplicate(ld.getLoaixd_id(),ld.getDon_gia(),ld.getThuc_xuat(),ld.getPhai_xuat(),LoaiPhieuCons.PHIEU_XUAT.getName())){
                            l.addDetail(ld);
                        }
                        setTonKhoLabel(inventory_quantity - ld.getSoluong());
                        setCellValueFactoryXuat(l.getLedgerDetails());
                        clearFields();
                    }
                }
            }
        }
    }
    @FXML
    public void xuat(ActionEvent actionEvent) {
        getLedger(l);
        l.setLedgerDetails(l.getLedgerDetails());
        if (!l.getLedgerDetails().isEmpty()) {
            if (DialogMessage.callAlertWithMessage("XUẤT", "TẠO PHIẾU XUẤT", "Xác nhận tạo phiếu XUẤT", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
                try {
                    l.setAmount(l.getLedgerDetails().stream().mapToDouble(x-> (x.getSoluong() *x.getDon_gia())).sum());
                    if (duplicateBillNumber(l.getBill_id(),LoaiPhieuCons.PHIEU_XUAT.getName())  && LedgerController.ledger_edit == null){
                        if (DialogMessage.callAlertWithMessage(MessageCons.THONGBAO.getName(), "Số "+l.getBill_id().concat(l.getBill_id2())
                                        +" đã được tạo, số phiếu hiện tại sẽ dời sang 1 đơn vị. Bạn có muốn tiếp tục tạo phiếu?",
                                null, Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                            ledgerService.updateBillNumber(l,ledgers);
                            saveLedger(l);
                        }
                    }else{
                        saveLedger(l);
                    }
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
    private void initLoaiXuatCbb() {
        loai_xuat_cbb.setItems(FXCollections.observableList(List.of(LoaiXuat.X_K.getName(),LoaiXuat.NV.getName())));
        loai_xuat_cbb.getSelectionModel().selectFirst();
        if (LedgerController.status.equals(StatusCons.ADD.getName())){
            addNewNode(dv);
        }
    }
    private void mapXdForCombobox(){
        setXangDauCombobox(cbb_tenxd);
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        if (lxd!=null){
            chungloai_lb.setText("Chủng loại: "+lxd.getLoai());
            mapPrice(lxd.getXd_id());
        }
    }
    private void mapPrice(int xd_id){
        transactionHistories = transactionHistoryService.getLastestTimeForEachPrices(xd_id);
        transactionHistories = transactionHistories.stream().filter(x->x.getTonkho_gia()>0).toList();
        if (!transactionHistories.isEmpty()){
            List<Double> priceList = transactionHistories.stream().map(TransactionHistory::getMucgia).toList();
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
            TransactionHistory transactionHistory = transactionHistories.stream().filter(x->x.getMucgia()==pre_price).findFirst().orElse(null);
            if (transactionHistory!=null){
                setTonKhoLabel(transactionHistory.getTonkho_gia());
            }else{
                setTonKhoLabel(0);
            }
        } else {
            setTonKhoLabel(0);
        }
    }
    private void setGiaCbbItems(List<Double> ls){
        cbb_dongia.setItems(FXCollections.observableList(ls));
        cbb_dongia.getSelectionModel().selectFirst();
    }
    private void setCellValueFactoryXuat(List<LedgerDetails> ls){
        tbView.setItems(FXCollections.observableList(ls));
        tbView.refresh();
    }
    private void clearFields(){
        phaixuat.setText("0");
        thucxuat.setText("0");
        nhietdo.setText("0");
        vcf.setText("0");
        tytrong.setText("0");
    }
    private Ledger getLedger(Ledger ledger) {
        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();

        ledger.setCreate_by(ConnectLan.pre_acc.getId());
        ledger.setFrom_date(tungay.getValue());
        ledger.setEnd_date(denngay.getValue());
        ledger.setStatus(StatusCons.ACTIVED.getName());
        ledger.setLoai_phieu(LoaiPhieuCons.PHIEU_XUAT.getName());
        ledger.setRoot_id(DashboardController.ref_Dv.getId());
        ledger.setDvi_xuat_id(DashboardController.ref_Dv.getId());

        if (lx.equals(LoaiXuat.NV.getName())){
            if (!xuatNVController.isValidField()){
                return null;
            }
            assignmentBillDto = xuatNVController.getInfor_valid();
            if (assignmentBillDto!=null){
                ledger.setDvi_baono(assignmentBillDto.getDvx_bn_id());
                ledger.setNguoi_nhan(assignmentBillDto.getNguoinhan());
                ledger.setSo_xe(assignmentBillDto.getSo_xe());
                ledger.setLenh_so(assignmentBillDto.getLenhso());
                splitBillNumber(assignmentBillDto.getSo(),ledger);
                ledger.setNhiemvu(assignmentBillDto.getCtnv().getChitiet());
                ledger.setNhiemvu_id(assignmentBillDto.getCtnv().getCtnv_id());
                ledger.setLoaigiobay(assignmentBillDto.getLgb());
                ledger.setPt_id(assignmentBillDto.getXmt().getId());
                ledger.setXmt_id(assignmentBillDto.getUnit().getId());
                ledger.setLpt(assignmentBillDto.getLpt().getTypeName());
                ledger.setLpt_2(assignmentBillDto.getLpt().getType());
                ledger.setSo_km(assignmentBillDto.getSokm());
                if (assignmentBillDto.getLgb().equals(TypeCons.TREN_KHONG.getName())){
                    ledger.setGiohd_tk(assignmentBillDto.getHours_act());
                    ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
                }else{
                    ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
                    ledger.setGiohd_md(assignmentBillDto.getHours_act());
                }
            }else{
                return null;
            }
            ledger.setTructhuoc(TructhuocEnum.NV.getName());
            ledger.setTcn_id(0);
        } else if (lx.equals(LoaiXuat.X_K.getName())){
            if (!xuatDVController.isValidField()){
                return null;
            }
            unitBillDto = xuatDVController.getInfo_valid();
            if (unitBillDto!=null){
                ledger.setNguoi_nhan(unitBillDto.getNguoinhan());
                ledger.setSo_xe(unitBillDto.getSo_xe());
                ledger.setLenh_so(unitBillDto.getLenhso());
                splitBillNumber(unitBillDto.getSo(),ledger);
                ledger.setDvi_nhan(unitBillDto.getDvi_nhan().getTen());
                ledger.setDvi_xuat(unitBillDto.getDvi_xuat().getTen());
                ledger.setDvi_nhan_id(unitBillDto.getDvi_nhan().getId());
                ledger.setDvi_xuat_id(unitBillDto.getDvi_xuat().getId());
                ledger.setTcn_id(unitBillDto.getTcn().getId());
                ledger.setTructhuoc(tructhuocService.findById(unitBillDto.getDvi_nhan().getTructhuoc_id()).orElseThrow().getType());
            }else{
                return null;
            }
            ledger.setDvi_baono(DashboardController.ref_Dv.getId());
            ledger.setLoaigiobay(null);
            ledger.setNhiemvu(null);
            ledger.setNhiemvu_id(0);
            ledger.setSo_km(0);
            ledger.setGiohd_tk(DefaultVarCons.GIO_HD.getName());
            ledger.setGiohd_md(DefaultVarCons.GIO_HD.getName());
            ledger.setNote(note.getText());
        }
        if(LedgerController.status.equals(StatusCons.EDIT.getName())){
            ledger.setId(LedgerController.ledger_edit.getId());
        }else{
            ledger.setId(ledger_id);
        }
        ledger.setYear(tungay.getValue().getYear());
        return ledger;
    }
    private LedgerDetails getLedgerDetails(LoaiXangDauDto lxd, Double gia){
        LedgerDetails ledgerDetails = new LedgerDetails();

        double txuat = thucxuat.getText().isBlank() ? 0 : Double.parseDouble(thucxuat.getText());
        double pxuat = phaixuat.getText().isBlank() ? 0 : Double.parseDouble(phaixuat.getText());

        String lx = loai_xuat_cbb.getSelectionModel().getSelectedItem();

        ledgerDetails.setTen_xd(lxd.getTenxd());
        ledgerDetails.setMa_xd(lxd.getMaxd());
        ledgerDetails.setSscd_nvdx(Purpose.NVDX.getName());
        ledgerDetails.setChung_loai(lxd.getChungloai());
        ledgerDetails.setDon_gia(gia);
        ledgerDetails.setPhai_xuat(pxuat);
        ledgerDetails.setThuc_xuat(txuat);
        ledgerDetails.setNhiet_do_tt(Double.parseDouble(nhietdo.getText().isBlank() ? "0" : nhietdo.getText().trim()));
        ledgerDetails.setHe_so_vcf(Double.parseDouble(vcf.getText().isBlank() ? "0" : vcf.getText().trim()));
        ledgerDetails.setTy_trong(Double.parseDouble(tytrong.getText().isBlank() ? "0" : tytrong.getText().trim()));
        ledgerDetails.setLoaixd_id(lxd.getXd_id());
        ledgerDetails.setSoluong(txuat);
        ledgerDetails.setSoluong_px(pxuat);
        ledgerDetails.setThuc_nhap(0);
        ledgerDetails.setPhai_nhap(0);
        if (LedgerController.status.equals(StatusCons.EDIT.getName())){
            ledgerDetails.setLedger_id(LedgerController.ledger_edit.getId());
        }else{
            ledgerDetails.setLedger_id(ledger_id);
        }
        ledgerDetails.setId(generateLEdgerDetailId());

        if (lx.equals(LoaiXuat.NV.getName())){
            assignmentBillDto = xuatNVController.getInfor_valid();
            if (assignmentBillDto!=null){
                if (assignmentBillDto.getLgb().equals(TypeCons.TREN_KHONG.getName())){
                    ledgerDetails.setThuc_xuat_tk(txuat);
                    ledgerDetails.setThuc_xuat(0);
                } else {
                    ledgerDetails.setThuc_xuat_tk(0);
                    ledgerDetails.setThuc_xuat(txuat);
                }
            }else{
                return null;
            }
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
    @FXML
    public void selected_item(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            if (DialogMessage.callAlertWithMessage(null, null, "Xác nhận xoa",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
                LedgerDetails ld = tbView.getSelectionModel().getSelectedItem();
                if (ld!=null){
                    deteled_ledDetail.add(ld);
                    l.removeDetail(ld);
                    setTonKhoLabel(inventory_quantity+ld.getSoluong());
                    tbView.setItems(FXCollections.observableList(l.getLedgerDetails()));
                    tbView.refresh();
                }
            }
        }
        LedgerDetails ld = tbView.getSelectionModel().getSelectedItem();
        if (ld!=null){
            cbb_tenxd.getSelectionModel().select(lxdLs.stream().filter(x->x.getXd_id()==ld.getLoaixd_id()).findFirst().orElse(null));
            dongia.setText(String.valueOf(ld.getDon_gia()));
            thucxuat.setText(String.valueOf(ld.getThuc_xuat()));
            phaixuat.setText(String.valueOf(ld.getPhai_xuat()));
            nhietdo.setText(String.valueOf(ld.getNhiet_do_tt()));
            tytrong.setText(String.valueOf(ld.getTy_trong()));
            vcf.setText(String.valueOf(ld.getHe_so_vcf()));
        }
    }
    @FXML
    public void chitietExited(MouseEvent mouseEvent) {
    }
    @FXML
    public void chitietEnter(MouseEvent mouseEvent) {
    }
    @FXML
    public void editBtnAction(ActionEvent actionEvent) {
        LoaiXangDauDto lxd = cbb_tenxd.getSelectionModel().getSelectedItem();
        Double gia = cbb_dongia.getSelectionModel().getSelectedItem();
        if (isCbb(lxd,gia)){
            if (validField()){
                LedgerDetails ld = getLedgerDetails(lxd, gia);
                if (ld!=null){
                    if (inventory_quantity < ld.getSoluong()) {
                        DialogMessage.message(null, "so luong xuat > so luong ton kho", MessageCons.CO_LOI_XAY_RA.getName(), Alert.AlertType.WARNING);
                    }else{
                        boolean a = true;
                        for (int i =0; i< l.getLedgerDetails().size(); i++){
                            LedgerDetails ld2 = l.getLedgerDetails().get(i);
                            if (ld2.getLoaixd_id()==ld.getLoaixd_id() && ld.getDon_gia()==ld.getDon_gia()){
                                a=false;
                                ld2.setThuc_xuat(ld.getThuc_xuat());
                                ld2.setPhai_xuat(ld.getPhai_xuat());
                                ld2.setSoluong(ld.getThuc_xuat());
                                ld2.setSoluong_px(ld.getPhai_xuat());
                                ld2.setSoluong_str(TextToNumber.textToNum_2digits(ld.getSoluong()));
                                ld2.setSoluongpx_str(TextToNumber.textToNum_2digits(ld.getSoluong_px()));
                                ld2.setThucxuat_str(TextToNumber.textToNum_2digits(ld.getThuc_xuat()));
                                ld2.setPhaixuat_str(TextToNumber.textToNum_2digits(ld.getPhai_xuat()));
                                ld2.setThanhtien_str(TextToNumber.textToNum_2digits(ld.getThuc_xuat()*ld.getDon_gia()));
                                l.getLedgerDetails().set(i, ld);
                                inventory_quantity = inventory_quantity-ld.getSoluong();
                            }
                        }
                        if (a){
                            l.addDetail(ld);
                        }
                        setItemToList(l.getLedgerDetails());
                        setTonKhoLabel(inventory_quantity-ld.getSoluong());
                        setCellValueFactoryXuat(l.getLedgerDetails());
                        clearFields();
                    }
                }
            }
        }
    }
}
