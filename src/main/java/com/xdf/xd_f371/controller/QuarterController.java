package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class QuarterController implements Initializable {
    @FXML
    private Button addNewBtn,cancelBtn;
    @FXML
    private TextField quy;
    @FXML
    private DatePicker s_time,e_time;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void quyClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void addNew(ActionEvent actionEvent) {
        if (DialogMessage.callAlert()== ButtonType.OK){
            ConnectLan.primaryStage = new Stage();
            Common.openNewStage("dashboard2.fxml", ConnectLan.primaryStage,"XĂNG DẦU F371", StageStyle.DECORATED);
            ConnectLan.primaryStage2.close();
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
    }
    @FXML
    public void e_timeAction(ActionEvent actionEvent) {
    }
}
