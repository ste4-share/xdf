package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
    private TextField hostname, ip;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
       checkConnection();
       DialogMessage.message("Thong bao", "Ket noi thanh cong", "Thanh cong", Alert.AlertType.CONFIRMATION);
    }

//    private void setHintForIp(List<String> list){
//        TextFields.bindAutoCompletion(ip, t -> {
//            return list.stream().filter(elem
//                    -> {
//                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase().trim());
//            }).collect(Collectors.toList());
//        });
//    }

    private void checkConnection() {
        // Simulate a database connection check or any other connection
        try {
            // Simulate checking the database
            System.out.println("Checking database connection...");
            DataSource ds = MainApplicationApp.context.getBean(DataSource.class);
            try (Connection connection = ds.getConnection()) {
                // If the connection is successful, it won't throw an exception
                System.out.println("Database connection successful!");
                return ;
            } catch (SQLException e) {
                showErrorDialog("Get error when connect to database. ",e.getMessage());
            }
            // Simulate success
            Thread.sleep(3000);
        } catch (Exception e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
    }
    private void showErrorDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
