package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.dto.NormDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.NguonNxService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Component
public class DinhMucPhuongTienController implements Initializable {
    public static int nguonnx_id;
    public static Stage norm_stage;
    public static DinhMucPhuongTienDto dinhMucPhuongTienDto;

    @FXML
    TableView<DinhMucPhuongTienDto> pt_tb;
    @FXML
    ComboBox<NguonNx> units_cbb;
    @FXML
    RadioButton xe_radio,may_radio,mb_radio;
    @FXML
    TableColumn<NormDto, String> xmt_name,type_name,quantity,km,h,md,tk,createtime,chungloaipt;

    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private NguonNxService nguonNxService;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pt_tb.setPrefWidth(DashboardController.screenWidth);
        pt_tb.setPrefHeight(DashboardController.screenHeigh-300);
        dinhMucPhuongTienDto = new DinhMucPhuongTienDto();
        xe_radio.setSelected(true);
        fillDatatoptTable();
        initNguonnxCbb();
    }

    private void initNguonnxCbb() {
        units_cbb.setItems(FXCollections.observableList(nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName())));
        units_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx nguonNx) {
                return nguonNx==null? "" : nguonNx.getTen();
            }

            @Override
            public NguonNx fromString(String s) {
                return nguonNxService.findById(units_cbb.getValue().getId()).get();
            }
        });
        units_cbb.getSelectionModel().selectFirst();
    }

    private void fillDatatoptTable() {
        pt_tb.setItems(FXCollections.observableList(dinhmucService.findAllBy(DashboardController.findByTime.getId())));
        xmt_name.setCellValueFactory(new PropertyValueFactory<NormDto, String>("name_pt"));
        type_name.setCellValueFactory(new PropertyValueFactory<NormDto, String>("typeName"));
        quantity.setCellValueFactory(new PropertyValueFactory<NormDto, String>("quantity"));
        chungloaipt.setCellValueFactory(new PropertyValueFactory<NormDto, String>("type"));
        km.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_xm_km"));
        h.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_xm_gio"));
        md.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_md_gio"));
        tk.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_tk_gio"));
    }

    public void selectUnit(ActionEvent actionEvent) {
    }

    @FXML
    public void addNewPt(ActionEvent actionEvent) throws IOException {
        dinhMucPhuongTienDto = new DinhMucPhuongTienDto();
        dinhMucPhuongTienDto.setDm_md_gio(0);
        dinhMucPhuongTienDto.setDm_tk_gio(0);
        dinhMucPhuongTienDto.setDm_xm_gio(0);
        dinhMucPhuongTienDto.setDm_xm_km(0);
        dinhMucPhuongTienDto.setPhuongtien_id(0);
        Parent root = FXMLLoader.load(getClass().getResource("../add_pt.fxml"));
        Scene scene = new Scene(root);
        norm_stage = new Stage();
        norm_stage.setScene(scene);
        norm_stage.initStyle(StageStyle.DECORATED);
        norm_stage.initModality(Modality.APPLICATION_MODAL);
        norm_stage.setTitle("Thêm phương tiện");
        norm_stage.showAndWait();
        fillDatatoptTable();
    }
    @FXML
    public void xe_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void may_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void maybay_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void pt_selected(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            dinhMucPhuongTienDto = pt_tb.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("../add_pt.fxml"));
            Scene scene = new Scene(root);
            norm_stage = new Stage();
            norm_stage.setScene(scene);
            norm_stage.initStyle(StageStyle.DECORATED);
            norm_stage.initModality(Modality.APPLICATION_MODAL);
            norm_stage.setTitle("Thêm phương tiện");
            norm_stage.showAndWait();

        }
    }
}
