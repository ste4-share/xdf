package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.cons.LoaiPTEnum;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class DinhMucPhuongTienController implements Initializable {
    public static Stage norm_stage;
    public static DinhMucPhuongTienDto dinhMucPhuongTienDto = new DinhMucPhuongTienDto();
    public static List<DinhMucPhuongTienDto> ls = new ArrayList<>();
    @FXML
    TableView<DinhMucPhuongTienDto> pt_tb;
    @FXML
    private Button addBtn;
    @FXML
    ComboBox<NguonNx> units_cbb;
    @FXML
    ComboBox<Integer> year_cbb;
    @FXML
    RadioButton xe_radio,may_radio,mb_radio;
    @FXML
    TextField search_tf;
    @FXML
    TableColumn<DinhMucPhuongTienDto, String> stt,xmt_name,type_name,quantity,km,h,md,tk,createtime,tructhuoc;

    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private NguonNxService nguonNxService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pt_tb.setPrefWidth(DashboardController.screenWidth);
        pt_tb.setPrefHeight(DashboardController.screenHeigh-300);
        xe_radio.setSelected(true);
        setfactoryForTable();
        initYearCbb();
        initNguonnxCbb();
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        Common.hoverButton(addBtn, "#ffffff");
    }
    private void initYearCbb() {
        year_cbb.setItems(FXCollections.observableList(dinhmucService.findAllYear()));
        year_cbb.getSelectionModel().selectFirst();
    }
    private void initNguonnxCbb() {
        ComponentUtil.setItemsToComboBox(units_cbb,nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()),NguonNx::getTen, input-> nguonNxService.findByTen(input).orElse(null));
        units_cbb.getSelectionModel().selectLast();
    }
    private void fillDatatoptTable(String lpt) {
        NguonNx dvi = units_cbb.getSelectionModel().getSelectedItem();
        Integer y = year_cbb.getSelectionModel().getSelectedItem();
        if (dvi!=null && y!=null){
            ls = dinhmucService.findAllBy(y, lpt);
            if (!ls.isEmpty()){
                pt_tb.setItems(FXCollections.observableList(ls));
            }else {
                if (DialogMessage.callAlertWithMessage(null,null,"Chưa đặt định mức cho phương tiện năm " + LocalDate.now().getYear()+
                                ". Bạn có muốn chuyển định mức năm "+(LocalDate.now().getYear()-1)+" để dùng cho năm "+(LocalDate.now().getYear())
                        , Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                    switchDinhmuc();
                    xe_radio.setSelected(true);
                    fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
                }
            }
        }
    }
    private void mapPtTb(List<DinhMucPhuongTienDto> ls){
        pt_tb.setItems(FXCollections.observableList(ls));
        pt_tb.refresh();
    }
    private void switchDinhmuc(){
        List<DinhMuc> previousDm = dinhmucService.findAllByYear((LocalDate.now().getYear()-1));
        if (!previousDm.isEmpty()){
            previousDm.forEach(x->{
                x.setYears(LocalDate.now().getYear());
                dinhmucService.save(x);
            });
            DialogMessage.successShowing("Chuyen doi thanh cong");
        } else {
            DialogMessage.errorShowing("Khong co du lieu tu nam "+(LocalDate.now().getYear()-1));
        }
    }
    private void setfactoryForTable(){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(pt_tb.getItems().indexOf(column.getValue())+1).asString());
        xmt_name.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("name_pt"));
        type_name.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("typeName"));
        quantity.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("quantity"));
        tructhuoc.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("nameDv"));
        km.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_xm_km"));
        h.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_xm_gio"));
        md.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_md_gio"));
        tk.setCellValueFactory(new PropertyValueFactory<DinhMucPhuongTienDto, String>("dm_tk_gio"));
    }
    @FXML
    public void selectUnit(ActionEvent actionEvent) {
        if (xe_radio.isSelected()){
            fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        }else if (may_radio.isSelected()){
            fillDatatoptTable(LoaiPTEnum.MAY.getNameVehicle());
        }else if (mb_radio.isSelected()){
            fillDatatoptTable(LoaiPTEnum.MAYBAY.getNameVehicle());
        }
    }
    @FXML
    public void addNewPt(ActionEvent actionEvent) throws IOException {
        dinhMucPhuongTienDto = new DinhMucPhuongTienDto();
        dinhMucPhuongTienDto.setDm_md_gio(0);
        dinhMucPhuongTienDto.setDm_tk_gio(0);
        dinhMucPhuongTienDto.setDm_xm_gio(0);
        dinhMucPhuongTienDto.setDm_xm_km(0);
        dinhMucPhuongTienDto.setPhuongtien_id(0);
        dinhMucPhuongTienDto.setQuantity(0);
        dinhMucPhuongTienDto.setNnx_id(units_cbb.getSelectionModel().getSelectedItem().getId());
        Integer y = year_cbb.getSelectionModel().getSelectedItem();
        if (xe_radio.isSelected()){
            ls = dinhmucService.findAllBy(y,LoaiPTEnum.XE.getNameVehicle());
        }else if (may_radio.isSelected()){
            ls = dinhmucService.findAllBy(y,LoaiPTEnum.MAY.getNameVehicle());
        }else if (mb_radio.isSelected()){
            ls = dinhmucService.findAllBy(y,LoaiPTEnum.MAYBAY.getNameVehicle());
        }
        openAddScreen();
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        xe_radio.setSelected(true);
    }
    @FXML
    public void pt_selected(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            dinhMucPhuongTienDto = pt_tb.getSelectionModel().getSelectedItem();
            if (dinhMucPhuongTienDto!=null){
                openAddScreen();
                fillDatatoptTable(dinhMucPhuongTienDto.getType());
            }
        }
    }
    @FXML
    public void xe_selected(ActionEvent actionEvent) {
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
    }
    @FXML
    public void may_selected(ActionEvent actionEvent) {
        fillDatatoptTable(LoaiPTEnum.MAY.getNameVehicle());
    }
    @FXML
    public void maybay_selected(ActionEvent actionEvent) {
        fillDatatoptTable(LoaiPTEnum.MAYBAY.getNameVehicle());
    }

    private void openAddScreen(){
        norm_stage = new Stage();
        Common.openNewStage("add_pt.fxml",norm_stage,"Thêm phương tiện", StageStyle.DECORATED);
    }
    @FXML
    public void yearAction(ActionEvent actionEvent) {
        xe_radio.setSelected(true);
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
    }
    @FXML
    public void searchKr(KeyEvent keyEvent) {
        String t = search_tf.getText().trim();
        if (!t.isEmpty()){
            mapPtTb(ls.stream().filter(x->x.getName_pt().toLowerCase().contains(t)).toList());
        }else{
            mapPtTb(ls);
        }
    }
    @FXML
    public void searchClicked(MouseEvent mouseEvent) {
        search_tf.selectAll();
    }
}
