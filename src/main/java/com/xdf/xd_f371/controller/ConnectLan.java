package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.service.AccountService;
import com.xdf.xd_f371.service.ConnectionService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.Properties;
import java.util.ResourceBundle;

@Component
public class ConnectLan implements Initializable {
    private static final String CREDENTIALS_FILE = "credentials.properties";
    public static Stage primaryStage;
    public static Accounts pre_acc = new Accounts();
    private String zeroTo255 = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
    private String regex
            = zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255;
    @FXML
    private TextField username,ip,port;
    @FXML
    private PasswordField passwd;
    @FXML
    private Button connect,exitbtn,ckbtn;
    @FXML
    private Label conn_status;
    @FXML
    private CheckBox ck_save;
    @Autowired
    private AccountService accountService;
    @Autowired
    private ConnectionService connectionService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Common.hoverButton(connect,"#009107");
        Common.hoverButton(exitbtn,"#5b5b5b");
        Common.hoverButton(ckbtn,"#ffffff");
        conn_status.setText("-----");
        // Load saved credentials if available
        loadCredentials(username,ip,port, passwd, ck_save);
        connect.requestFocus();
    }
    @FXML
    public void connectedClicked(ActionEvent actionEvent) {
        try {
            Platform.runLater(()->{
                primaryStage = new Stage();
                Common.task(this::login,()-> InitProgressBar.stage.close(),()-> {});
                Common.openNewStage("dashboard2.fxml", primaryStage,"XĂNG DẦU F371", StageStyle.DECORATED);
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void login(){
       String user = username.getText().trim();
       String p = passwd.getText().trim();
       String i = ip.getText().trim();
       String po = port.getText().trim();
       if (isvalid(user,p,i,po)){
           if (connectionService.checkConnection(ip.getText(),Integer.parseInt(port.getText()))){
               rememberme(user,p,i,po);
               Optional<Accounts> acc = accountService.login(user,p);
               if (acc.isPresent()){
                   pre_acc = acc.get();
               } else {
                   DialogMessage.message(null, "Tài khoản hoặc mật khẩu không chính xác, vui lòng thử lại.",
                           "Đăng nhập không thành công", Alert.AlertType.INFORMATION);
               }
           }else{
               DialogMessage.message(null, "Vui long kiem tra lai ip va port", "That bai", Alert.AlertType.CONFIRMATION);
               conn_status.setText("FAIL");
           }
       }
    }
    private void rememberme(String user, String p,String i,String po){
        boolean rememberMe = ck_save.isSelected();
        if (rememberMe) {
            saveCredentials(user, p,i,po);
        } else {
            clearCredentials();
        }
    }
    private boolean isvalid(String user, String p,String ip,String po){
        if (!user.isEmpty()){
            if (!p.isEmpty()){
                if (ip.matches(regex) || ip.equals("localhost")){
                    if (Common.isNumber(po.trim())){
                        return true;
                    }else {
                        DialogMessage.message(null, "port khong dung dinh dang",
                                null, Alert.AlertType.INFORMATION);
                    }
                }else{
                    DialogMessage.message(null, "Ip khong dung dinh dang",
                            null, Alert.AlertType.INFORMATION);
                }
            }else{
                DialogMessage.message(null, "Cần nhập mật khẩu.",
                        "Chưa nhập mật khẩu", Alert.AlertType.INFORMATION);
            }
        }else {
            DialogMessage.message(null, "Cần nhập tên tài khoản.",
                    "Chưa nhập Tên tài khoản", Alert.AlertType.INFORMATION);
        }
        return false;
    }
    @FXML
    public void exit(ActionEvent actionEvent) {
        InitProgressBar.stage.close();
    }
    @FXML
    public void checkConnection(ActionEvent actionEvent) {
        if (isvalid(username.getText(),passwd.getText(),ip.getText(),port.getText())){
            if (connectionService.checkConnection(ip.getText(),Integer.parseInt(port.getText()))){
                DialogMessage.message("Thong bao", "Ket noi thanh cong", "Thanh cong", Alert.AlertType.CONFIRMATION);
                conn_status.setText("OK");
            }else{
                DialogMessage.message(null, "Vui long kiem tra lai ip va port", "That bai", Alert.AlertType.CONFIRMATION);
                conn_status.setText("FAIL");
            }
        }
    }

    private void saveCredentials(String username, String password,String ip,String port) {
        try (FileOutputStream out = new FileOutputStream(CREDENTIALS_FILE)) {
            Properties props = new Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);
            props.setProperty("ip", ip);
            props.setProperty("port", port);
            props.store(out, "Saved Credentials");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCredentials(TextField usernameField,TextField ip,TextField p, PasswordField passwordField, CheckBox rememberMeCheckBox) {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                Properties props = new Properties();
                props.load(in);
                ip.setText(props.getProperty("ip", ""));
                p.setText(props.getProperty("port", ""));
                usernameField.setText(props.getProperty("username", ""));
                passwordField.setText(props.getProperty("password", ""));
                rememberMeCheckBox.setSelected(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void clearCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            if (!file.delete()) {
                System.out.println("Failed to delete credentials file.");
            }
        }
    }
}
