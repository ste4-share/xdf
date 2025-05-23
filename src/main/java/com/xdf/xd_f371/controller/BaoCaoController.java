package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.fatory.ExportFactory;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;

import com.xdf.xd_f371.util.FxUtilTest;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class BaoCaoController implements Initializable {
    private List<NguonNx> nguonNxList = new ArrayList<>();
    private static List<String> arr_tt = new ArrayList<>();
    private final String file_name = System.getProperty("user.dir")+"\\xlsx_template\\data.xlsx";
    private final String file_name2 = System.getProperty("user.dir")+"\\xlsx_template\\data_template.xlsx";
    private static String dest_file;
    private static String dest_file2;
    private int start_row = 8;
    private int start_col = 6;
    @Autowired
    private NguonNxService nguonNxService;
    @FXML
    private ComboBox<NguonNx> dvi_cbb;
    @FXML
    private CheckBox tdvCk;
    @FXML
    VBox rvb;
    @FXML
    Label fromdate,todate,nxt_lb,ttnlbtkh_lb,ttxdtnv_lb,lcv_lb,ttxd_xmt_lb,pttk_lb;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dest_file = DashboardController.pre_path+"\\baocao.xlsx";
        dest_file2 = DashboardController.pre_path+"\\baocao_2.xlsx";
        rvb.setPrefWidth(DashboardController.screenWidth-300);
        rvb.setPrefHeight(DashboardController.screenHeigh-300);
        nguonNxList = nguonNxService.findByAllBy();
        initDviCbb(nguonNxList);
        dvi_cbb.getSelectionModel().select(DashboardController.ref_Dv);
        initQuarter();
    }
    private void initQuarter() {
            fromdate.setText(DashboardController.ref_Quarter.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            todate.setText(DashboardController.ref_Quarter.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
    }

    private void initDviCbb(List<NguonNx> ls) {
        ComponentUtil.setItemsToComboBox(dvi_cbb,ls,NguonNx::getTen, input -> ls.stream().filter(x->x.getTen().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvi_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
        dvi_cbb.getSelectionModel().selectFirst();
    }
    private Integer map_pttk_create(XSSFWorkbook wb,String sheetName){
        return Common.mapDataToSheet(wb.createSheet(sheetName), 8, SubQuery.bc_pttk_q(), 4);
    }
    private Integer map_pttk_get(XSSFWorkbook wb,String sheetName){
        return Common.mapDataToSheet(wb.getSheet(sheetName), 8, SubQuery.bc_pttk_q(), 4);
    }
    @FXML
    public void bc_nxt_experiment(ActionEvent actionEvent) {
        Stage stage_1 = new Stage();
        Common.getLoading(stage_1);
        Platform.runLater(()-> {
            Common.task(this::nxtmap_2, stage_1::close, () -> DialogMessage.successShowing("Cap nhat thanh cong"));
            nxt_lb.setText("UPDATED");
        });
    }
    private void nxtmap_2() {
        Common.copyFileExcel(file_name2,dest_file2);
        Common.mapExcelFile_JustExist(dest_file2,(input) -> fillDataToPhieuNhap(input,SheetNameCons.NXT2.getName()), SheetNameCons.NXT2.getName());
    }
    @FXML
    public void bc_ttnlbtkh(ActionEvent actionEvent) {
        Stage stage_1 = new Stage();
        Common.getLoading(stage_1);
        Platform.runLater(()-> {
            Common.task(this::ttnlbtkh, stage_1::close, () -> DialogMessage.successShowing("Cap nhat thanh cong"));
            ttnlbtkh_lb.setText("UPDATED");
        });
    }
    private void ttnlbtkh(){
        Common.copyFileExcel(file_name2,dest_file2);
        Common.mapExcelFile_JustExist(dest_file2,input -> fillDataTo_ttnlbtkh(input,SheetNameCons.NL_BAY_THEO_KH.getName()),SheetNameCons.NL_BAY_THEO_KH.getName());
    }
    @FXML
    public void bc_ttxd_xmt(ActionEvent actionEvent) {
        Stage stage_1 = new Stage();
        Common.getLoading(stage_1);
        Platform.runLater(()-> {
            Common.task(this::ttxd_xmt,stage_1::close,()->DialogMessage.successShowing("Cap nhat thanh cong"));
            ttxd_xmt_lb.setText("UPDATED");
        });
    }
    private void ttxd_xmt(){
        Common.copyFileExcel(file_name2,dest_file2);
        Common.mapExcelFile_JustExist(dest_file2,input -> fillDataTo_ttxd_xmt(input,SheetNameCons.TTXD_XMT_BETA.getName()),SheetNameCons.TTXD_XMT_BETA.getName());
    }
    @FXML
    public void bc_ttxdtnv(ActionEvent actionEvent) {
        Stage stage_1 = new Stage();
        Common.getLoading(stage_1);
        Platform.runLater(()-> {
            Common.task(this::ttxdtnv,stage_1::close,()->DialogMessage.successShowing("Cap nhat thanh cong"));
            ttxdtnv_lb.setText("UPDATED");
        });
    }
    private void ttxdtnv(){
        Common.copyFileExcel(file_name2,dest_file2);
        Common.mapExcelFile_JustExist(dest_file2,input -> fillDataTo_ttxd_nv(input,SheetNameCons.TTXD_BETA.getName()),SheetNameCons.TTXD_BETA.getName());
    }

    @FXML
    public void bc_pttk(ActionEvent actionEvent) {
        Stage stage_1 = new Stage();
        Common.getLoading(stage_1);
        Platform.runLater(()-> {
            Common.task(this::pttk,stage_1::close,()->DialogMessage.successShowing("Cap nhat thanh cong"));
            pttk_lb.setText("UPDATED");
        });
    }
    private void pttk(){
        String sheetName = "pttk_data";
        Common.copyFileExcel(file_name,dest_file2);
        Common.mapExcelFile(file_name,input -> map_pttk_create(input,sheetName),input -> map_pttk_get(input,sheetName),SheetNameCons.PT_TONKHO.getName());
    }
    private String getCusQueryNl(String begin_1,String end_q1, String end_q1_1,LocalDate sd,LocalDate ed){
        arr_tt.clear();
        String n_sum1="";
        String n_sum2="";
        String x_sum2="";
        String x_sum3="";
        String sl1="";
        String sl2="";
        String n_case_1="";
        String x_case_2="";
        String en = " group by rollup(tinhchat,chungloai,tenxd)) z ORDER BY tc desc,tinhchat desc,l desc,CHUNGLOAI desc,tc desc,rank_cl,xd desc";
        List<TrucThuoc> tt_list_n = DashboardController.map.get(LoaiPhieuCons.PHIEU_NHAP.getName());
        List<TrucThuoc> tt_list_x = DashboardController.map.get(LoaiPhieuCons.PHIEU_XUAT.getName());
        if (tdvCk.isSelected()){
            for (int i=0; i<tt_list_n.size(); i++){
                n_sum1 = n_sum1.concat("n"+i+" as n"+i+",");
                sl1=sl1.concat("case when sum(n"+i+".soluong) is null then 0 else sum(n"+i+".soluong) end as n"+i+",");
                n_case_1 = n_case_1.concat(" left join (SELECT petro_id, sum(nvdx_quantity) as soluong FROM public.inventory_units where bill_type like 'NHAP' and tructhuoc like '"+tt_list_n.get(i).getType()+"' and st_time between '"+sd+"' and '"+ed+"' group by 1) n"+i+" on adm.id=n"+i+".petro_id");
            }for (int i=0; i<tt_list_x.size(); i++) {
                x_sum2 = x_sum2.concat("x"+i+" as x"+i+",");
                sl2=sl2.concat("case when sum(x"+i+".soluong) is null then 0 else sum(x"+i+".soluong) end as x"+i+",");
                x_case_2 = x_case_2.concat(" left join (SELECT petro_id, sum(nvdx_quantity) as soluong FROM public.inventory_units where bill_type like 'XUAT' and tructhuoc like '"+tt_list_x.get(i).getType()+"' and st_time between '"+sd+"' and '"+ed+"' group by 1) x"+i+" on adm.id=x"+i+".petro_id");
            }
            return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(sl1).concat(sl2).concat(end_q1_1).concat(n_case_1).concat(x_case_2).concat(en);
        }else{
            for (int i=0; i<tt_list_n.size(); i++) {
                n_sum1 = n_sum1.concat("n"+i+" as n"+i+",");
                if (i==tt_list_n.size()-1){
                    n_sum2 = n_sum2.concat("n"+i+" as tong_n,");
                }else{
                    n_sum2 = n_sum2.concat("n"+i+"+");
                }
                sl1=sl1.concat("case when sum(n"+i+".soluong) is null then 0 else sum(n"+i+".soluong) end as n"+i+",");
                n_case_1 = n_case_1.concat(" left join (SELECT distinct on (xd_id) xd_id, soluong_tt as soluong FROM public.transaction_history where loaiphieu like 'NHAP' and tructhuoc like '"+tt_list_n.get(i).getType()+"' and date between '"+sd+"' and '"+ed+"' order by xd_id, created_at desc) n"+i+" on adm.id=n"+i+".xd_id");
            }
            for (int i=0; i<tt_list_x.size(); i++) {
                x_sum2 = x_sum2.concat("x"+i+" as x"+i+",");
                if (i==tt_list_x.size()-1){
                    x_sum3 = x_sum3.concat("x"+i+" as tong_x,");
                }else{
                    x_sum3 = x_sum3.concat("x"+i+"+");
                }
                sl2=sl2.concat("case when sum(x"+i+".soluong) is null then 0 else sum(x"+i+".soluong) end as x"+i+",");
                x_case_2 = x_case_2.concat(" left join (SELECT distinct on (xd_id) xd_id, soluong_tt as soluong FROM public.transaction_history where loaiphieu like 'XUAT' and tructhuoc like '"+tt_list_x.get(i).getType()+"' and date between '"+sd+"' and '"+ed+"' order by xd_id, created_at desc) x"+i+" on adm.id=x"+i+".xd_id");
            }
            return begin_1.concat(n_sum1).concat(n_sum2).concat(x_sum2).concat(x_sum3).concat(end_q1).concat(sl1).concat(sl2).concat(end_q1_1).concat(n_case_1).concat(x_case_2).concat(en);
        }
    }

    @FXML
    public void dvi_cbbAction(ActionEvent actionEvent) {
    }
    @FXML
    public void toandonvickAction(ActionEvent actionEvent) {
        if (tdvCk.isSelected()){
            dvi_cbb.setDisable(true);
        }else{
            dvi_cbb.setDisable(false);
        }
    }
    private Integer fillDataTo_ttxd_nv(XSSFWorkbook wb, String name) {
        XSSFSheet sheet = wb.getSheet(name);
        initHeder_ttxd_nv(sheet,wb);
        fillData_ttxd_nv(wb,sheet);
        return -1;
    }
    private void fillData_ttxd_nv(XSSFWorkbook wb, XSSFSheet sheet) {
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> xmtls = reportDAO.findByWhatEver(SubQuery.ttxd_nv(DashboardController.ref_Quarter.getStart_date(),
                DashboardController.ref_Quarter.getEnd_date(),DashboardController.ref_Dv.getId()));
        fillData_forttxdnv(wb,sheet,xmtls,2,start_row-3,10,12,3);
    }

    private void initHeder_ttxd_nv(XSSFSheet sheet, XSSFWorkbook wb) {
        XSSFRow r1 = sheet.getRow(1);
        XSSFCellStyle style = wb.createCellStyle();
        ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
        ExportFactory.setBoldFont(wb,style);
        ExportFactory.setCellAlightmentStyle(style);
        ExportFactory.removeMerger(sheet,1,1,11,16);
        Cell cell = r1.createCell(11);
        cell.setCellValue("BÁO CÁO TIÊU THỤ XĂNG DẦU THEO NHIỆM VỤ \n" +
                "(Từ ngày "+DashboardController.ref_Quarter.getStart_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" đến "+
                DashboardController.ref_Quarter.getEnd_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+")");
        for (int i =12;i<=17;i++){
            Cell ce = r1.createCell(i);
            ce.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(1,1,11,17));
        cell.setCellStyle(style);
    }

    private Integer fillDataTo_ttxd_xmt(XSSFWorkbook wb, String name) {
        XSSFSheet sheet = wb.getSheet(name);
        initHeder_TTXMT(sheet,wb);
        fillData_ttxmt(wb,sheet);
        return -1;
    }

    private void fillData_ttxmt(XSSFWorkbook wb, XSSFSheet sheet) {
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> xmtls = reportDAO.findByWhatEver(SubQuery.bc_ttxd_xmt_q(DashboardController.ref_Quarter.getStart_date(),DashboardController.ref_Quarter.getEnd_date(),DashboardController.ref_Dv.getId()));
        fillData(wb,sheet,xmtls,1,5,7,0,4);
    }
    private void initHeder_TTXMT(XSSFSheet sheet, XSSFWorkbook wb) {
        XSSFRow r1 = sheet.getRow(1);
        XSSFCellStyle style = wb.createCellStyle();
        ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
        ExportFactory.setBoldFont(wb,style);
        ExportFactory.setCellAlightmentStyle(style);
        ExportFactory.removeMerger(sheet,1,1,7,12);
        Cell cell = r1.createCell(7);
        cell.setCellValue("BÁO CÁO TIÊU THỤ XĂNG DẦU CỦA XE - MÁY - TÀU VÀ BQ, BD, SC \n" +
                "(Từ ngày "+DashboardController.ref_Quarter.getStart_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" đến "+
                DashboardController.ref_Quarter.getEnd_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+")");
        for (int i =8;i<=12;i++){
            Cell ce = r1.createCell(i);
            ce.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(1,1,7,12));
        cell.setCellStyle(style);
    }

    private int fillDataToPhieuNhap(XSSFWorkbook wb,String sheet_n){
        XSSFSheet sheet = wb.getSheet(sheet_n);
        initHeder(sheet,wb);
        initHederColumnFor_nxt(sheet,wb);
        ExportFactory.mergerCell(sheet);
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(getCusQueryNl(SubQuery.begin_q1(),SubQuery.end_q1(),
                SubQuery.end_q1_1(DashboardController.ref_Dv.getId(),DashboardController.ref_Quarter.getStart_date()),
                DashboardController.ref_Quarter.getStart_date(),DashboardController.ref_Quarter.getEnd_date()));
        fillData(wb,sheet,nxtls,4,start_row,0,0,3);
        return -1;
    }
    private void fillData_forttxdnv(XSSFWorkbook wb, XSSFSheet sheet,List<Object[]> nxtls,int scol,int srol, int a,int a1,int b) {
        for (int i = 0; i<nxtls.size();i++){
            int lastRow = sheet.getLastRowNum();
            sheet.shiftRows(srol+i+2, lastRow, 1, true, true);
            XSSFRow row1 = sheet.createRow(srol+i+2);
            Object[] rows_data = nxtls.get(i);
            for (int j = 0; j<rows_data.length;j++){
                String val = rows_data[j]==null ? "" : rows_data[j].toString();
                XSSFCell c = row1.createCell(scol+j);
                XSSFCellStyle style = wb.createCellStyle();
                if (rows_data[1]==null){
                    ExportFactory.setBoldFont(wb,style);
                }
                if(b!=0 && j==b){
                    ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
                }else{
                    ExportFactory.setCellAlightmentStyle(style);
                    ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
                }
                if (Common.isDoubleNumber(val)){
                    ExportFactory.setDataFormat(wb,style,"#,##0.00");
                    BigDecimal bigDecimal = new BigDecimal(val).setScale(1, RoundingMode.HALF_UP);
                    c.setCellValue(bigDecimal.doubleValue());
                } else if(Common.isLongNumber(val)){
                    long l = (long) Double.parseDouble(val);
                    if (a!=0 && j==a || a1!=0 && j==a1){
                        ExportFactory.setDataFormat(wb,style,"[h]:mm");
                        c.setCellValue(Common.convertSecondsToTime((long) Double.parseDouble(val)));
                        c.setCellStyle(style);
                        continue;
                    }
                    if (l==0){
                        c.setCellValue("");
                        c.setCellStyle(style);
                        continue;
                    }
                    ExportFactory.setDataFormat(wb,style,"#,##0");
                    c.setCellValue((long) Double.parseDouble(val));
                } else {
                    if (j==3){
                        if (SubQuery.lxdMap().get(val)!=null){
                            c.setCellValue(SubQuery.lxdMap().get(val));
                        }else{
                            c.setCellValue(val);
                        }
                    }else{
                        c.setCellValue(val);
                    }
                }
                c.setCellStyle(style);
            }
        }
    }
    private void fillData(XSSFWorkbook wb, XSSFSheet sheet,List<Object[]> nxtls,int scol,int srol, int a,int a1,int b) {
        for (int i = 0; i<nxtls.size();i++){
            int lastRow = sheet.getLastRowNum();
            sheet.shiftRows(srol+i+2, lastRow, 1, true, true);
            XSSFRow row1 = sheet.createRow(srol+i+2);
            Object[] rows_data = nxtls.get(i);
            for (int j = 0; j<rows_data.length;j++){
                String val = rows_data[j]==null ? "" : rows_data[j].toString();
                XSSFCell c = row1.createCell(scol+j);
                XSSFCellStyle style = wb.createCellStyle();
                if (Integer.parseInt(rows_data[2].toString())==1){
                    ExportFactory.setBoldFont(wb,style);
                }
                if(b!=0 && j==b){
                    ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
                }else{
                    ExportFactory.setCellAlightmentStyle(style);
                    ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
                }
                if (Common.isDoubleNumber(val)){
                    ExportFactory.setDataFormat(wb,style,"#,##0.00");
                    BigDecimal bigDecimal = new BigDecimal(val).setScale(1, RoundingMode.HALF_UP);
                    c.setCellValue(bigDecimal.doubleValue());
                } else if(Common.isLongNumber(val)){
                    long l = (long) Double.parseDouble(val);
                    if (a!=0 && j==a || a1!=0 && j==a1){
                        ExportFactory.setDataFormat(wb,style,"[h]:mm");
                        c.setCellValue(Common.convertSecondsToTime((long) Double.parseDouble(val)));
                        c.setCellStyle(style);
                        continue;
                    }
                    if (l==0){
                        c.setCellValue("");
                        c.setCellStyle(style);
                        continue;
                    }
                    ExportFactory.setDataFormat(wb,style,"#,##0");
                    c.setCellValue((long) Double.parseDouble(val));
                } else {
                    if (j==3){
                        if (SubQuery.lxdMap().get(val)!=null){
                            c.setCellValue(SubQuery.lxdMap().get(val));
                        }else{
                            c.setCellValue(val);
                        }
                    }else{
                        c.setCellValue(val);
                    }
                }
                c.setCellStyle(style);
            }
        }
    }
    private void initHeder(XSSFSheet sheet, XSSFWorkbook wb) {
        XSSFRow r1 = sheet.getRow(1);
        XSSFCellStyle style = wb.createCellStyle();
        ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
        ExportFactory.setBoldFont(wb,style);
        ExportFactory.setCellAlightmentStyle(style);
        ExportFactory.removeMerger(sheet,1,1,12,20);
        Cell cell = r1.createCell(12);
        cell.setCellValue("BÁO CÁO NHẬP XUẤT TỒN XĂNG DẦU MỠ \n" +
                "(Từ ngày "+DashboardController.ref_Quarter.getStart_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" đến "+
                DashboardController.ref_Quarter.getEnd_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+")");
        for (int i =13;i<=20;i++){
            Cell ce = r1.createCell(i);
            ce.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(1,1,12,20));
        cell.setCellStyle(style);
    }
    private void initHederColumnFor_nxt(XSSFSheet sheet, XSSFWorkbook wb){
        XSSFRow r1 = sheet.createRow(start_row);
        XSSFCellStyle style = wb.createCellStyle();
        ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
        ExportFactory.setBoldFont(wb,style);
        ExportFactory.setCellAlightmentStyle(style);
        Cell r1_c1 =r1.createCell(start_col);
        r1_c1.setCellValue("TT");
        r1_c1.setCellStyle(style);
        Cell r1_c2 =r1.createCell(start_col+1);
        r1_c2.setCellValue("Tên Xăng dầu");
        r1_c2.setCellStyle(style);
        Cell r1_c3 =r1.createCell(start_col+2);
        r1_c3.setCellValue("Tồn đầu kỳ");
        r1_c3.setCellStyle(style);
        Cell r1_c4 =r1.createCell(start_col+3);
        r1_c4.setCellStyle(style);
        Cell r1_c5 =r1.createCell(start_col+4);
        r1_c5.setCellStyle(style);

        Map<String, List<TrucThuoc>> tt_list = DashboardController.map;
        List<TrucThuoc> indexs_n = tt_list.get(LoaiPhieuCons.PHIEU_NHAP.getName());
        List<TrucThuoc> indexs_x = tt_list.get(LoaiPhieuCons.PHIEU_XUAT.getName());
        XSSFRow r2 = sheet.createRow(start_row+1);
        Cell c2_r0 = r2.createCell(start_col);
        c2_r0.setCellStyle(style);
        Cell c2_r0_1 = r2.createCell(start_col+1);
        c2_r0_1.setCellStyle(style);
        Cell c2_r1 = r2.createCell(start_col+2);
        c2_r1.setCellStyle(style);
        c2_r1.setCellValue(ConfigCons.NVDX.getName());
        Cell c2_r2 = r2.createCell(start_col+3);
        c2_r2.setCellStyle(style);
        c2_r2.setCellValue(ConfigCons.SSCD.getName());
        Cell c2_r3 = r2.createCell(start_col+4);
        c2_r3.setCellStyle(style);
        c2_r3.setCellValue(ConfigCons.CONG.getName());

        Cell c2_rsum = r2.createCell(indexs_n.size()+start_col+5);
        c2_rsum.setCellStyle(style);
        c2_rsum.setCellValue("Tổng nhập");
        Cell c3_rsum = r2.createCell(start_col+6+indexs_n.size()+indexs_x.size());
        c3_rsum.setCellStyle(style);
        c3_rsum.setCellValue("Tổng xuất");

        for (int i=0; i<indexs_n.size(); i++) {
            String tructh = indexs_n.get(i).getName();
            int ind = start_col+5+i;
            XSSFCell c1 = r1.createCell(ind);
            c1.setCellStyle(style);
            if (i==indexs_n.size()-1){
                XSSFCell c1_1 = r1.createCell(ind+1);
                c1_1.setCellStyle(style);
            }
            if (i==0){
                c1.setCellValue(LoaiPhieuCons.PHIEU_NHAP.getName());
            }
            XSSFCell c2 = r2.createCell(ind);
            c2.setCellValue(tructh);
            c2.setCellStyle(style);
        }
        for (int i=0; i<indexs_x.size(); i++) {
            String tructh = indexs_x.get(i).getName();
            int ind = start_col+6+indexs_n.size()+i;
            XSSFCell c1 = r1.createCell(ind);
            c1.setCellStyle(style);
            if (i==indexs_x.size()-1){
                XSSFCell c1_1 = r1.createCell(ind+1);
                c1_1.setCellStyle(style);
            }
            if (i==0){
                c1.setCellValue(LoaiPhieuCons.PHIEU_XUAT.getName());
            }
            XSSFCell c2 = r2.createCell(ind);
            c2.setCellStyle(style);
            c2.setCellValue(tructh);
        }
        ExportFactory.removeMerger(sheet,8,8,11,indexs_n.size());
        sheet.addMergedRegion(new CellRangeAddress(8,8,11,11+indexs_n.size()));
        ExportFactory.removeMerger(sheet,8,8,12+indexs_n.size(),11+indexs_n.size()+indexs_x.size());
        sheet.addMergedRegion(new CellRangeAddress(8,8,12+indexs_n.size(),12+indexs_n.size()+indexs_x.size()));
    }
    private int fillDataTo_ttnlbtkh(XSSFWorkbook wb,String sheet_n){
        XSSFSheet sheet = wb.getSheet(sheet_n);
        initHederColumnFor_ttnlbtkh(sheet,wb);
        fillData_ttnlbtkh(wb,sheet);
        return -1;
    }
    private void fillData_ttnlbtkh(XSSFWorkbook wb, XSSFSheet sheet) {
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> mb_ls = reportDAO.findByWhatEver(SubQuery.ttnlbtkh_for_mb(DashboardController.ref_Quarter.getStart_date(),DashboardController.ref_Quarter.getEnd_date(),DashboardController.ref_Dv.getId()));
        map_ttnlbtkh(mb_ls,sheet,wb,start_row-1);
        List<Object[]> all_nv_ls = reportDAO.findByWhatEver(SubQuery.ttnlbtkh_for_all(DashboardController.ref_Quarter.getStart_date(),DashboardController.ref_Quarter.getEnd_date(),DashboardController.ref_Dv.getId()));
        map_ttnlbtkh(all_nv_ls,sheet,wb,start_row+mb_ls.size()-1);
        int mbnv_start = start_row+mb_ls.size()+all_nv_ls.size()-1;
        List<Object[]> mbls = reportDAO.findByWhatEver(SubQuery.ttnlbtkh_for_tongmaybay(DashboardController.ref_Quarter.getStart_date(), DashboardController.ref_Quarter.getEnd_date(),DashboardController.ref_Dv.getId()));
        map_ttnlbtkh(mbls,sheet,wb,mbnv_start);
    }
    private void map_ttnlbtkh(List<Object[]> ls,XSSFSheet sheet,XSSFWorkbook wb,int sr) {
        for (int i = 0; i<ls.size();i++){
            int lastRow = sheet.getLastRowNum();
            sheet.shiftRows(sr+i, lastRow, 1, true, true);
            XSSFRow row1 = sheet.createRow(sr+i);
            Object[] rows_data = ls.get(i);

            for (int j = 0; j<rows_data.length;j++){
                String val = rows_data[j]==null ? "" : rows_data[j].toString();
                XSSFCell c = row1.createCell(j+1);
                XSSFCellStyle style = wb.createCellStyle();
                int b = (int) Double.parseDouble(rows_data[0]==null ? "" : rows_data[0].toString());
                if (b==1){
                    if (i==0){
                        ExportFactory.setRedFont(wb,style);
                    }else{
                        ExportFactory.setBoldFont(wb,style);
                    }
                }
                if(j==3){
                    ExportFactory.setCellAlightmentStyle(style);
                    ExportFactory.setCellBorderStyle(style, BorderStyle.THIN,BorderStyle.THIN,BorderStyle.DOUBLE,BorderStyle.THIN);
                }else{
                    ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
                }

                if (Common.isDoubleNumber(val)){
                    ExportFactory.setDataFormat(wb,style,"#,##0.00");
                    BigDecimal bigDecimal = new BigDecimal(val).setScale(1, RoundingMode.HALF_UP);
                    c.setCellValue(bigDecimal.doubleValue());
                } else if(Common.isLongNumber(val)){
                    long l = (long) Double.parseDouble(val);
                    if (l==0){
                        c.setCellValue("");
                        c.setCellStyle(style);
                        continue;
                    }
                    if (j==5||j==6||j==7||j==9||j==10||j==11){
                        ExportFactory.setDataFormat(wb,style,"[h]:mm");
                        c.setCellValue(Common.convertSecondsToTime((long) Double.parseDouble(val)));
                        c.setCellStyle(style);
                        continue;
                    }
                    ExportFactory.setDataFormat(wb,style,"#,##0");
                    c.setCellValue((long) Double.parseDouble(val));
                }else {
                    c.setCellValue(val);
                }
                c.setCellStyle(style);
            }
            for (int j=rows_data.length; j<rows_data.length+13;j++){
                XSSFCellStyle style1 = wb.createCellStyle();
                XSSFCell c = row1.createCell(j+1);
                ExportFactory.setCellBorderStyle(style1, BorderStyle.THIN);
                c.setCellStyle(style1);
            }
            sheet.autoSizeColumn(0);
        }
    }

    private void initHederColumnFor_ttnlbtkh(XSSFSheet sheet, XSSFWorkbook wb){
        XSSFCellStyle style = wb.createCellStyle();
        ExportFactory.setCellBorderStyle(style, BorderStyle.THIN);
        ExportFactory.setBoldFont(wb,style);
        ExportFactory.setCellAlightmentStyle(style);
        ExportFactory.removeMerger(sheet,1,1,11,21);
        XSSFRow row = sheet.getRow(1);
        Cell cell = row.createCell(11);
        cell.setCellValue("BÁO CÁO THANH TOÁN NHIÊN LIỆU BAY THEO KẾ HOẠCH \n" +
                "(Từ ngày "+DashboardController.ref_Quarter.getStart_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+" đến "+
                DashboardController.ref_Quarter.getEnd_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))+")");
        for (int i =12;i<=21;i++){
            Cell ce = row.createCell(i);
            ce.setCellStyle(style);
        }
        sheet.addMergedRegion(new CellRangeAddress(1,1,11,21));
        cell.setCellStyle(style);
    }
}
