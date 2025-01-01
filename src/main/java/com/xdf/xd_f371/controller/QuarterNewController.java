package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
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
    private TableView<Quarter> quy_tb;
    @FXML
    private TableColumn<Quarter,String> stt,quy,s,e;
    @Autowired
    private QuarterService quarterService;
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
        s.setCellValueFactory(new PropertyValueFactory<Quarter, String>("s"));
        e.setCellValueFactory(new PropertyValueFactory<Quarter, String>("e"));
    }

    private void initY() {
        year_cbb.setItems(FXCollections.observableList(quarterService.getAllYear()));
        year_cbb.getSelectionModel().selectFirst();
    }

    @FXML
    public void edAction(ActionEvent actionEvent) {
    }
    @FXML
    public void add(ActionEvent actionEvent) {
        if (!q_name.getText().trim().isEmpty()) {
            if (sd.getValue()!=null && ed.getValue()==null){
                ls.add(new Quarter(sd.getValue(),ed.getValue(),String.valueOf(year_cbb.getSelectionModel().getSelectedItem()), q_name.getText()));
                quy_tb.setItems(FXCollections.observableList(ls));
            }else{
                DialogMessage.errorShowing("ngay bat dau va ket thuc khong duoc de trong");
            }
        }else{
            q_name.setStyle(CommonFactory.styleErrorField);
        }
    }
    @FXML
    public void save(ActionEvent actionEvent) {
        if (!ls.isEmpty()){
            for (Quarter l : ls) {
                quarterService.save(l);
            }
            DialogMessage.successShowing("success");
            DashboardController.primaryStage.close();
        }
    }
    @FXML
    public void exit(ActionEvent actionEvent) {
        DashboardController.primaryStage.close();
    }
    @FXML
    public void yearAction(ActionEvent actionEvent) {
        initQuarterTb();
    }
}
