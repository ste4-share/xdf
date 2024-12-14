package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.LedgerService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.Files;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ChiTietSCController implements Initializable {

    private List<LedgerDto> ls;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private TcnService tcnService;
    @Autowired
    private LedgerService ledgerService;

    @FXML
    private VBox vb_root;

    @FXML
    private TableView<LedgerDto> tbChiTiet;

    @FXML
    private TableColumn<LedgerDto,String> fct_stt, fct_tenxd, fct_dongia,fct_phaixuat, fct_nhietdo, fct_tytrong, fct_vcf, fct_thucxuat, fct_tong;
    @FXML
    private Label lb_dvvc, lb_dvn, lb_so, lb_nguoinhan, lb_tungay, lb_denngay, lb_tcn, lb_lenhkh, lb_soxe, lb_loaiphieu,lb_giohd_md,lb_giohd_tk,lb_sokm;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        addNewImport();
        ls = ledgerService.getLedgers();
        tbChiTiet.setItems(FXCollections.observableList(ls));
        fillDataToLabels();
    }

    @FXML
    public void printPhieu(ActionEvent actionEvent) {
        printBill();
    }
    @FXML
    public void exitBtn(ActionEvent actionEvent) {
        DashboardController.ctStage.close();
    }

    private void printBill(){
        if (DialogMessage.callAlertWithMessage(MessageCons.TITLE_PRINT.getName(), MessageCons.HEADER_PRINT.getName(), MessageCons.CONTENT.getName(), Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            String temp_file_name="src/main/resources/com/xdf/xd_f371/xlsx_template/phieu_mau.xlsx";
            String file_name = "baocao/phieu_nhap_xuat.xlsx";
            String sheetName = null;
            copyFileExcel(temp_file_name, file_name);
            if (ls.get(0).getLoai_phieu().equals("NHAP")){
                sheetName = "phieu_nhap";
            }else if (ls.get(0).getLoai_phieu().equals("XUAT")){
                sheetName = "phieu_xuat";
            }
            try{
                File file = new File(file_name);
                if (!file.exists()){
                    file.createNewFile();
                    FileInputStream fis = new FileInputStream(file);
                    XSSFWorkbook wb = new XSSFWorkbook();
                    // Now creating Sheets using sheet object
                    fillDataToPhieuNhap(wb.getSheet(sheetName));
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
                        fillDataToPhieuNhap(wb.getSheet(sheetName));
                    }else{
                        fillDataToPhieuNhap(wb.createSheet(sheetName));
                    }

                    fis.close();
                    FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                    XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
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

    private void fillDataToPhieuNhap(XSSFSheet sheet){
        setCEll(sheet, ls.get(0).getDvi_nhan(), 3,3);
        setCEll(sheet, ls.get(0).getDvi_xuat(), 4,3);
        if (tcnService.findById(ls.get(0).getTcn_id()).orElse(null)==null){
            setCEll(sheet, chitietNhiemvuService.findById(ls.get(0).getNhiemvu_id()).orElse(null).getNhiemvu(), 5,3);
        }else{
            setCEll(sheet, tcnService.findById(ls.get(0).getTcn_id()).orElse(null).getName(), 5,3);
        }

        setCEll(sheet, ls.get(0).getLenh_so(), 6,3);
        setCEll(sheet, ls.get(0).getNguoi_nhan(), 7,3);
        setCEll(sheet, "ABC", 8,3);
        setCEll(sheet, String.valueOf(ls.get(0).getEnd_date()), 3,10);
        setCEll(sheet, ls.get(0).getSo_xe(), 4,10);
        setCEll(sheet, String.valueOf(ls.get(0).getBill_id()), 3,7);
        setCEll(sheet, String.valueOf(ls.get(0).getFrom_date()), 4,7);
        for (int i = 0; i< ls.size(); i++) {
            addNewRow(sheet);
        }
        int row_num = 12;
        Long thanh_tien = 0L;
        for (int i = 0; i< ls.size(); i++) {
            setCEll(sheet, String.valueOf(i+1), row_num,1);
            setCEll(sheet, ls.get(i).getMa_xd(), row_num,2);
            setCEll(sheet, ls.get(i).getTen_xd(), row_num,3);
            setCEll(sheet, ls.get(i).getChat_luong(), row_num,4);
            setCEll(sheet, String.valueOf(ls.get(i).getSoluong_px()), row_num,5);
            setCEll(sheet, String.valueOf(ls.get(i).getNhiet_do_tt()), row_num,6);
            setCEll(sheet, String.valueOf(ls.get(i).getTy_trong()), row_num,7);
            setCEll(sheet, String.valueOf(ls.get(i).getHe_so_vcf()), row_num,8);
            setCEll(sheet, String.valueOf(ls.get(i).getSoluong()), row_num,9);
            setCEll(sheet, String.valueOf(ls.get(i).getDon_gia()), row_num,10);
            setCEll(sheet, String.valueOf(ls.get(i).getSoluong()*ls.get(i).getDon_gia()), row_num,11);
            row_num = row_num+1;
            thanh_tien = thanh_tien + ((long) ls.get(i).getDon_gia() * ls.get(i).getSoluong());
            if (i == ls.size() - 1){
                setCEll(sheet,String.valueOf(ls.get(0).getAmount()), 14+ls.size(),11);
            }
        }
    }

    private void addNewRow(XSSFSheet sheet){
        int lastRow = sheet.getLastRowNum();
        sheet.shiftRows(13, lastRow, 1, true, true);
        sheet.copyRows(13,14,13,new CellCopyPolicy());
    }

    private void setCEll(XSSFSheet sheet, String value, int row_num, int cell_num){
        XSSFRow row = sheet.getRow(row_num);
        if (StringUtils.isNumeric(value)){
            row.getCell(cell_num).setCellValue(new BigDecimal(value).doubleValue());
        } else {
            row.getCell(cell_num).setCellValue(value);
        }
    }

    private void addNewImport() {
        fct_stt.setSortable(false);
        fct_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tbChiTiet.getItems().indexOf(column.getValue())+1).asString());
        fct_tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("ten_xd"));
        fct_dongia.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("don_gia"));
        fct_phaixuat.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("soluongpx_str"));
        fct_thucxuat.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("soluong_str"));
        fct_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("nhiet_do_tt"));
        fct_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("he_so_vcf"));
        fct_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("ty_trong"));
        fct_tong.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("thanhtien"));
    }

    private void fillDataToLabels(){
        lb_dvvc.setText(ls.get(0).getDvi_xuat());
        if (ls.get(0).getNhiemvu_id()==0) {
            lb_dvn.setText(ls.get(0).getDvi_nhan());
            lb_tcn.setText(tcnService.findById(ls.get(0).getTcn_id()).orElse(null).getName());
        } else {
            lb_dvn.setText(phuongtienService.findById(ls.get(0).getPhuongtien_id()).orElse(null).getName());
            lb_tcn.setText(chitietNhiemvuService.findById(ls.get(0).getNhiemvu_id()).orElse(null).getNhiemvu());
        }
        lb_so.setText(String.valueOf(ls.get(0).getBill_id()));
        lb_nguoinhan.setText(ls.get(0).getNguoi_nhan());
        lb_lenhkh.setText(ls.get(0).getLenh_so());
        lb_soxe.setText(ls.get(0).getSo_xe());
        lb_tungay.setText(String.valueOf(ls.get(0).getFrom_date()));
        lb_loaiphieu.setText(ls.get(0).getLoai_phieu());
        lb_denngay.setText(String.valueOf(ls.get(0).getEnd_date()));
        lb_giohd_md.setText(ls.get(0).getGiohd_md());
        lb_giohd_tk.setText(ls.get(0).getGiohd_tk());
        lb_sokm.setText(String.valueOf(ls.get(0).getSo_km()));
    }
}
