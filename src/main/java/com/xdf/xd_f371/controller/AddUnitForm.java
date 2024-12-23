package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.TructhuocService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Controller
public class AddUnitForm implements Initializable {
    @FXML
    ComboBox<TrucThuoc> tructhuoc_cbb;
    @FXML
    TextField unit_name;
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
            //create new category
            nguonNxService.save(new NguonNx(unit_name.getText()));
            DonviController.unit_stage.close();
        }
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        DonviController.unit_stage.close();
    }
}
