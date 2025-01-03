package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.PriceAndQuantityDto;
import com.xdf.xd_f371.dto.TonkhoDto;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.cons.MucGiaEnum;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
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
import java.util.*;
import java.util.function.Function;

@Component
public class ChangingController implements Initializable {
    public static Stage addAff_stage;
    public static int sum = 0;
    public static Long quantity =0L;
    public static int quantity_convert =0;
    private static TonkhoDto tonkho_selected;
    private static List<PriceAndQuantityDto> sscd_ls_buf = new ArrayList<>();
    private static List<PriceAndQuantityDto> nvdx_ls_buf = new ArrayList<>();
    public static List<InventoryDto> list = new ArrayList<>();
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
        nvdx_ls_buf = new ArrayList<>();
        tonkho_selected = TonkhoController.pickTonKho;
        petro_name.setText(tonkho_selected.getTenxd());
        list = inventoryService.findPreInventoryPetro(tonkho_selected.getPetro_id());
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
        nvdx_ls_buf = setDataToTable(tb, list, InventoryDto::getNhap_nvdx, InventoryDto::getXuat_nvdx);
        setFieldFactoryTb(nvdx_price, nvdx_quantity);
    }
    private void setSScdTable(TableView<PriceAndQuantityDto> tb) {
        sscd_ls_buf= setDataToTable(tb, list, InventoryDto::getNhap_sscd, InventoryDto::getXuat_sscd);
        setFieldFactoryTb(sscd_price, sscd_quantity);
    }
    private void openChangeQuantityForm(){
        addAff_stage = new Stage();
        Common.openNewStage("quantity_form.fxml", addAff_stage, "EDIT", StageStyle.DECORATED);
    }
    public void cancelForm(ActionEvent actionEvent) {
        TonkhoController.tk_stage.close();
    }
    @FXML
    public void nvdxToSscdAction(ActionEvent actionEvent) {
        PriceAndQuantityDto p = nvdx_tb.getSelectionModel().getSelectedItem();
        if (p!=null){
            quantity=p.getQuantity();
            openChangeQuantityForm();
            updateTableView(p);
        }
    }
    private void updateTableView(PriceAndQuantityDto p){
        list.stream().filter(x->x.getDon_gia()==p.getPrice()).findFirst()
                .ifPresent(i -> {i.setNhap_nvdx(i.getNhap_nvdx()-quantity_convert);i.setNhap_sscd(i.getNhap_sscd()+quantity_convert);});
        setDataToTable(nvdx_tb, list, InventoryDto::getNhap_nvdx, InventoryDto::getXuat_nvdx);
        setDataToTable(sscd_tb, list, InventoryDto::getNhap_sscd, InventoryDto::getXuat_sscd);
    }
    @FXML
    public void sscdToNvdxAction(ActionEvent actionEvent) {
        PriceAndQuantityDto p = sscd_tb.getSelectionModel().getSelectedItem();
        if (p!=null){
            quantity=p.getQuantity();
            openChangeQuantityForm();
            quantity_convert=quantity_convert*(-1);
            updateTableView(p);
        }
    }
    @FXML
    public void changeSScd(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("", "Luu thay doi", "Xac nhan luu thay doi", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            try {
                list.forEach(x-> {
                    inventoryService.saveInventoryWithLedger(x);
                });
                TonkhoController.tk_stage.close();
            } catch (Exception e){
                DialogMessage.errorShowing(e.getMessage());
            }
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
    private List<PriceAndQuantityDto> setDataToTable(TableView<PriceAndQuantityDto> tb,List<InventoryDto> list,
                                                     Function<InventoryDto,Long> toStr1,Function<InventoryDto,Long> toStr2){
        List<PriceAndQuantityDto> result = new ArrayList<>();
        if (!list.isEmpty()) {
            for (InventoryDto inventory : list) {
                Long quantity = toStr1.apply(inventory)-toStr2.apply(inventory);
                int pri = inventory.getDon_gia();
                if (quantity>0) {
                    result.add(new PriceAndQuantityDto(Integer.parseInt(String.valueOf(pri)),quantity));
                }
            }
        }
        tb.setItems(FXCollections.observableList(result));
        return result;
    }
    @FXML
    public void nvdxClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void sscdClicked(MouseEvent mouseEvent) {
    }
}
