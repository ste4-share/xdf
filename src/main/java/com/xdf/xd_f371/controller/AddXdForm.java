package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.service.LoaiXdService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddXdForm implements Initializable {

    @Autowired
    private LoaiXdService loaiXdService;

    @FXML
    private TextField mxd,txd;
    @FXML
    private Button add_btn,cancel_btn;
    @FXML
    private ComboBox<String> tc_cbb,cl_cbb,loai_cbb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChungloaiCbb();
        initLoaiCbb();
        inittinhchatCbb();

    }

    private void inittinhchatCbb() {
    }

    private void initLoaiCbb() {
    }

    private void initChungloaiCbb() {
    }


    @FXML
    public void tc_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void cl_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void loai_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void addNew(ActionEvent actionEvent) {
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
    }
}
