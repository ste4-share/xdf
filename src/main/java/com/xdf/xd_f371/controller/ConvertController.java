package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

@Component
public class ConvertController implements Initializable {
    @FXML
    private ComboBox<Quarter> prv_cbb,pre_cbb;
    @FXML
    private Button convertBtn;
    @FXML
    private Label prv_sd,pre_sd,prv_ed,pre_ed;
    @Autowired
    private QuarterService quarterService;
    @Autowired
    private InventoryService inventoryService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPre_cbb();setPrv_cbb();
        Common.hoverButton(convertBtn,"#ffffff");
    }

    private void setPre_cbb(){
        ComponentUtil.setItemsToComboBox(pre_cbb,quarterService.findAllDescSD(), Quarter::getIndex, input->quarterService.findByIndex(input).orElse(null));
        pre_cbb.getSelectionModel().selectFirst();
        Quarter q = pre_cbb.getSelectionModel().getSelectedItem();
        if (q!=null){
            setTe(pre_sd,pre_ed,q);
        }
    }
    private void setPrv_cbb(){
        ComponentUtil.setItemsToComboBox(prv_cbb,quarterService.findAllDescSD(), Quarter::getIndex, input->quarterService.findByIndex(input).orElse(null));
        prv_cbb.getSelectionModel().selectFirst();
        Quarter q = prv_cbb.getSelectionModel().getSelectedItem();
        if (q!=null){
            setTe(prv_sd,prv_ed,q);
        }
    }
    private void setTe(Label sd,Label ed,Quarter q){
        sd.setText(q.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        ed.setText(q.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }
    @FXML
    public void prv(ActionEvent actionEvent) {
        Quarter q = prv_cbb.getSelectionModel().getSelectedItem();
        if (q!=null){
            prv_sd.setText(q.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            prv_ed.setText(q.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
    @FXML
    public void pre(ActionEvent actionEvent) {
        Quarter q = pre_cbb.getSelectionModel().getSelectedItem();
        if (q!=null){
            pre_sd.setText(q.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            pre_ed.setText(q.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
    }
    @FXML
    public void convertAction(ActionEvent actionEvent) {
        Quarter pre = pre_cbb.getSelectionModel().getSelectedItem();
        Quarter prv = prv_cbb.getSelectionModel().getSelectedItem();
        if (DialogMessage.callAlert()== ButtonType.OK){
            if (pre!=null && prv!=null){
                inventoryService.saveInvWhenSwitchQuarter(prv,pre.getId());
                DialogMessage.successShowing("Thanh cong");
                TonkhoController.tk_stage.close();
            }
        }
    }
}
