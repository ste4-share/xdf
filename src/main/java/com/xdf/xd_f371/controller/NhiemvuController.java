package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class NhiemvuController implements Initializable {
    public static Stage nvStage;

    @Autowired
    private QuarterRepository quarterRepository;
    @Autowired
    private ChitietNhiemvuRepo chitietNhiemvuRepo;
    @Autowired
    private NguonNxRepo nguonNxRepo;
    @Autowired
    private HanmucNhiemvu2Repository hanmucNhiemvu2Repository;
    @Autowired
    private HanmucNhiemvuRepo hanmucNhiemvuRepo;

    @FXML
    TableView<HanmucNhiemvu2Dto> tieuthunhiemvu;
    @FXML
    TableView<ChitieuNhiemvuDto> ctnv_tb;
    @FXML
    TableColumn<ChitieuNhiemvuDto, String> t2_tt,t2_nv,t2_ct,t2_tk,t2_md,t2_nl;
    @FXML
    TableColumn<HanmucNhiemvu2Dto, String> nv,ct,xang,diezel,daubay;
    @FXML
    ComboBox<Quarter> quy_cbb;
    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @FXML
    ComboBox<String> donvi;
    @FXML
    TableView<NhiemVuDto> nv_tb;
    @FXML
    TableColumn<NhiemVuDto, String> tennv, ctnv,lnv, khoi;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initQuarterCbb();
        donvi.setItems(FXCollections.observableList(List.of("F bộ")));
        donvi.getSelectionModel().selectFirst();
        inithanmucTb();
        initNvTb();
        initDviTb();
    }

    private void initDviTb() {
        dvi_cbb.setItems(FXCollections.observableList(nguonNxRepo.findByAllBy()));
        dvi_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "": object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxRepo.findByTen(string).orElse(null);
            }
        });
        dvi_cbb.getSelectionModel().selectFirst();
    }

    private void setCtnv_tb(int dvi_id){
        ctnv_tb.setItems(FXCollections.observableList(hanmucNhiemvuRepo.findAllByUnit(dvi_id, DashboardController.findByTime.getId())));
        t2_tt.setSortable(false);
        t2_tt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(ctnv_tb.getItems().indexOf(column.getValue())+1).asString());
        t2_nv.setCellValueFactory(new PropertyValueFactory<>("ten_nv"));
        t2_ct.setCellValueFactory(new PropertyValueFactory<>("chitiet"));
        t2_tk.setCellValueFactory(new PropertyValueFactory<>("ct_tk"));
        t2_md.setCellValueFactory(new PropertyValueFactory<>("ct_md"));
        t2_nl.setCellValueFactory(new PropertyValueFactory<>("consumpt_str"));
        ctnv_tb.refresh();
    }

    private void initNvTb() {
        nv_tb.setItems(FXCollections.observableList(chitietNhiemvuRepo.findAllDtoBy(3)));
        tennv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_nv"));
        ctnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("chitiet"));
        lnv.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("ten_loai_nv"));
        khoi.setCellValueFactory(new PropertyValueFactory<NhiemVuDto, String>("khoi"));
    }

    private void inithanmucTb() {
        tieuthunhiemvu.setItems(FXCollections.observableList(hanmucNhiemvu2Repository.findAllDto()));
        nv.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("tenNv"));
        ct.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("chitiet_nhiemvu"));
        xang.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("xang"));
        diezel.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("diezel"));
        daubay.setCellValueFactory(new PropertyValueFactory<HanmucNhiemvu2Dto, String>("daubay"));
        tieuthunhiemvu.refresh();
    }

    private void initQuarterCbb(){
        quy_cbb.setItems(FXCollections.observableList(quarterRepository.findAll()));
        quy_cbb.setConverter(new StringConverter<Quarter>() {
            @Override
            public String toString(Quarter object) {
                return object==null ?"": object.getName();
            }

            @Override
            public Quarter fromString(String string) {
                return quarterRepository.findByName(quy_cbb.getValue().getName()).isPresent()?quy_cbb.getValue():null;
            }
        });
    }

    @FXML
    public void nhiemvu_selected(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount()==2){

        }
    }
    @FXML
    public void bcNlbayTheoKh(ActionEvent actionEvent) throws IOException, SQLException, JRException {
        generatePdf();
    }
    private void generatePdf() throws IOException, SQLException, JRException {
        HashMap<String, Object> map = new HashMap<>();
        DataSource ds = (DataSource) MainApplicationApp.context.getBean("dataSource");
        Connection c = ds.getConnection();
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/com/xdf/xd_f371/templates/bc_01.jrxml"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,c);
        JasperExportManager.exportReportToPdfFile(jasperPrint,("report/baocaokehoachbay.pdf"));
        DialogMessage.message("Thông báo", "Đã tạo báo cáo", "Thành công", Alert.AlertType.INFORMATION);
        String cwd = Path.of("").toAbsolutePath().toString();
        try {
            Runtime.getRuntime().exec("cmd /c explorer " + cwd+"\\report");
        } catch (IOException e) {
            throw new IOException(e);
        }
    }
    @FXML
    public void donviselected(ActionEvent actionEvent) {
    }
    @FXML
    public void addhanmucxangdau(ActionEvent actionEvent) {
        nvStage = new Stage();
        Common.openNewStage("addnew_nvhanmuc.fxml", nvStage,"HANMUC");
        inithanmucTb();
    }
    @FXML
    public void chitieunvSelected(MouseEvent mouseEvent) {

    }

    @FXML
    public void dviSelected(ActionEvent actionEvent) {
        if (dvi_cbb.getSelectionModel().getSelectedItem()!=null) {
            setCtnv_tb(dvi_cbb.getSelectionModel().getSelectedItem().getId());
        }
    }
    @FXML
    public void addnewchitieubay(ActionEvent actionEvent) {
        nvStage = new Stage();
        Common.openNewStage("add_chitieunv.fxml", nvStage,"FORM");
        if (dvi_cbb.getSelectionModel().getSelectedItem()==null) {
            setCtnv_tb(dvi_cbb.getSelectionModel().getSelectedItem().getId());
        }
    }
}
