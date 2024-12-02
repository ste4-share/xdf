package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.repo.TructhuocRepo;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
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
import java.util.ResourceBundle;
@Component
public class BaoCaoController extends JRDefaultScriptlet implements Initializable {

    @Autowired
    private TructhuocRepo tructhuocRepo;

    @Autowired
    private

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void createbc(ActionEvent actionEvent) throws SQLException, JRException, IOException {
        HashMap<String, Object> map = new HashMap<>();
        DataSource ds = (DataSource) MainApplicationApp.context.getBean("dataSource");
        Connection c = ds.getConnection();
        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/com/xdf/xd_f371/templates/test.jrxml"));
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,c);
        JasperExportManager.exportReportToPdfFile(jasperPrint,("report/test.pdf"));
        DialogMessage.message("Thông báo", "Đã tạo báo cáo", "Thành công", Alert.AlertType.INFORMATION);
        String cwd = Path.of("").toAbsolutePath().toString();
        Runtime.getRuntime().exec("cmd /c explorer " + cwd+"\\report");
    }
}
