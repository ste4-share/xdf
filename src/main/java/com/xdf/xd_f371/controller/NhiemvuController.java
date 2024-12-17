package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiNVCons;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.util.Common;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class NhiemvuController implements Initializable {
    public static Stage nvStage;
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
    TableView<ChitieuNhiemvuDto> ctnv_tb;
    @FXML
    TableView<HanmucNhiemvuTaubayDto> ctnv_pt;
    @FXML
    TableColumn<HanmucNhiemvuTaubayDto, String> t2_tt1,dvi_x,t2_pt,t2_nv_2,ct_nv_2,t2_tk_2,t2_md_2,t2_nl_2;
    @FXML
    TableColumn<ChitieuNhiemvuDto, String> t2_tt,t2_nv,t2_ct,t2_tk,t2_md,t2_nl;
    @FXML
    TableColumn<HanmucNhiemvu2Dto, String> nv,ct,xang,diezel,daubay;
    @FXML
    ComboBox<Quarter> quy_cbb;
    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @FXML
    ComboBox<String> donvi;
    @FXML
    TableView<NhiemVuDto> nv_tb;
    @FXML
    TableColumn<NhiemVuDto, String> tennv, ctnv,lnv, khoi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initQuarterCbb();
        donvi.setItems(FXCollections.observableList(List.of("F bộ")));
        donvi.getSelectionModel().selectFirst();
        inithanmucTb();
        initNvTb();
        initDviTb();
        initHanmucNhiemvuTaubay();
    }

    private void initHanmucNhiemvuTaubay() {
        ctnv_pt.setItems(FXCollections.observableList(hanmucNhiemvuService.getAllBy()));
        t2_tt1.setSortable(false);
        t2_tt1.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(ctnv_tb.getItems().indexOf(column.getValue())+1).asString());
        dvi_x.setCellValueFactory(new PropertyValueFactory<>("donvi"));
        t2_pt.setCellValueFactory(new PropertyValueFactory<>("tenpt"));
        t2_nv_2.setCellValueFactory(new PropertyValueFactory<>("nhiemvu"));
        ct_nv_2.setCellValueFactory(new PropertyValueFactory<>("ct_nhiemvu"));
        t2_tk_2.setCellValueFactory(new PropertyValueFactory<>("tk"));
        t2_md_2.setCellValueFactory(new PropertyValueFactory<>("md"));
        t2_nl_2.setCellValueFactory(new PropertyValueFactory<>("nhienlieu"));
        ctnv_pt.refresh();
    }

    @FXML
    public void nhiemvu_selected(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){

        }
    }
    @FXML
    public void bcNlbayTheoKh(ActionEvent actionEvent) throws IOException, SQLException {

    }

    @FXML
    public void donviselected(ActionEvent actionEvent) {
    }
    @FXML
    public void addhanmucxangdau(ActionEvent actionEvent) {
        nvStage = new Stage();
        Common.openNewStage("addnew_nvhanmuc.fxml", nvStage,"HANMUC");
        inithanmucTb();
    }
    @FXML
    public void chitieunvSelected(MouseEvent mouseEvent) {

    }

    @FXML
    public void dviSelected(ActionEvent actionEvent) {
        if (dvi_cbb.getSelectionModel().getSelectedItem()!=null) {
            setCtnv_tb(dvi_cbb.getSelectionModel().getSelectedItem().getId());
        }
    }
    @FXML
    public void addnewchitieubay(ActionEvent actionEvent) {
        nvStage = new Stage();
        Common.openNewStage("add_chitieunv.fxml", nvStage,"FORM");
        if (dvi_cbb.getSelectionModel().getSelectedItem()==null) {
        }
    }
    @FXML
    public void addnewpt_nvbay(ActionEvent actionEvent) {
        nvStage = new Stage();
        Common.openNewStage("add_hanmuc_pt_taubay.fxml", nvStage,"FORM");
        initHanmucNhiemvuTaubay();
    }

    private void initDviTb() {
        dvi_cbb.setItems(FXCollections.observableList(nguonNxService.findByAllBy()));
        dvi_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "": object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findByTen(string).orElse(null);
            }
        });
        dvi_cbb.getSelectionModel().selectFirst();
    }

    private void setCtnv_tb(int dvi_id){
//        ctnv_tb.setItems(FXCollections.observableList(chitietNhiemvuService.findAllByUnit(dvi_id, DashboardController.findByTime.getId())));
        t2_tt.setSortable(false);
        t2_tt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(ctnv_tb.getItems().indexOf(column.getValue())+1).asString());
        t2_nv.setCellValueFactory(new PropertyValueFactory<>("ten_nv"));
        t2_ct.setCellValueFactory(new PropertyValueFactory<>("chitiet"));
        t2_tk.setCellValueFactory(new PropertyValueFactory<>("ct_tk"));
        t2_md.setCellValueFactory(new PropertyValueFactory<>("ct_md"));
        t2_nl.setCellValueFactory(new PropertyValueFactory<>("consumpt_str"));
        ctnv_tb.refresh();
    }

    private void initNvTb() {
        nv_tb.setItems(FXCollections.observableList(chitietNhiemvuService.findAllDtoBy(LoaiNVCons.HAOHUT.getName())));
        tennv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_nv"));
        ctnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("chitiet"));
        lnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_loai_nv"));
        khoi.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("khoi"));
    }

    private void inithanmucTb() {
        tieuthunhiemvu.setItems(FXCollections.observableList(hanmucNhiemvuService.findAllDto()));
        nv.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("tenNv"));
        ct.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("chitiet_nhiemvu"));
        xang.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("xang"));
        diezel.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("diezel"));
        daubay.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("daubay"));
        tieuthunhiemvu.refresh();
    }

    private void initQuarterCbb(){
        quy_cbb.setItems(FXCollections.observableList(quarterService.findAll()));
        quy_cbb.setConverter(new StringConverter<Quarter>() {
            @Override
            public String toString(Quarter object) {
                return object==null ?"": object.getName();
            }

            @Override
            public Quarter fromString(String string) {
                return quarterService.findByName(quy_cbb.getValue().getName()).isPresent()?quy_cbb.getValue():null;
            }
        });
    }
}
