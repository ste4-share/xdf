package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.service.AccountService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.Year;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class SettingController implements Initializable {
    private String set_style = "-fx-background-color: red; ";
    private boolean tf_isChanged = false;
    @Autowired
    private QuarterService quarterService;
    @Autowired
    private AccountService accountService;
    @FXML
    private TextField color,username;
    @FXML
    private Button savechangeBtn;
    @FXML
    private HBox rootbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hoverBtn();
        String pre_year = String.valueOf(Year.now().getValue());
        username.setText(ConnectLan.pre_acc.getUsername());
        Optional<Accounts> a = accountService.findAccountByUsername(ConnectLan.pre_acc.getUsername());
        if (a.isPresent()){
            color.setText(a.get().getColor());
        }else{
            color.setText(null);
        }

        tf_listten_change(username);
        tf_listten_change(color);
    }

    @FXML
    public void save_Changed(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null, "Luu",
                "Luu thay doi?", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
            if (tf_isChanged) {
                saveUser();
            }
            DialogMessage.message("Thông báo", "Lưu thay đổi thành công",
                    "Thành công", Alert.AlertType.CONFIRMATION);
            ConnectLan.pre_acc.setColor(color.getText());
        }
    }
    @FXML
    public void rootCbbAction(ActionEvent actionEvent) {
    }
    private void saveUser(){
        try {
            ConnectLan.pre_acc.setColor(color.getText());
            accountService.save(ConnectLan.pre_acc);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void tf_listten_change(TextField tf){
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tf_isChanged = true; // Mark as changed
            }
        });
    }

    private void hoverBtn(){
        rootbox.setPrefHeight(DashboardController.screenHeigh-350);
        rootbox.setPrefWidth(DashboardController.screenWidth);
        Common.hoverButton(savechangeBtn,"#ffffff");
    }

}
