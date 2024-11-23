package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.model.ChungloaiMap;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.service.impl.LoaiXdImp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PetroleumController implements Initializable {

    @FXML
    TableView<LoaiXangDau> petroleum_table;
    @FXML
    TableColumn<LoaiXangDau, String> maxd_col,tenxd_col,type_col,status_col;
    @FXML
    ComboBox<String> chungloai_cbb;
    @FXML
    TextField petro_search;

    private LoaiXdService loaiXdService = new LoaiXdImp();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillTypePetroToCbb();
    }


    private void fillTypePetroToCbb() {
        List<String> str_ls = new ArrayList<>();
        loaiXdService.getAllType().forEach(x -> str_ls.add(x+" | "+ChungloaiMap.getMapChungloai().get(x)));

        chungloai_cbb.setItems(FXCollections.observableList(str_ls));
        chungloai_cbb.setConverter(new StringConverter<String>() {
            @Override
            public String toString(String s) {
                return s.substring(s.indexOf("|")+1).trim();
            }

            @Override
            public String fromString(String s) {
                return s.substring(0, s.indexOf("|")-1).trim();
            }
        });
        chungloai_cbb.getSelectionModel().selectFirst();
        String cl = chungloai_cbb.getSelectionModel().getSelectedItem();
        String param = cl.substring(0, cl.indexOf("|")-1).trim();
        fillDataToTable(param);
    }
    private void fillDataToTable(String chungloai) {
        petroleum_table.setItems(FXCollections.observableList(loaiXdService.findLoaiXdByChungloai(chungloai)));
        setFatoryForPetroTb();
    }
    private void setFatoryForPetroTb(){
        maxd_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDau, String>("maxd"));
        tenxd_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDau, String>("tenxd"));
        type_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDau, String>("type"));
        status_col.setCellValueFactory(new PropertyValueFactory<LoaiXangDau, String>("status"));
    }
    @FXML
    public void selectChungLoaiAction(ActionEvent actionEvent) {
        String cl = chungloai_cbb.getSelectionModel().getSelectedItem();
        String param = cl.substring(0, cl.indexOf("|")-1).trim();
        fillDataToTable(param);
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
