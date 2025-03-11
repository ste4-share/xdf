package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.SheetNameCons;
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
import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
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
    }
    @FXML
    public void exitBtn(ActionEvent actionEvent) {
        DashboardController.primaryStage.close();
    }


    private void addNewImport() {
        fct_stt.setSortable(false);
        fct_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tbChiTiet.getItems().indexOf(column.getValue())+1).asString());
        fct_tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("ten_xd"));
        fct_dongia.setCellValueFactory(new PropertyValueFactory<LedgerDto, String>("dongia_str"));
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
            DashboardController.primaryStage.close();
        }
    }
}
