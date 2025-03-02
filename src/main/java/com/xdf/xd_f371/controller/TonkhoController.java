package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.PttkDto;
import com.xdf.xd_f371.dto.TonkhoDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
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
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class TonkhoController implements Initializable {

    public static Stage tk_stage;
    private static List<TonkhoDto> tkt = new ArrayList<>();
    private static List<LichsuXNK> histories = new ArrayList<>();
    public static TonkhoDto pickTonKho = new TonkhoDto();
    private static List<PttkDto> pttkDtos = new ArrayList<>();
    public static NguonNx ref_unit = null;
    @FXML
    public TableView<TonkhoDto> tb_tonkho;
    @FXML
    public TableView<LichsuXNK> tb_history;
    @FXML
    public TableView<PttkDto> pttk_tb;
    @FXML
    public TableColumn<PttkDto, String> pttk_stt,pttk_loaixd,pttk_tenxd,pttk_e916,pttk_e921,pttk_e923,pttk_e927,pttk_dnb,pttk_dka,pttk_dvi,pttk_dns,pttk_fbo,pttk_tdv;
    @FXML
    public TableColumn<TonkhoDto, String> col_stt_tk,col_tenxd_tk,col_cl,col_nvdx_tdk,col_sscd_tdk,
            col_cong_tdk, col_nhap_nvdx, col_xuat_nvdx,col_nvdx, col_nhap_sscd, col_xuat_sscd,col_sscd,
            col_nvdx_tck,col_sscd_tck,col_cong_tck;
    @FXML
    public TableColumn<LichsuXNK, String> ls_stt,ls_so,ls_lp,ls_dvn,ls_dvx,
            ls_tenxd, ls_cl,ls_soluong,ls_lnv, ls_tonsau,ls_gia, ls_create_at;
    @FXML
    private TextField ls_search, search_inventory,ls_search_so,from_date,to_date,timkiem_pttk_tf, ls_search_tendv;
    @FXML
    private DatePicker ls_s_date;
    @FXML
    private Label sd_lb, ed_lb,dv_lb;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private LichsuService lichsuService;
    @Autowired
    private NguonNxService nguonNxService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tb_tonkho.setPrefWidth(DashboardController.screenWidth);
        tb_tonkho.setPrefHeight(DashboardController.screenHeigh-350);
        tb_history.setPrefWidth(DashboardController.screenWidth);
        tb_history.setPrefHeight(DashboardController.screenHeigh-350);
        pttk_tb.setPrefWidth(DashboardController.screenWidth);
        pttk_tb.setPrefHeight(DashboardController.screenHeigh-350);

        CommonFactory.setVi_DatePicker(ls_s_date);
        setLb();
        pickTonKho = new TonkhoDto();
        fillDataToTableTonkho();
        setTonkhoTongToCol();
        setLichsuTb();
        fillDataToTableLichsu();
        setCellFactoryPttk();
        initPttkTb();
        searching(tkt.stream().map(TonkhoDto::getTenxd).toList());
        searching_ls(histories.stream().map(LichsuXNK::getTen_xd).toList());
    }
    private void setLb(){
        Accounts acc = ConnectLan.pre_acc;
        if (acc.getSd()!=null && acc.getEd()!=null){
            sd_lb.setText(acc.getSd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            ed_lb.setText(acc.getEd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }else{
            sd_lb.setText(null);
            ed_lb.setText(null);
        }
        indentifyNguonnx();
    }
    private void indentifyNguonnx(){
        Integer i =inventoryService.getDvFromInv();
        if (i!=null){
            Optional<NguonNx> nx = nguonNxService.findById(i);
            nx.ifPresent(nguonNx -> {
                ref_unit=nguonNx;
                dv_lb.setText(nguonNx.getTen());
            });
        }else {
            ref_unit=null;
            dv_lb.setText(null);
        }
    }
    private void fillDataToTableLichsu() {
        histories = new ArrayList<>();
        Accounts acc = ConnectLan.pre_acc;
        if (acc.getSd()!=null && acc.getEd()!=null){
            List<LichsuXNK> ls = lichsuService.findAllByQuyid(acc.getSd(),acc.getEd());
            ls.forEach(x->histories.add(new LichsuXNK(x)));
        }else{
            List<LichsuXNK> ls = lichsuService.findAll();
            ls.forEach(x->histories.add(new LichsuXNK(x)));
        }
        tb_history.setItems(FXCollections.observableArrayList(histories));
    }
    private void mapLsTb(List<LichsuXNK> ls){
        tb_history.setItems(FXCollections.observableArrayList(ls));
    }

    private void fillDataToTableTonkho(){
        Accounts acc = ConnectLan.pre_acc;
        if (acc.getSd()!=null && acc.getEd()!=null){
            if (ref_unit!=null){
                dv_lb.setText("--Tồn kho: " +ref_unit.getTen());
                tkt = inventoryService.getAllTonkhoTDV(acc.getSd(),acc.getEd(),ref_unit.getId());
            } else {
                dv_lb.setText("--Tồn kho toàn đơn vị--");
                tkt = inventoryService.getAllTonkho(acc.getSd(),acc.getEd());
            }
        }else{
            tkt = inventoryService.getAllTonkhoNotCondition();
        }
        tb_tonkho.setItems(FXCollections.observableArrayList(tkt));
        tb_tonkho.refresh();
    }
    private void mapInvTb(List<TonkhoDto> ls){
        tb_tonkho.setItems( FXCollections.observableArrayList(ls));
    }
    private void initPttkTb(){
        Accounts q = ConnectLan.pre_acc;
        if (q.getSd()!=null){
            pttkDtos = inventoryService.mapPttkPetro();
            mapPttkTb(pttkDtos);
        }else{
            mapPttkTb(new ArrayList<>());
        }
    }
    private void mapPttkTb(List<PttkDto> ls){
        pttk_tb.setItems( FXCollections.observableArrayList(ls));
        pttk_tb.refresh();
    }
    private void setCellFactoryPttk(){
        pttk_stt.setSortable(false);
        pttk_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(pttk_tb.getItems().indexOf(column.getValue())+1).asString());
        pttk_tenxd.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("tenxd"));
        pttk_loaixd.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("loai"));
        pttk_e916.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e916"));
        pttk_e921.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e921"));
        pttk_e923.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e923"));
        pttk_e927.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("e927"));
        pttk_dnb.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dnb"));
        pttk_dka.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dka"));
        pttk_dvi.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dvi"));
        pttk_dns.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("dns"));
        pttk_fbo.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("fb"));
        pttk_tdv.setCellValueFactory(new PropertyValueFactory<PttkDto, String>("tdv"));
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
    public void setClickToTonTb(MouseEvent mouseEvent) {
        TonkhoDto spotDto = tb_tonkho.getSelectionModel().getSelectedItem();
        if (mouseEvent.getClickCount() == 2 && spotDto != null) {
            pickTonKho = spotDto;
            tk_stage = new Stage();
            List<InventoryDto> list;
            if (ref_unit!=null){
                list = inventoryService.findPreInventoryPetroFollowUnit(spotDto.getPetro_id(),ref_unit.getId());
            }else{
                list = inventoryService.findPreInventoryPetro(spotDto.getPetro_id());
            }
            if (list==null){
                DialogMessage.successShowing(spotDto.getTenxd() + " đã hết hàng!! Vui lòng nhập thêm");
            }else{
                Common.openNewStage("changesscd-form.fxml", tk_stage,"Thay Doi", StageStyle.DECORATED);
                fillDataToTableTonkho();
            }
        }
    }
    @FXML
    public void addnew_petro(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("add_inv_form.fxml", tk_stage,"THEM MOI", StageStyle.DECORATED);
        fillDataToTableTonkho();
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
        LocalDate sd = ls_s_date.getValue();
        if (sd!=null){
            mapLsTb(histories.stream().filter(x->x.getSd().isAfter(sd)).toList());
        } else {
            mapLsTb(histories);
        }
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
    @FXML
    public void endQuarterAction(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("quarter.fxml", tk_stage,null, StageStyle.UTILITY);
        fillDataToTableTonkho();
    }

    @FXML
    public void pttkClicked(MouseEvent mouseEvent) {
        timkiem_pttk_tf.selectAll();
    }
    @FXML
    public void pttk_kr(KeyEvent keyEvent) {
        String t = timkiem_pttk_tf.getText().trim();
        if (!t.isEmpty()){
            mapPttkTb(pttkDtos.stream().filter(x->x.getTenxd().toLowerCase().contains(t)).toList());
        }else{
            mapPttkTb(pttkDtos);
        }
    }
    @FXML
    public void ls_search_tendv_clicked(MouseEvent mouseEvent) {
        ls_search_tendv.selectAll();
    }
    @FXML
    public void ls_tendv_kr(KeyEvent keyEvent) {
        String t = ls_search_tendv.getText().trim();
        if (!t.isEmpty()){
            mapLsTb(histories.stream().filter(x->x.getDvn().toLowerCase().contains(t)).toList());
        }else{
            mapLsTb(histories);
        }
    }
}