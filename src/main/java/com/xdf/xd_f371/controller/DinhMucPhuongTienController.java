package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.dto.NormDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.model.LoaiPTEnum;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DinhMucPhuongTienController implements Initializable {
    public static int nguonnx_id;
    public static Stage norm_stage;
    public static DinhMucPhuongTienDto dinhMucPhuongTienDto = new DinhMucPhuongTienDto();

    @FXML
    TableView<DinhMucPhuongTienDto> pt_tb;
    @FXML
    private Button addBtn;
    @FXML
    ComboBox<NguonNx> units_cbb;
    @FXML
    RadioButton xe_radio,may_radio,mb_radio;
    @FXML
    TableColumn<NormDto, String> xmt_name,type_name,quantity,km,h,md,tk,createtime,chungloaipt;


    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private NguonNxService nguonNxService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pt_tb.setPrefWidth(DashboardController.screenWidth);
        pt_tb.setPrefHeight(DashboardController.screenHeigh-300);
        xe_radio.setSelected(true);
        setfactoryForTable();
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        initNguonnxCbb();
        Common.hoverButton(addBtn, "#ffffff");
    }
    private void initNguonnxCbb() {
        ComponentUtil.setItemsToComboBox(units_cbb,nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()),NguonNx::getTen,input-> nguonNxService.findByTen(input).orElse(null));
        units_cbb.getSelectionModel().selectFirst();
    }
    private void fillDatatoptTable(String lpt) {
        pt_tb.setItems(FXCollections.observableList(dinhmucService.findAllBy(DashboardController.findByTime.getId(), lpt)));
    }
    private void setfactoryForTable(){
        xmt_name.setCellValueFactory(new PropertyValueFactory<NormDto, String>("name_pt"));
        type_name.setCellValueFactory(new PropertyValueFactory<NormDto, String>("typeName"));
        quantity.setCellValueFactory(new PropertyValueFactory<NormDto, String>("quantity"));
        chungloaipt.setCellValueFactory(new PropertyValueFactory<NormDto, String>("type"));
        km.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_xm_km"));
        h.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_xm_gio"));
        md.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_md_gio"));
        tk.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_tk_gio"));
    }
    public void selectUnit(ActionEvent actionEvent) {
    }
    @FXML
    public void addNewPt(ActionEvent actionEvent) throws IOException {
        dinhMucPhuongTienDto = new DinhMucPhuongTienDto();
        dinhMucPhuongTienDto.setDm_md_gio(0);
        dinhMucPhuongTienDto.setDm_tk_gio(0);
        dinhMucPhuongTienDto.setDm_xm_gio(0);
        dinhMucPhuongTienDto.setDm_xm_km(0);
        dinhMucPhuongTienDto.setPhuongtien_id(0);
        openAddScreen();
        fillDatatoptTable(dinhMucPhuongTienDto.getType());
    }
    @FXML
    public void xe_selected(ActionEvent actionEvent) {
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
    }
    @FXML
    public void may_selected(ActionEvent actionEvent) {
        fillDatatoptTable(LoaiPTEnum.MAY.getNameVehicle());
    }
    @FXML
    public void maybay_selected(ActionEvent actionEvent) {
        fillDatatoptTable(LoaiPTEnum.MAYBAY.getNameVehicle());
    }
    @FXML
    public void pt_selected(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            dinhMucPhuongTienDto = pt_tb.getSelectionModel().getSelectedItem();
            openAddScreen();
        }
    }
    private void openAddScreen(){
        norm_stage = new Stage();
        Common.openNewStage("add_pt.fxml",norm_stage,"Thêm phương tiện");
    }
}
