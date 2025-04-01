package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.InventoryDto;
import com.xdf.xd_f371.dto.InventoryUnitDto;
import com.xdf.xd_f371.dto.PriceAndQuantityDto;
import com.xdf.xd_f371.dto.TonkhoDto;
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
    public static double quantity =0;
    public static double quantity_convert =0;
    private static InventoryUnitDto tonkho_selected;
    private static List<PriceAndQuantityDto> sscd_ls_buf = new ArrayList<>();
    private static List<PriceAndQuantityDto> nvdx_ls_buf = new ArrayList<>();
    public static List<InventoryDto> list = new ArrayList<>();
    @FXML
    private TableView<PriceAndQuantityDto> nvdx_tb,sscd_tb;
    @FXML
    private TableColumn<PriceAndQuantityDto, String> nvdx_price,nvdx_quantity,sscd_price,sscd_quantity;
    @FXML
    private Label petro_name,unit_lb;
    @FXML
    private Button nvdx_sscd, sscd_nvdx,changeBtn,cancel_btn;
    @Autowired
    private InventoryService inventoryService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        nvdx_ls_buf = new ArrayList<>();
        tonkho_selected = TonkhoController.pickTonKho;
        petro_name.setText(tonkho_selected.getTenxd());
        initListInv_p();
        setNvdxTable(nvdx_tb);
        setSScdTable(sscd_tb);
        setHoverForBtn();
        setLabels();
    }

    private void initListInv_p() {
        if (DashboardController.ref_Dv!=null){
            list = inventoryService.findPreInventoryPetroFollowUnit(tonkho_selected.getPetro_id(),DashboardController.ref_Dv.getId());
        }else{
            list = inventoryService.findPreInventoryPetro(tonkho_selected.getPetro_id());
            changeBtn.setDisable(true);
            nvdx_sscd.setVisible(false);
            sscd_nvdx.setVisible(false);
        }
    }

    private void setLabels() {
        if (DashboardController.ref_Dv!=null){
            unit_lb.setText(DashboardController.ref_Dv.getTen());
        }else{
            unit_lb.setText("Toàn đơn vị");
        }
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
        if (DialogMessage.callAlertWithMessage(null, "Luu thay doi", "Xac nhan luu thay doi", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
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
                                                     Function<InventoryDto,Double> toStr1,Function<InventoryDto,Double> toStr2){
        List<PriceAndQuantityDto> result = new ArrayList<>();
        if (list!=null && !list.isEmpty()) {
            for (InventoryDto inventory : list) {
                double quantity = toStr1.apply(inventory)-toStr2.apply(inventory);
                double pri = inventory.getDon_gia();
                if (quantity>0) {
                    result.add(new PriceAndQuantityDto(pri,quantity));
                }
            }
        }
        tb.setItems(FXCollections.observableList(result));
        tb.refresh();
        return result;
    }
    @FXML
    public void nvdxClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void sscdClicked(MouseEvent mouseEvent) {
    }
}
