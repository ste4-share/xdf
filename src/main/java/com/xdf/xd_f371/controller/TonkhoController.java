package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.TonkhoDto;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javafx.stage.StageStyle;
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
    private static List<TonkhoDto> tkt = new ArrayList<>();
    private static List<LichsuXNK> histories = new ArrayList<>();
    private static Quarter findByTime;
    public static TonkhoDto pickTonKho = new TonkhoDto();
    @FXML
    public TableView<TonkhoDto> tb_tonkho;
    @FXML
    public TableView<LichsuXNK> tb_history;
    @FXML
    public TableColumn<TonkhoDto, String> col_stt_tk,col_tenxd_tk,col_cl,col_nvdx_tdk,col_sscd_tdk,
            col_cong_tdk, col_nhap_nvdx, col_xuat_nvdx,col_nvdx, col_nhap_sscd, col_xuat_sscd,col_sscd,
            col_nvdx_tck,col_sscd_tck,col_cong_tck;
    @FXML
    public TableColumn<LichsuXNK, String> ls_stt,ls_so,ls_lp,ls_dvn,ls_dvx,
            ls_tenxd, ls_cl, ls_tontruoc,ls_soluong,ls_lnv, ls_tonsau,ls_gia, ls_create_at;
    @FXML
    private TextField ls_search, search_inventory,ls_search_so;
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
        tb_tonkho.setPrefHeight(DashboardController.screenHeigh-350);
        tb_history.setPrefWidth(DashboardController.screenWidth);
        tb_history.setPrefHeight(DashboardController.screenHeigh-350);

        pickTonKho = new TonkhoDto();
        findByTime = quarterService.findByCurrentTime(LocalDate.now()).get();
        tkt = inventoryService.getAllTonkho(findByTime.getId());

        setQuarterListToCbb();
        setTonkhoTongToCol();
        fillDataToTableTonkho(findByTime.getId());
        setLichsuTb();
        fillDataToTableLichsu(findByTime.getId());

        searching(tkt.stream().map(TonkhoDto::getTenxd).toList());
        searching_ls(histories.stream().map(LichsuXNK::getTen_xd).toList());
    }
    private void fillDataToTableLichsu(int id) {
        histories =lichsuService.findAllByQuyid(id);
        tb_history.setItems(FXCollections.observableArrayList(histories));
    }
    private void mapLsTb(List<LichsuXNK> ls){
        tb_history.setItems(FXCollections.observableArrayList(ls));
    }
    private void setQuarterListToCbb(){
        ComponentUtil.setItemsToComboBox(cbb_quarter, quarterService.findAllByYear(String.valueOf(Year.now().getValue())), Quarter::getIndex, input -> quarterService.findByIndex(input).orElse(null));
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
        tkt = inventoryService.getAllTonkho(quy_id);
        tb_tonkho.setItems( FXCollections.observableArrayList(tkt));
    }
    private void mapInvTb(List<TonkhoDto> ls){
        tb_tonkho.setItems( FXCollections.observableArrayList(ls));
    }
    private void setTonkhoTongToCol(){
        col_stt_tk.setSortable(false);
        col_stt_tk.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_tonkho.getItems().indexOf(column.getValue())+1).asString());
        col_tenxd_tk.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tenxd"));
        col_cl.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("loai"));
        col_nvdx_tdk.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tdk_nvdx_str"));
        col_sscd_tdk.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tdk_sscd_str"));
        col_cong_tdk.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tdk_total"));
        col_nhap_nvdx.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("nhap_nvdx_str"));
        col_xuat_nvdx.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("xuat_nvdx_str"));
        col_nvdx.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("nvdx_str"));
        col_nhap_sscd.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("nhap_sscd_str"));
        col_xuat_sscd.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("xuat_sscd_str"));
        col_sscd.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("sscd_str"));
        col_nvdx_tck.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tck_nvdx_str"));
        col_sscd_tck.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tck_sscd_str"));
        col_cong_tck.setCellValueFactory(new PropertyValueFactory<TonkhoDto, String>("tck_total"));
    }
    private void setLichsuTb(){
        ls_stt.setSortable(false);
        ls_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_history.getItems().indexOf(column.getValue())+1).asString());
        ls_so.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("so"));
        ls_lp.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("loai_phieu"));
        ls_dvn.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("dvn"));
        ls_dvx.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("dvx"));
        ls_tenxd.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("ten_xd"));
        ls_cl.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("chungloaixd"));
        ls_tontruoc.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tontruoc_str"));
        ls_soluong.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("soluong_str"));
        ls_tonsau.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("tonsau_str"));
        ls_create_at.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("createtime_str"));
        ls_lnv.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("type"));
        ls_gia.setCellValueFactory(new PropertyValueFactory<LichsuXNK, String>("gia_str"));
    }
    private void searching(List<String> search_arr){
        TextFields.bindAutoCompletion(search_inventory,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().contains(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
    }
    private void searching_ls(List<String> search_arr){
        TextFields.bindAutoCompletion(ls_search,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().contains(t.getUserText().toLowerCase());
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
        TonkhoDto spotDto = tb_tonkho.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && spotDto != null) {
            pickTonKho = spotDto;
            tk_stage = new Stage();
            Common.openNewStage("changesscd-form.fxml", tk_stage,"Thay Doi", StageStyle.DECORATED);
            fillDataToTableTonkho(findByTime.getId());
        }
    }
    @FXML
    public void addnew_petro(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("add_inv_form.fxml", tk_stage,"THEM MOI", StageStyle.DECORATED);
    }
    @FXML
    public void timkiem_tk_clicked(MouseEvent mouseEvent) {
        search_inventory.selectAll();
    }
    @FXML
    public void ls_search_clicked(MouseEvent mouseEvent) {
        ls_search.selectAll();
    }
    @FXML
    public void inv_kr(KeyEvent keyEvent) {
        String text = search_inventory.getText().trim();
        if (!text.isEmpty()){
            List<TonkhoDto> ls = tkt.stream().filter(x->x.getTenxd().equals(text)).toList();
            mapInvTb(ls);
        }else{
            mapInvTb(tkt);
        }
    }
    @FXML
    public void ls_kr(KeyEvent keyEvent) {
        String text = ls_search.getText().trim();
        if (!text.isEmpty()){
            List<LichsuXNK> ls = histories.stream().filter(x->x.getTen_xd().equals(text)).toList();
            mapLsTb(ls);
        } else {
            mapLsTb(histories);
        }
    }
    @FXML
    public void sd_clicked(ActionEvent actionEvent) {
        System.out.println("from date" + s_date.getValue().toString());
    }
    @FXML
    public void ls_search_so_clicked(MouseEvent mouseEvent) {
        ls_search_so.selectAll();
    }
    @FXML
    public void ls_so_kr(KeyEvent keyEvent) {
        String text = ls_search_so.getText().trim();
        if (!text.isEmpty()){
            List<LichsuXNK> ls = histories.stream().filter(x->x.getSo()==Integer.parseInt(text)).toList();
            mapLsTb(ls);
        } else {
            mapLsTb(histories);
        }
    }
}