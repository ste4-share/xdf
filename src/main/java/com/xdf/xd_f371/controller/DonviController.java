package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.TructhuocDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.service.TructhuocService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DonviController implements Initializable {

    public static Stage unit_stage;
    public static TructhuocDto selectedUnit;
    @FXML
    private TableView<TructhuocDto> tb_unit;
    @FXML
    private TableView<Tcn> tb_property;
    @FXML
        private TableColumn<TructhuocDto, String> col_name_unit,col_create_time,col_affilated,nhomtructhuoc;
    @FXML
    private TableColumn<Tcn, String> col_property_name,col_property_status;

    @Autowired
    private TcnService tcnService;
    @Autowired
    private TructhuocService tructhuocService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tb_unit.setPrefWidth(DashboardController.screenWidth);
        tb_unit.setPrefHeight(DashboardController.screenHeigh-300);
        tb_property.setPrefWidth(DashboardController.screenWidth);
        tb_property.setPrefHeight(DashboardController.screenHeigh-300);
        selectedUnit = new TructhuocDto();
        fillDataForTable_tcn();
        fillDataForTable_nguonnx();
    }

    private void fillDataForTable_nguonnx(){
        tb_unit.setItems(FXCollections.observableList(tructhuocService.findAllBy().stream().toList()));
        setFactoryCell_for_Nguonnx();
    }

    private void fillDataForTable_tcn(){
        tb_property.setItems(FXCollections.observableList(tcnService.findAll()));
        setFactoryCell_for_Tcn();
    }

    private void setFactoryCell_for_Tcn() {
        col_property_name.setCellValueFactory(new PropertyValueFactory<Tcn, String>("name"));
        col_property_status.setCellValueFactory(new PropertyValueFactory<Tcn, String>("status"));
    }

    private void setFactoryCell_for_Nguonnx() {
        col_name_unit.setCellValueFactory(new PropertyValueFactory<TructhuocDto, String>("nguonnx_name"));
        col_affilated.setCellValueFactory(new PropertyValueFactory<TructhuocDto, String>("tentructhuoc"));
        nhomtructhuoc.setCellValueFactory(new PropertyValueFactory<TructhuocDto, String>("nhomtructhuoc"));
        col_create_time.setCellValueFactory(new PropertyValueFactory<TructhuocDto, String>("timestamp"));
    }

    private void showUnitsDetailScreen() throws IOException {
        selectedUnit = tb_unit.getSelectionModel().getSelectedItem();
        Parent root = (Parent) DashboardController.getNodeBySource("unit_detail.fxml");
        Scene scene = new Scene(root);
        unit_stage = new Stage();
        unit_stage.setScene(scene);
        unit_stage.initStyle(StageStyle.DECORATED);
        unit_stage.initModality(Modality.APPLICATION_MODAL);
        unit_stage.setTitle("Chi tiết");
        unit_stage.showAndWait();
        fillDataForTable_nguonnx();
        tb_unit.refresh();
    }

    @FXML
    public void searchButtonUnit(ActionEvent actionEvent) {
    }


    @FXML
    public void addUnitAction(ActionEvent actionEvent){
        selectedUnit = tb_unit.getSelectionModel().getSelectedItem();
        Common.openNewStage("add_unit.fxml", unit_stage, "Thêm mới");
        fillDataForTable_nguonnx();
        tb_unit.refresh();
    }

    @FXML
    public void editUnitAction(ActionEvent actionEvent) {

    }

    @FXML
    public void deleteUnitAction(ActionEvent actionEvent) {
        TructhuocDto nguonNx = tb_unit.getSelectionModel().getSelectedItem();
        if (nguonNx!=null){
            if (DialogMessage.callAlert()== ButtonType.OK){
                fillDataForTable_nguonnx();
                tb_unit.refresh();
            }
        }
    }

    @FXML
    public void tb_tcn_clicked(MouseEvent mouseEvent) {
    }

    @FXML
    public void addPropertyAction(ActionEvent actionEvent) {
    }

    @FXML
    public void editPropertyAction(ActionEvent actionEvent) {
    }

    @FXML
    public void delPropertyAction(ActionEvent actionEvent) {
    }

    @FXML
    public void unit_clicked(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            showUnitsDetailScreen();
            fillDataForTable_nguonnx();
            tb_unit.refresh();
        }
    }
}
