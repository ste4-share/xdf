package com.xdf.xd_f371.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SettingController implements Initializable {
    @FXML
    private TextField reportFolderPath,username,passwd;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void dvi_cbbAction(ActionEvent actionEvent) {
    }
    @FXML
    public void openReportPath(MouseEvent mouseEvent) {
    }
    @FXML
    public void saveChange(ActionEvent actionEvent) {
    }
    @FXML
    public void closingAction(ActionEvent actionEvent) {DashboardController.primaryStage.close();
    }
}
