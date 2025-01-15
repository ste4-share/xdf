package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.dto.LedgerDto;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.LedgerService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class ChiTietSCController implements Initializable {
    private List<LedgerDto> ls = new ArrayList<>();

    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private TcnService tcnService;
    @Autowired
    private LedgerService ledgerService;

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
            String currentDir = System.getProperty("user.dir");
            String temp_file_name=currentDir+"\\xlsx_template\\phieu_mau.xlsx";
            String file_name_1 = "/phieu_nhap_xuat.xlsx";
            if (Common.isDirectory(ConnectLan.pre_path)){
                    Platform.runLater(()->{
                        Common.copyFileExcel(temp_file_name, ConnectLan.pre_path+"/"+file_name_1);
                        StringBuilder file_name = new StringBuilder().append(ConnectLan.pre_path).append("/").append(file_name_1);
                        Common.mapExcelFile(file_name.toString(),(input)->fillDataToPhieuNhap(input.createSheet(getPhieu()),true),
                                (input)->fillDataToPhieuNhap(input.getSheet(getPhieu()),false));
                        if (DialogMessage.callAlertWithMessage(null,"Thanh cong","Click OK để mở thư mục xuất phiếu." , Alert.AlertType.INFORMATION)==ButtonType.OK){
                            Common.openDesktop();
                        }
                    });
            }else {
                DialogMessage.message(null,null,"Thư mục tại " + ConnectLan.pre_path + " không tồn tại. Cấu hình thư mục báo cáo tại --Setting--", Alert.AlertType.WARNING);
                DashboardController.ctStage.close();
            }
        }
    }
    private String getPhieu(){
        if (ls.get(0).getLoai_phieu().equals("NHAP")){
            return "phieu_nhap";
        }
        return "phieu_xuat";
    }

    private int fillDataToPhieuNhap(XSSFSheet sheet,boolean isNew){
        setCEll(sheet, ls.get(0).getDvi_nhan(), 3,3,isNew);
        setCEll(sheet, ls.get(0).getDvi_xuat(), 4,3,isNew);
        if (tcnService.findById(ls.get(0).getTcn_id()).orElse(null)==null){
            setCEll(sheet, chitietNhiemvuService.findById(ls.get(0).getNhiemvu_id()).orElse(null).getNhiemvu(), 5,3,isNew);
        } else {
            setCEll(sheet, tcnService.findById(ls.get(0).getTcn_id()).orElse(null).getName(), 5,3,isNew);
        }

        setCEll(sheet, ls.get(0).getLenh_so(), 6,3,isNew);
        setCEll(sheet, ls.get(0).getNguoi_nhan(), 7,3,isNew);
        setCEll(sheet, "ABC", 8,3,isNew);
        setCEll(sheet, ls.get(0).getEnd_date()==null? "" : ls.get(0).getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 3,10,isNew);
        setCEll(sheet, ls.get(0).getSo_xe(), 4,10,isNew);
        setCEll(sheet, String.valueOf(ls.get(0).getBill_id()), 3,7,isNew);
        setCEll(sheet, ls.get(0).getFrom_date()==null?  "": ls.get(0).getFrom_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")), 4,7,isNew);
        for (int i = 0; i< ls.size(); i++) {
            addNewRow(sheet);
        }
        int row_num = 12;
        Long thanh_tien = 0L;
        for (int i = 0; i< ls.size(); i++) {
            setCEll(sheet, String.valueOf(i+1), row_num,1,isNew);
            setCEll(sheet, ls.get(i).getMa_xd(), row_num,2,isNew);
            setCEll(sheet, ls.get(i).getTen_xd(), row_num,3,isNew);
            setCEll(sheet, ls.get(i).getChat_luong(), row_num,4,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getSoluong_px()), row_num,5,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getNhiet_do_tt()), row_num,6,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getTy_trong()), row_num,7,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getHe_so_vcf()), row_num,8,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getSoluong()), row_num,9,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getDon_gia()), row_num,10,isNew);
            setCEll(sheet, String.valueOf(ls.get(i).getSoluong()*ls.get(i).getDon_gia()), row_num,11,isNew);
            row_num = row_num+1;
            thanh_tien = thanh_tien + ((long) ls.get(i).getDon_gia() * ls.get(i).getSoluong());
            if (i == ls.size() - 1){
                setCEll(sheet,String.valueOf(ls.get(0).getAmount()), 14+ls.size(),11,isNew);
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
                row.getCell(cell_num).setCellValue(new BigDecimal(value).doubleValue());
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
            if (ls.get(0).getHaohut_sl()==0){
                lb_dvn.setText(phuongtienService.findById(ls.get(0).getPhuongtien_id()).orElse(null).getName());
                lb_tcn.setText(chitietNhiemvuService.findById(ls.get(0).getNhiemvu_id()).orElse(null).getNhiemvu());
            }else{
                lb_dvn.setText(ls.get(0).getDvi_nhan());
                lb_tcn.setText(chitietNhiemvuService.findById(ls.get(0).getNhiemvu_id()).orElse(null).getNhiemvu());
            }
        }
        lb_so.setText(String.valueOf(ls.get(0).getBill_id()));
        lb_nguoinhan.setText(ls.get(0).getNguoi_nhan());
        lb_lenhkh.setText(ls.get(0).getLenh_so());
        lb_soxe.setText(ls.get(0).getSo_xe());
        lb_tungay.setText(ls.get(0).getFrom_date()==null ? "" : ls.get(0).getFrom_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lb_loaiphieu.setText(ls.get(0).getLoai_phieu());
        lb_denngay.setText(ls.get(0).getEnd_date()==null ? "" : ls.get(0).getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        lb_giohd_md.setText(ls.get(0).getGiohd_md());
        lb_giohd_tk.setText(ls.get(0).getGiohd_tk());
        lb_sokm.setText(String.valueOf(ls.get(0).getSo_km()));
    }
    @FXML
    public void delClicked(MouseEvent mouseEvent) {
        if (DialogMessage.callAlertWithMessage(null, "ban co chac chan muon xoa","Xoa", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            ledgerService.inactiveLedger(ls.get(0).getLedger_id());
            DialogMessage.message(null,"Xoa thanh cong", null, Alert.AlertType.INFORMATION);
            DashboardController.ctStage.close();
        }
    }
}
