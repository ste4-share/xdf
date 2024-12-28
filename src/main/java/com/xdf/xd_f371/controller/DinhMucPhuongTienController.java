package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.model.LoaiPTEnum;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DinhMucPhuongTienController implements Initializable {
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
    TableColumn<DinhMucPhuongTienDto, String> stt,xmt_name,type_name,quantity,km,h,md,tk,createtime,tructhuoc;

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
        initNguonnxCbb();
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        Common.hoverButton(addBtn, "#ffffff");
    }
    private void initNguonnxCbb() {
        ComponentUtil.setItemsToComboBox(units_cbb,nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()),NguonNx::getTen,input-> nguonNxService.findByTen(input).orElse(null));
        units_cbb.getSelectionModel().selectLast();
    }
    private void fillDatatoptTable(String lpt) {
        NguonNx dvi = units_cbb.getSelectionModel().getSelectedItem();
        if (dvi!=null){
            pt_tb.setItems(FXCollections.observableList(dinhmucService.findAllBy(DashboardController.findByTime.getId(), lpt)));
        }
    }
    private void setfactoryForTable(){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(pt_tb.getItems().indexOf(column.getValue())+1).asString());
        xmt_name.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("name_pt"));
        type_name.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("typeName"));
        quantity.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("quantity"));
        tructhuoc.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("nameDv"));
        km.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_xm_km"));
        h.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_xm_gio"));
        md.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_md_gio"));
        tk.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_tk_gio"));
    }
    @FXML
    public void selectUnit(ActionEvent actionEvent) {
        if (xe_radio.isSelected()){
            fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        }else if (may_radio.isSelected()){
            fillDatatoptTable(LoaiPTEnum.MAY.getNameVehicle());
        }else if (mb_radio.isSelected()){
            fillDatatoptTable(LoaiPTEnum.MAYBAY.getNameVehicle());
        }
    }
    @FXML
    public void addNewPt(ActionEvent actionEvent) throws IOException {
        dinhMucPhuongTienDto = new DinhMucPhuongTienDto();
        dinhMucPhuongTienDto.setDm_md_gio(0);
        dinhMucPhuongTienDto.setDm_tk_gio(0);
        dinhMucPhuongTienDto.setDm_xm_gio(0);
        dinhMucPhuongTienDto.setDm_xm_km(0);
        dinhMucPhuongTienDto.setPhuongtien_id(0);
        dinhMucPhuongTienDto.setQuantity(0);
        dinhMucPhuongTienDto.setNnx_id(units_cbb.getSelectionModel().getSelectedItem().getId());
        openAddScreen();
        fillDatatoptTable(dinhMucPhuongTienDto.getType());
    }
    @FXML
    public void pt_selected(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            dinhMucPhuongTienDto = pt_tb.getSelectionModel().getSelectedItem();
            if (dinhMucPhuongTienDto!=null){
                openAddScreen();
                fillDatatoptTable(dinhMucPhuongTienDto.getType());
            }
        }
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

    private void openAddScreen(){
        norm_stage = new Stage();
        Common.openNewStage("add_pt.fxml",norm_stage,"Thêm phương tiện", StageStyle.DECORATED);
    }
}
