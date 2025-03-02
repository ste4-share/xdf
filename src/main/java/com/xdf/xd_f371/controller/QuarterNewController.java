package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
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
    @FXML
    private ComboBox<NguonNx> dviCbb;
    @FXML
    private CheckBox tdv;

    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private NguonNxService nguonNxService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Accounts acc = ConnectLan.pre_acc;
        CommonFactory.setVi_DatePicker(sd);
        CommonFactory.setVi_DatePicker(ed);
        sd.setValue(acc.getSd());
        ed.setValue(acc.getEd());
        initDviCbb();
    }
    private void initDviCbb() {
        ComponentUtil.setItemsToComboBox(dviCbb,nguonNxService.findByAllBy(), NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dviCbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
        dviCbb.getSelectionModel().selectFirst();
    }
    @FXML
    public void save(ActionEvent actionEvent) {
        if (Common.date_valid(ed,sd)){
            if (DialogMessage.callAlertWithMessage(null,"Luu", "Luu thay doi?", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                NguonNx nnx = dviCbb.getSelectionModel().getSelectedItem();
                selectUnit(nnx);
                inventoryService.saveInvWhenSwitchQuarter(sd,ed,nnx,tdv.isSelected());
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
    @FXML
    public void dviCbbAction(ActionEvent actionEvent) {

    }
    @FXML
    public void tdvAction(ActionEvent actionEvent) {
        NguonNx nnx = dviCbb.getSelectionModel().getSelectedItem();
        selectUnit(nnx);
    }

    private void selectUnit(NguonNx nnx) {
        if (tdv.isSelected()){
            TonkhoController.ref_unit=null;
            dviCbb.setDisable(true);
        }else{
            TonkhoController.ref_unit=nnx;
            dviCbb.setDisable(false);
        }
    }
}
