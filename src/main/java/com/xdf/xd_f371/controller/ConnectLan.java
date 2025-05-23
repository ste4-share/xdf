package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.cons.DefaultVarCons;
import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.AccountService;
import com.xdf.xd_f371.service.ConnectionService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
    public static String ip_pre;
    public static String port_pre;
    private String zeroTo255 = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
    private String regex
            = zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255;
    @FXML
    private TextField username;
    @FXML
    private PasswordField passwd;
    @FXML
    private Button connect,exitbtn;
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
        conn_status.setText("-----");
        ip_pre = "";
        port_pre = "0";
        loadCredentials(username,passwd, ck_save);
        connect.requestFocus();
    }
    @FXML
    public void connectedClicked(ActionEvent actionEvent) {
        try {
            String user = username.getText().trim();
            String p = passwd.getText().trim();
            if (isvalid(user,p, DefaultVarCons.IP.getName(),DefaultVarCons.PORT.getName())){
                if (connectionService.checkConnection(DefaultVarCons.IP.getName(),Integer.parseInt(DefaultVarCons.PORT.getName()))){
                rememberme(user,p,DefaultVarCons.IP.getName(),DefaultVarCons.PORT.getName());
                setContext(DefaultVarCons.IP.getName(),DefaultVarCons.PORT.getName());
                log_in(user,p,DefaultVarCons.IP.getName(),DefaultVarCons.PORT.getName());
            }else{
                DialogMessage.message(null, "Vui long kiem tra lai ip va port", "That bai", Alert.AlertType.CONFIRMATION);
                conn_status.setText("FAIL");
            }
        }
        }catch (Exception e){
            e.printStackTrace();
            DialogMessage.errorShowing("Co loi xay ra :" + e.getMessage());
        }
    }
    private void log_in(String user,String p,String i,String po){
                Optional<Accounts> acc = accountService.login(user,p);
                if (acc.isPresent()){
                    InitProgressBar.stage.close();
                    ip_pre = i;
                    port_pre = po;
                    pre_acc = acc.get();
                    connectionService.maintainConnection();
                    primaryStage = new Stage();
                    primaryStage.setOnCloseRequest(event -> {
                        if (DialogMessage.callAlertWithMessage(null,"Thoát","Xác nhận thoát ứng dụng.", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                            Platform.exit();
                            System.exit(0);
                        }
                        event.consume();
                    });
                    Common.openNewStage2("dashboard2.fxml", primaryStage,"XĂNG DẦU F371", StageStyle.DECORATED);
                    ConnectLan.primaryStage.close();
                } else {
                    DialogMessage.message(null, "Tài khoản hoặc mật khẩu không chính xác, vui lòng thử lại.",
                            "Đăng nhập không thành công", Alert.AlertType.INFORMATION);
                }
    }
    private void setContext(String ip,String p){
        String url = "jdbc:postgresql://"+ip+":"+p+"/postgres";
        DataSourceManager dataSourceManager = MainApplicationApp.context.getBean(DataSourceManager.class);
        dataSourceManager.configureDataSource(url, "postgres", "postgres");
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
        if (isvalid(username.getText(),passwd.getText(),DefaultVarCons.IP.getName(),DefaultVarCons.PORT.getName())){
            if (connectionService.checkConnection(DefaultVarCons.IP.getName(),Integer.parseInt(DefaultVarCons.PORT.getName()))){
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
    private void loadCredentials(TextField usernameField, PasswordField passwordField, CheckBox rememberMeCheckBox) {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                Properties props = new Properties();
                props.load(in);
//                ip.setText(props.getProperty("ip", ""));
//                p.setText(props.getProperty("port", ""));
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
                DialogMessage.errorShowing("Failed to delete credentials file.");
            }
        }
    }
    @FXML
    public void browserBtnACtion(ActionEvent actionEvent) {
        CommonFactory.setSelectDirectory(DashboardController.primaryStage);
    }
}
