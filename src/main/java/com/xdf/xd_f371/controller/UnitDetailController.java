package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.TructhuocRepo;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class UnitDetailController implements Initializable {

    @FXML
    TextField unit_name_tf;
    @FXML
    ComboBox<TrucThuoc> tructhuoc_cbb;
    @Autowired
    private TructhuocRepo tructhuocRepo;
    @Autowired
    private NguonNxRepo nguonNxRepo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        unit_name_tf.setText(DonviController.selectedUnit.getNguonnx_name());
        setTructhuocCombobox();
    }

    private void setTructhuocCombobox(){
        tructhuoc_cbb.setItems(FXCollections.observableList(tructhuocRepo.findAll()));
        tructhuoc_cbb.setConverter(new StringConverter<TrucThuoc>() {
            @Override
            public String toString(TrucThuoc trucThuoc) {
                return trucThuoc==null ? "" : trucThuoc.getName();
            }
            @Override
            public TrucThuoc fromString(String s) {
                return tructhuocRepo.findById(tructhuoc_cbb.getValue().getId()).get();
            }
        });
        tructhuoc_cbb.getSelectionModel().select(tructhuocRepo.findById(DonviController.selectedUnit.getTructhuoc_id()).orElseThrow(RuntimeException::new));
    }

    @FXML
    public void exitScreen(ActionEvent actionEvent) {
        DonviController.unit_stage.close();
    }

    @FXML
    public void saveUnit(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("Thông báo", "Lưu thay đổi", "Xác nhận Lưu thay đổi?",Alert.AlertType.CONFIRMATION)== ButtonType.OK){
            nguonNxRepo.save(new NguonNx(DonviController.selectedUnit.getNguonnx_id(),unit_name_tf.getText(),DonviController.selectedUnit.getStatus(),tructhuoc_cbb.getValue().getId()));
            if (DialogMessage.callAlertWithMessage("Thông báo", "Thành công", "Đã lưu thay đổi",Alert.AlertType.INFORMATION)== ButtonType.OK){
                DonviController.unit_stage.close();
            }
        }
    }

}
