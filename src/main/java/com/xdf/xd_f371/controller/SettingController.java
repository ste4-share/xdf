package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.entity.Configuration;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.ConfigurationService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class SettingController implements Initializable {
    @FXML
    private TextField reportFolderPath,passwd;
    @FXML
    private Label username_lb;
    @FXML
    private ComboBox<NguonNx> dvi_cbb;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private ConfigurationService configurationService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemsFor_dviCbb(nguonNxService.findByAllBy());
        initValueToField();
    }

    private void initValueToField() {
        Optional<Configuration> dv = configurationService.findByParam(ConfigCons.ROOT_ID.getName());
        Optional<Configuration> report_path = configurationService.findByParam(ConfigCons.REPORT_PATH.getName());
        dv.ifPresent(x->dvi_cbb.getSelectionModel().select(DashboardController.ref_Dv));
        report_path.ifPresent(x->reportFolderPath.setText(report_path.get().getValue()));
        username_lb.setText(ConnectLan.pre_acc.getUsername());
        passwd.setText(ConnectLan.pre_acc.getPasswd());
    }

    private void setItemsFor_dviCbb(List<NguonNx> ls){
        dvi_cbb.setItems(FXCollections.observableList(ls));
        ComponentUtil.setItemsToComboBox(dvi_cbb,ls,NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvi_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
    }
    @FXML
    public void saveChange(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null,null,"Luu thay doi?", Alert.AlertType.CONFIRMATION)== ButtonType.OK){
            NguonNx n = dvi_cbb.getSelectionModel().getSelectedItem();
            if (n!=null){
                configurationService.updateValueByParam(ConfigCons.ROOT_ID.getName(), String.valueOf(n.getId()));
                configurationService.updateValueByParam(ConfigCons.REPORT_PATH.getName(), reportFolderPath.getText());
                DashboardController.ref_Dv = n;
                DashboardController.pre_path = reportFolderPath.getText();
                DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
                DashboardController.primaryStage.close();
            }
        }
    }
    @FXML
    public void closingAction(ActionEvent actionEvent) {DashboardController.primaryStage.close();}
    @FXML
    public void getPathClicked(MouseEvent mouseEvent) {
        CommonFactory.pre_path = null;
        CommonFactory.setSelectDirectory(DashboardController.primaryStage);
        if (CommonFactory.pre_path!=null){
            reportFolderPath.setText(CommonFactory.pre_path);
        }
    }
}
