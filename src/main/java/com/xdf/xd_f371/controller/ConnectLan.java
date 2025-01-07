package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
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
import javafx.stage.DirectoryChooser;
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
    public static String pre_path;
    public static String port_pre;
    private String zeroTo255 = "(\\d{1,2}|(0|1)\\d{2}|2[0-4]\\d|25[0-5])";
    private String regex
            = zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255;
    @FXML
    private TextField username,ip,port,path_tf;
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
        ip_pre = "";
        port_pre = "0";
        // Load saved credentials if available
        loadCredentials(username,ip,port, passwd,path_tf, ck_save);
        connect.requestFocus();
    }
    @FXML
    public void connectedClicked(ActionEvent actionEvent) {
        try {
            String user = username.getText().trim();
            String p = passwd.getText().trim();
            String i = ip.getText().trim();
            String po = port.getText().trim();
            String path = path_tf.getText().trim();
            if (isvalid(user,p,i,po,path)){
                if (connectionService.checkConnection(ip.getText(),Integer.parseInt(port.getText()))){
                    rememberme(user,p,i,po,path);
                    setContext(i,po);
                    Optional<Accounts> acc = accountService.login(user,p);
                    if (acc.isPresent()){
                        InitProgressBar.stage.close();
                        ip_pre = i;
                        port_pre = po;
                        pre_path = path;
                        pre_acc = acc.get();
                        connectionService.maintainConnection();
                        primaryStage = new Stage();
                        primaryStage.setOnCloseRequest(event -> {
                            if (DialogMessage.callAlertWithMessage(null,"Thoát","Xác nhận thoát ứng dụng.", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                                Platform.exit(); // Cleanly stop the JavaFX thread
                                System.exit(0);  // Ensure JVM termination
                            }
                            event.consume(); // Prevent the stage from closing
                        });
                        Common.openNewStage2("dashboard2.fxml", primaryStage,"XĂNG DẦU F371", StageStyle.DECORATED);
                        ConnectLan.primaryStage.close();
                    } else {
                        DialogMessage.message(null, "Tài khoản hoặc mật khẩu không chính xác, vui lòng thử lại.",
                                "Đăng nhập không thành công", Alert.AlertType.INFORMATION);
                    }
                }else{
                    DialogMessage.message(null, "Vui long kiem tra lai ip va port", "That bai", Alert.AlertType.CONFIRMATION);
                    conn_status.setText("FAIL");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void setContext(String ip,String p){
        String url = "jdbc:postgresql://"+ip+":"+p+"/postgres";
        DataSourceManager dataSourceManager = MainApplicationApp.context.getBean(DataSourceManager.class);
        dataSourceManager.configureDataSource(url, "postgres", "postgres");
    }
    private void rememberme(String user, String p,String i,String po,String path){
        boolean rememberMe = ck_save.isSelected();
        if (rememberMe) {
            saveCredentials(user, p,i,po,path);
        } else {
            clearCredentials();
        }
    }
    private boolean isvalid(String user, String p,String ip,String po,String path){
        if (!path.isEmpty() && Common.isDirectory(path)){
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
        }else{
            path_tf.setStyle(CommonFactory.styleErrorField);
            DialogMessage.message(null, "Đường dẫn không chính xác",null, Alert.AlertType.INFORMATION);
        }
        return false;
    }
    @FXML
    public void exit(ActionEvent actionEvent) {
        InitProgressBar.stage.close();
    }
    @FXML
    public void checkConnection(ActionEvent actionEvent) {
        if (isvalid(username.getText(),passwd.getText(),ip.getText(),port.getText(),path_tf.getText())){
            if (connectionService.checkConnection(ip.getText(),Integer.parseInt(port.getText()))){
                DialogMessage.message("Thong bao", "Ket noi thanh cong", "Thanh cong", Alert.AlertType.CONFIRMATION);
                conn_status.setText("OK");
            }else{
                DialogMessage.message(null, "Vui long kiem tra lai ip va port", "That bai", Alert.AlertType.CONFIRMATION);
                conn_status.setText("FAIL");
            }
        }
    }
    private void saveCredentials(String username, String password,String ip,String port,String path) {
        try (FileOutputStream out = new FileOutputStream(CREDENTIALS_FILE)) {
            Properties props = new Properties();
            props.setProperty("username", username);
            props.setProperty("password", password);
            props.setProperty("ip", ip);
            props.setProperty("port", port);
            props.setProperty("path", path);
            props.store(out, "Saved Credentials");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void loadCredentials(TextField usernameField,TextField ip,TextField p, PasswordField passwordField,TextField path, CheckBox rememberMeCheckBox) {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file)) {
                Properties props = new Properties();
                props.load(in);
                ip.setText(props.getProperty("ip", ""));
                p.setText(props.getProperty("port", ""));
                path.setText(props.getProperty("path", ""));
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
        setSelectDirectory(DashboardController.primaryStage);
    }
    private void setSelectDirectory(Stage primaryStage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Directory");

        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            path_tf.setText(selectedDirectory.getAbsolutePath());
            pre_path= selectedDirectory.getAbsolutePath();
        } else {
            pre_path = null;
            DialogMessage.message(null, null,"No directory selected.", Alert.AlertType.WARNING);
        }
    }

}
