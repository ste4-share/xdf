package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
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
import javafx.scene.control.TextField;
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
        initDvnCbb();
        initDvxCbb();
        initTcx();
    }

    private void initTcx() {
        ComponentUtil.setItemsToComboBox(tcx_cbb, tcnService.findByLoaiphieu(LoaiPhieuCons.PHIEU_XUAT.getName()), Tcn::getName, input -> tcnService.findByName(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(tcx_cbb, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        tcx_cbb.getSelectionModel().selectFirst();
    }
    private void initDvxCbb() {
        ComponentUtil.setItemsToComboBox(dvx_cbb,List.of(DashboardController.ref_Dv), NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvx_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
        dvx_cbb.getSelectionModel().selectFirst();
    }
    private void initDvnCbb() {
        ComponentUtil.setItemsToComboBox(dvn_cbb,nguonNxService.findAllByDifrentId(DashboardController.ref_Dv.getId()), NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvn_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
        dvn_cbb.getSelectionModel().selectFirst();
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
        e_so.setText(null);
        if(!so.getText().isBlank()) {
            if (ledgers.stream().anyMatch(l->l.getBill_id().equals(so.getText()))){
                e_so.setText("Số phiếu này đã được tạo, vui lòng nhập số khác");
            }
        }
    }
    @FXML
    public void so_clicked(MouseEvent mouseEvent) {
        so.selectAll();
    }
    @FXML
    public void lenhkhKr(KeyEvent keyEvent) {
        e_lenhkh.setText(null);
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
    @FXML
    public void dvnAction(ActionEvent actionEvent) {
        dvx_cbb.setStyle(null);
        e_dvx.setText(null);
    }
}
