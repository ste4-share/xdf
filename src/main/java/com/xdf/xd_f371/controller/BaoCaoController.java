package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.service.TructhuocService;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.util.StringConverter;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class BaoCaoController implements Initializable {
    private static List<String> arr_tt = new ArrayList<>();
    @Autowired
    private TructhuocService tructhuocService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private QuarterService quarterService;
    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @FXML
    ComboBox<Quarter> quy_cbb;
    @FXML
    Label fromdate,todate;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initdvcbb();
        initquycbb();
    }
    @FXML
    public void createbcnxt(ActionEvent actionEvent) throws SQLException, IOException {
        String file_name = "src/main/resources/com/xdf/xd_f371/xlsx_template/data.xlsx";
        String dest_file = "baocao/baocao.xlsx";
        mapdataToNxtSheet(file_name);
        saveBcTieuthuXdTheoNhiemvu(file_name);
        saveBcThanhtoanNhienlieuBayTheoKeHoach(file_name);
        copyFileExcel(file_name,dest_file);
        try {
            String[] env = {"ComSpec='C:\\WINDOWS\\system32\\cmd.exe'"};

            Process process = Runtime.getRuntime().exec("cmd /c start .",env);
//            Runtime.getRuntime().exec("cmd /c start excel "+ dest_file);
            BufferedReader stdOut = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader stdErr = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;
            while ((line = stdOut.readLine()) != null) {
                System.out.println("OUTPUT: " + line);
            }
            while ((line = stdErr.readLine()) != null) {
                System.err.println("ERROR: " + line);
            }
            process.waitFor();
        }catch (IOException | InterruptedException io){
            DialogMessage.message("Message", io.getMessage(), "fail!", Alert.AlertType.INFORMATION);
            throw new RuntimeException(io);
        }
        DialogMessage.message("Message", "Cap nhat thanh cong.", "Successfully!", Alert.AlertType.INFORMATION);
    }
    @FXML
    public void dvi_selected(ActionEvent actionEvent) {

    }
    @FXML
    public void quy_selected(ActionEvent actionEvent) {

    }
    private void initquycbb() {
        quy_cbb.setConverter(new StringConverter<Quarter>() {
            @Override
            public String toString(Quarter object) {
                return object==null ? "" : object.getName();
            }

            @Override
            public Quarter fromString(String string) {
                return quarterService.findByName(string).orElse(null);
            }
        });
        quy_cbb.getSelectionModel().select(quarterService.findByCurrentTime(LocalDate.now()).orElse(null));
        todate.setText(quy_cbb.getValue().getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        fromdate.setText(quy_cbb.getValue().getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
    }
    private void initdvcbb(){
        dvi_cbb.setItems(FXCollections.observableList(nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName())));
        dvi_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findByTen(string).orElse(null);
            }
        });
        dvi_cbb.getSelectionModel().selectFirst();
    }
    private void saveBcThanhtoanNhienlieuBayTheoKeHoach(String file_name) {
        String sheetName = "bc_ttnl_theo_kh";
        try{
            File file = new File(file_name);
            SubQuery subQuery = new SubQuery();
            if (!file.exists()){
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook();
                // Now creating Sheets using sheet object
                mapDataToSheet(wb.createSheet(sheetName), 8,subQuery.ttnlbtkh_for_mb(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);

                // Now creating Sheets using sheet object
                if (wb!=null){
                    int row_index1 = mapDataToSheet(wb.getSheet(sheetName), 8,subQuery.ttnlbtkh_for_mb(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
                    int row_index2 = mapDataToSheet(wb.getSheet(sheetName), 8+row_index1,subQuery.ttnlbtkh_for_all(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
                    int row_index3 = mapDataToSheet(wb.getSheet(sheetName), 8+row_index2+row_index1,subQuery.ttnlbtkh_for_dv(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
                    mapDataToSheet(wb.getSheet(sheetName), 8+row_index2+row_index1+row_index3,subQuery.ttnlbtkh_for_tongmaybay(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
                }else{
                    mapDataToSheet(wb.createSheet(sheetName), 8,subQuery.ttnlbtkh_for_mb(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
                }

                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }

        } catch (IOException e) {
            DialogMessage.message("THÔNG BÁO LỖI", e.getMessage(), "Có lỗi xảy ra!", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
    private void saveBcTieuthuXdTheoNhiemvu(String file_name) {
        String sheetName = "t_thu_xd_theo_n_vu";
        try{
            File file = new File(file_name);
            SubQuery subQuery = new SubQuery();
            if (!file.exists()){
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook();
                // Now creating Sheets using sheet object
                mapDataToSheet(wb.createSheet(sheetName), 8,subQuery.ttxd_nv(quy_cbb.getSelectionModel().getSelectedItem().getId(),dvi_cbb.getSelectionModel().getSelectedItem().getId()),4);
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                // Now creating Sheets using sheet object
                mapDataToSheet(wb.getSheet(sheetName), 8,subQuery.ttxd_nv(quy_cbb.getSelectionModel().getSelectedItem().getId(),dvi_cbb.getSelectionModel().getSelectedItem().getId()),4);
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void mapdataToNxtSheet(String file_name) {
        String sheetName = "bc_nxt";
        try{
            File file = new File(file_name);
            SubQuery subQuery = new SubQuery();
            if (!file.exists()){
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook();
                // Now creating Sheets using sheet object
                int row_ind= createDataSheet(wb.createSheet(sheetName), 8, getCusQueryNl(subQuery.nl_begin_q1(),subQuery.nl_end_q1(),subQuery.nl_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
                createDataSheet(wb.createSheet(sheetName), row_ind,getCusQueryNl(subQuery.dmn_begin_q1(),subQuery.dmn_end_q1(),subQuery.dmn_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                // Now creating Sheets using sheet object
                int row_ind= createDataSheet(wb.getSheet(sheetName), 8, getCusQueryNl(subQuery.nl_begin_q1(),subQuery.nl_end_q1(),subQuery.nl_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
                createDataSheet(wb.getSheet(sheetName), row_ind,getCusQueryNl(subQuery.dmn_begin_q1(),subQuery.dmn_end_q1(),subQuery.dmn_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private int mapDataToSheet(XSSFSheet sheet,  int begin_data_current,String query, int begin_col){
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(query);
        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.getRow(begin_data_current+i);
            if (row==null){
                row = sheet.createRow(begin_data_current+i);
            }
            for (int j =0;j<rows_data.length;j++){
                String val = rows_data[j]==null ?"" : rows_data[j].toString();
                if (row.getCell(j+begin_col)==null){
                    if (val==null){
                        row.createCell(j+begin_col).setCellValue(val);
                    } else if (StringUtils.isNumeric(val)){
                        row.createCell(j+begin_col).setCellValue(new BigDecimal(val).intValue());
                    } else {
                        row.createCell(j+begin_col).setCellValue(val);
                    }
                }else{
                    if (val==null){
                        row.getCell(j+begin_col).setCellValue(val);
                    } else if (StringUtils.isNumeric(val)){
                        row.getCell(j+begin_col).setCellValue(new BigDecimal(val).intValue());
                    } else {
                        row.getCell(j+begin_col).setCellValue(val);
                    }
                }
            }
        }
        return nxtls.size();
    }
    public void copyFileExcel(String sour, String target){
        if (deleteExcel(target)){
            File source = new File(sour);
            File dest = new File(target);
            if (source.exists()){
                long start = System.nanoTime();
                try {
                    Files.copy(source.toPath(), dest.toPath());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Time taken by Java7 Files Copy = "+(System.nanoTime()-start));
            }else {
                System.out.println("source file doesn't exists");
            }
        }
    }
    private boolean deleteExcel(String file_name){
        File file = new File(file_name);
        try {
            return file.delete();
        }
        catch(Exception e) {
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
    private String getCusQueryNl(String begin_1,String end_q1, String end){
        arr_tt.clear();
        String n_sum1="";
        String x_sum2="";
        String n_case_1="";
        String x_case_2="";
        for (int i=0; i<tructhuocService.findAll().size(); i++) {
            TrucThuoc tt = tructhuocService.findAll().get(i);
            arr_tt.add(tt.getType());
            n_sum1 = n_sum1.concat("sum(n"+tt.getType()+") as "+tt.getType()+",");
            x_sum2 = x_sum2.concat("sum(x"+tt.getType()+") as "+tt.getType()+",");
            n_case_1 = n_case_1.concat("case when tonkhonhap_xd("+quy_cbb.getSelectionModel().getSelectedItem().getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhonhap_xd("+quy_cbb.getSelectionModel().getSelectedItem().getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end as n"+tt.getType()+",");
            x_case_2 = x_case_2.concat("case when tonkhoxuat_xd("+quy_cbb.getSelectionModel().getSelectedItem().getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhoxuat_xd("+quy_cbb.getSelectionModel().getSelectedItem().getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end as x"+tt.getType()+",");
        }
        return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(n_case_1).concat(x_case_2).concat(end);
    }
}
