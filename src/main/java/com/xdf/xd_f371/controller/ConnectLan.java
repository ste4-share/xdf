package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.service.AccountService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class ConnectLan implements Initializable {

    public static Stage primaryStage;
    public static Accounts pre_acc = new Accounts();
    @FXML
    private TextField hostname,passwd;
    @FXML
    private Button connect,exitbtn,ckbtn;
    @FXML
    private Label conn_status;
    @FXML
    private CheckBox ck_save;
    @Autowired
    private AccountService accountService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Common.hoverButton(connect,"#009107");
        Common.hoverButton(exitbtn,"#5b5b5b");
        Common.hoverButton(ckbtn,"#ffffff");
        conn_status.setText("-----");
    }

    @FXML
    public void connectedClicked(ActionEvent actionEvent) {
        String user = hostname.getText().trim();
        String p = passwd.getText().trim();
        if (!user.isEmpty()){
            if (!p.isEmpty()){
                Optional<Accounts> acc = accountService.login(user,p);
                if (acc.isPresent()){
                    pre_acc = acc.get();
                    primaryStage = new Stage();
                    Common.openNewStage("dashboard2.fxml", primaryStage,"XĂNG DẦU F371");
                    MainApplicationApp.rootStage.close();
                }else{
                    DialogMessage.message("", "Tài khoản hoặc mật khẩu không chính xác, vui lòng thử lại.",
                            "Đăng nhập không thành công", Alert.AlertType.INFORMATION);
                }
            }else{
                DialogMessage.message("", "Cần nhập mật khẩu.",
                        "Chưa nhập mật khẩu", Alert.AlertType.INFORMATION);
            }
        }else {
            DialogMessage.message("", "Cần nhập tên tài khoản.",
                    "Chưa nhập Tên tài khoản", Alert.AlertType.INFORMATION);
        }
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
