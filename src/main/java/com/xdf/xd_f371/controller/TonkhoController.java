package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class TonkhoController implements Initializable {

    public static Stage tk_stage;
    private static List<SpotDto> tkt;
    private static Quarter findByTime;
    public static SpotDto pickTonKho = new SpotDto();
    @FXML
    public TableView<SpotDto> tb_tonkho,tb_inv_chitiet;
    @FXML
    public TableColumn<SpotDto, String> col_stt_tk,col_maxd_tk,col_tenxd_tk,col_nvdx_tk,col_sscd_tk,col_sum_tk,
            col_stt_qt, col_tenxd_qt, col_chungloai_qt,col_nvdx_tdk, col_sscd_tdk, col_sum_tdk,col_tong_nhap,
            col_nhap_nvdx,col_xuat_nvdx,col_nhap_sscd,col_xuat_sscd,col_nvdx_tck,col_sscd_tck,col_sum_tck;
    @FXML
    private TextField tf_search_inv_qt, search_inventory;
    @FXML
    private DatePicker start_date_qt, end_date_qt;
    @FXML
    private Button addNewQuarter_btn, createQuarterBtn, cancel_quaterbtn,printBcNxt;
    @FXML
    private ComboBox<Quarter> cbb_quarter;
    @FXML
    private Label lb_end_date,lb_start_date;

    @Autowired
    private QuarterService quarterService;
    @Autowired
    private MucgiaService mucgiaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tkt = new ArrayList<>();
        pickTonKho = new SpotDto();
        findByTime = new Quarter();
        findByTime = quarterService.findByCurrentTime(LocalDate.now()).get();
        setQuarterListToCbb();

        setTonkhoTongToCol();
        setTonkhoTongToCol2();
        fillDataToTableTonkho();
        tb_tonkho.setPrefWidth(DashboardController.screenWidth);
        tb_tonkho.setPrefHeight(DashboardController.screenHeigh);
        tb_inv_chitiet.setPrefWidth(DashboardController.screenWidth);
        tb_inv_chitiet.setPrefHeight(DashboardController.screenHeigh);
    }

    private void setQuarterListToCbb(){
        lb_start_date.setTextFill(Color.rgb(33, 12, 162));
        lb_end_date.setTextFill(Color.rgb(33, 12, 162));
        try {
            cbb_quarter.setConverter(new StringConverter<Quarter>() {
                @Override
                public String toString(Quarter object) {
                    return object==null ? "" : object.getName();
                }

                @Override
                public Quarter fromString(String string) {
                    return quarterService.findByName(string).get();
                }
            });

            cbb_quarter.setItems(FXCollections.observableArrayList(quarterService.findAll()));
            cbb_quarter.getSelectionModel().select(findByTime);
            lb_end_date.setText(cbb_quarter.getValue().getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
            lb_start_date.setText(cbb_quarter.getValue().getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    @FXML
    public void addNewQuarter(ActionEvent actionEvent) throws IOException {
        tk_stage = new Stage();
        Common.openNewStage("quater_form.fxml", tk_stage,"Tạo Quý");
    }

    @FXML
    public void selectQuarter(ActionEvent actionEvent) {
        Quarter selected = cbb_quarter.getSelectionModel().getSelectedItem();
        lb_end_date.setText(selected.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_start_date.setText(selected.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        fillDataToTable(selected);
    }
    private void fillDataToTableTonkho(){
        tkt = mucgiaService.getAllSpots(findByTime.getId());
        searching(tkt.stream().map(x->x.getTenxd()).toList());
        ObservableList<SpotDto> observableList = FXCollections.observableArrayList(tkt);
        tb_tonkho.setItems(observableList);
    }
    private void setTonkhoTongToCol(){
        col_stt_tk.setSortable(false);
        col_stt_tk.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_tonkho.getItems().indexOf(column.getValue())+1).asString());
        col_maxd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("maxd"));
        col_tenxd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tenxd"));
        col_nvdx_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nvdx_str"));
        col_sscd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("sscd_str"));
        col_sum_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("total"));
    }
    private boolean addedBySelection = false;
    private void searching(List<String> search_arr){
        TextFields.bindAutoCompletion(search_inventory,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
        search_inventory.setOnKeyPressed(e -> {
            addedBySelection = false;
        });

        search_inventory.setOnKeyReleased(e -> {
            if (search_inventory.getText().trim().isEmpty()){
                fillDataToTableTonkho();
            }
            addedBySelection = true;
        });

        search_inventory.textProperty().addListener(e -> {
            if (addedBySelection) {
                List<SpotDto> tkt_buf = tkt.stream().filter(tonkhoTong -> tonkhoTong.getTenxd().equals(search_inventory.getText())).toList();
                ObservableList<SpotDto> observableList = FXCollections.observableArrayList(tkt_buf);
                tb_tonkho.setItems(observableList);
                addedBySelection = false;
            }
        });
    }

    private void fillDataToTable(Quarter selected){
        tb_inv_chitiet.setItems(FXCollections.observableList(mucgiaService.getAllSpots2(selected.getId())));
    }

    private void setTonkhoTongToCol2(){
        col_stt_qt.setSortable(false);
        col_stt_qt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_inv_chitiet.getItems().indexOf(column.getValue())+1).asString());
        col_tenxd_qt.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tenxd"));
        col_chungloai_qt.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("chungloai"));
        col_nvdx_tdk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tdk_nvdx_str"));
        col_sscd_tdk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tdk_sscd_str"));
        col_sum_tdk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tdk_total"));

        col_nhap_nvdx.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nhap_nvdx_str"));
        col_xuat_nvdx.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("xuat_nvdx_str"));
        col_nhap_sscd.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nhap_sscd_str"));
        col_xuat_sscd.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("xuat_sscd_str"));

        col_nvdx_tck.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tck_nvdx_str"));
        col_sscd_tck.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tck_sscd_str"));
        col_sum_tck.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tck_total"));
    }

    @FXML
    public void changeTabTheoQuy(Event event) {
        Quarter selected = cbb_quarter.getValue();
        lb_end_date.setText(selected.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_start_date.setText(selected.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        fillDataToTable(selected);
    }

    @FXML
    public void setClickToTonTb(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            tk_stage = new Stage();
            Common.openNewStage("changesscd-form.fxml", tk_stage,"Thay Doi");
        }
    }
}
