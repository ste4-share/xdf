package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.PriceAndQuantityDto;
import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.model.MucGiaEnum;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.MucgiaService;
import com.xdf.xd_f371.util.Common;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.*;
import java.util.function.Function;

@Component
public class ChangingController implements Initializable {
    public static Stage addAff_stage;
    public static int sum = 0;
    public static String quantity ;
    public static String quantity_convert =null;
    private static SpotDto tonkho_selected;
    private static List<PriceAndQuantityDto> sscd_ls_buf = new ArrayList<>();
    private static List<PriceAndQuantityDto> nvdx_ls_buf = new ArrayList<>();
    public static List<Inventory> list = new ArrayList<>();

    @FXML
    private TableView<PriceAndQuantityDto> nvdx_tb,sscd_tb;
    @FXML
    private TableColumn<PriceAndQuantityDto, String> nvdx_price,nvdx_quantity,sscd_price,sscd_quantity;
    @FXML
    private Label petro_name;
    @FXML
    private Button nvdx_sscd, sscd_nvdx,changeBtn,cancel_btn;

    @Autowired
    private InventoryService inventoryService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAff_stage = new Stage();
        tonkho_selected = TonkhoController.pickTonKho;
        petro_name.setText(tonkho_selected.getTenxd());
        list = inventoryService.findByPetro_idAndQuarter_id(tonkho_selected.getLxd_id(),DashboardController.findByTime.getId(),MucGiaEnum.IN_STOCK.getStatus());
        setNvdxTable(nvdx_tb);
        setSScdTable(sscd_tb);
        setVisibleForBtn(true, true);
        setHoverForBtn();
    }
    private void setHoverForBtn(){
        Common.hoverButton(sscd_nvdx, "#ffffff");
        Common.hoverButton(nvdx_sscd, "#ffffff");
        Common.hoverButton(changeBtn, "#ffffff");
        Common.hoverButton(cancel_btn, "#ffffff");
    }
    private void setVisibleForBtn(Boolean nvdx, Boolean sscd){
        nvdx_sscd.setDisable(nvdx);
        sscd_nvdx.setDisable(sscd);
    }
    private void setNvdxTable(TableView<PriceAndQuantityDto> tb) {
        setDataToTable(tb, list, Inventory::getNhap_nvdx, Inventory::getXuat_nvdx);
        setFieldFactoryTb(nvdx_price, nvdx_quantity);
    }
    private void setSScdTable(TableView<PriceAndQuantityDto> tb) {
        setDataToTable(tb, list, Inventory::getNhap_sscd, Inventory::getXuat_sscd);
        setFieldFactoryTb(sscd_price, sscd_quantity);
    }
    private void openChangeQuantityForm(){
        Common.openNewStage("quantity_form.fxml", addAff_stage, "EDIT");
    }
    public void cancelForm(ActionEvent actionEvent) {
        TonkhoController.tk_stage.close();
    }
    @FXML
    public void nvdxToSscdAction(ActionEvent actionEvent) {

    }
    @FXML
    public void sscdToNvdxAction(ActionEvent actionEvent) {

    }
    private void resetTable(Mucgia pre_convert,List<Mucgia> pre,List<Mucgia> after){
    }
    private void refreshTable(){
        nvdx_tb.setItems(FXCollections.observableList(nvdx_ls_buf));
        sscd_tb.setItems(FXCollections.observableList(sscd_ls_buf));
        nvdx_tb.refresh();
        sscd_tb.refresh();
    }
    private void updateMucgia(List<Mucgia> MucgiaList,String purpose){

    }
    @FXML
    public void changeSScd(ActionEvent actionEvent) {
        try {
            TonkhoController.tk_stage.close();
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException();
        }
    }
    @FXML
    public void nvdx_press(MouseEvent mouseEvent) {
        setVisibleForBtn(false, true);
    }
    @FXML
    public void sscd_press(MouseEvent mouseEvent) {
        setVisibleForBtn(true,false);
    }
    private void setFieldFactoryTb(TableColumn<PriceAndQuantityDto, String> col1, TableColumn<PriceAndQuantityDto, String> col2){
        col1.setCellValueFactory(new PropertyValueFactory<PriceAndQuantityDto, String>("price_str"));
        col2.setCellValueFactory(new PropertyValueFactory<PriceAndQuantityDto, String>("quantity_str"));
    }
    private void setDataToTable(TableView<PriceAndQuantityDto> tb,List<Inventory> list,
                                                     Function<Inventory,Integer> toStr1,Function<Inventory,Integer> toStr2){
        List<PriceAndQuantityDto> result = new ArrayList<>();
        if (!list.isEmpty()){
            for (Inventory inventory : list){
                int quantity = toStr1.apply(inventory)-toStr2.apply(inventory);
                Long pri = inventory.getPrice();
                if (quantity>0){
                    result.add(new PriceAndQuantityDto(Integer.parseInt(String.valueOf(pri)),quantity));
                }
            }
        }
        tb.setItems(FXCollections.observableList(result));
    }
}
