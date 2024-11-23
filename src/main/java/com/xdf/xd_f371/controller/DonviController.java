package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.TcnRepo;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
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
    public static NguonNx selectedUnit;
    @FXML
    private TableView<NguonNx> tb_unit;
    @FXML
    private TableView<Tcn> tb_property;
    @FXML
        private TableColumn<NguonNx, String> col_name_unit,col_create_time,col_affilated,col_loaiphieu;
    @FXML
    private TableColumn<Tcn, String> col_property_name,col_property_btype  ,col_property_status;

    private NguonNXService nguonNXService = new NguonNXImp();
    @Autowired
    private NguonNxRepo nguonNxRepo;
    @Autowired
    private TcnRepo tcnRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        selectedUnit = new NguonNx();
        fillDataForTable_tcn();
        fillDataForTable_nguonnx();
    }

    private void fillDataForTable_nguonnx(){
        tb_unit.setItems(FXCollections.observableList(nguonNXService.getAllAndNguonnx()));
        setFactoryCell_for_Nguonnx();
    }

    private void fillDataForTable_tcn(){
        tb_property.setItems(FXCollections.observableList(tcnRepo.findAll()));
        setFactoryCell_for_Tcn();
    }

    private void setFactoryCell_for_Tcn() {
        col_property_btype.setCellValueFactory(new PropertyValueFactory<Tcn, String>("lp"));
        col_property_name.setCellValueFactory(new PropertyValueFactory<Tcn, String>("name"));
        col_property_status.setCellValueFactory(new PropertyValueFactory<Tcn, String>("status"));
    }

    private void setFactoryCell_for_Nguonnx() {
        col_name_unit.setCellValueFactory(new PropertyValueFactory<NguonNx, String>("ten"));
        col_create_time.setCellValueFactory(new PropertyValueFactory<NguonNx, String>("createtime"));
        col_affilated.setCellValueFactory(new PropertyValueFactory<NguonNx, String>("tructhuoc"));
        col_loaiphieu.setCellValueFactory(new PropertyValueFactory<NguonNx, String>("loaiphieu"));
    }

    private void showUnitsDetailScreen() throws IOException {
        selectedUnit = tb_unit.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("../unit_detail.fxml"));
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
    public void addUnitAction(ActionEvent actionEvent) throws IOException{
        selectedUnit = tb_unit.getSelectionModel().getSelectedItem();
        Parent root = FXMLLoader.load(getClass().getResource("../add_unit.fxml"));
        Scene scene = new Scene(root);
        unit_stage = new Stage();
        unit_stage.setScene(scene);
        unit_stage.initStyle(StageStyle.DECORATED);
        unit_stage.initModality(Modality.APPLICATION_MODAL);
        unit_stage.setTitle("Thêm mới");
        unit_stage.showAndWait();
        fillDataForTable_nguonnx();
        tb_unit.refresh();
    }

    @FXML
    public void editUnitAction(ActionEvent actionEvent) {
    }

    @FXML
    public void deleteUnitAction(ActionEvent actionEvent) {
        NguonNx nguonNx = tb_unit.getSelectionModel().getSelectedItem();
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
