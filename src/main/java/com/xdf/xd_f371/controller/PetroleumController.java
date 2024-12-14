package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.service.LoaiXdService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class PetroleumController implements Initializable {

    @FXML
    TableView<LoaiXangDauDto> petroleum_table;
    @FXML
    TableColumn<LoaiXangDauDto, String> maxd_col,tenxd_col,type_col,loai,tinhchat,status_col;
    @FXML
    TextField petro_search;

    @Autowired
    private LoaiXdService loaiXdService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataToTable();
    }

    private void fillDataToTable() {
        petroleum_table.setItems(FXCollections.observableList(loaiXdService.findAllBy()));
        setFatoryForPetroTb();
    }
    private void setFatoryForPetroTb(){
        maxd_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDauDto, String>("maxd"));
        tenxd_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDauDto, String>("tenxd"));
        type_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDauDto, String>("chungloai"));
        loai.setCellValueFactory(new PropertyValueFactory<LoaiXangDauDto, String>("loai"));
        tinhchat.setCellValueFactory(new PropertyValueFactory<LoaiXangDauDto, String>("tinhchat"));
        status_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDauDto, String>("status"));
    }

    @FXML
    public void addAction(ActionEvent actionEvent) {

    }
    @FXML
    public void editAction(ActionEvent actionEvent) {
    }
    @FXML
    public void delAction(ActionEvent actionEvent) {
    }
}
