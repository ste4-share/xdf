package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class QuarterNewController implements Initializable {
    private static List<Quarter> ls = new ArrayList<>();
    @FXML
    private DatePicker ed,sd;
    @FXML
    private TextField q_name;
    @FXML
    private ComboBox<Integer> year_cbb;
    @FXML
    private CheckBox recordChk;
    @FXML
    private TableView<Quarter> quy_tb;
    @FXML
    private TableColumn<Quarter,String> stt,quy,s,e;
    @Autowired
    private QuarterService quarterService;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initY();
        initQuarterTb();
        initFactory();
    }

    private void initQuarterTb() {
        Integer y = year_cbb.getSelectionModel().getSelectedItem();
        if (y!=null){
            ls = quarterService.findAllByYear(String.valueOf(y));
            quy_tb.setItems(FXCollections.observableList(ls));
        }
    }

    private void initFactory() {
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(quy_tb.getItems().indexOf(column.getValue())+1).asString());
        quy.setCellValueFactory(new PropertyValueFactory<Quarter, String>("index"));
        s.setCellValueFactory(new PropertyValueFactory<Quarter, String>("start_date"));
        e.setCellValueFactory(new PropertyValueFactory<Quarter, String>("end_date"));
    }

    private void initY() {
        List<Integer> ls = quarterService.getAllYear();
        if (ls.isEmpty()){
            year_cbb.setItems(FXCollections.observableList(List.of(LocalDate.now().getYear())));
            year_cbb.getSelectionModel().selectFirst();
        }else{
            year_cbb.setItems(FXCollections.observableList(ls));
            year_cbb.getSelectionModel().selectFirst();
        }
    }

    @FXML
    public void edAction(ActionEvent actionEvent) {
    }
    @FXML
    public void add(ActionEvent actionEvent) {
        if (!q_name.getText().trim().isEmpty()) {
            if (sd.getValue()!=null && ed.getValue()!=null){
                if (DialogMessage.callAlertWithMessage(null,"Them moi", "Xac nhan them moi.", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                    if (year_cbb.getSelectionModel().getSelectedItem()!=null){
                        if (recordChk.isSelected()){
                            inventoryService.changeRecordingRangeTime(new Quarter(sd.getValue(),ed.getValue(),String.valueOf(year_cbb.getSelectionModel().getSelectedItem()),
                                    q_name.getText(), StatusCons.RECORDING.getName()));
                        }else{
                            quarterService.save(new Quarter(sd.getValue(),ed.getValue(),String.valueOf(year_cbb.getSelectionModel().getSelectedItem()),
                                    q_name.getText(), StatusCons.DONE.getName()));
                        }
                    }
                    DialogMessage.successShowing("Them thanh cong");
                    DashboardController.primaryStage.close();
                }
            }else{
                DialogMessage.errorShowing("ngay bat dau va ket thuc khong duoc de trong");
            }
        }else{
            q_name.setStyle(CommonFactory.styleErrorField);
        }
    }
    @FXML
    public void save(ActionEvent actionEvent) {
        Quarter q = quy_tb.getSelectionModel().getSelectedItem();
        if (q!=null){
            if (DialogMessage.callAlertWithMessage(null,"Luu", "Luu thay doi?", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                if (recordChk.isSelected()){
                    q.setStatus(StatusCons.RECORDING.getName());
                    q.setIndex(q_name.getText());
                    q.setStart_date(sd.getValue());
                    q.setEnd_date(ed.getValue());
                    Quarter q1 = quarterService.save(q);
                    inventoryService.changeRecordingRangeTime(q1);
                }else{
                    q.setStatus(StatusCons.DONE.getName());
                    q.setIndex(q_name.getText());
                    q.setStart_date(sd.getValue());
                    q.setEnd_date(ed.getValue());
                    quarterService.save(q);
                }
            }
        }
    }
    @FXML
    public void exit(ActionEvent actionEvent) {
        DashboardController.primaryStage.close();
    }
    @FXML
    public void yearAction(ActionEvent actionEvent) {
        q_name.setText(null);
        sd.setValue(null);
        ed.setValue(null);
        initQuarterTb();
    }
    @FXML
    public void quyClicked(MouseEvent mouseEvent) {
        Quarter q = quy_tb.getSelectionModel().getSelectedItem();
        if (q!=null){
            recordChk.setSelected(q.getStatus().equals(StatusCons.RECORDING.getName()));
            q_name.setText(q.getIndex());
            year_cbb.getSelectionModel().select(Integer.parseInt(q.getYear()));
            sd.setValue(q.getStart_date());
            ed.setValue(q.getEnd_date());
        }
    }
}
