package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.service.LedgerDetailsService;
import com.xdf.xd_f371.service.NhiemVuService;
import com.xdf.xd_f371.service.PhuongTienService;
import com.xdf.xd_f371.service.impl.LedgerDetailsImp;
import com.xdf.xd_f371.service.impl.NhiemVuImp;
import com.xdf.xd_f371.service.impl.PhuongTienImp;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ChiTietSCController implements Initializable {

    private LedgerDetailsService ledgerDetailsService = new LedgerDetailsImp();
    private PhuongTienService phuongTienService = new PhuongTienImp();
    private NhiemVuService nhiemVuService = new NhiemVuImp();
    private List<LedgerDetails> ls;
    @FXML
    private VBox vb_root;

    @FXML
    private TableView<LedgerDetails> tbChiTiet;

    @FXML
    private TableColumn<LedgerDetails,String> fct_stt, fct_tenxd, fct_dongia,fct_phaixuat, fct_nhietdo, fct_tytrong, fct_vcf, fct_thucxuat, fct_tong;
    @FXML
    private Label lb_dvvc, lb_dvn, lb_so, lb_nguoinhan, lb_tungay, lb_denngay, lb_tcn, lb_lenhkh, lb_soxe, lb_sokm, lb_sogio, lb_loaiphieu;
    @FXML
    private Button exitBtn, printBtn;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addNewImport();
        ls = new ArrayList<>();
        int index_val = 0;

        List<LedgerDetails> ledgerDetailsList = ledgerDetailsService.getChiTietSoCai(DashboardController.so_clicked);
        for (LedgerDetails ledgerDetails : ledgerDetailsList) {
            ledgerDetails.setStt(index_val + 1);
            ledgerDetails.setThuc_xuat_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getSoluong())));
            ledgerDetails.setPhai_xuat_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getPhai_xuat())));
            ledgerDetails.setThanh_tien_str(TextToNumber.textToNum(String.valueOf(ledgerDetails.getThanh_tien())));
            ls.add(ledgerDetails);
            index_val = index_val + 1;
        }
        ObservableList<LedgerDetails> observableList = FXCollections.observableList(ledgerDetailsList);
        tbChiTiet.setItems(observableList);
        fillDataToLabels();
        exitBtn.setOnAction(actionEvent -> {
            DashboardController.ctStage.close();
        });

        printBtn.setOnAction(actionEvent -> {
            if (ledgerDetailsList.get(0).getLoai_phieu().equals("N")){
                System.out.println("Coping ...");
                copyFileExcel();
                System.out.println("fill data to report...");
                String file_name = "PhieuNhap_Pr.xlsx";
                try{
                    File file = new File(file_name);
                    XSSFWorkbook wb = null;
                    if (file.exists()) {

                        FileInputStream fileInputStream = new FileInputStream(file);
                        wb = new XSSFWorkbook(fileInputStream);
                        new XSSFWorkbook(new FileInputStream(file));


                        // Now creating Sheets using sheet object
                        XSSFSheet sheet1 = wb.getSheet("phieu_nhap");

                        fillDataToPhieuNhap(sheet1, wb);
                        FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                        wb.write(fileOutputStream);
                        fileOutputStream.close();
                        ButtonType ok_btn = new ButtonType("OK");
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION,"Click open file to see report.", ok_btn);
                        a.setTitle("Open file");
                        a.showAndWait().ifPresent(response -> {
                            if (response==ok_btn) {
                                try {
                                    Runtime.getRuntime().exec("cmd /c start excel PhieuNhap_Pr.xlsx");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }else {
                System.out.println("Coping ...");
                copyFileExcel_xuat();
                System.out.println("fill data to report...");
                String file_name = "PhieuXuat_Pr.xlsx";
                try{
                    File file = new File(file_name);
                    XSSFWorkbook wb = null;
                    if (file.exists()) {

                        FileInputStream fileInputStream = new FileInputStream(file);
                        wb = new XSSFWorkbook(fileInputStream);
                        new XSSFWorkbook(new FileInputStream(file));

                        // Now creating Sheets using sheet object
                        XSSFSheet sheet1 = wb.getSheet("phieu_xuat");

                        fillDataToPhieuXuat(sheet1, wb);
                        FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                        wb.write(fileOutputStream);
                        fileOutputStream.close();

                        ButtonType ok_btn = new ButtonType("OK");
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION, "Click open file to see report", ok_btn);
                        a.setTitle("Open file");
                        a.showAndWait().ifPresent(response -> {
                            if (response==ok_btn) {
                                try {
                                    Runtime.getRuntime().exec("cmd /c start excel PhieuXuat_Pr.xlsx");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

//            PrintService defaultPrintService =  PrintServiceLookup.lookupDefaultPrintService();
//            DocPrintJob printerJob = defaultPrintService.createPrintJob();
//            File pdfFile = new File("yourfile");
//            SimpleDoc simpleDoc = null;
//
//            try {
//                simpleDoc = new SimpleDoc(pdfFile.toURL(), DocFlavor.URL.AUTOSENSE, null);
//            } catch (MalformedURLException ex) {
//                ex.printStackTrace();
//            }
//            try {
//                printerJob.print(simpleDoc, null);
//            } catch (PrintException ex) {
//                ex.printStackTrace();
//            }

        });
    }


    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    private static void deleteExcel(){
        File file = new File("PhieuNhap_Pr.xlsx");
        try
        {
            if(file.delete())                      //returns Boolean value
            {
                System.out.println(file.getName() + " deleted");   //getting and printing the file name
            }
            else
            {
                System.out.println("failed");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private static void deleteExcelPhieuXuat(){
        File file = new File("PhieuXuat_Pr.xlsx");
        try
        {
            if(file.delete())                      //returns Boolean value
            {
                System.out.println(file.getName() + " deleted");   //getting and printing the file name
            }
            else
            {
                System.out.println("failed");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void copyFileExcel(){
        deleteExcel();
        File source = new File("baocao.xlsx");
        File dest = new File("PhieuNhap_Pr.xlsx");
        if (source.exists()){
             long start = System.nanoTime();
             try {
                 copyFileUsingJava7Files(source, dest);
             } catch (IOException e) {
                 throw new RuntimeException(e);
             }
             System.out.println("Time taken by Java7 Files Copy = "+(System.nanoTime()-start));
        }else {
            System.out.println("source file doesn't exists");
        }
    }

    public static void copyFileExcel_xuat(){
        deleteExcelPhieuXuat();
        File source = new File("baocao.xlsx");
        File dest = new File("PhieuXuat_Pr.xlsx");
        if (source.exists()){
            long start = System.nanoTime();
            try {
                copyFileUsingJava7Files(source, dest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Time taken by Java7 Files Copy = "+(System.nanoTime()-start));
        }else {
            System.out.println("source file doesn't exists");
        }
    }

    private void fillDataToPhieuNhap(XSSFSheet sheet, XSSFWorkbook wb){
        setCEll(sheet, ls.get(0).getDvi(), 3,3);
        setCEll(sheet, ls.get(0).getDvvc(), 4,3);
        setCEll(sheet, ls.get(0).getNhiem_vu(), 5,3);
        setCEll(sheet, ls.get(0).getTheo_lenh_so(), 6,3);
        setCEll(sheet, ls.get(0).getNguoi_nhan_hang(), 7,3);
        setCEll(sheet, "ABC", 8,3);
        setCEll(sheet, "", 3,10);
        setCEll(sheet, ls.get(0).getSo_xe(), 4,10);

        setCEll(sheet, ls.get(0).getSo(), 3,7);
        setCEll(sheet, ls.get(0).getNgay(), 4,7);

        for (int i = 0; i< ls.size(); i++) {
            addNewRow(sheet);
        }

        int row_num = 12;
        Long thanh_tien = 0L;
        for (int i = 0; i< ls.size(); i++) {

            setCEll(sheet, String.valueOf(i+1), row_num,1);
            String generatedString = RandomStringUtils.randomAlphanumeric(5);

            setCEll(sheet, generatedString, row_num,2);
            setCEll(sheet, ls.get(i).getTen_xd(), row_num,3);
            setCEll(sheet, ls.get(i).getChat_luong(), row_num,4);
            setCEll(sheet, String.valueOf(ls.get(i).getPhai_xuat()), row_num,5);
            setCEll(sheet, String.valueOf(ls.get(i).getNhiet_do_tt()), row_num,6);
            setCEll(sheet, String.valueOf(ls.get(i).getTy_trong()), row_num,7);
            setCEll(sheet, String.valueOf(ls.get(i).getHe_so_vcf()), row_num,8);
            setCEll(sheet, String.valueOf(ls.get(i).getThuc_xuat()), row_num,9);
            setCEll(sheet, String.valueOf(ls.get(i).getDon_gia()), row_num,10);
            setCEll(sheet, String.valueOf(ls.get(i).getThanh_tien()), row_num,11);
            row_num = row_num+1;
            thanh_tien = thanh_tien + ls.get(i).getThanh_tien();
            if (i == ls.size() - 1){
                setCEll(sheet,String.valueOf(thanh_tien), 14+ls.size(),11);
            }
        }
    }

    private void fillDataToPhieuXuat(XSSFSheet sheet, XSSFWorkbook wb){
        setCEll(sheet, ls.get(0).getDvi(), 3,3);
        setCEll(sheet, ls.get(0).getDvvc(), 4,3);
        setCEll(sheet, ls.get(0).getNhiem_vu(), 5,3);
        setCEll(sheet, ls.get(0).getTheo_lenh_so(), 6,3);
        setCEll(sheet, ls.get(0).getNguoi_nhan_hang(), 7,3);
        setCEll(sheet, "ABC", 8,3);
        setCEll(sheet, ls.get(0).getDenngay(), 3,10);
        setCEll(sheet, ls.get(0).getSo_xe(), 4,10);
        setCEll(sheet, String.valueOf(ls.get(0).getSo_km()), 6,10);
        setCEll(sheet, String.valueOf(ls.get(0).getSo_gio()), 7,10);
        setCEll(sheet, ls.get(0).getSo(), 3,7);
        setCEll(sheet, ls.get(0).getNgay(), 4,7);

        for (int i = 0; i< ls.size(); i++) {
            addNewRow(sheet);
        }

        int row_num = 12;
        Long thanh_tien = 0L;
        for (int i = 0; i< ls.size(); i++) {

            setCEll(sheet, String.valueOf(i+1), row_num,1);
            String generatedString = RandomStringUtils.randomAlphanumeric(5);

            setCEll(sheet, generatedString, row_num,2);
            setCEll(sheet, ls.get(i).getTen_xd(), row_num,3);
            setCEll(sheet, ls.get(i).getChat_luong(), row_num,4);
            setCEll(sheet, String.valueOf(ls.get(i).getPhai_xuat()), row_num,5);
            setCEll(sheet, String.valueOf(ls.get(i).getNhiet_do_tt()), row_num,6);
            setCEll(sheet, String.valueOf(ls.get(i).getTy_trong()), row_num,7);
            setCEll(sheet, String.valueOf(ls.get(i).getHe_so_vcf()), row_num,8);
            setCEll(sheet, String.valueOf(ls.get(i).getThuc_xuat()), row_num,9);
            setCEll(sheet, String.valueOf(ls.get(i).getDon_gia()), row_num,10);
            setCEll(sheet, String.valueOf(ls.get(i).getThanh_tien()), row_num,11);
            row_num = row_num+1;
            thanh_tien = thanh_tien + ls.get(i).getThanh_tien();
            if (i == ls.size() - 1){
                setCEll(sheet,String.valueOf(thanh_tien), 14+ls.size(),11);
            }
        }
    }

    private void addNewRow(XSSFSheet sheet){
        int lastRow = sheet.getLastRowNum();
        sheet.shiftRows(13, lastRow, 1, true, true);
        sheet.copyRows(13,14,13,new CellCopyPolicy());
    }

    private void setCEll(XSSFSheet sheet, String value, int row_num, int cell_num){
        Row row =  sheet.getRow(row_num);
        Cell cell = row.getCell(cell_num);
        try {
            int num = Integer.parseInt(value);
            cell.setCellType(CellType.NUMERIC);
        } catch (NumberFormatException e) {
            cell.setCellType(CellType.STRING);
        }

        cell.setCellValue(value);
    }


    private void print(Node node){
        PrinterJob printerJob = PrinterJob.createPrinterJob();
        if (printerJob != null && printerJob.showPrintDialog(vb_root.getScene().getWindow())){

            if (printerJob.printPage(node)){
                printerJob.endJob();
            }else
            {
                System.out.println("print failed");            }
        }
        else {
            System.out.println("could not create a printer job");
        }
    }

    private void addNewImport(){
        fct_stt.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("stt"));
        fct_tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        fct_dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("don_gia"));
        fct_phaixuat.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phai_xuat_str"));
        fct_thucxuat.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thuc_xuat_str"));
        fct_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        fct_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        fct_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        fct_tong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanh_tien_str"));
    }

    private void fillDataToLabels(){
        lb_dvvc.setText(ls.get(0).getDvvc());
        if (ls.get(0).getNhiemvu_id()==0){
            System.out.println("nhiemvu = 0");
            lb_dvn.setText(ls.get(0).getDvi());
            lb_tcn.setText(ls.get(0).getNhiem_vu());
        }else {
            lb_dvn.setText(phuongTienService.findPhuongTienById(ls.get(0).getPhuongtien_id()).getName());
            lb_tcn.setText(nhiemVuService.findById(ls.get(0).getNhiemvu_id()).getTen_nv());
        }

        lb_so.setText(ls.get(0).getSo());
        lb_nguoinhan.setText(ls.get(0).getNguoi_nhan_hang());
        lb_lenhkh.setText(ls.get(0).getTheo_lenh_so());
        lb_soxe.setText(ls.get(0).getSo_xe());
        lb_tcn.setText(ls.get(0).getNhiem_vu());
        lb_tungay.setText(ls.get(0).getNgay());
        lb_sokm.setText(String.valueOf(ls.get(0).getSo_km()));
        lb_sogio.setText(String.valueOf(ls.get(0).getSo_gio()));
        lb_loaiphieu.setText(ls.get(0).getLoai_phieu().equals("N") ? "Phiếu nhập" : "Phiếu xuất");
        lb_denngay.setText("32/12/2024");
    }
}
