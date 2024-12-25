package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.entity.Quarter;
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
    private boolean isChanged = false;
    private boolean tf_isChanged = false;

    @Autowired
    private QuarterService quarterService;
    @Autowired
    private AccountService accountService;

    @FXML
    private TextField q1_name,q2_name,q3_name,q4_name,color,username,path;
    @FXML
    private Button savechangeBtn,browserBtn;
    @FXML
    private Label year_lb;
    @FXML
    private DatePicker s_q1,e_q1,s_q2,e_q2,s_q3,e_q3,s_q4,e_q4;
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
        color.setText(a.getColor());
        path.setText(a.getPath());
        listten_change(s_q1);
        listten_change(e_q1);
        listten_change(s_q2);
        listten_change(e_q2);
        listten_change(s_q3);
        listten_change(e_q3);
        listten_change(s_q4);
        listten_change(e_q4);
        tf_listten_change(username);
        tf_listten_change(color);
        tf_listten_change(path);
    }
    private void initCurrentQuarterToField(String pre_year){

        List<Quarter> preQuarter  = quarterService.findAllByYear(pre_year);
        if(preQuarter.isEmpty()){
            clearField();
        }else{
            q1_name.setText(preQuarter.get(0).getName());
            q2_name.setText(preQuarter.get(1).getName());
            q3_name.setText(preQuarter.get(2).getName());
            q4_name.setText(preQuarter.get(3).getName());

            setDateToDatePicker(s_q1,e_q1,preQuarter.get(0));
            setDateToDatePicker(s_q2,e_q2,preQuarter.get(1));
            setDateToDatePicker(s_q3,e_q3,preQuarter.get(2));
            setDateToDatePicker(s_q4,e_q4,preQuarter.get(3));
        }
    }

    private void setDateToDatePicker(DatePicker s,DatePicker e, Quarter q){
        ComponentUtil.createDatePicker(s, q.getStart_date(), "dd-MM-yyyy");
        ComponentUtil.createDatePicker(e, q.getEnd_date(), "dd-MM-yyyy");
    }

    private void clearField(){
        q1_name.clear();
        q2_name.clear();
        q3_name.clear();
        q4_name.clear();
        s_q1.setValue(null);
        e_q1.setValue(null);
        s_q2.setValue(null);
        e_q2.setValue(null);
        s_q3.setValue(null);
        e_q3.setValue(null);
        s_q4.setValue(null);
        e_q4.setValue(null);
    }

    @FXML
    public void save_Changed(ActionEvent actionEvent) {
        if (!isEmptyField(q1_name) && !isEmptyField(q2_name) && !isEmptyField(q3_name) && !isEmptyField(q4_name)) {
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
    private void saveUser(){
        Accounts a = accountService.findAccountByUsername(ConnectLan.pre_acc.getUsername());
        if (DashboardController.o_path!=null){
            a.setPath(DashboardController.o_path);
            a.setColor(color.getText());
            accountService.save(a);
        }
    }
    private void saveQuarter(){
        Quarter q1= quarterService.findByUnique(year_lb.getText(),1).orElse(null);
        Quarter q2= quarterService.findByUnique(year_lb.getText(),2).orElse(null);
        Quarter q3= quarterService.findByUnique(year_lb.getText(),3).orElse(null);
        Quarter q4= quarterService.findByUnique(year_lb.getText(),4).orElse(null);
        if (q1==null){
            quarterService.save(new Quarter(q1_name.getText(), s_q1.getValue(),e_q1.getValue(),String.valueOf(Year.now().getValue()), 1));
        }if (q2==null){
            quarterService.save(new Quarter(q2_name.getText(), s_q2.getValue(),e_q2.getValue(),String.valueOf(Year.now().getValue()), 2));
        }if (q3==null){
            quarterService.save(new Quarter(q3_name.getText(), s_q3.getValue(),e_q3.getValue(),String.valueOf(Year.now().getValue()), 3));
        }if (q4==null){
            quarterService.save(new Quarter(q4_name.getText(), s_q4.getValue(),e_q4.getValue(),String.valueOf(Year.now().getValue()), 4));
        }if (q1!=null){
            quarterService.save(new Quarter(q1.getId(),q1_name.getText(), s_q1.getValue(),e_q1.getValue(),String.valueOf(Year.now().getValue()), 1));
        }if (q2!=null){
            quarterService.save(new Quarter(q2.getId(), q2_name.getText(), s_q2.getValue(),e_q2.getValue(),String.valueOf(Year.now().getValue()), 2));
        }if (q3!=null){
            quarterService.save(new Quarter(q3.getId(), q3_name.getText(), s_q3.getValue(),e_q3.getValue(),String.valueOf(Year.now().getValue()), 3));
        }if (q4!=null){
            quarterService.save(new Quarter(q4.getId(), q4_name.getText(), s_q4.getValue(),e_q4.getValue(),String.valueOf(Year.now().getValue()), 4));
        }
    }

    private void setSelectDirectory(Stage primaryStage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Directory");

        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            path.setText(selectedDirectory.getAbsolutePath());
            DashboardController.o_path= selectedDirectory.getAbsolutePath();
        } else {
            DashboardController.o_path = null;
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
        if (isFutureDate(s_q1.getValue(), e_q1.getValue())){
            setErrorStyle(s_q1,e_q1);
            return false;
        } else if (isFutureDate(e_q1.getValue(), s_q2.getValue())) {
            setErrorStyle(e_q1,s_q2);
            return false;
        }else if (isFutureDate(s_q2.getValue(), e_q2.getValue())) {
            setErrorStyle(s_q2,e_q2);
            return false;
        }else if (isFutureDate(e_q2.getValue(), s_q3.getValue())) {
            setErrorStyle(e_q2,s_q3);
            return false;
        }else if (isFutureDate(s_q3.getValue(), e_q3.getValue())) {
            setErrorStyle(e_q3,s_q3);
            return false;
        }else if (isFutureDate(e_q3.getValue(), s_q4.getValue())) {
            setErrorStyle(e_q3,s_q4);
            return false;
        }else if (isFutureDate(s_q4.getValue(), e_q4.getValue())) {
            setErrorStyle(e_q4,s_q4);
            return false;
        }
        resetErrorStyle(s_q1,e_q1,s_q2,e_q2);
        resetErrorStyle(s_q3,e_q3,s_q4,e_q4);
        return true;
    }
    private void setErrorStyle(DatePicker l1, DatePicker l2){
        l1.setStyle(set_style);
        l2.setStyle(set_style);
    }
    private void resetErrorStyle(DatePicker l1, DatePicker l2,DatePicker l3, DatePicker l4){
        l1.setStyle(null);
        l2.setStyle(null);
        l3.setStyle(null);
        l4.setStyle(null);
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
