package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class NhiemvuController implements Initializable {
    public static Stage nvStage;
    public static List<HanmucNhiemvuTaubayDto> hmnv = new ArrayList<>();
    public static HanmucNhiemvuTaubayDto hm = new HanmucNhiemvuTaubayDto();
    public static List<HanmucNhiemvu2Dto> hm2_ls = new ArrayList<>();
    public static HanmucNhiemvu2Dto hm2 = new HanmucNhiemvu2Dto();
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
    TableColumn<HanmucNhiemvu2Dto, String> nv,ct,xang,diezel,daubay,stt_3,cong;

    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @FXML
    ComboBox<Integer> year_cbb;
    @FXML
    TableView<NhiemVuDto> nv_tb;
    @FXML
    TableColumn<NhiemVuDto, String> tennv, ctnv,lnv, khoi,stt_1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setScreen();
        initDviTb();
        initNvTable();

        initYearCbb();
        initHanmucTb();
        inithanmucCellFactory();
        initNvCellFactory();
        initHanmucNhiemvuTaubayCellFactory();
    }
    private void initHanmucTb() {
        Integer y = year_cbb.getSelectionModel().getSelectedItem();
        if (y!=null){
            hmnv = hanmucNhiemvuService.getAllByYear(y);
            if (!hmnv.isEmpty()){
                initNhiemvuTaubay(hmnv);
            }else {
                if (DialogMessage.callAlertWithMessage(null,null,"Chưa đặt Chỉ tiêu, Hạn mức cho Nhiệm vụ tàu bay năm " + LocalDate.now().getYear()+
                                ". Bạn có muốn chuyển Chỉ tiêu, Hạn mức Nhiệm vụ Tàu bay năm "+(LocalDate.now().getYear()-1)+" để dùng cho năm "+(LocalDate.now().getYear())
                        , Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                    hanmucNhiemvuService.switchNhiemvuTauBay();
                    hmnv = hanmucNhiemvuService.getAllByYear(y);
                    if (!hmnv.isEmpty()){
                        initNhiemvuTaubay(hmnv);
                    }
                }
            }
            hm2_ls = hanmucNhiemvuService.findAllDto(y);
            if (!hm2_ls.isEmpty()){
                initHanmuc(hm2_ls);
            }else{
                if (DialogMessage.callAlertWithMessage(null,null,"Chưa đặt Chỉ tiêu, Hạn mức cho Nhiệm vụ năm " + LocalDate.now().getYear()+
                                ". Bạn có muốn chuyển Chỉ tiêu, Hạn mức Nhiệm vụ năm "+(LocalDate.now().getYear()-1)+" để dùng cho năm "+(LocalDate.now().getYear())
                        , Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                    hanmucNhiemvuService.switchHanmucNhiemvu();
                    hm2_ls = hanmucNhiemvuService.findAllDto(y);
                    if (!hm2_ls.isEmpty()){
                        initHanmuc(hm2_ls);
                    }
                }
            }
        }
    }
    private void initYearCbb() {
        year_cbb.setItems(FXCollections.observableList(hanmucNhiemvuService.findAllYearByYear()));
        year_cbb.getSelectionModel().selectFirst();
    }
    private void initAddHm(){
        nvStage = new Stage();
        nvStage.initStyle(StageStyle.UTILITY);
        Common.openNewStage("addnew_nvhanmuc.fxml", nvStage,"HANMUC", StageStyle.DECORATED);
    }
    @FXML
    public void nhiemvu_selected(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){
            hm2 = tieuthunhiemvu.getSelectionModel().getSelectedItem();
            Integer y = year_cbb.getSelectionModel().getSelectedItem();
            if (y!=null){
                if (hm2!=null) {
                    initAddHm();
                    hm2_ls = hanmucNhiemvuService.findAllDto(y);
                    initHanmuc(hm2_ls);
                }
            }
        }
    }
    @FXML
    public void dviSelected(ActionEvent actionEvent) {
        NguonNx n = dvi_cbb.getSelectionModel().getSelectedItem();
        if (n!=null) {
            List<HanmucNhiemvuTaubayDto> prels = hmnv.stream().filter(x->x.getDonvi().equals(n.getTen())).toList();
            initNhiemvuTaubay(prels);
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
        Common.openNewStage("add_nv.fxml", nvStage,null, StageStyle.DECORATED);
        initNvTable();
    }
    private void openAddChitieuForm(){
        nvStage = new Stage();
        nvStage.initStyle(StageStyle.UTILITY);
        Common.openNewStage("add_chitieunv.fxml", nvStage,null, StageStyle.DECORATED);
        Integer y = year_cbb.getSelectionModel().getSelectedItem();
        if (y!=null){
            hmnv = hanmucNhiemvuService.getAllByYear(y);
            initNhiemvuTaubay(hmnv);
        }
    }
    private void initNvTable(){
        nv_tb.setItems(FXCollections.observableList(chitietNhiemvuService.findAllBy()));
    }
    private void initNhiemvuTaubay(List<HanmucNhiemvuTaubayDto> hmnv){
        ctnv_pt.setItems(FXCollections.observableList(hmnv));
    }
    private void initHanmuc(List<HanmucNhiemvu2Dto> ls){
        tieuthunhiemvu.setItems(FXCollections.observableList(ls));
    }
    private void initDviTb() {
        ComponentUtil.setItemsToComboBox(dvi_cbb,nguonNxService.findByAllBy(),NguonNx::getTen,input->nguonNxService.findByTen(input).orElse(null));
        dvi_cbb.getSelectionModel().selectFirst();
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
        xang.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("xang_str"));
        diezel.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("diezel_str"));
        daubay.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("daubay_str"));
        cong.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("cong"));
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

    @FXML
    public void yearSelected(ActionEvent actionEvent) {
        Integer y = year_cbb.getSelectionModel().getSelectedItem();
        if (y!=null){
            hmnv = hanmucNhiemvuService.getAllByYear(y);
            initNhiemvuTaubay(hmnv);
            hm2_ls = hanmucNhiemvuService.findAllDto(y);
            initHanmuc(hm2_ls);
        }
    }
    @FXML
    public void ctvn_convertACtion(ActionEvent actionEvent) {
        try {
            if (DialogMessage.callAlertWithMessage(null,null,"Chưa đặt Chỉ tiêu, Hạn mức cho Nhiệm vụ tàu bay năm " + LocalDate.now().getYear()+
                            ". Bạn có muốn chuyển Chỉ tiêu, Hạn mức Nhiệm vụ Tàu bay năm "+(LocalDate.now().getYear()-1)+" để dùng cho năm "+(LocalDate.now().getYear())
                    , Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                hanmucNhiemvuService.switchNhiemvuTauBay();
            }
        }catch (Exception e){
            DialogMessage.errorShowing("co loi xay ra");
        }
    }
    @FXML
    public void convertHmBtnAction(ActionEvent actionEvent) {
        try {
            if (DialogMessage.callAlertWithMessage(null,null,"Chưa đặt Chỉ tiêu, Hạn mức cho Nhiệm vụ năm " + LocalDate.now().getYear()+
                            ". Bạn có muốn chuyển Chỉ tiêu, Hạn mức Nhiệm vụ năm "+(LocalDate.now().getYear()-1)+" để dùng cho năm "+(LocalDate.now().getYear())
                    , Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                hanmucNhiemvuService.switchHanmucNhiemvu();
            }
        }catch (Exception e){
            DialogMessage.errorShowing("co loi xay ra");
        }

    }
}
