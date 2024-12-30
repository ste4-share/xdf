package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
@Component
public class QuarterController implements Initializable {
    @FXML
    private Button addNewBtn,cancelBtn;
    @FXML
    private TextField quy;
    @FXML
    private DatePicker s_time,e_time;
    @FXML
    private Label pre_quarter;
    @Autowired
    private QuarterService quarterService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setPrevioutDate();
    }
    private void setPrevioutDate(){
        Optional<Quarter> quarter = quarterService.findPreviousTime();
        if (quarter.isPresent()){
            pre_quarter.setText("Quý trước: " + quarter.get().getStart_date().toString() +  " ----> " + quarter.get().getEnd_date().toString());
        }else{
            pre_quarter.setText(null);
        }
    }
    @FXML
    public void quyClicked(MouseEvent mouseEvent) {
        quy.setStyle(null);
        quy.selectAll();
    }
    @FXML
    public void addNew(ActionEvent actionEvent) {
        if (DialogMessage.callAlert()== ButtonType.OK){
            if (isValid()) {
                try {
                    quarterService.save(new Quarter(s_time.getValue(),e_time.getValue(),String.valueOf(e_time.getValue().getYear()),quy.getText()));
                    ConnectLan.primaryStage = new Stage();
                    Common.openNewStage("dashboard2.fxml", ConnectLan.primaryStage,"XĂNG DẦU F371", StageStyle.DECORATED);
                    ConnectLan.primaryStage2.close();
                } catch (DataIntegrityViolationException e) {
                    // Handle duplicate unique key exception
                    quy.setStyle(CommonFactory.styleErrorField);
                    DialogMessage.errorShowing("Tên quý đã tồn tại trong năm, vui lòng thử lại");
                }
            }
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        ConnectLan.primaryStage2.close();
        Platform.exit(); // Cleanly stop the JavaFX thread
        System.exit(0);  // Ensure JVM termination
    }
    @FXML
    public void e_timeAction(ActionEvent actionEvent) {
        s_time.setStyle(null);
        e_time.setStyle(null);
        isValid();
    }
    private boolean isValid(){
        if (s_time.getValue()==null){
            s_time.setStyle(CommonFactory.styleErrorField);
        }else{
            if (e_time.getValue()==null){
                e_time.setStyle(CommonFactory.styleErrorField);
            }else {
                if (e_time.getValue().isAfter(s_time.getValue())){
                    if (s_time.getValue().isBefore(LocalDate.now())){
                        if (e_time.getValue().isAfter(LocalDate.now())|| Objects.equals(e_time.getValue(), LocalDate.now())){
                            if (quy.getText().trim().isEmpty()){
                                quy.setStyle(CommonFactory.styleErrorField);
                            }else {
                                return true;
                            }
                        }else {
                            e_time.setStyle(CommonFactory.styleErrorField);
                            DialogMessage.errorShowing("Ngày hiện tại không nằm Giữa ngày bắt đầu và ngày kết thúc.");
                        }
                    }else{
                        s_time.setStyle(CommonFactory.styleErrorField);
                        DialogMessage.errorShowing("Ngày hiện tại không nằm Giữa ngày bắt đầu và ngày kết thúc.");
                    }
                }else {
                    DialogMessage.errorShowing("Ngày bắt đầu < ngày kết thúc.");
                    e_time.setStyle(CommonFactory.styleErrorField);
                }
            }
        }
        return false;
    }
}
