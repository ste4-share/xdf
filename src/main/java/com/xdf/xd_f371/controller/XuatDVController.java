package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.dto.UnitBillDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.Tcn;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class XuatDVController extends CommonFactory implements Initializable {

    @FXML
    private Label e_dvn, e_dvx,e_so,e_lenhkh,e_tcx,e_nguoinhan,e_soxe;
    @FXML
    private ComboBox<NguonNx> dvn_cbb,dvx_cbb;
    @FXML
    private ComboBox<Tcn> tcx_cbb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        unitBillDto = new UnitBillDto();
        initLocalList();
        setDvCombobox(dvn_cbb,dvvcLs);
        setDvCombobox(dvx_cbb,dvnLs);
        initTcx();
        initPredictBillNumber();
        initEditLedger();
    }

    private void initEditLedger() {
        if (LedgerController.ledger_edit!=null){
            l = LedgerController.ledger_edit;
            String bid = l.getBill_id()==null ? "" : l.getBill_id();
            String bid2 = l.getBill_id2()==null ? "" : l.getBill_id2();
            so.setText(bid.concat(bid2));
            nguoinhan.setText(l.getNguoi_nhan());
            lenhso.setText(l.getLenh_so());
            soxe.setText(l.getSo_xe());
            dvn_cbb.getSelectionModel().select(DashboardController.units_ls.stream().filter(x->x.getId()==l.getDvi_nhan_id()).findFirst().orElse(null));
            dvx_cbb.getSelectionModel().select(DashboardController.units_ls.stream().filter(x->x.getId()==l.getDvi_xuat_id()).findFirst().orElse(null));
            tcx_cbb.getSelectionModel().select(DashboardController.tcn_ls.stream().filter(x->x.getId()==l.getTcn_id()).findFirst().orElse(null));
        }
    }

    private void initTcx() {
        ComponentUtil.setItemsToComboBox(tcx_cbb, tcnx_ls, Tcn::getName, input -> tcnx_ls.stream().filter(x->x.getName().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(tcx_cbb, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        tcx_cbb.getSelectionModel().selectFirst();
    }
    private void setDvCombobox(ComboBox<NguonNx> cmb_dv, List<NguonNx> nguonNxList){
        setNguonnxCombobox(cmb_dv, nguonNxList);
        cmb_dv.getSelectionModel().selectFirst();
    }
    @FXML
    public void dvxAction(ActionEvent actionEvent) {
        dvx_cbb.setStyle(null);
        e_dvx.setText(null);
    }
    @FXML
    public void tcx_cbbACtion(ActionEvent actionEvent) {
        tcx_cbb.setStyle(null);
        e_tcx.setText(null);
    }
    @FXML
    public void soKeyRealed(KeyEvent keyEvent) {
        if(!so.getText().isBlank()) {
            e_so.setText(null);
            so.setStyle(null);
        }else{
            e_so.setText(MessageCons.NOT_EMPTY_so.getName());
            so.setStyle(CommonFactory.styleErrorField);
        }
    }
    @FXML
    public void so_clicked(MouseEvent mouseEvent) {
        so.selectAll();
    }
    @FXML
    public void lenhkhKr(KeyEvent keyEvent) {
        e_lenhkh.setText(null);
        lenhso.setStyle(null);
        if(!lenhso.getText().isBlank()) {
            if (ledgers.stream().anyMatch(l->l.getBill_id().equals(lenhso.getText()))){
                e_lenhkh.setText("Lệnh này đã được tạo, vui lòng nhập lệnh khác");
            }
        }
    }
    @FXML
    public void lenhkhClicked(MouseEvent mouseEvent) {
        lenhso.selectAll();
    }
    public boolean isValidField(){
        if (!lenhso.getText().isBlank()){
            if (!so.getText().isBlank()){
                return true;
            }else{
                DialogMessage.errorShowing("Cần nhập số.");
                so.setStyle(CommonFactory.styleErrorField);
            }
        }else{
            DialogMessage.errorShowing("Cần nhập lệnh.");
            lenhso.setStyle(CommonFactory.styleErrorField);
        }
        return false;
    }
    public UnitBillDto getInfo(){
        NguonNx dvn = dvn_cbb.getSelectionModel().getSelectedItem();
        NguonNx dvx = dvx_cbb.getSelectionModel().getSelectedItem();
        Tcn tcn = tcx_cbb.getSelectionModel().getSelectedItem();
        if (dvn!=null){
            if (dvx!=null){
                if (tcn!=null){
                    unitBillDto = new UnitBillDto(dvn,dvx,tcn,so.getText(),lenhso.getText(),nguoinhan.getText(),soxe.getText());
                    return unitBillDto;
                }else{
                    DialogMessage.errorShowing("Tính chất xuất không xác định");
                    e_tcx.setText("Đơn vị xuất không xác định");
                    tcx_cbb.setStyle(CommonFactory.styleErrorField);
                }
            }else {
                DialogMessage.errorShowing("Đơn vị xuất không xác định");
                e_dvx.setText("Đơn vị xuất không xác định");
                dvx_cbb.setStyle(CommonFactory.styleErrorField);
            }
        }else {
            DialogMessage.errorShowing("Đơn vị nhập không xác định");
            e_dvn.setText("Đơn vị nhập không xác định");
            dvn_cbb.setStyle(CommonFactory.styleErrorField);
        }
        return null;
    }
    public UnitBillDto getInfo_valid(){
        NguonNx dvn = dvn_cbb.getSelectionModel().getSelectedItem();
        NguonNx dvx = dvx_cbb.getSelectionModel().getSelectedItem();
        Tcn tcn = tcx_cbb.getSelectionModel().getSelectedItem();
        if (dvn!=null){
            if (dvx!=null){
                if (tcn!=null){
                    if (isValidField()){
                        unitBillDto = new UnitBillDto(dvn,dvx,tcn,so.getText(),lenhso.getText(),nguoinhan.getText(),soxe.getText());
                        return unitBillDto;
                    }
                }else{
                    DialogMessage.errorShowing("Tính chất xuất không xác định");
                    e_tcx.setText("Đơn vị xuất không xác định");
                    tcx_cbb.setStyle(CommonFactory.styleErrorField);
                }
            }else {
                DialogMessage.errorShowing("Đơn vị xuất không xác định");
                e_dvx.setText("Đơn vị xuất không xác định");
                dvx_cbb.setStyle(CommonFactory.styleErrorField);
            }
        }else {
            DialogMessage.errorShowing("Đơn vị nhập không xác định");
            e_dvn.setText("Đơn vị nhập không xác định");
            dvn_cbb.setStyle(CommonFactory.styleErrorField);
        }
        return null;
    }
    @FXML
    public void dvnAction(ActionEvent actionEvent) {
        dvx_cbb.setStyle(null);
        e_dvx.setText(null);
    }
}
