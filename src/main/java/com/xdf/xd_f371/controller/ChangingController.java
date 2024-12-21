package com.xdf.xd_f371.controller;

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

@Component
public class ChangingController implements Initializable {
    public static Stage addAff_stage;
    public static int sum = 0;
    public static String quantity ;
    public static String quantity_convert =null;
    private static SpotDto tonkho_selected;
    private static Map<String, String> sscd_ls_buf = new HashMap<>();
    private static Map<String, String> nvdx_ls_buf = new HashMap<>();

    @FXML
    private TableView<String> nvdx_tb,sscd_tb;
    @FXML
    private TableColumn<String, String> nvdx_price,nvdx_quantity,sscd_price,sscd_quantity;
    @FXML
    private Label petro_name;
    @FXML
    private Button nvdx_sscd, sscd_nvdx,changeBtn,cancel_btn;

    @Autowired
    private InventoryService inventoryService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAff_stage = new Stage();
        petro_name.setText(TonkhoController.pickTonKho.getTenxd());
        tonkho_selected = TonkhoController.pickTonKho;
        setNvdxTable();
        setSScdTable();
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
    private void setNvdxTable() {
        nvdx_ls_buf = setDataToTable(nvdx_tb);
        setFieldFactoryTb(nvdx_price, nvdx_quantity);
    }
    private void setSScdTable() {
        sscd_ls_buf = setDataToTable(sscd_tb);
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
        for(int i = 0;i < MucgiaList.size(); i++){
            Mucgia mucgia = mucgiaService.findAllMucgiaUnique(tonkho_selected.getLxd_id(),DashboardController.findByTime.getId(),MucgiaList.get(i).getPrice()).orElse(null);
            if (mucgia==null){
                Mucgia after_convert = new Mucgia();
                after_convert.setPrice(MucgiaList.get(i).getPrice());
                after_convert.setAmount(MucgiaList.get(i).getAmount());
                after_convert.setStatus(MucGiaEnum.IN_STOCK.getStatus());
                after_convert.setItem_id(tonkho_selected.getLxd_id());
                after_convert.setQuarter_id(DashboardController.findByTime.getId());
                mucgiaService.save(after_convert);
            }else{
                mucgia.setAmount(MucgiaList.get(i).getAmount());
                mucgiaService.save(mucgia);
            }
        }
    }

    @FXML
    public void changeSScd(ActionEvent actionEvent) {
        try {
//            updateMucgia(nvdx_ls_buf,mucgiaService.findByName(AssignTypeEnum.NVDX.getName()).getId());
//            updateMucgia(sscd_ls_buf,mucgiaService.findByName(AssignTypeEnum.SSCD.getName()).getId());
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

    private void setFieldFactoryTb(TableColumn<Mucgia, String> col1,TableColumn<Mucgia, String> col2){
        col1.setCellValueFactory(new PropertyValueFactory<Mucgia, String>("price"));
        col2.setCellValueFactory(new PropertyValueFactory<Mucgia, String>("amount"));
    }
    private Map<String, String> setDataToTable(TableView<String> tb){
        int petroId = tonkho_selected.getLxd_id();
        List<Inventory> list = inventoryService.findByPetro_idAndQuarter_id(petroId,DashboardController.findByTime.getId(),MucGiaEnum.IN_STOCK.getStatus());
        Map<String, String> map = new HashMap<>();
        if (!list.isEmpty()){
            for (Inventory inventory : list){
                map.put(inventory.getPrice(), inventory.getNhap_nvdx()-inventory.getXuat_nvdx())
            }
        }
        tb.setItems(FXCollections.observableList(list.stream().map()));
        return map;
    }
}
