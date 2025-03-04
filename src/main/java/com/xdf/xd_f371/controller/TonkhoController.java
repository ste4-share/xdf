package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.InventoryUnitDto;
import com.xdf.xd_f371.dto.PttkDto;
import com.xdf.xd_f371.entity.*;
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
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class TonkhoController implements Initializable {

    public static Stage tk_stage;
    private static List<InventoryUnitDto> inventoryUnitDtoArrayList = new ArrayList<>();
    public static InventoryUnitDto pickTonKho = new InventoryUnitDto();
    private static List<PttkDto> pttkDtos = new ArrayList<>();
    public static NguonNx ref_unit = null;
    @FXML
    private TableView<InventoryUnitDto> tb_inventory;
    @FXML
    public TableView<PttkDto> pttk_tb;
    @FXML
    public TableColumn<PttkDto, String> pttk_stt,pttk_loaixd,pttk_tenxd,pttk_e916,pttk_e921,pttk_e923,pttk_e927,pttk_dnb,pttk_dka,pttk_dvi,pttk_dns,pttk_fbo,pttk_tdv;
    @FXML
    public TableColumn<InventoryUnitDto, String> col_stt_i,col_tenxd_i,col_cl_i,col_nvdx_tdk_i,col_sscd_tdk_i,
            col_cong_tdk_i, col_pre_nvdx_i, col_pre_sscd_i,col_pre_inv_i, col_nvdx_datt_i, col_sscd_datt_i,col_cong_datt_i;
    @FXML
    private TextField search_inventory,timkiem_pttk_tf;
    @FXML
    private Label sd_lb, ed_lb,dv_lb;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ConfigurationService configurationService;
    @Autowired
    private NguonNxService nguonNxService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tb_inventory.setPrefWidth(DashboardController.screenWidth);
        tb_inventory.setPrefHeight(DashboardController.screenHeigh-350);
        pttk_tb.setPrefWidth(DashboardController.screenWidth);
        pttk_tb.setPrefHeight(DashboardController.screenHeigh-350);
        setLb();
        fillDataToTableInventoryUnit();
        setCellFactoryInventoryTb();
        setCellFactoryPttk();
        initPttkTb();
        searching(inventoryUnitDtoArrayList.stream().map(InventoryUnitDto::getTenxd).toList());
    }
    private void fillDataToTableInventoryUnit() {
        if (ref_unit!=null){
            dv_lb.setText("--Tồn kho: " +ref_unit.getTen());
            inventoryUnitDtoArrayList = inventoryService.getAllInventoryUnitDtoByUnit(ref_unit.getId());
            setDataToTbInventory(inventoryUnitDtoArrayList);
        }else{
            dv_lb.setText("--Tồn kho toàn đơn vị--");
            inventoryUnitDtoArrayList = inventoryService.getAllInventoryUnitDto();
            setDataToTbInventory(inventoryUnitDtoArrayList);
        }
    }
    private void setDataToTbInventory(List<InventoryUnitDto> ls){
        tb_inventory.setItems(FXCollections.observableList(ls));
        tb_inventory.refresh();
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
        Optional<Configuration> i = configurationService.findByParam(ConfigCons.ROOT_ID.getName());
        if (i.isPresent()){
            Optional<NguonNx> nx = nguonNxService.findById(Integer.parseInt(i.get().getValue()));
            nx.ifPresent(nguonNx -> {
                ref_unit=nguonNx;
                dv_lb.setText(nguonNx.getTen());
            });
        }else {
            ref_unit=null;
            dv_lb.setText(null);
        }
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
    private void setCellFactoryInventoryTb() {
        col_stt_i.setSortable(false);
        col_stt_i.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tb_inventory.getItems().indexOf(column.getValue())+1).asString());
        col_tenxd_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tenxd"));
        col_cl_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("loai"));
        col_nvdx_tdk_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tdk_nvdx_str"));
        col_sscd_tdk_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tdk_sscd_str"));
        col_cong_tdk_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("tdk_str"));
        col_pre_nvdx_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("pre_nvdx_str"));
        col_pre_sscd_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("pre_sscd_str"));
        col_pre_inv_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("pre_str"));
        col_nvdx_datt_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("datt_nvdx_str"));
        col_sscd_datt_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("datt_sscd_str"));
        col_cong_datt_i.setCellValueFactory(new PropertyValueFactory<InventoryUnitDto, String>("datt_str"));
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
    private void searching(List<String> search_arr){
        TextFields.bindAutoCompletion(search_inventory,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().contains(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
    }

    @FXML
    public void setClickToTonTb(MouseEvent mouseEvent) {
        InventoryUnitDto spotDto = tb_inventory.getSelectionModel().getSelectedItem();
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
                fillDataToTableInventoryUnit();
            }
        }
    }
    @FXML
    public void addnew_petro(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("add_inv_form.fxml", tk_stage,"THEM MOI", StageStyle.DECORATED);
        fillDataToTableInventoryUnit();
    }
    @FXML
    public void timkiem_tk_clicked(MouseEvent mouseEvent) {
        search_inventory.selectAll();
    }
    @FXML
    public void inv_kr(KeyEvent keyEvent) {
        String text = search_inventory.getText().trim();
        if (!text.isEmpty()){
            List<InventoryUnitDto> ls = inventoryUnitDtoArrayList.stream().filter(x->x.getTenxd().equals(text)).toList();
            setDataToTbInventory(ls);
        }else{
            setDataToTbInventory(inventoryUnitDtoArrayList);
        }
    }
    @FXML
    public void endQuarterAction(ActionEvent actionEvent) {
        tk_stage = new Stage();
        Common.openNewStage("quarter.fxml", tk_stage,null, StageStyle.UTILITY);
        fillDataToTableInventoryUnit();
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
}