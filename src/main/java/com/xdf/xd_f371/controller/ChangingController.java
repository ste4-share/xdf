package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.AssignTypePriceDto;
import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.Mucgia;
import com.xdf.xd_f371.model.AssignTypeEnum;
import com.xdf.xd_f371.model.MucGiaEnum;
import com.xdf.xd_f371.service.MucgiaService;
import com.xdf.xd_f371.service.impl.MucgiaImp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChangingController implements Initializable {
    public static Stage addAff_stage;
    public static int sum = 0;
    public static String nvdx ;
    public static String sscd ;
    public static String quantity ;
    public static String quantity_convert =null;
    private static SpotDto tonkho_selected;
    private static List<AssignTypePriceDto> sscd_ls_buf;
    private static List<AssignTypePriceDto> nvdx_ls_buf;

    @FXML
    private TableView<AssignTypePriceDto> nvdx_tb,sscd_tb;
    @FXML
    private TableColumn<AssignTypePriceDto, String> nvdx_price,nvdx_quantity,sscd_price,sscd_quantity;
    @FXML
    private Label petro_name;
    @FXML
    private Button nvdx_sscd, sscd_nvdx;

    private MucgiaService mucgiaService = new MucgiaImp();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        nvdx_ls_buf = setDataToTable(nvdx_tb, AssignTypeEnum.NVDX.getName());
        setFieldFactoryTb(nvdx_price, nvdx_quantity);
    }
    private void setSScdTable() {
        sscd_ls_buf = setDataToTable(sscd_tb, AssignTypeEnum.SSCD.getName());
        setFieldFactoryTb(sscd_price, sscd_quantity);
    }

    private void openChangeQuantityForm(){
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("../quantity_form.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        addAff_stage = new Stage();
        addAff_stage.setScene(scene);
        addAff_stage.initStyle(StageStyle.DECORATED);
        addAff_stage.initModality(Modality.APPLICATION_MODAL);
        addAff_stage.showAndWait();

    }
    private void alert(String message){
        Alert info= new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Lỗi");
        info.setContentText(message);
        info.showAndWait();
    }

    public void cancelForm(ActionEvent actionEvent) {
        TonkhoController.ChangeSScd_stage.close();
    }

    @FXML
    public void nvdxToSscdAction(ActionEvent actionEvent) {
        AssignTypePriceDto assignTypePriceDto = nvdx_tb.getSelectionModel().getSelectedItem();
        if (assignTypePriceDto.getSoluong().equals("0")){
            alert("Số lượng mức giá " + assignTypePriceDto.getPrice() + " đã hết.");
        }else {
            if (assignTypePriceDto!=null){
                quantity = assignTypePriceDto.getSoluong();
                openChangeQuantityForm();
                resetTable(assignTypePriceDto, nvdx_ls_buf, sscd_ls_buf);
                refreshTable();
            }else{
                alert("Vui lòng chọn mức giá.");
            }
        }
    }

    @FXML
    public void sscdToNvdxAction(ActionEvent actionEvent) {
        AssignTypePriceDto assignTypePriceDto = sscd_tb.getSelectionModel().getSelectedItem();
        if (assignTypePriceDto.getSoluong().equals("0")){
            alert("Số lượng mức giá " + assignTypePriceDto.getPrice() + " đã hết.");
        }else {
            if (assignTypePriceDto!=null){
                quantity = assignTypePriceDto.getSoluong();
                openChangeQuantityForm();
                try {
                    resetTable(assignTypePriceDto, sscd_ls_buf, nvdx_ls_buf);
                }catch (Exception e){
                    e.printStackTrace();
                }
                refreshTable();
            } else {
                alert("Vui lòng chọn mức giá.");
            }
        }
    }

    private void resetTable(AssignTypePriceDto pre_convert,List<AssignTypePriceDto> pre,List<AssignTypePriceDto> after){
        if (quantity_convert!=null || quantity_convert!="0"){
            int sl_conlai = Integer.parseInt(pre_convert.getSoluong()) -Integer.parseInt(quantity_convert);
            AssignTypePriceDto after_convert = after.stream().filter(x -> x.getPrice().equals(pre_convert.getPrice())).findAny().orElse(null);
            if (after_convert==null){
                after.add(new AssignTypePriceDto(tonkho_selected.getLxd_id(), pre_convert.getPrice(), quantity_convert));
                for (int i = 0; i< pre.size(); i++){
                    if (pre.get(i).getPrice().equals(pre_convert.getPrice())) {
                        pre.get(i).setSoluong(String.valueOf(sl_conlai));
                    }
                }
            }else{
                int sl = Integer.parseInt(after_convert.getSoluong()) + Integer.parseInt(quantity_convert);
                for (int i =0; i< pre.size(); i++){
                    if (pre.get(i).getPrice().equals(pre_convert.getPrice())){
                        pre.get(i).setSoluong(String.valueOf(sl_conlai));
                    }
                }
                for (int i =0; i< after.size(); i++){
                    if (after.get(i).getPrice().equals(after_convert.getPrice())){
                        after.get(i).setSoluong(String.valueOf(sl));
                    }
                }
            }
        }
    }

    private void refreshTable(){
        nvdx_tb.setItems(FXCollections.observableList(nvdx_ls_buf));
        sscd_tb.setItems(FXCollections.observableList(sscd_ls_buf));
        nvdx_tb.refresh();
        sscd_tb.refresh();
    }

    private void updateMucgia(List<AssignTypePriceDto> assignTypePriceDtoList,int ass_id){
        for(int i = 0;i < assignTypePriceDtoList.size(); i++){
            Mucgia mucgia = mucgiaService.findMucgiaByGia(tonkho_selected.getLxd_id(),DashboardController.findByTime.getId(),Integer.parseInt(assignTypePriceDtoList.get(i).getPrice()),ass_id);
            if (mucgia==null){
                Mucgia after_convert = new Mucgia();
                after_convert.setAssign_type_id(ass_id);
                after_convert.setPrice(Integer.parseInt(assignTypePriceDtoList.get(i).getPrice()));
                after_convert.setAmount(Integer.parseInt(assignTypePriceDtoList.get(i).getSoluong()));
                after_convert.setStatus(MucGiaEnum.IN_STOCK.getStatus());
                after_convert.setItem_id(tonkho_selected.getLxd_id());
                after_convert.setQuarter_id(DashboardController.findByTime.getId());
                mucgiaService.createNew(after_convert);
            }else{
                mucgia.setAmount(Integer.parseInt(assignTypePriceDtoList.get(i).getSoluong()));
                mucgiaService.updateMucGia(mucgia);
            }
        }
    }

    @FXML
    public void changeSScd(ActionEvent actionEvent) {
        try {
            updateMucgia(nvdx_ls_buf,mucgiaService.findByName(AssignTypeEnum.NVDX.getName()).getId());
            updateMucgia(sscd_ls_buf,mucgiaService.findByName(AssignTypeEnum.SSCD.getName()).getId());
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

    private void setFieldFactoryTb(TableColumn<AssignTypePriceDto, String> col1,TableColumn<AssignTypePriceDto, String> col2){
        col1.setCellValueFactory(new PropertyValueFactory<AssignTypePriceDto, String>("price"));
        col2.setCellValueFactory(new PropertyValueFactory<AssignTypePriceDto, String>("soluong"));
    }
    private List<AssignTypePriceDto> setDataToTable(TableView<AssignTypePriceDto> tb, String assTypeName){
        int assId = mucgiaService.findByName(assTypeName).getId();
        int petroId = tonkho_selected.getLxd_id();
        List<AssignTypePriceDto> list = mucgiaService.getPriceAndQuanTityByAssId(assId,petroId,DashboardController.findByTime.getId());
        tb.setItems(FXCollections.observableList(list));
        return list;
    }
}
