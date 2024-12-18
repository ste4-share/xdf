package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.model.MucGiaEnum;
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
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class ChangingController implements Initializable {
    public static Stage addAff_stage;
    public static int sum = 0;
    public static String quantity ;
    public static String quantity_convert =null;
    private static SpotDto tonkho_selected;
    private static List<Mucgia> sscd_ls_buf;
    private static List<Mucgia> nvdx_ls_buf;

    @FXML
    private TableView<Mucgia> nvdx_tb,sscd_tb;
    @FXML
    private TableColumn<Mucgia, String> nvdx_price,nvdx_quantity,sscd_price,sscd_quantity;
    @FXML
    private Label petro_name;
    @FXML
    private Button nvdx_sscd, sscd_nvdx;


    @Autowired
    private MucgiaService mucgiaService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addAff_stage = new Stage();
        petro_name.setText(TonkhoController.pickTonKho.getTenxd());
        tonkho_selected = TonkhoController.pickTonKho;
        sscd_ls_buf = new ArrayList<>();
        nvdx_ls_buf = new ArrayList<>();
        setNvdxTable();
        setSScdTable();
        setVisibleForBtn(true, true);
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
        TonkhoController.ChangeSScd_stage.close();
    }

    @FXML
    public void nvdxToSscdAction(ActionEvent actionEvent) {
//        Mucgia Mucgia = nvdx_tb.getSelectionModel().getSelectedItem();
//        if (Mucgia.getAmount()==0){
//            alert("Số lượng mức giá " + Mucgia.getPrice() + " đã hết.");
//        }else {
//            if (Mucgia!=null){
//                quantity = Mucgia.getAmount();
//                openChangeQuantityForm();
//                resetTable(Mucgia, nvdx_ls_buf, sscd_ls_buf);
//                refreshTable();
//            }else{
//                alert("Vui lòng chọn mức giá.");
//            }
//        }
    }

    @FXML
    public void sscdToNvdxAction(ActionEvent actionEvent) {
//        Mucgia mucgia = sscd_tb.getSelectionModel().getSelectedItem();
//        if (mucgia.getAmount().==("0")){
//            alert("Số lượng mức giá " + mucgia.getPrice() + " đã hết.");
//        }else {
//            if (mucgia!=null){
//                quantity = mucgia.getAmount();
//                openChangeQuantityForm();
//                try {
//                    resetTable(mucgia, sscd_ls_buf, nvdx_ls_buf);
//                }catch (Exception e){
//                    e.printStackTrace();
//                }
//                refreshTable();
//            } else {
//                alert("Vui lòng chọn mức giá.");
//            }
//        }
    }

    private void resetTable(Mucgia pre_convert,List<Mucgia> pre,List<Mucgia> after){
//        if (quantity_convert!=null || quantity_convert!="0"){
//            int sl_conlai = pre_convert.getAmount() -Integer.parseInt(quantity_convert);
//            Mucgia after_convert = after.stream().filter(x -> x.getPrice()==pre_convert.getPrice()).findAny().orElse(null);
//            if (after_convert==null){
//                after.add(new Mucgia(tonkho_selected.getLxd_id(), pre_convert.getPrice(), quantity_convert));
//                for (int i = 0; i< pre.size(); i++){
//                    if (pre.get(i).getPrice()==pre_convert.getPrice()) {
//                        pre.get(i).getAmount(String.valueOf(sl_conlai));
//                    }
//                }
//            }else{
//                int sl = Integer.parseInt(after_convert.getSoluong()) + Integer.parseInt(quantity_convert);
//                for (int i =0; i< pre.size(); i++){
//                    if (pre.get(i).getPrice().equals(pre_convert.getPrice())){
//                        pre.get(i).getAmount(String.valueOf(sl_conlai));
//                    }
//                }
//                for (int i =0; i< after.size(); i++){
//                    if (after.get(i).getPrice().equals(after_convert.getPrice())){
//                        after.get(i).setSoluong(String.valueOf(sl));
//                    }
//                }
//            }
//        }
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
            TonkhoController.ChangeSScd_stage.close();
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
    private List<Mucgia> setDataToTable(TableView<Mucgia> tb){
        int petroId = tonkho_selected.getLxd_id();
        List<Mucgia> list = mucgiaService.findAllMucgiaByItemID(petroId,DashboardController.findByTime.getId(),MucGiaEnum.IN_STOCK.getStatus());
        tb.setItems(FXCollections.observableList(list));
        return list;
    }
}
