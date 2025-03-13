package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.SheetNameCons;
import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.entity.Accounts;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.TructhuocService;
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

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class BaoCaoController implements Initializable {
    private List<NguonNx> nguonNxList = new ArrayList<>();
    private static List<String> arr_tt = new ArrayList<>();
    private final String file_name = System.getProperty("user.dir")+"\\xlsx_template\\data.xlsx";
    private static String dest_file;
    private static Accounts q = new Accounts();
    @Autowired
    private TructhuocService tructhuocService;
    @Autowired
    private NguonNxService nguonNxService;
    @FXML
    private TableView<Object> nltb_tb,lsb_tb,ptnnx_tb;
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
        dest_file = ConnectLan.pre_path+"\\baocao.xlsx";
        rvb.setPrefWidth(DashboardController.screenWidth-300);
        rvb.setPrefHeight(DashboardController.screenHeigh-300);
        nguonNxList = nguonNxService.findByAllBy();
        initDviCbb(nguonNxList);
        dvi_cbb.getSelectionModel().select(DashboardController.ref_Dv);
        initQuarter();
    }
    private void initQuarter() {
        q = ConnectLan.pre_acc;
        if (q.getSd()!=null){
            fromdate.setText(q.getSd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            todate.setText(q.getEd().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }else{
            fromdate.setText("--/--/----");
            todate.setText("--/--/----");
        }
    }

    private void initDviCbb(List<NguonNx> ls) {
        ComponentUtil.setItemsToComboBox(dvi_cbb,ls,NguonNx::getTen, input -> ls.stream().filter(x->x.getTen().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvi_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
        dvi_cbb.getSelectionModel().selectFirst();
    }
    private Integer map_bc_lcv_create(XSSFWorkbook wb,String sheetName){
        Accounts a = ConnectLan.pre_acc;
        if (a.getSd()!=null){
            if (q!=null){
                return Common.mapDataToSheet(wb.getSheet(sheetName), 8,
                        SubQuery.lcv_q(q.getSd(),q.getEd()),1);
            }
        }else{
            DialogMessage.errorShowing("Cần kết quý trước khi tạo báo cáo");
        }
        return null;
    }
    private Integer map_bc_nxt_create(XSSFWorkbook wb,String sheetName){
        Accounts a = ConnectLan.pre_acc;
        if (a.getSd()!=null){
            if (q!=null){
                return createDataSheet(wb.createSheet(sheetName), getCusQueryNl(SubQuery.begin_q1(),SubQuery.end_q1(),SubQuery.end_q1_1(),a.getSd(),a.getEd()));
            }
        }else{
            if (q!=null){
                return createDataSheet(wb.createSheet(sheetName), getCusQueryNlEmpty(SubQuery.begin_q1(),SubQuery.end_q1(),SubQuery.end_q1_1()));
            }
        }
        return null;
    }
    private Integer map_bc_nxt_getting(XSSFWorkbook wb,String sheetName){
        Accounts a = ConnectLan.pre_acc;
        if (a.getSd()!=null){
            if (q!=null) {
                return createDataSheet(wb.getSheet(sheetName), getCusQueryNl(SubQuery.begin_q1(),SubQuery.end_q1(),SubQuery.end_q1_1(),a.getSd(),a.getEd()));
            }
        }else{
            if (q!=null){
                return createDataSheet(wb.getSheet(sheetName), getCusQueryNlEmpty(SubQuery.begin_q1(),SubQuery.end_q1(),SubQuery.end_q1_1()));
            }
        }

        return null;
    }
    private Integer map_ttnlbtkh_create(XSSFWorkbook wb,String sheetName){
        if (q!=null){
            return Common.mapDataToSheet(wb.createSheet(sheetName), 8,SubQuery.ttnlbtkh_for_mb(q.getSd(),q.getEd()),1);
        }
        return null;
    }
    private Integer map_ttnlbtkh_getting(XSSFWorkbook wb,String sheetName){
        if (q!=null) {
            int row_index1 = Common.mapDataToSheet(wb.getSheet(sheetName), 8,
                    SubQuery.ttnlbtkh_for_mb(q.getSd(),q.getEd()),1);
            int row_index2 = Common.mapDataToSheet(wb.getSheet(sheetName), 8+row_index1,
                    SubQuery.ttnlbtkh_for_all(q.getSd(),q.getEd()),1);
            int row_index3 = Common.mapDataToSheet(wb.getSheet(sheetName), 8+row_index2+row_index1,
                    SubQuery.ttnlbtkh_for_dv(q.getSd(),q.getEd()),1);
            return Common.mapDataToSheet(wb.getSheet(sheetName), 8+row_index2+row_index1+row_index3,
                    SubQuery.ttnlbtkh_for_tongmaybay(q.getSd(),q.getEd()),1);
        }
        return 0;
    }
    private Integer map_ttxdtnv_create(XSSFWorkbook wb,String sheetName){
        if (q!=null) {
            return Common.mapDataToSheet(wb.createSheet(sheetName), 8, SubQuery.ttxd_nv(LocalDate.now().getYear()), 4);
        }
        return 0;
    }
    private Integer map_ttxdtnv_getting(XSSFWorkbook wb,String sheetName){
        if (q!=null){
            return Common.mapDataToSheet(wb.getSheet(sheetName), 8,SubQuery.ttxd_nv(LocalDate.now().getYear()),4);
        }
        return 0;
    }
    private Integer map_ttxd_xmt_create(XSSFWorkbook wb,String sheetName){
        if (q!=null) {
            return Common.mapDataToSheet(wb.createSheet(sheetName), 8, SubQuery.bc_ttxd_xmt_q(q.getSd(),q.getEd(),q.getSd().getYear()), 4);
        }
        return 0;
    }
    private Integer map_ttxd_xmt_get(XSSFWorkbook wb,String sheetName){
        if (q!=null) {
            return Common.mapDataToSheet(wb.getSheet(sheetName), 8, SubQuery.bc_ttxd_xmt_q(q.getSd(),q.getEd(),q.getSd().getYear()), 4);
        }
        return 0;
    }
    private Integer map_pttk_create(XSSFWorkbook wb,String sheetName){
        return Common.mapDataToSheet(wb.createSheet(sheetName), 8, SubQuery.bc_pttk_q(), 4);
    }
    private Integer map_pttk_get(XSSFWorkbook wb,String sheetName){
        return Common.mapDataToSheet(wb.getSheet(sheetName), 8, SubQuery.bc_pttk_q(), 4);
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
        String sheetName = "bc_nxt_data";
        Common.mapExcelFile(file_name,input -> map_bc_nxt_create(input,sheetName),input -> map_bc_nxt_getting(input,sheetName), SheetNameCons.NXT.getName());
        Common.copyFileExcel(file_name,dest_file);
    }
    @FXML
    public void bc_lcv(ActionEvent actionEvent) {
        if (q.getSd()!=null){
            Stage stage_1 = new Stage();
            Common.getLoading(stage_1);
            Platform.runLater(()-> {
                Common.task(this::bc_lcv_map, stage_1::close, () -> DialogMessage.successShowing("Cap nhat thanh cong"));
                lcv_lb.setText("UPDATED");
            });
        }else{
            DialogMessage.errorShowing("Cần kết quý trước khi In báo cáo!!");
        }
    }
    private void bc_lcv_map() {
        String sheetName = "luan_chuyenvon_data";
        Common.mapExcelFile(file_name,input -> map_bc_lcv_create(input,sheetName),input -> map_bc_lcv_create(input,sheetName),SheetNameCons.LCV.getName());
        Common.copyFileExcel(file_name,dest_file);
    }
    @FXML
    public void bc_ttnlbtkh(ActionEvent actionEvent) {
        if (q.getSd()!=null){
            Stage stage_1 = new Stage();
            Common.getLoading(stage_1);
            Platform.runLater(()-> {
                Common.task(this::ttnlbtkh, stage_1::close, () -> DialogMessage.successShowing("Cap nhat thanh cong"));
                ttnlbtkh_lb.setText("UPDATED");
            });
        }else{
            DialogMessage.errorShowing("Cần kết quý trước khi In báo cáo!!");
        }
    }
    private void ttnlbtkh(){
        String sheetName = "bc_ttnl_theo_kh_data";
        Common.mapExcelFile(file_name,input -> map_ttnlbtkh_create(input,sheetName),input -> map_ttnlbtkh_getting(input,sheetName),SheetNameCons.NL_BAY_THEO_KH.getName());
        Common.copyFileExcel(file_name,dest_file);
    }
    @FXML
    public void bc_ttxdtnv(ActionEvent actionEvent) {
        if (q.getSd()!=null){
            Stage stage_1 = new Stage();
            Common.getLoading(stage_1);
            Platform.runLater(()-> {
                Common.task(this::ttxdtnv,stage_1::close,()->DialogMessage.successShowing("Cap nhat thanh cong"));
                ttxdtnv_lb.setText("UPDATED");
            });
        } else {
            DialogMessage.errorShowing("Cần kết quý trước khi In báo cáo!!");
        }
    }
    private void ttxdtnv(){
        String sheetName = "t_thu_xd_theo_n_vu_data";
        Common.mapExcelFile(file_name,input -> map_ttxdtnv_create(input,sheetName),input -> map_ttxdtnv_getting(input,sheetName),SheetNameCons.TTXD.getName());
        Common.copyFileExcel(file_name,dest_file);
    }
    @FXML
    public void bc_ttxd_xmt(ActionEvent actionEvent) {
        if (q.getSd()!=null){
            Stage stage_1 = new Stage();
            Common.getLoading(stage_1);
            Platform.runLater(()-> {
                Common.task(this::ttxd_xmt,stage_1::close,()->DialogMessage.successShowing("Cap nhat thanh cong"));
                ttxd_xmt_lb.setText("UPDATED");
            });
        }else {
            DialogMessage.errorShowing("Cần kết quý trước khi In báo cáo!!");
        }
    }
    private void ttxd_xmt(){
        String sheetName = "ttxd_xmt_data";
        Common.mapExcelFile(file_name,input -> map_ttxd_xmt_create(input,sheetName),input -> map_ttxd_xmt_get(input,sheetName),SheetNameCons.TTXD_XMT.getName());
        Common.copyFileExcel(file_name,dest_file);
    }

    @FXML
    public void bc_pttk(ActionEvent actionEvent) {
        if (q.getSd()!=null){
            Stage stage_1 = new Stage();
            Common.getLoading(stage_1);
            Platform.runLater(()-> {
                Common.task(this::pttk,stage_1::close,()->DialogMessage.successShowing("Cap nhat thanh cong"));
                pttk_lb.setText("UPDATED");
            });
        } else {
            DialogMessage.errorShowing("Cần Kết quý trước khi In báo cáo!!");
        }
    }
    private void pttk(){
        String sheetName = "pttk_data";
        Common.mapExcelFile(file_name,input -> map_pttk_create(input,sheetName),input -> map_pttk_get(input,sheetName),SheetNameCons.PT_TONKHO.getName());
        Common.copyFileExcel(file_name,dest_file);
    }
    private String getCusQueryNl(String begin_1,String end_q1, String end_q1_1,LocalDate sd,LocalDate ed){
        arr_tt.clear();
        String n_sum1="";
        String x_sum2="";
        String sl1="";
        String sl2="";
        String n_case_1="";
        String x_case_2="";
        if (tdvCk.isSelected()){
            for (int i=0; i<tructhuocService.findAll().size(); i++) {
                TrucThuoc tt = tructhuocService.findAll().get(i);
                arr_tt.add(tt.getType());
                n_sum1 = n_sum1.concat("sum(n"+tt.getType()+") as "+tt.getType()+",");
                x_sum2 = x_sum2.concat("sum(x"+tt.getType()+") as "+tt.getType()+",");
                sl1=sl1.concat("case when max(n"+tt.getType()+".soluong) is null then 0 else max(n"+tt.getType()+".soluong) end as n"+tt.getType()+",");
                sl2=sl2.concat("case when max(x"+tt.getType()+".soluong) is null then 0 else max(x"+tt.getType()+".soluong) end as x"+tt.getType()+",");
                n_case_1 = n_case_1.concat(" left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and tructhuoc like '"+tt.getType()+"' and status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' group by 1) n"+tt.getType()+" on lxd.id=n"+tt.getType()+".loaixd_id");
                x_case_2 = x_case_2.concat(" left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'XUAT' and tructhuoc like '"+tt.getType()+"' and status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' group by 1) x"+tt.getType()+" on lxd.id=x"+tt.getType()+".loaixd_id");
            }
            String en = " group by 1,2,3,4,5,6) z group by rollup(tinhchat,chungloai,loai,tenxd) order by tc desc,tinhchat desc,l desc,p3 asc,xd desc";
            return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(sl1).concat(sl2).concat(end_q1_1).concat(n_case_1).concat(x_case_2).concat(en);
        }else{
            int dv_id =dvi_cbb.getSelectionModel().getSelectedItem().getId();
            for (int i=0; i<tructhuocService.findAll().size(); i++) {
                TrucThuoc tt = tructhuocService.findAll().get(i);
                arr_tt.add(tt.getType());
                n_sum1 = n_sum1.concat("sum(n"+tt.getType()+") as "+tt.getType()+",");
                x_sum2 = x_sum2.concat("sum(x"+tt.getType()+") as "+tt.getType()+",");
                sl1=sl1.concat("case when max(n"+tt.getType()+".soluong) is null then 0 else max(n"+tt.getType()+".soluong) end as n"+tt.getType()+",");
                sl2=sl2.concat("case when max(x"+tt.getType()+".soluong) is null then 0 else max(x"+tt.getType()+".soluong) end as x"+tt.getType()+",");
                n_case_1 = n_case_1.concat(" left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and tructhuoc like '"+tt.getType()+"' and status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' and l.dvi_nhan_id="+dv_id+" group by 1) n"+tt.getType()+" on lxd.id=n"+tt.getType()+".loaixd_id");
                x_case_2 = x_case_2.concat(" left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'XUAT' and tructhuoc like '"+tt.getType()+"' and status like 'ACTIVE' and l.from_date between '"+sd+"' and '"+ed+"' and l.dvi_xuat_id="+dv_id+" group by 1) x"+tt.getType()+" on lxd.id=x"+tt.getType()+".loaixd_id");
            }
            String en = " group by 1,2,3,4,5,6) z group by rollup(tinhchat,chungloai,loai,tenxd) order by tc desc,tinhchat desc,l desc,p3 asc,xd desc";
            return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(sl1).concat(sl2).concat(SubQuery.end_q2_2(dv_id)).concat(n_case_1).concat(x_case_2).concat(en);
        }
    }
    private String getCusQueryNlEmpty(String begin_1,String end_q1, String end_q1_1){
        arr_tt.clear();
        String n_sum1="";
        String x_sum2="";
        String sl1="";
        String sl2="";
        String n_case_1="";
        String x_case_2="";
        for (int i=0; i<tructhuocService.findAll().size(); i++) {
            TrucThuoc tt = tructhuocService.findAll().get(i);
            arr_tt.add(tt.getType());
            n_sum1 = n_sum1.concat("sum(n"+tt.getType()+") as "+tt.getType()+",");
            x_sum2 = x_sum2.concat("sum(x"+tt.getType()+") as "+tt.getType()+",");
            sl1=sl1.concat("case when max(n"+tt.getType()+".soluong) is null then 0 else max(n"+tt.getType()+".soluong) end as n"+tt.getType()+",");
            sl2=sl2.concat("case when max(x"+tt.getType()+".soluong) is null then 0 else max(x"+tt.getType()+".soluong) end as x"+tt.getType()+",");
            n_case_1 = n_case_1.concat(" left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'NHAP' and tructhuoc like '"+tt.getType()+"' and status like 'ACTIVE' group by 1) n"+tt.getType()+" on lxd.id=n"+tt.getType()+".loaixd_id");
            x_case_2 = x_case_2.concat(" left join (select loaixd_id, sum(so_luong) as soluong from ledgers l join ledger_details ld on l.id=ld.ledger_id where loai_phieu like 'XUAT' and tructhuoc like '"+tt.getType()+"' and status like 'ACTIVE' group by 1) x"+tt.getType()+" on lxd.id=x"+tt.getType()+".loaixd_id");
        }
        String en = " group by 1,2,3,4,5,6) z group by rollup(tinhchat,chungloai,loai,tenxd) order by tc desc,tinhchat desc,l desc,p3 asc,xd desc";
        return begin_1.concat(n_sum1).concat(x_sum2).concat(end_q1).concat(sl1).concat(sl2).concat(end_q1_1).concat(n_case_1).concat(x_case_2).concat(en);
    }

    private int createDataSheet(XSSFSheet sheet,String query) {
        int sizett = arr_tt.size();
        ReportDAO reportDAO = new ReportDAO();
        List<Object[]> nxtls = reportDAO.findByWhatEver(query);
        XSSFRow row_header = sheet.createRow(7);
        if (row_header==null){
            row_header = sheet.createRow(7);
        }
        XSSFRow row_header_1 = sheet.createRow(6);
        if (row_header_1==null){
            row_header_1 = sheet.createRow(6);
        }
        for (int i=0;i<arr_tt.size();i++){
            row_header_1.createCell(8+i).setCellValue("NHAP");
            row_header_1.createCell(sizett+8+i).setCellValue("XUAT");
            row_header.createCell(8+i).setCellValue(arr_tt.get(i));
            row_header.createCell(sizett+8+i).setCellValue(arr_tt.get(i));
        }
        for(int i =0; i< nxtls.size(); i++){
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(8+i);
            for (int j =0;j<rows_data.length;j++){
//                row.createCell(j+1).setCellValue(rows_data[j]==null ?"" : rows_data[j].toString());
                String val = rows_data[j]==null ?"" : rows_data[j].toString();
                if (row.getCell(j+1)==null){
                    if (val==null){
                        row.createCell(j+1).setCellValue(val);
                    } else if (Common.isDoubleNumber(val)){
                        BigDecimal bigDecimal = new BigDecimal(val).setScale(1, RoundingMode.HALF_UP);
                        row.createCell(j+1).setCellValue(bigDecimal.doubleValue());
                    } else {
                        row.createCell(j+1).setCellValue(val);
                    }
                }else{
                    if (val==null){
                        row.getCell(j+1).setCellValue(val);
                    } else if (Common.isDoubleNumber(val)){
                        BigDecimal bigDecimal = new BigDecimal(val).setScale(1, RoundingMode.HALF_UP);
                        row.getCell(j+1).setCellValue(bigDecimal.doubleValue());
                    } else {
                        row.getCell(j+1).setCellValue(val);
                    }
                }
            }
        }
        arr_tt.clear();
        return nxtls.size()+11;
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

    @FXML
    public void bc_nxt_experiment(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(MessageCons.TITLE_PRINT.getName(), MessageCons.HEADER_PRINT.getName(), MessageCons.CONTENT.getName(), Alert.AlertType.CONFIRMATION)== ButtonType.OK){
            String currentDir = System.getProperty("user.dir");
            String temp_file_name=currentDir+"\\xlsx_template\\phieu_mau.xlsx";
            String file_name_1 = "/baocao.xlsx";
            if (Common.isDirectory(ConnectLan.pre_path)){
                try {
                    String sheet_n = "beta_nxt";
                    Platform.runLater(()->{
                        Common.copyFileExcel(temp_file_name, ConnectLan.pre_path+"/"+file_name_1);
                        StringBuilder file_name = new StringBuilder().append(ConnectLan.pre_path).append("/").append(file_name_1);
                        Common.mapExcelFile(file_name.toString(),(input)->fillDataToPhieuNhap(input.createSheet(sheet_n),true),
                                (input)->fillDataToPhieuNhap(input.getSheet(sheet_n),false),sheet_n);
                        if (DialogMessage.callAlertWithMessage(null,"Thanh cong","Click OK để mở thư mục xuất phiếu." , Alert.AlertType.INFORMATION)==ButtonType.OK){
                            Common.openDesktop();
                        }
                    });
                } catch (Exception e) {
                    DialogMessage.errorShowing("Có lỗi xảy ra, vui lòng đóng file phieu_nhap_xuat trước khi tạo file mới.");
                    throw new RuntimeException(e);
                }
            }else {
                DialogMessage.message(null,null,"Thư mục tại " + ConnectLan.pre_path + " không tồn tại. Cấu hình thư mục báo cáo tại --Setting--", Alert.AlertType.WARNING);
                DashboardController.primaryStage.close();
            }
        }
    }
    private int fillDataToPhieuNhap(XSSFSheet sheet, boolean isNew){
        setCEll(sheet, ledger.getDvi_nhan(), 3,3,isNew);
        setCEll(sheet, ledger.getDvi_xuat(), 4,3,isNew);
        if (tcnService.findById(ledger.getTcn_id()).orElse(null)==null){
            setCEll(sheet,ledger.getNhiemvu(), 5,3,isNew);
        } else {
            setCEll(sheet, tcnService.findById(ledger.getTcn_id()).orElse(null).getName(), 5,3,isNew);
        }

        setCEll(sheet, ledger.getLenh_so(), 6,3,isNew);
        setCEll(sheet, ledger.getNguoi_nhan(), 7,3,isNew);
        setCEll(sheet, "ABC", 8,3,isNew);
        setCEll(sheet, ledger.getEnd_date()==null? "" : ledger.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 3,10,isNew);
        setCEll(sheet, ledger.getSo_xe(), 4,10,isNew);
        setCEll(sheet, String.valueOf(ledger.getBill_id()), 3,7,isNew);
        setCEll(sheet, ledger.getFrom_date()==null?  "": ledger.getFrom_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 4,7,isNew);
        for (int i = 0; i< details.size(); i++) {
            addNewRow(sheet);
        }
        int row_num = 12;
        double thanh_tien = 0;
        for (int i = 0; i< details.size(); i++) {
            setCEll(sheet, String.valueOf(i+1), row_num,1,isNew);
            setCEll(sheet, details.get(i).getMa_xd(), row_num,2,isNew);
            setCEll(sheet, details.get(i).getTen_xd(), row_num,3,isNew);
            setCEll(sheet, details.get(i).getChat_luong(), row_num,4,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getSoluong_px()), row_num,5,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getNhiet_do_tt()), row_num,6,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getTy_trong()), row_num,7,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getHe_so_vcf()), row_num,8,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getSoluong()), row_num,9,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getDon_gia()), row_num,10,isNew);
            setCEll(sheet, String.valueOf(details.get(i).getSoluong()*details.get(i).getDon_gia()), row_num,11,isNew);
            row_num = row_num+1;
            thanh_tien = thanh_tien + (details.get(i).getDon_gia() * details.get(i).getSoluong());
            if (i == details.size() - 1){
                setCEll(sheet,String.valueOf(ledger.getAmount()), 14+details.size(),11,isNew);
            }
        }
        return 1;
    }
    private void addNewRow(XSSFSheet sheet){
        if (sheet.getPhysicalNumberOfRows() > 0) {
            int lastRow = sheet.getLastRowNum();
            sheet.shiftRows(13, lastRow, 1, true, true);
            sheet.copyRows(13,14,13,new CellCopyPolicy());
        } else {
            System.out.println("The sheet is empty.");
        }
    }
    private void setCEll(XSSFSheet sheet, String value, int row_num, int cell_num,boolean isNew){
        if (!isNew){
            XSSFRow row = sheet.getRow(row_num);
            if (StringUtils.isNumeric(value)){
                BigDecimal bigDecimal = new BigDecimal(value);
                bigDecimal.setScale(2, RoundingMode.HALF_UP);
                NumberFormat numberFormat = NumberFormat.getInstance();
                numberFormat.setMinimumFractionDigits(2);
                row.getCell(cell_num).setCellValue(numberFormat.format(bigDecimal));
            } else {
                row.getCell(cell_num).setCellValue(value);
            }
        }else{
            XSSFRow row = sheet.createRow(row_num);
            if (StringUtils.isNumeric(value)){
                row.createCell(cell_num).setCellValue(new BigDecimal(value).doubleValue());
            } else {
                row.createCell(cell_num).setCellValue(value);
            }
        }
    }
}
