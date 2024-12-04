package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;

@Component
public class BaoCaoController extends JRDefaultScriptlet implements Initializable {

    private static List<String> arr_tt = new ArrayList<>();
    private static List<String> loaiphieu = new ArrayList<>();

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
    public void createbc(ActionEvent actionEvent) throws SQLException, JRException, IOException {
//        createReportByJasper();
        System.out.println("querynl:"+getCusQuery());
//        System.out.println("queryMN:"+createcustomQueryForDMN());
//        writeDataToExcelFile();
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
                int row_ind= createDataSheet(wb.createSheet(sheetName), 8, createcustomQueryForNl());
                createDataSheet(wb.createSheet(sheetName), row_ind,createcustomQueryForDMN());
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                // Now creating Sheets using sheet object
                int row_ind= fillDataToSocaiSheet(wb.getSheet(sheetName), 8,createcustomQueryForNl());
                fillDataToSocaiSheet(wb.getSheet(sheetName), row_ind,createcustomQueryForDMN());
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

        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(query);
        XSSFRow row_header = sheet.createRow(begin_data_current-1);
        XSSFRow row_header_1 = sheet.createRow(begin_data_current-2);
        for (int i=0; i< arr_tt.size(); i++){
            row_header_1.createCell(i+7).setCellValue(loaiphieu.get(i));
            row_header.createCell(i+7).setCellValue(arr_tt.get(i));
        }
        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(begin_data_current+i);
            for (int j =0;j<rows_data.length;j++){
                row.createCell(j+1).setCellValue(rows_data[j]==null ?"" : rows_data[j].toString());
            }
        }
        return nxtls.size() +11;
    }

    private int fillDataToSocaiSheet(XSSFSheet sheet,  int begin_data_current,String query) {
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(query);
        XSSFRow row_header = sheet.createRow(begin_data_current-1);
        XSSFRow row_header_1 = sheet.createRow(begin_data_current-2);
        for (int i=0; i< arr_tt.size(); i++){
            row_header_1.createCell(i+7).setCellValue(loaiphieu.get(i));
            row_header.createCell(i+7).setCellValue(arr_tt.get(i));
        }

        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(begin_data_current+i);
            for (int j =0;j<rows_data.length;j++){
                row.createCell(j+1).setCellValue(rows_data[j]==null ?"" : rows_data[j].toString());
            }
        }
        return nxtls.size()+11;
    }

    private String createcustomQueryForDMN() {
        arr_tt.clear();
        loaiphieu.clear();
        String result = "select max(stt_index) as stt,chungloai,loai,CASE WHEN tenxd is null and grouping(chungloai)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,sum(CXD) cxd,max(priority_3),grouping(chungloai) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,chungloai,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,";
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            loaiphieu.add("NHAP");
            String s = "sum(case when tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end) as "+tt.getType()+",";
            result = result.concat(s);
        }
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            loaiphieu.add("XUAT");
            String s = "sum(case when tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end) as "+tt.getType()+",";
            result = result.concat(s);
        }
        result = result.concat("'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat <> 'Nhiên liệu') s group by rollup(chungloai,loai,tenxd) order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc");
        return result;
    }

    private String createcustomQueryForNl() {
        arr_tt.clear();
        loaiphieu.clear();
        String result = "select max(stt_index) as stt,tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,sum(CXD) cxd,max(priority_3), grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,tinhchat,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,";
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            loaiphieu.add("NHAP");
            String s = "sum(case when tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end) as "+tt.getType()+",";
            result = result.concat(s);
        }
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            loaiphieu.add("XUAT");
            String s = "sum(case when tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end) as "+tt.getType()+",";
            result = result.concat(s);
        }
        result = result.concat("'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat like 'Nhiên liệu') s group by rollup(tinhchat,loai,tenxd) order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc");
        return result;
    }
    //tonkhonhap_xd
    private String getCusQuery(){
        String begin_q1 = "select max(stt_index) as stt,tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(cong) as cong,";
        String n_sum1="";
        String x_sum2="";
        String n_case_1="";
        String x_case_2="";
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            n_sum1 = begin_q1.concat("sum("+tt.getType()+") as n"+tt.getType()+",");
            x_sum2 = begin_q1.concat("sum("+tt.getType()+") as x"+tt.getType()+",");
            n_case_1 = "case when tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhonhap_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end as n"+tt.getType()+",";
            x_case_2 = "case when tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") is null then 0 else tonkhoxuat_xd("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+dvi_cbb.getSelectionModel().getSelectedItem().getId()+") end as x"+tt.getType()+",";
        }
        String end_q1 = "max(priority_3), grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr from (select stt_index,tinhchat,loai,tenxd,tdk_sscd,tdk_nvdx,tdk_sscd+tdk_nvdx as cong,priority_3,";
        String end = "'ss' from inventory i join loaixd2 lxd on i.petro_id=lxd.id join chungloaixd cl on lxd.petroleum_type_id=cl.id where tinhchat like 'Nhiên liệu') s group by rollup(tinhchat,loai,tenxd) order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc";
        return begin_q1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(n_case_1).concat(x_case_2).concat(end);
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

    @FXML
    public void dvi_selected(ActionEvent actionEvent) {

    }
}
