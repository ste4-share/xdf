package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

@Component
public class ConnectLan implements Initializable {

    public static Stage primaryStage;

    @FXML
    private TextField hostname,passwd;
    @FXML
    private Label conn_status;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn_status.setText("");
    }

    @FXML
    public void connectedClicked(ActionEvent actionEvent) {
        primaryStage = new Stage();
        Common.openNewStage("dashboard2.fxml", primaryStage,"XĂNG DẦU F371");
    }
    @FXML
    public void exit(ActionEvent actionEvent) {
        MainApplicationApp.rootStage.close();
    }
    @FXML
    public void checkConnection(ActionEvent actionEvent) {
       if (checkConnection()){
           DialogMessage.message("Thong bao", "Ket noi thanh cong", "Thanh cong", Alert.AlertType.CONFIRMATION);
           conn_status.setText("OK");
       }else{
           conn_status.setText("FAIL");
       }
    }

    private boolean checkConnection() {
        DataSource ds = MainApplicationApp.context.getBean(DataSource.class);
        try (Connection connection = ds.getConnection()) {
            return true;
        } catch (SQLException e) {
            DialogMessage.message("Thong bao", e.getMessage(), "Get error when connect to database.", Alert.AlertType.ERROR);
            e.printStackTrace();
        }
        return false;
    }
}
