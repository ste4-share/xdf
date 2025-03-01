package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.TypeCons;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.LedgerService;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
@Component
public class LedgerDetailController implements Initializable {
    private List<LedgerDetails> details = new ArrayList<>();
    private final Ledger ledger = LedgerController.ledger_id;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
        xmt.setText(ledger.getLpt());
        loai_xmt.setText(ledger.getLpt_2());
        getDinhmucPhuongtien(ledger);
        so.setText(String.valueOf(ledger.getBill_id()));
        tcnx.setText(tcnService.findById(ledger.getTcn_id()).get().getName());
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
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(details.indexOf(column.getValue())+1).asString());
        tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        loaixd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("chung_loai"));
        gia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
            px.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phainhap_str"));
            tx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thucnhap_str"));
        }else{
            px.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phaixuat_str"));
            tx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thucxuat_str"));
        }
        thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
    }
    @FXML
    public void closeAction(ActionEvent actionEvent) {
        LedgerController.primaryStage.close();
    }
}
