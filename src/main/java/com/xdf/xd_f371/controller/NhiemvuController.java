package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.QuarterService;
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

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class NhiemvuController implements Initializable {
    public static Stage nvStage;
    public static HanmucNhiemvuTaubayDto hm = new HanmucNhiemvuTaubayDto();
    public static HanmucNhiemvu2Dto hm2 = new HanmucNhiemvu2Dto();
    @Autowired
    private QuarterService quarterService;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private HanmucNhiemvuService hanmucNhiemvuService;
    @FXML
    TableView<HanmucNhiemvu2Dto> tieuthunhiemvu;
    @FXML
    private Button add_btn_ctb;
    @FXML
    TableView<HanmucNhiemvuTaubayDto> ctnv_pt;
    @FXML
    TabPane tab;
    @FXML
    TableColumn<HanmucNhiemvuTaubayDto, String> stt_2,dvi_x,t2_pt,t2_nv_2,ct_nv_2,t2_tk_2,t2_md_2,t2_nl_2;
    @FXML
    TableColumn<HanmucNhiemvu2Dto, String> nv,ct,xang,diezel,daubay,stt_3;
    @FXML
    ComboBox<Quarter> quy_cbb;
    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @FXML
    TableView<NhiemVuDto> nv_tb;
    @FXML
    TableColumn<NhiemVuDto, String> tennv, ctnv,lnv, khoi,stt_1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setScreen();
        initQuarterCbb();
        initDviTb();
        initNvTable();
        initNhiemvuTaubay();
        initHanmuc();

        inithanmucCellFactory();
        initNvCellFactory();
        initHanmucNhiemvuTaubayCellFactory();
    }
    @FXML
    public void nhiemvu_selected(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
        }
    }
    @FXML
    public void addhanmucxangdau(ActionEvent actionEvent) {
        nvStage = new Stage();
        nvStage.initStyle(StageStyle.UTILITY);
        Common.openNewStage("addnew_nvhanmuc.fxml", nvStage,"HANMUC");
        initHanmuc();
    }
    @FXML
    public void dviSelected(ActionEvent actionEvent) {
        if (dvi_cbb.getSelectionModel().getSelectedItem()!=null) {
        }
    }
    @FXML
    public void chitieunvSelected(MouseEvent mouseEvent) {
        hm = ctnv_pt.getSelectionModel().getSelectedItem();
        if (hm!=null){
            openAddChitieuForm();
        }
    }
    @FXML
    public void addchitieuBay(ActionEvent actionEvent) {
        hm = null;
        openAddChitieuForm();
    }
    @FXML
    public void addNvAction(ActionEvent actionEvent) {
        nvStage = new Stage();
        nvStage.initStyle(StageStyle.UTILITY);
        Common.openNewStage("add_nv.fxml", nvStage,null);
        initNvTable();
    }
    private void openAddChitieuForm(){
        nvStage = new Stage();
        nvStage.initStyle(StageStyle.UTILITY);
        Common.openNewStage("add_chitieunv.fxml", nvStage,null);
        initNhiemvuTaubay();
    }
    private void initNvTable(){
        nv_tb.setItems(FXCollections.observableList(chitietNhiemvuService.findAllBy()));
    }
    private void initNhiemvuTaubay(){
        ctnv_pt.setItems(FXCollections.observableList(hanmucNhiemvuService.getAllBy()));
    }
    private void initHanmuc(){
        tieuthunhiemvu.setItems(FXCollections.observableList(hanmucNhiemvuService.findAllDto()));
    }
    private void initDviTb() {
        ComponentUtil.setItemsToComboBox(dvi_cbb,nguonNxService.findByAllBy(),NguonNx::getTen,input->nguonNxService.findByTen(input).orElse(null));
        dvi_cbb.getSelectionModel().selectFirst();
    }
    private void initQuarterCbb(){
        ComponentUtil.setItemsToComboBox(quy_cbb,quarterService.findAll(),Quarter::getName,input->quarterService.findByName(input).orElse(null));
        quy_cbb.getSelectionModel().selectFirst();
    }
    private void initNvCellFactory() {
        stt_1.setSortable(false);
        stt_1.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(nv_tb.getItems().indexOf(column.getValue())+1).asString());
        tennv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_nv"));
        ctnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("chitiet"));
        lnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_loai_nv"));
        khoi.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("khoi"));
    }
    private void inithanmucCellFactory() {
        stt_3.setSortable(false);
        stt_3.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tieuthunhiemvu.getItems().indexOf(column.getValue())+1).asString());
        nv.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("tenNv"));
        ct.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("chitiet_nhiemvu"));
        xang.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("xang"));
        diezel.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("diezel"));
        daubay.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("daubay"));
    }
    private void initHanmucNhiemvuTaubayCellFactory() {
        stt_2.setSortable(false);
        stt_2.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(ctnv_pt.getItems().indexOf(column.getValue())+1).asString());
        dvi_x.setCellValueFactory(new PropertyValueFactory<>("donvi"));
        t2_pt.setCellValueFactory(new PropertyValueFactory<>("tenpt"));
        t2_nv_2.setCellValueFactory(new PropertyValueFactory<>("nhiemvu"));
        ct_nv_2.setCellValueFactory(new PropertyValueFactory<>("ct_nhiemvu"));
        t2_tk_2.setCellValueFactory(new PropertyValueFactory<>("tk"));
        t2_md_2.setCellValueFactory(new PropertyValueFactory<>("md"));
        t2_nl_2.setCellValueFactory(new PropertyValueFactory<>("nhienlieu_str"));
    }
    private void setScreen(){
        ctnv_pt.setPrefWidth(DashboardController.screenWidth);
        ctnv_pt.setPrefHeight(DashboardController.screenHeigh-350);
        nv_tb.setPrefWidth(DashboardController.screenWidth);
        nv_tb.setPrefHeight(DashboardController.screenHeigh-350);
        tieuthunhiemvu.setPrefWidth(DashboardController.screenWidth);
        tieuthunhiemvu.setPrefHeight(DashboardController.screenHeigh-350);
    }

}
