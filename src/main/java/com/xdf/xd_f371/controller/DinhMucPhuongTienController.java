package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.XmtDto;
import com.xdf.xd_f371.cons.LoaiPTEnum;
import com.xdf.xd_f371.entity.UnitXmt;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.service.UnitXmtService;
import com.xdf.xd_f371.util.Common;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DinhMucPhuongTienController implements Initializable {
    public static Stage norm_stage;
    public static XmtDto xmtDto = new XmtDto();
    public static List<XmtDto> ls = new ArrayList<>();
    @FXML
    TableView<XmtDto> pt_tb;
    @FXML
    private Button addBtn;
    @FXML
    private TableView<UnitXmt> unit_xmt;
    @FXML
    private Label dv_lable;
    @FXML
    private TableColumn<UnitXmt,String> xmt_unit_stt,xmt_unit_soxe,xmt_unit_km,xmt_unit_gio,xmt_unit_md,xmt_unit_tk,xmt_unit_status,xmt_unit_id;
    @FXML
    private ComboBox<Integer> year_cbb;
    @FXML
    RadioButton xe_radio,may_radio,mb_radio;
    @FXML
    TextField search_tf;
    @FXML
    private CheckBox tdvChk;
    @FXML
    TableColumn<XmtDto, String> stt,xmt_name,type_name,quantity,xmtid;

    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private UnitXmtService unitXmtService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pt_tb.setPrefWidth(DashboardController.screenWidth-800);
        pt_tb.setPrefHeight(DashboardController.screenHeigh-300);
        unit_xmt.setPrefWidth(DashboardController.screenWidth-800);
        unit_xmt.setPrefHeight(DashboardController.screenHeigh-300);
        xe_radio.setSelected(true);
        dv_lable.setText(DashboardController.ref_Dv.getTen());
        setfactoryForTable();
        setfactoryFor_UnitXmt();
        initYearCbb();

        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        Common.hoverButton(addBtn, "#ffffff");
    }

    private void setItemsFor_unitxmt(List<UnitXmt> ls){
        unit_xmt.setItems(FXCollections.observableList(ls));
        unit_xmt.refresh();
    }
    private void initYearCbb() {
        year_cbb.setItems(FXCollections.observableList(dinhmucService.findAllYear()));
        year_cbb.getSelectionModel().selectFirst();
    }
    private void fillDatatoptTable(String lpt) {
        if (tdvChk.isSelected()){
            ls = phuongtienService.findAllXmtByType(lpt);
            setItemsToTable(ls);
        }else{
            ls = phuongtienService.findXmtByType(lpt,DashboardController.ref_Dv.getId());
            setItemsToTable(ls);
        }
    }
    private void setItemsToTable(List<XmtDto> ls){
        if (!ls.isEmpty()){
            pt_tb.setItems(FXCollections.observableList(ls));
        }
    }
    private void mapPtTb(List<XmtDto> ls){
        pt_tb.setItems(FXCollections.observableList(ls));
        pt_tb.refresh();
    }

    private void setfactoryForTable(){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(pt_tb.getItems().indexOf(column.getValue())+1).asString());
        xmt_name.setCellValueFactory(new PropertyValueFactory<XmtDto, String>("name"));
        xmtid.setCellValueFactory(new PropertyValueFactory<XmtDto, String>("xmtId"));
        type_name.setCellValueFactory(new PropertyValueFactory<XmtDto, String>("loaiXmt"));
        quantity.setCellValueFactory(new PropertyValueFactory<XmtDto, String>("quantity"));
    }
    private void setfactoryFor_UnitXmt(){
        xmt_unit_stt.setSortable(false);
        xmt_unit_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(unit_xmt.getItems().indexOf(column.getValue())+1).asString());
        xmt_unit_soxe.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("licence_plate_number"));
        xmt_unit_id.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("id"));
        xmt_unit_km.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("dm_km"));
        xmt_unit_gio.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("dm_hours"));
        xmt_unit_md.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("dm_md"));
        xmt_unit_tk.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("dm_tk"));
        xmt_unit_status.setCellValueFactory(new PropertyValueFactory<UnitXmt, String>("status"));
    }

    private void selectUnitByType() {
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
        openAddScreen();
    }
    @FXML
    public void pt_selected(MouseEvent mouseEvent) throws IOException {
        xmtDto = pt_tb.getSelectionModel().getSelectedItem();
        if (xmtDto!=null){
            List<UnitXmt> ls = unitXmtService.findByUnitIdAndPtId(DashboardController.ref_Dv.getId(), xmtDto.getXmtId());
            setItemsFor_unitxmt(ls);
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
    @FXML
    public void yearAction(ActionEvent actionEvent) {
        xe_radio.setSelected(true);
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
    }
    @FXML
    public void searchKr(KeyEvent keyEvent) {
        String t = search_tf.getText().trim();
        if (!t.isEmpty()){
            mapPtTb(ls.stream().filter(x->x.getName().toLowerCase().contains(t)).toList());
        }else{
            mapPtTb(ls);
        }
    }
    @FXML
    public void searchClicked(MouseEvent mouseEvent) {
        search_tf.selectAll();
    }
    @FXML
    public void tdvChkAction(ActionEvent actionEvent) {
        if (tdvChk.isSelected()){
            selectUnitByType();
        }else{
            selectUnitByType();
        }
    }
}
