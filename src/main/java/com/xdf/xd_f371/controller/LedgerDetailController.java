package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.SheetNameCons;
import com.xdf.xd_f371.cons.TypeCons;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.LedgerService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellCopyPolicy;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
@Component
public class LedgerDetailController implements Initializable {
    private List<LedgerDetails> details = new ArrayList<>();
    private Ledger ledger = new Ledger();
    @FXML
    private Label phieu,tungay,denngay,dvx,dvn,nv,km,gio,loainv,nguoinhan,xmt,loai_xmt,dinhmuckm,dinhmucgio,dinhmucmd,dinhmuctk,so,tcnx,lenhkh,soxe;
    @FXML
    private TableView<LedgerDetails> chitietdonhang_table;
    @FXML
    private TableColumn<LedgerDetails,String> stt,tenxd,loaixd,gia,nhietdo,tytrong,vcf,px,tx,thanhtien;

    @Autowired
    private LedgerService ledgerService;
    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private TcnService tcnService;
    @Autowired
    private PhuongtienService phuongtienService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ledger = LedgerController.ledger;
        setCellFactoryForTable();
        details = ledgerService.getLedgerDetailById(ledger.getId());
        convertDetailsList();
        setItemsForTable(details);
        initLabelValues();
    }
    private void convertDetailsList(){
        details.forEach(x->{
            x.setPhainhap_str(TextToNumber.textToNum_2digits(x.getPhai_nhap()));
            x.setThucnhap_str(TextToNumber.textToNum_2digits(x.getThuc_nhap()));
            x.setPhaixuat_str(TextToNumber.textToNum_2digits(x.getPhai_xuat()));
            x.setSoluong_str(TextToNumber.textToNum_2digits(x.getSoluong()));
            x.setSoluongpx_str(TextToNumber.textToNum_2digits(x.getSoluong_px()));
            x.setThucxuat_str(TextToNumber.textToNum_2digits(x.getThuc_xuat()));
            x.setDongia_str(TextToNumber.textToNum_2digits(x.getDon_gia()));
            x.setThanhtien_str(TextToNumber.textToNum_2digits(x.getThanhtien()));
        });
    }
    private void initLabelValues(){
        phieu.setText(ledger.getLoai_phieu());
        tungay.setText(ledger.getFrom_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        denngay.setText(ledger.getEnd_date()==null ? "..." : ledger.getEnd_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        dvx.setText(ledger.getDvi_xuat());
        dvn.setText(ledger.getDvi_nhan());
        nv.setText(ledger.getNhiemvu());
        km.setText(String.valueOf(ledger.getSo_km()));
        if (ledger.getLoaigiobay()==null){
            gio.setText("00:00");
        } else if (ledger.getLoaigiobay().equals(TypeCons.TREN_KHONG.getName())){
            gio.setText(ledger.getGiohd_tk());
        }else {
            gio.setText(ledger.getGiohd_md());
        }
        loainv.setText(ledger.getLoainv());
        nguoinhan.setText(ledger.getNguoi_nhan());
        Optional<PhuongTien> pt = phuongtienService.findById(ledger.getPt_id());
        pt.ifPresent(x->xmt.setText(x.getName()));

        loai_xmt.setText(ledger.getLpt());
        getDinhmucPhuongtien(ledger);
        so.setText(String.valueOf(ledger.getBill_id()));
        if (tcnService.findById(ledger.getTcn_id()).isPresent()){
            tcnx.setText(tcnService.findById(ledger.getTcn_id()).get().getName());
        }else {
            tcnx.setText(null);
        }
        lenhkh.setText(ledger.getLenh_so());
        soxe.setText(ledger.getSo_xe());
    }
    private void getDinhmucPhuongtien(Ledger l){
        Optional<DinhMuc> dm = dinhmucService.findDinhmucByPhuongtien(l.getPt_id(),l.getFrom_date().getYear());
        if (dm.isPresent()){
            dinhmuckm.setText(String.valueOf(dm.get().getDm_xm_km()));
            dinhmucgio.setText(String.valueOf(dm.get().getDm_xm_gio()));
            dinhmucmd.setText(String.valueOf(dm.get().getDm_md_gio()));
            dinhmuctk.setText(String.valueOf(dm.get().getDm_tk_gio()));
        }else {
            dinhmuckm.setText("...");
            dinhmucgio.setText("...");
            dinhmucmd.setText("...");
            dinhmuctk.setText("...");
        }
    }
    private void setItemsForTable(List<LedgerDetails> ls){
        chitietdonhang_table.refresh();
        chitietdonhang_table.setItems(FXCollections.observableList(ls));
    }
    private void setCellFactoryForTable(){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(chitietdonhang_table.getItems().indexOf(column.getValue())+1).asString());
        tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        loaixd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("chung_loai"));
        gia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        px.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("soluongpx_str"));
        tx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("soluong_str"));
        thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
    }

    private void printBill(){
        if (DialogMessage.callAlertWithMessage(MessageCons.TITLE_PRINT.getName(), MessageCons.HEADER_PRINT.getName(), MessageCons.CONTENT.getName(), Alert.AlertType.CONFIRMATION)== ButtonType.OK){
            String currentDir = System.getProperty("user.dir");
            String temp_file_name=currentDir+"\\xlsx_template\\phieu_mau.xlsx";
            String file_name_1 = "/phieu_nhap_xuat.xlsx";
            if (Common.isDirectory(ConnectLan.pre_path)){
                try {
                    Platform.runLater(()->{
                        Common.copyFileExcel(temp_file_name, ConnectLan.pre_path+"/"+file_name_1);
                        StringBuilder file_name = new StringBuilder().append(ConnectLan.pre_path).append("/").append(file_name_1);
                        Common.mapExcelFile(file_name.toString(),(input)->fillDataToPhieuNhap(input.createSheet(getPhieu()),true),
                                (input)->fillDataToPhieuNhap(input.getSheet(getPhieu()),false),getPhieu());
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
    private String getPhieu(){
        if (ledger.getLoai_phieu().equals("NHAP")){
            return SheetNameCons.PHIEU_NHAP.getName();
        }
        return SheetNameCons.PHIEU_XUAT.getName();
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
    @FXML
    public void closeAction(ActionEvent actionEvent) {
        LedgerController.primaryStage.close();
    }
    @FXML
    public void exportFileAction(ActionEvent actionEvent) {
        printBill();
    }
    @FXML
    public void deleteLedgerAction(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null, "ban co chac chan muon xoa","Xoa", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            ledgerService.inactiveLedger(ledger.getId());
            DialogMessage.message(null,"Xoa thanh cong", null, Alert.AlertType.INFORMATION);
            DashboardController.primaryStage.close();
        }
    }
}
