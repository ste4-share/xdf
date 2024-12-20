package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
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
    public TableView<SpotDto> tb_tonkho;
    @FXML
    public TableView<LichsuXNK> tb_history;
    @FXML
    public TableColumn<SpotDto, String> col_stt_tk,col_maxd_tk,col_tenxd_tk,col_cl,col_nvdx_tdk,col_sscd_tdk,
            col_cong_tdk, col_nhap_nvdx, col_xuat_nvdx,col_nvdx, col_nhap_sscd, col_xuat_sscd,col_sscd,
            col_nvdx_tck,col_sscd_tck,col_cong_tck;
    @FXML
    public TableColumn<LichsuXNK, String> ls_stt,ls_so,ls_lp,ls_dvn,ls_dvx,ls_tc,
            ls_tenxd, ls_cl, ls_tontruoc,ls_soluong,ls_lnv, ls_tonsau,ls_gia, ls_create_at;
    @FXML
    private TextField ls_search, search_inventory;
    @FXML
    private DatePicker s_date, e_date;
    @FXML
    private ComboBox<Quarter> cbb_quarter;
    @FXML
    private Label lb_end_date,lb_start_date;

    @Autowired
    private QuarterService quarterService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private LichsuService lichsuService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tb_tonkho.setPrefWidth(DashboardController.screenWidth);
        tb_tonkho.setPrefHeight(DashboardController.screenHeigh);

        tkt = new ArrayList<>();
        pickTonKho = new SpotDto();
        findByTime = quarterService.findByCurrentTime(LocalDate.now()).get();
        setQuarterListToCbb();

        setTonkhoTongToCol();
        fillDataToTableTonkho(findByTime.getId());
        setLichsuTb();
        fillDataToTableLichsu(findByTime.getId());
        searching(tkt.stream().map(SpotDto::getTenxd).toList());
    }
    private void fillDataToTableLichsu(int id) {
        tb_history.setItems(FXCollections.observableArrayList(lichsuService.findAllByQuyid(id)));
    }
    private void setQuarterListToCbb(){
        ComponentUtil.setItemsToComboBox(cbb_quarter, quarterService.findAllByYear(String.valueOf(Year.now().getValue())), Quarter::getName, input -> quarterService.findByName(input).orElse(null));
        cbb_quarter.getSelectionModel().select(findByTime);
        setLabel();
    }
    private void setLabel(){
        lb_start_date.setTextFill(Color.rgb(33, 12, 162));
        lb_end_date.setTextFill(Color.rgb(33, 12, 162));
        lb_end_date.setText(cbb_quarter.getValue().getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_start_date.setText(cbb_quarter.getValue().getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
    }
    @FXML
    public void selectQuarter(ActionEvent actionEvent) {

        fillDataToTableTonkho(getCurrentQuarter().getId());
    }
    private void fillDataToTableTonkho(int quy_id){
        tkt = inventoryService.getAllSpots(quy_id);
        tb_tonkho.setItems( FXCollections.observableArrayList(tkt));
    }
    private void setTonkhoTongToCol(){
        col_stt_tk.setSortable(false);
        col_stt_tk.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_tonkho.getItems().indexOf(column.getValue())+1).asString());
        col_maxd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("maxd"));
        col_tenxd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tenxd"));
        col_cl.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("chungloai"));
        col_nvdx_tdk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tdk_nvdx_str"));
        col_sscd_tdk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tdk_sscd_str"));
        col_cong_tdk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tdk_total"));
        col_nhap_nvdx.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nhap_nvdx_str"));
        col_xuat_nvdx.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("xuat_nvdx_str"));
        col_nvdx.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nvdx_str"));
        col_nhap_sscd.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nhap_sscd_str"));
        col_xuat_sscd.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("xuat_sscd_str"));
        col_sscd.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("sscd_str"));
        col_nvdx_tck.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tck_nvdx_str"));
        col_sscd_tck.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tck_sscd_str"));
        col_cong_tck.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tck_total"));
    }

    private void setLichsuTb(){
        ls_stt.setSortable(false);
        ls_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_history.getItems().indexOf(column.getValue())+1).asString());
        ls_so.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("so"));
        ls_lp.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("loai_phieu"));
        ls_dvn.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("dvn"));
        ls_dvx.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("dvx"));
        ls_tc.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tinhchat"));
        ls_tenxd.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("ten_xd"));
        ls_cl.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("chungloaixd"));
        ls_tontruoc.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tontruoc_str"));
        ls_soluong.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("soluong_str"));
        ls_tonsau.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tonsau_str"));
        ls_create_at.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("createTime"));
        ls_lnv.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("type"));
        ls_gia.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("gia"));
    }
    private void searching(List<String> search_arr){
        TextFields.bindAutoCompletion(search_inventory,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
    }

    @FXML
    public void changeTabTheoQuy(Event event) {
        fillDataToTableTonkho(getCurrentQuarter().getId());
    }

    private Quarter getCurrentQuarter(){
        Quarter selected = cbb_quarter.getSelectionModel().getSelectedItem();
        lb_end_date.setText(selected.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_start_date.setText(selected.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        return selected;
    }

    @FXML
    public void setClickToTonTb(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2){
            tk_stage = new Stage();
            Common.openNewStage("changesscd-form.fxml", tk_stage,"Thay Doi");
        }
    }
    @FXML
    public void addnew_petro(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("add_inv_form.fxml", tk_stage,"THEM MOI");
    }
}
