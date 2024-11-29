package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.dto.*;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.repo.*;
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
    private NhiemvuRepository nhiemvuRepository;

    @FXML
    TableView<HanmucNhiemvu2Dto> tieuthunhiemvu;
    @FXML
    TableColumn<HanmucNhiemvu2Dto, String> nv,ct,xang,diezel,daubay;
    @FXML
    ComboBox<Quarter> quy_cbb;
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
}
