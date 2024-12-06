package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.SubQueryEnum;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.repo.TructhuocRepo;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import net.sf.jasperreports.engine.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

@Component
public class BaoCaoController extends JRDefaultScriptlet implements Initializable {
    private static List<String> arr_tt = new ArrayList<>();
    @Autowired
    private TructhuocRepo tructhuocRepo;
    @Autowired
    private NguonNxRepo nguonNxRepo;
    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dvi_cbb.setItems(FXCollections.observableList(nguonNxRepo.findByStatus(StatusEnum.ROOT_STATUS.getName())));
        dvi_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxRepo.findByTen(string).orElse(null);
            }
        });
        dvi_cbb.getSelectionModel().selectFirst();
    }
    @FXML
    public void createbcnxt(ActionEvent actionEvent) throws SQLException, JRException, IOException {
//        createReportByJasper();
        writeDataToExcelFile();
    }
    @FXML
    public void dvi_selected(ActionEvent actionEvent) {

    }
    @FXML
    public void createxd_nv(ActionEvent actionEvent) {
        saveBcTieuthuXdTheoNhiemvu();
    }
    @FXML
    public void createttNlTheoKH(ActionEvent actionEvent) {

    }
    private void saveBcTieuthuXdTheoNhiemvu() {
        String file_name = "data.xlsx";
        String sheetName = "t_thu_xd_theo_n_vu";
        try{
            File file = new File(file_name);
            if (!file.exists()){
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook();
                // Now creating Sheets using sheet object
                int row_ind= createDataSheet(wb.createSheet(sheetName), 8, getCusQueryNl(SubQueryEnum.nl_begin_q1.getName(),SubQueryEnum.nl_end_q1.getName(), SubQueryEnum.nl_end.getName()));
                createDataSheet(wb.createSheet(sheetName), row_ind,getCusQueryNl(SubQueryEnum.dmn_begin_q1.getName(),SubQueryEnum.dmn_end_q1.getName(), SubQueryEnum.dmn_end.getName()));
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                // Now creating Sheets using sheet object
                mapDataToSheet(wb.getSheet(sheetName), 8);
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }
            try {
                Runtime.getRuntime().exec("cmd /c start excel "+ file_name);
            }catch (IOException io){
                throw new RuntimeException(io);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void mapDataToSheet(XSSFSheet sheet,  int begin_data_current){
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(SubQueryEnum.ttxd_nv.getName());
        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.getRow(begin_data_current+i);
            for (int j =0;j<rows_data.length;j++){
                String val = rows_data[j]==null ?"" : rows_data[j].toString();
                if (val==null){
                    row.getCell(j+4).setCellValue(val);
                } else if (StringUtils.isNumeric(val)){
                    row.getCell(j+4).setCellValue(new BigDecimal(val).intValue());
                } else {
                    row.getCell(j+4).setCellValue(val);
                }
            }
        }
    }

    private void writeDataToExcelFile() {
        String file_name = "data.xlsx";
        String sheetName = "bc_nxt";
        try{
            File file = new File(file_name);

            if (!file.exists()){
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook();
                // Now creating Sheets using sheet object
                int row_ind= createDataSheet(wb.createSheet(sheetName), 8, getCusQueryNl(SubQueryEnum.nl_begin_q1.getName(),SubQueryEnum.nl_end_q1.getName(), SubQueryEnum.nl_end.getName()));
                createDataSheet(wb.createSheet(sheetName), row_ind,getCusQueryNl(SubQueryEnum.dmn_begin_q1.getName(),SubQueryEnum.dmn_end_q1.getName(), SubQueryEnum.dmn_end.getName()));
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                // Now creating Sheets using sheet object
                int row_ind= fillDataToSocaiSheet(wb.getSheet(sheetName), 8,getCusQueryNl(SubQueryEnum.nl_begin_q1.getName(),SubQueryEnum.nl_end_q1.getName(), SubQueryEnum.nl_end.getName()));
                fillDataToSocaiSheet(wb.getSheet(sheetName), row_ind,getCusQueryNl(SubQueryEnum.dmn_begin_q1.getName(),SubQueryEnum.dmn_end_q1.getName(), SubQueryEnum.dmn_end.getName()));
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }
            try {
                Runtime.getRuntime().exec("cmd /c start excel "+ file_name);
            }catch (IOException io){
                throw new RuntimeException(io);
            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private int createDataSheet(XSSFSheet sheet, int begin_data_current,String query) {
        int sizett = arr_tt.size();
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(query);
        XSSFRow row_header = sheet.createRow(begin_data_current-1);
        XSSFRow row_header_1 = sheet.createRow(begin_data_current-2);

        for (int i=0;i<arr_tt.size();i++){
            row_header_1.createCell(8+i).setCellValue("NHAP");
            row_header_1.createCell(sizett+8+i).setCellValue("XUAT");
            row_header.createCell(8+i).setCellValue(arr_tt.get(i));
            row_header.createCell(sizett+8+i).setCellValue(arr_tt.get(i));
        }
        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(begin_data_current+i);
            for (int j =0;j<rows_data.length;j++){
                row.createCell(j+1).setCellValue(rows_data[j]==null ?"" : rows_data[j].toString());
            }
        }
        arr_tt.clear();
        return nxtls.size()+11;
    }

    private int fillDataToSocaiSheet(XSSFSheet sheet, int begin_data_current,String query) {
        int sizett = arr_tt.size();
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(query);
        XSSFRow row_header = sheet.getRow(begin_data_current-1);
        XSSFRow row_header_1 = sheet.getRow(begin_data_current-2);

        for (int i=0;i<arr_tt.size();i++){
            row_header_1.getCell(8+i).setCellValue("NHAP");
            row_header_1.getCell(sizett+8+i).setCellValue("XUAT");
            row_header.getCell(8+i).setCellValue(arr_tt.get(i));
            row_header.getCell(sizett+8+i).setCellValue(arr_tt.get(i));
        }
        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.getRow(begin_data_current+i);
            for (int j =0;j<rows_data.length;j++){
                String val = rows_data[j]==null ?"" : rows_data[j].toString();
                if (val==null){
                    row.getCell(j+1).setCellValue(val);
                } else if (StringUtils.isNumeric(val)){
                    row.getCell(j+1).setCellValue(new BigDecimal(val).intValue());
                } else {
                    row.getCell(j+1).setCellValue(val);
                }
            }
        }
        arr_tt.clear();
        return nxtls.size()+11;
    }

    private String getCusQueryNl(String begin_1,String end_q1, String end){
        String n_sum1="";
        String x_sum2="";
        String n_case_1="";
        String x_case_2="";
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            n_sum1 = n_sum1.concat("sum(n"+tt.getType()+") as "+tt.getType()+",");
            x_sum2 = x_sum2.concat("sum(x"+tt.getType()+") as "+tt.getType()+",");
            n_case_1 = n_case_1.concat("case when tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end as n"+tt.getType()+",");
            x_case_2 = x_case_2.concat("case when tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end as x"+tt.getType()+",");
        }
        return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(n_case_1).concat(x_case_2).concat(end);
    }

    private void createReportByJasper() throws SQLException, JRException, IOException{
//        HashMap<String, Object> map = new HashMap<>();
//        DataSource ds = (DataSource) MainApplicationApp.context.getBean("dataSource");
//        Connection c = ds.getConnection();
//        JasperReport jasperReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/com/xdf/xd_f371/templates/test.jrxml"));
//        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,c);
//        JasperExportManager.exportReportToPdfFile(jasperPrint,("report/test.pdf"));
//        DialogMessage.message("Thông báo", "Đã tạo báo cáo", "Thành công", Alert.AlertType.INFORMATION);
//        String cwd = Path.of("").toAbsolutePath().toString();
//        Runtime.getRuntime().exec("cmd /c explorer " + cwd+"\\report");
    }

}
