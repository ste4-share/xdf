package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.TructhuocService;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class UnitDetailController implements Initializable {

    @FXML
    private TextField unit_name_tf;
    @FXML
    private ComboBox<TrucThuoc> tructhuoc_cbb;
    @FXML
    private RadioButton nhap_rd,xuat_rd,all_rd;
    @Autowired
    private TructhuocService tructhuocService;
    @Autowired
    private NguonNxService nguonNxService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unit_name_tf.setText(DonviController.selectedUnit.getNguonnx_name());
        setTructhuocCombobox();
        initPhieuRadio();
    }

    private void initPhieuRadio() {
        TrucThuoc trucThuoc = DashboardController.trucThuocs_ls.stream().filter(x->x.getId()==DonviController.selectedUnit.getTructhuoc_id()).findFirst().orElse(null);
        setTrucThuocByRadio(trucThuoc);
    }

    private void setTrucThuocByRadio(TrucThuoc trucThuoc){
        if (trucThuoc!=null){
            if (trucThuoc.getLoaiphieu().contains("N") && trucThuoc.getLoaiphieu().contains("X")){
                all_rd.setSelected(true);
            } else if (trucThuoc.getLoaiphieu().contains("N")) {
                nhap_rd.setSelected(true);
            } else if (trucThuoc.getLoaiphieu().contains("X")) {
                xuat_rd.setSelected(true);
            }
        }
    }

    private void setTructhuocCombobox(){
        ComponentUtil.setItemsToComboBox(tructhuoc_cbb,tructhuocService.findAll(),TrucThuoc::getName, input->tructhuocService.findTructhuocByName(input));
        tructhuoc_cbb.getSelectionModel().select(tructhuocService.findById(DonviController.selectedUnit.getTructhuoc_id()).orElse(null));
    }
    @FXML
    public void exitScreen(ActionEvent actionEvent) {
        DonviController.unit_stage.close();
    }
    @FXML
    public void saveUnit(ActionEvent actionEvent) {
        TrucThuoc tt = tructhuoc_cbb.getSelectionModel().getSelectedItem();
        if (DialogMessage.callAlertWithMessage("Thông báo", "Lưu thay đổi", "Xác nhận Lưu thay đổi?",Alert.AlertType.CONFIRMATION)== ButtonType.OK){
            if (tt!=null){
                if (tt.getLoaiphieu().contains("N") && tt.getLoaiphieu().contains("X")){
                    tt.setLoaiphieu("N,X");
                } else if (tt.getLoaiphieu().contains("N")) {
                    tt.setLoaiphieu("N");
                }else if (tt.getLoaiphieu().contains("X")) {
                    tt.setLoaiphieu("X");
                }else {
                    tt.setLoaiphieu("N,X");
                }
                nguonNxService.saveNnxAndLedger(new NguonNx(DonviController.selectedUnit.getNguonnx_id(),unit_name_tf.getText(),DonviController.selectedUnit.getStatus(),
                        DonviController.selectedUnit.getCode(),tructhuoc_cbb.getValue().getId()),tt);
                DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
                DonviController.unit_stage.close();
            }
        }
    }
    @FXML
    public void nhap_rdAction(ActionEvent actionEvent) {
    }
    @FXML
    public void xuat_rdAction(ActionEvent actionEvent) {
    }
    @FXML
    public void all_rdAction(ActionEvent actionEvent) {
    }
    @FXML
    public void tructhuoc_cbbAction(ActionEvent actionEvent) {
        TrucThuoc tt = tructhuoc_cbb.getSelectionModel().getSelectedItem();
        if (tt!=null){
            setTrucThuocByRadio(tt);
        }
    }
}
