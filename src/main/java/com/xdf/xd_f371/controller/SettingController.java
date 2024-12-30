package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.AccountService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class SettingController implements Initializable {
    private String set_style = "-fx-background-color: red; ";
    private static String o_path;
    private boolean isChanged = false;
    private boolean tf_isChanged = false;
    @Autowired
    private QuarterService quarterService;
    @Autowired
    private AccountService accountService;
    @FXML
    private TextField q_name,color,username,path;
    @FXML
    private Button savechangeBtn,browserBtn;
    @FXML
    private Label year_lb;
    @FXML
    private DatePicker s_q,e_q;
    @FXML
    private HBox rootbox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hoverBtn();
        String pre_year = String.valueOf(Year.now().getValue());
        year_lb.setText(pre_year);
        clearField();
        initCurrentQuarterToField(pre_year);
        username.setText(ConnectLan.pre_acc.getUsername());
        Accounts a = accountService.findAccountByUsername(ConnectLan.pre_acc.getUsername());
        if (a!=null){
            color.setText(a.getColor());
            path.setText(a.getPath());
        }else{
            color.setText(null);
            path.setText(null);
        }

        tf_listten_change(username);
        tf_listten_change(color);
        tf_listten_change(path);
    }
    private void initCurrentQuarterToField(String pre_year){
        List<Quarter> preQuarter  = quarterService.findAllByYear(pre_year);
        if(preQuarter.isEmpty()){
            clearField();
        }else{

        }
    }
    private void setDateToDatePicker(DatePicker s,DatePicker e, Quarter q){
        ComponentUtil.createDatePicker(s, q.getStart_date(), "dd-MM-yyyy");
        ComponentUtil.createDatePicker(e, q.getEnd_date(), "dd-MM-yyyy");
    }
    private void clearField(){
        q_name.clear();
        s_q.setValue(null);
        e_q.setValue(null);
    }
    @FXML
    public void save_Changed(ActionEvent actionEvent) {
        if (!isEmptyField(q_name)) {
            if (isDuplicateDate()){
                if (DialogMessage.callAlertWithMessage(null, "Luu",
                        "Luu thay doi?", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
                    if (isChanged){
                        saveQuarter();
                    }
                    if (tf_isChanged) {
                        saveUser();
                    }
                    DialogMessage.message("Thông báo", "Lưu thay đổi thành công",
                            "Thành công", Alert.AlertType.CONFIRMATION);
                    ConnectLan.pre_acc.setPath(path.getText());
                    ConnectLan.pre_acc.setColor(color.getText());
                }
            }else{
                DialogMessage.message("Thông báo", "Thời gian mỗi quý phải là khoảng riêng biệt.",
                        "Có lỗi xảy ra", Alert.AlertType.ERROR);
            }
        }
    }
    @FXML
    public void pathChange(ActionEvent actionEvent) {
        setSelectDirectory(DashboardController.primaryStage);
    }
    @FXML
    public void rootCbbAction(ActionEvent actionEvent) {
    }
    private void saveUser(){
        try {
            Accounts a = accountService.findAccountByUsername(ConnectLan.pre_acc.getUsername());
            a.setPath(path.getText());
            a.setColor(color.getText());
            accountService.save(a);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void saveQuarter(){
        Quarter q1= quarterService.findByUnique(year_lb.getText(),q_name.getText()).orElse(null);
        if (q1==null){
            quarterService.save(new Quarter( s_q.getValue(),e_q.getValue(),String.valueOf(Year.now().getValue()), q_name.getText()));
        }
    }
    private void setSelectDirectory(Stage primaryStage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Directory");

        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            path.setText(selectedDirectory.getAbsolutePath());
            o_path= selectedDirectory.getAbsolutePath();
        } else {
            o_path = null;
            System.out.println("No directory selected.");
            DialogMessage.message(null, null,"No directory selected.", Alert.AlertType.WARNING);
        }
    }
    private void listten_change(DatePicker dp){
        dp.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                isChanged = true; // Mark as changed
            }
        });
    }
    private void tf_listten_change(TextField tf){
        tf.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                tf_isChanged = true; // Mark as changed
            }
        });
    }

    private boolean isEmptyField(TextField tf){
        if (tf.getText().trim().isEmpty()){
            return true;
        }
        return false;
    }
    private boolean isDuplicateDate(){
        if (isFutureDate(s_q.getValue(), e_q.getValue())){
            setErrorStyle(s_q,e_q);
            return false;
        }
        s_q.setStyle(null);
        e_q.setStyle(null);
        return true;
    }
    private void setErrorStyle(DatePicker l1, DatePicker l2){
        l1.setStyle(CommonFactory.styleErrorField);
        l2.setStyle(set_style);
    }

    private boolean isFutureDate(LocalDate start_date, LocalDate end_date){
        return start_date.isAfter(end_date);
    }

    private void hoverBtn(){
        rootbox.setPrefHeight(DashboardController.screenHeigh-350);
        rootbox.setPrefWidth(DashboardController.screenWidth);
        Common.hoverButton(browserBtn,"#ffffff");
        Common.hoverButton(savechangeBtn,"#ffffff");
    }

}
