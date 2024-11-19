package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.NormDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.model.LoaiPTEnum;
import com.xdf.xd_f371.service.NguonNXService;
import com.xdf.xd_f371.service.PhuongTienService;
import com.xdf.xd_f371.service.impl.NguonNXImp;
import com.xdf.xd_f371.service.impl.PhuongTienImp;
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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NormController implements Initializable {
    public static int nguonnx_id;
    public static Stage norm_stage;
    public static NormDto normDto;

    @FXML
    TableView<NormDto> pt_tb;
    @FXML
    ComboBox<NguonNx> units_cbb;
    @FXML
    RadioButton xe_radio,may_radio,mb_radio;
    @FXML
    TableColumn<NormDto, String> xmt_name,type_name,quantity,km,h,md,tk,createtime;

    private PhuongTienService phuongTienService = new PhuongTienImp();
    private NguonNXService nguonNXService = new NguonNXImp();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        normDto = new NormDto();
        xe_radio.setSelected(true);
        fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
        initNguonnxCbb();
    }

    private void initNguonnxCbb() {
        units_cbb.setItems(FXCollections.observableList(phuongTienService.getIdNguonnx()));
        units_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx nguonNx) {
                if (nguonNx!=null){
                    nguonnx_id = nguonNx.getId();
                }
                return nguonNx==null? "" : nguonNx.getTen();
            }

            @Override
            public NguonNx fromString(String s) {
                return nguonNXService.findById(nguonnx_id);
            }
        });
        units_cbb.getSelectionModel().selectFirst();
    }

    private void fillDatatoptTable(String type) {
        pt_tb.setItems(FXCollections.observableList(phuongTienService.getAllPt(type)));
        xmt_name.setCellValueFactory(new PropertyValueFactory<NormDto, String>("namePt"));
        type_name.setCellValueFactory(new PropertyValueFactory<NormDto, String>("typePt"));
        quantity.setCellValueFactory(new PropertyValueFactory<NormDto, String>("quantity"));
        km.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_xm_km"));
        h.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_xm_gio"));
        md.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_md_gio"));
        tk.setCellValueFactory(new PropertyValueFactory<NormDto, String>("dm_tk_gio"));
        createtime.setCellValueFactory(new PropertyValueFactory<NormDto, String>("createtime"));
    }

    public void selectUnit(ActionEvent actionEvent) {
    }

    @FXML
    public void addNewPt(ActionEvent actionEvent) throws IOException {
        normDto = new NormDto();
        normDto.setDm_md_gio(0);
        normDto.setDm_tk_gio(0);
        normDto.setDm_xm_gio(0);
        normDto.setDm_xm_km(0);
        normDto.setPt_id(0);
        Parent root = FXMLLoader.load(getClass().getResource("../add_pt.fxml"));
        Scene scene = new Scene(root);
        norm_stage = new Stage();
        norm_stage.setScene(scene);
        norm_stage.initStyle(StageStyle.DECORATED);
        norm_stage.initModality(Modality.APPLICATION_MODAL);
        norm_stage.setTitle("Thêm phương tiện");
        norm_stage.showAndWait();
        fillDatatoptTable(LoaiPTEnum.XE.name());
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
        fillDatatoptTable(LoaiPTEnum.MAYBAY_a.getNameVehicle());
    }

    @FXML
    public void pt_selected(MouseEvent mouseEvent) throws IOException {
        if (mouseEvent.getClickCount()==2){
            normDto = pt_tb.getSelectionModel().getSelectedItem();
            Parent root = FXMLLoader.load(getClass().getResource("../add_pt.fxml"));
            Scene scene = new Scene(root);
            norm_stage = new Stage();
            norm_stage.setScene(scene);
            norm_stage.initStyle(StageStyle.DECORATED);
            norm_stage.initModality(Modality.APPLICATION_MODAL);
            norm_stage.setTitle("Thêm phương tiện");
            norm_stage.showAndWait();
            if (xe_radio.isSelected()){
                fillDatatoptTable(LoaiPTEnum.XE.getNameVehicle());
                pt_tb.refresh();
            }else if (may_radio.isSelected()){
                fillDatatoptTable(LoaiPTEnum.MAY.getNameVehicle());
                pt_tb.refresh();
            }else if (mb_radio.isSelected()){
                fillDatatoptTable(LoaiPTEnum.MAYBAY_a.getNameVehicle());
                pt_tb.refresh();
            }
        }
    }
}
