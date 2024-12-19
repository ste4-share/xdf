package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.service.QuarterService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SettingController implements Initializable {

    private QuarterService quarterService;

    @FXML
    private TextField q1_name,q2_name,q3_name,q4_name;
    @FXML
    private ComboBox<String> quy_cbb;
    @FXML
    private DatePicker s_q1,e_q1,s_q2,e_q2,s_q3,e_q3,s_q4,e_q4;
    @FXML
    private HBox rootbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rootbox.setPrefHeight(DashboardController.screenHeigh);
        rootbox.setPrefWidth(DashboardController.screenWidth);
    }

    @FXML
    public void save_Changed(ActionEvent actionEvent) {
    }
}
