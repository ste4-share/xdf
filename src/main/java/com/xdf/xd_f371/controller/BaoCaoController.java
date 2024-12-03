package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Tcn;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.repo.TructhuocRepo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import net.sf.jasperreports.engine.*;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class BaoCaoController extends JRDefaultScriptlet implements Initializable {

    private static List<String> arr_tt = new ArrayList<>();

    @Autowired
    private TructhuocRepo tructhuocRepo;

    @Autowired
    private NguonNxRepo nguonNxRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    @FXML
    public void createbc(ActionEvent actionEvent) throws SQLException, JRException, IOException {
//        createReportByJasper();
        writeDataToExcelFile();
    }

    private void writeDataToExcelFile() {
        String file_name = "data.xlsx";
        String sheetName = "socai";
        try{
            File file = new File(file_name);

            if (!file.exists()){
                file.createNewFile();
            }
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook();
            // Now creating Sheets using sheet object
            fillDataToSocaiSheet(wb.createSheet(sheetName), wb);
            fis.close();
            FileOutputStream fileOutputStream = new FileOutputStream(file_name);

            wb.write(fileOutputStream);
            fileOutputStream.close();
            wb.close();
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

    private void fillDataToSocaiSheet(XSSFSheet sheet, XSSFWorkbook wb) {
        int begin_data_current = 2;
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(createcustomQuery());
        XSSFRow row_header = sheet.createRow(begin_data_current-1);
        for (int i=0; i< arr_tt.size(); i++){
            row_header.createCell(i+7).setCellValue(arr_tt.get(i));
        }

        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(begin_data_current+i);
            for (int j =0;j<rows_data.length;j++){
                row.createCell(j+1).setCellValue(rows_data[j]==null ?"" : rows_data[j].toString());
            }
        }
    }

    private String createcustomQuery() {
        String result = "select tinhchat,loai,CASE WHEN tenxd is null and grouping(tinhchat)=0 then loai else tenxd end,sum(tdk_sscd) as tdk_sscd,sum(tdk_nvdx) as tdk_nvdx,sum(tdk_sscd+tdk_nvdx) as cong,";
        for (int i=0; i<tructhuocRepo.findAll().size(); i++) {
            TrucThuoc tt = tructhuocRepo.findAll().get(i);
            arr_tt.add(tt.getType());
            String s = "sum(case when totalLoaiXd2("+DashboardController.findByTime.getId()+",'"+tt.getType()+"', lxd.id,'NHAP') is null then 0 else totalLoaiXd2("+DashboardController.findByTime.getId()+", '"+tt.getType()+"', lxd.id,'NHAP') end) as "+tt.getType()+",";
            result = result.concat(s);
        }
        result = result.concat("grouping(tinhchat) as tc_gr,grouping(loai) as loai_gr,grouping(tenxd) as xd_gr, max(priority_3) as pr\n" +
                "from inventory i \n" +
                "join loaixd2 lxd on i.petro_id=lxd.id\n" +
                "join chungloaixd cl on lxd.petroleum_type_id=cl.id\n" +
                "where tinhchat like 'Nhiên liệu'\n" +
                "group by rollup(tinhchat,loai,tenxd)\n" +
                "order by tc_gr desc,loai_gr desc,pr asc,xd_gr desc\n");
        return result;
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
