package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.TructhuocRepo;
import com.xdf.xd_f371.service.NguonNXService;
import com.xdf.xd_f371.service.TrucThuocService;
import com.xdf.xd_f371.service.impl.NguonNXImp;
import com.xdf.xd_f371.service.impl.TrucThuocImp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class BaoCaoController implements Initializable {

    @FXML
    private TableView<NguonNx> nguonnx_tb;
    @FXML
    private TableView<TrucThuoc> tructhuoc_tb;
    @FXML
    private TableView<GroupTitle> group_tb;
    @FXML
    private TableView<NguonnxTitle> group_title_tb;


    @FXML
    private TableColumn<NguonNx, String> col_nnx_id, col_nnx_ten;
    @FXML
    private TableColumn<TrucThuoc, String> col_tt_id, col_tt_name,col_tt_type;
    @FXML
    private TableColumn<GroupTitle, String> col_gr_id, col_gr_name;
    @FXML
    private TableColumn<NguonnxTitle, String> col_group_id, col_group_ttid,col_group_nguonnxId,col_group_tructhuocId;

    private NguonNXService nguonNXService = new NguonNXImp();
    @Autowired
    private TructhuocRepo tructhuocRepo;
    @Autowired
    private NguonNxRepo nguonNxRepo;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataToTbTructhuoc();
        fillDataToTbNguonnx();
        fillDataToTbGroup();
    }

    private void fillDataToTbNguonxTitle(int group_id) {
        group_title_tb.setItems(FXCollections.observableList(nguonNXService.getAllNnxTitles(group_id)));
        col_group_id.setCellValueFactory(new PropertyValueFactory<NguonnxTitle, String>("id"));
        col_group_nguonnxId.setCellValueFactory(new PropertyValueFactory<NguonnxTitle, String>("nguonnx_id"));
        col_group_tructhuocId.setCellValueFactory(new PropertyValueFactory<NguonnxTitle, String>("title_id"));
        col_group_ttid.setCellValueFactory(new PropertyValueFactory<NguonnxTitle, String>("group_id"));
    }

    private void fillDataToTbGroup() {
        group_tb.setItems(FXCollections.observableList(nguonNXService.getAllGroup()));
        col_gr_id.setCellValueFactory(new PropertyValueFactory<GroupTitle, String>("id"));
        col_gr_name.setCellValueFactory(new PropertyValueFactory<GroupTitle, String>("groupName"));
    }

    private void fillDataToTbTructhuoc() {
        tructhuoc_tb.setItems(FXCollections.observableList(tructhuocRepo.findAll()));
        col_tt_id.setCellValueFactory(new PropertyValueFactory<TrucThuoc, String>("id"));
        col_tt_name.setCellValueFactory(new PropertyValueFactory<TrucThuoc, String>("name"));
        col_tt_type.setCellValueFactory(new PropertyValueFactory<TrucThuoc, String>("type"));
    }

    private void fillDataToTbNguonnx() {
        nguonnx_tb.setItems(FXCollections.observableList(nguonNxRepo.findAll()));
        col_nnx_id.setCellValueFactory(new PropertyValueFactory<NguonNx, String>("id"));
        col_nnx_ten.setCellValueFactory(new PropertyValueFactory<NguonNx, String>("ten"));
    }

    public void nguonnx_selected(MouseEvent mouseEvent) {
    }

    public void tructhuoc_selected(MouseEvent mouseEvent) {
    }

    public void groupTtSeleected(MouseEvent mouseEvent) {
        fillDataToTbNguonxTitle(group_tb.getSelectionModel().getSelectedItem().getId());
    }

    public void addNewGroupTitle(ActionEvent actionEvent) {

    }
}
