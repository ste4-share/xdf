package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.TructhuocService;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class AddUnitForm implements Initializable {
    private static boolean isValid=true;
    @FXML
    ComboBox<TrucThuoc> tructhuoc_cbb;
    @FXML
    TextField unit_name,code;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private TructhuocService tructhuocService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTructhuocCmb();
    }
    private void initTructhuocCmb() {
        ComponentUtil.setItemsToComboBox(tructhuoc_cbb,tructhuocService.findAll(),TrucThuoc::getName, input->tructhuocService.findTructhuocByName(input));
        tructhuoc_cbb.getSelectionModel().selectFirst();
    }
    @FXML
    public void addNew(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null, "Tạo mới đơn vị", "Xác nhận tạo mới",Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
            TrucThuoc tt = tructhuoc_cbb.getSelectionModel().getSelectedItem();
            if(isValid){
                if (tt!=null) {
                    nguonNxService.save(new NguonNx(unit_name.getText(),code.getText(), StatusCons.NORMAL_STATUS.getName(), tt.getId()));
                    DialogMessage.callAlertWithMessage(null, "Thanh cong", "Them moi thanh cong",Alert.AlertType.WARNING);
                    DonviController.unit_stage.close();
                } else {
                    DialogMessage.callAlertWithMessage(null, null, "Co loi xay ra, vui long thu lai sau",Alert.AlertType.WARNING);
                }
            } else{
                DialogMessage.callAlertWithMessage(null, null, "Valid failed!",Alert.AlertType.WARNING);
            }
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        DonviController.unit_stage.close();
    }
    @FXML
    public void ten_clicked(MouseEvent mouseEvent) {
        unit_name.selectAll();
    }
    @FXML
    public void ma_clicked(MouseEvent mouseEvent) {
        code.selectAll();
    }
    @FXML
    public void code_kr(KeyEvent keyEvent) {
        if (!code.getText().trim().isEmpty()){
            code.setStyle(null);
            isValid=true;
        }else{
            code.setStyle(CommonFactory.styleErrorField);
            isValid=false;
        }
    }
    @FXML
    public void ten_kr(KeyEvent keyEvent) {
        if (!unit_name.getText().trim().isEmpty()){
            unit_name.setStyle(null);
            isValid=true;
        }else{
            unit_name.setStyle(CommonFactory.styleErrorField);isValid=false;
        }
    }
}
