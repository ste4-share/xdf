package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.Quarter;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.QuarterService;
import com.xdf.xd_f371.service.TructhuocService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class BaoCaoController implements Initializable {
    private static List<String> arr_tt = new ArrayList<>();
    private final String file_name = System.getProperty("user.dir")+"\\xlsx_template\\data.xlsx";
    private static String dest_file;
    @Autowired
    private TructhuocService tructhuocService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private QuarterService quarterService;
    @FXML
    ComboBox<NguonNx> dvi_cbb;
    @FXML
    VBox rvb;
    @FXML
    ComboBox<Quarter> quy_cbb;
    @FXML
    Label fromdate,todate,nxt_lb,ttnlbtkh_lb,ttxdtnv_lb;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dest_file = ConnectLan.pre_path+"\\baocao.xlsx";
        rvb.setPrefWidth(DashboardController.screenWidth-300);
        rvb.setPrefHeight(DashboardController.screenHeigh-300);
        initdvcbb();
        initquycbb();
    }
    @FXML
    public void dvi_selected(ActionEvent actionEvent) {
    }
    @FXML
    public void quy_selected(ActionEvent actionEvent) {
    }
    private void initquycbb() {
        ComponentUtil.setItemsToComboBox(quy_cbb, quarterService.findAllByYear(String.valueOf(Year.now().getValue())),Quarter::getIndex, input-> quarterService.findByIndex(input).orElse(null));
        quy_cbb.getSelectionModel().select(quarterService.findByCurrentTime(LocalDate.now()).orElse(null));
        todate.setText(quy_cbb.getValue().getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        fromdate.setText(quy_cbb.getValue().getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
    }
    private void initdvcbb(){
        ComponentUtil.setItemsToComboBox(dvi_cbb, nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()),NguonNx::getTen, input-> nguonNxService.findByTen(input).orElse(null));
        dvi_cbb.getSelectionModel().selectFirst();
    }
    private Integer map_bc_nxt_create(XSSFWorkbook wb,String sheetName){
        int row_ind = createDataSheet(wb.createSheet(sheetName), 8, getCusQueryNl(SubQuery.nl_begin_q1(),SubQuery.nl_end_q1(),SubQuery.nl_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
        return createDataSheet(wb.createSheet(sheetName), row_ind,getCusQueryNl(SubQuery.dmn_begin_q1(),SubQuery.dmn_end_q1(),SubQuery.dmn_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
    }
    private Integer map_bc_nxt_getting(XSSFWorkbook wb,String sheetName){
        int row_ind= createDataSheet(wb.getSheet(sheetName), 8, getCusQueryNl(SubQuery.nl_begin_q1(),SubQuery.nl_end_q1(),SubQuery.nl_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
        return createDataSheet(wb.getSheet(sheetName), row_ind,getCusQueryNl(SubQuery.dmn_begin_q1(),SubQuery.dmn_end_q1(),SubQuery.dmn_end(quy_cbb.getSelectionModel().getSelectedItem().getId())));
    }
    private Integer map_ttnlbtkh_create(XSSFWorkbook wb,String sheetName){
        return mapDataToSheet(wb.createSheet(sheetName), 8,SubQuery.ttnlbtkh_for_mb(quy_cbb.getSelectionModel().getSelectedItem().getId()),1);
    }
    private Integer map_ttnlbtkh_getting(XSSFWorkbook wb,String sheetName){
        Quarter q = quy_cbb.getSelectionModel().getSelectedItem();
        if (q!=null){
            int row_index1 = mapDataToSheet(wb.getSheet(sheetName), 8,SubQuery.ttnlbtkh_for_mb(q.getId()),1);
            int row_index2 = mapDataToSheet(wb.getSheet(sheetName), 8+row_index1,SubQuery.ttnlbtkh_for_all(q.getId()),1);
            int row_index3 = mapDataToSheet(wb.getSheet(sheetName), 8+row_index2+row_index1,SubQuery.ttnlbtkh_for_dv(q.getId()),1);
            return mapDataToSheet(wb.getSheet(sheetName), 8+row_index2+row_index1+row_index3,SubQuery.ttnlbtkh_for_tongmaybay(q.getId()),1);
        }
        return 0;
    }
    private Integer map_ttxdtnv_create(XSSFWorkbook wb,String sheetName){
        Quarter q = quy_cbb.getSelectionModel().getSelectedItem();
        NguonNx nx = dvi_cbb.getSelectionModel().getSelectedItem();
        if (q!=null && nx!=null) {
            return mapDataToSheet(wb.createSheet(sheetName), 8, SubQuery.ttxd_nv(Integer.parseInt(q.getYear()), nx.getId()), 4);
        }
        return 0;
    }
    private Integer map_ttxdtnv_getting(XSSFWorkbook wb,String sheetName){
        Quarter q = quy_cbb.getSelectionModel().getSelectedItem();
        NguonNx nx = dvi_cbb.getSelectionModel().getSelectedItem();
        if (q!=null && nx!=null){
            return mapDataToSheet(wb.getSheet(sheetName), 8,SubQuery.ttxd_nv(Integer.parseInt(q.getYear()),nx.getId()),4);
        }
        return 0;
    }
    @FXML
    public void bc_nxt(ActionEvent actionEvent) {
        Stage stage_1 = new Stage();
        Common.getLoading(stage_1);
        Platform.runLater(()-> {
            Common.task(this::nxtmap, stage_1::close, () -> DialogMessage.successShowing("Cap nhat thanh cong"));
            nxt_lb.setText("UPDATED");
        });

    }
    private void nxtmap() {
        String sheetName = "bc_nxt";
        Common.mapExcelFile(file_name,input -> map_bc_nxt_create(input,sheetName),input -> map_bc_nxt_getting(input,sheetName));
        Common.copyFileExcel(file_name,dest_file);
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
        String sheetName = "bc_ttnl_theo_kh";
        Common.mapExcelFile(file_name,input -> map_ttnlbtkh_create(input,sheetName),input -> map_ttnlbtkh_getting(input,sheetName));
        Common.copyFileExcel(file_name,dest_file);

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
        String sheetName = "t_thu_xd_theo_n_vu";
        Common.mapExcelFile(file_name,input -> map_ttxdtnv_create(input,sheetName),input -> map_ttxdtnv_getting(input,sheetName));
        Common.copyFileExcel(file_name,dest_file);
    }
    @FXML
    public void bc_ptnn(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_pttk(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_hm_va_thucnhan(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_ttxd_xmt(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_ttxd_dmtt(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_dtsscd(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_ttns(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_lcv(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_nxtxd_nvk(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_nxt_hc(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_dauthau(ActionEvent actionEvent) {
    }
    @FXML
    public void bc_ttxd_dientap(ActionEvent actionEvent) {
    }
    private String getCusQueryNl(String begin_1,String end_q1, String end){
        arr_tt.clear();
        String n_sum1="";
        String x_sum2="";
        String n_case_1="";
        String x_case_2="";
        Quarter q = quy_cbb.getSelectionModel().getSelectedItem();
        NguonNx nx = dvi_cbb.getSelectionModel().getSelectedItem();
        for (int i=0; i<tructhuocService.findAll().size(); i++) {
            TrucThuoc tt = tructhuocService.findAll().get(i);
            arr_tt.add(tt.getType());
            n_sum1 = n_sum1.concat("sum(n"+tt.getType()+") as "+tt.getType()+",");
            x_sum2 = x_sum2.concat("sum(x"+tt.getType()+") as "+tt.getType()+",");
            n_case_1 = n_case_1.concat("max(case when tonkhonhap_xd2("+q.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+nx.getId()+",'ACTIVE') " +
                    "is null then 0 else tonkhonhap_xd2("+q.getId()+", '"+tt.getType()+"', lxd.id,'NHAP',"+nx.getId()+",'ACTIVE') end) as n"+tt.getType()+",");
            x_case_2 = x_case_2.concat("max(case when tonkhoxuat_xd2("+q.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+nx.getId()+",'ACTIVE') " +
                    "is null then 0 else tonkhoxuat_xd2("+q.getId()+", '"+tt.getType()+"', lxd.id,'XUAT',"+nx.getId()+",'ACTIVE') end) as x"+tt.getType()+",");
        }
        return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(n_case_1).concat(x_case_2).concat(end);
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
}
