package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.util.Common;
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
public class QuarterNewController implements Initializable {
    @FXML
    private DatePicker ed,sd;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Accounts acc = ConnectLan.pre_acc;
        CommonFactory.setVi_DatePicker(sd);
        CommonFactory.setVi_DatePicker(ed);
        sd.setValue(acc.getSd());
        ed.setValue(acc.getEd());
    }
    @FXML
    public void save(ActionEvent actionEvent) {
        if (Common.date_valid(ed,sd)){
            if (DialogMessage.callAlertWithMessage(null,"Luu", "Luu thay doi?", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                inventoryService.saveInvWhenSwitchQuarter(sd,ed);
                DialogMessage.successShowing("Luu thanh cong!!");
                TonkhoController.tk_stage.close();
            }
        }
    }
    @FXML
    public void exit(ActionEvent actionEvent) {
        TonkhoController.tk_stage.close();
    }
    @FXML
    public void edAction(ActionEvent actionEvent) {
    }
}
