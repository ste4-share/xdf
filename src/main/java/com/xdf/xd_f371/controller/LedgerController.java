package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.InvDto3;
import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.LedgerService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.TcnService;
import com.xdf.xd_f371.util.*;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class LedgerController implements Initializable {
    private static List<Ledger> ledgerSelectList = new ArrayList<>();
    private static List<String> selectedDateLs = new ArrayList<>();
    private List<LedgerDetails> details = new ArrayList<>();
    private LocalDate currentDateSelected;
    public static Stage primaryStage;
    public static Ledger ledger = new Ledger();

    @Autowired
    private LedgerService ledgerService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private DinhmucService dinhmucService;
    @Autowired
    private TcnService tcnService;

    @FXML
    private Button ref_to_root;
    @FXML
    private TableView<Ledger> ledgers_table,doichieu_table_1,doichieu_table_2;
    @FXML
    private TableColumn<Ledger,String> ledgers_col_stt,ledgers_col_id,ledgers_col_so,ledgers_col_stdate,ledgers_col_edate,ledgers_col_lenhkh,ledgers_col_loainx,
            ledgers_col_dvn,ledgers_col_dvx,ledgers_col_xmt,ledgers_col_nv,ledgers_col_note,ledgers_col_createtime,
            root_col_stt,root_col_id,root_col_so,root_col_lenh,root_col_loainx,root_col_dvnhap,root_col_dvx,root_col_xmt,root_col_nv,root_col_note,
            ref_col_stt,ref_col_id,ref_col_so,ref_col_lenhkh,ref_col_loainx,ref_col_dvn,ref_col_dvx,ref_col_xmt,ref_col_nv,ref_col_note;
    @FXML
    private TableView<LedgerDetails> chitiet_tb;
    @FXML
    private TableColumn<LedgerDetails,String> cttb_stt,cttb_txd,cttb_loai,cttb_dongia,cttb_nhietdo,cttb_tytrong,cttb_vcf,cttb_px,cttb_tx,cttb_thanhtien;
    @FXML
    private TableView<InvDto3> tonkho_tb;
    @FXML
    private Label tab1_dvi_label,root_unit_lable,unitClickedLable,ctnv_lb,xmt_lb,loaixmt_lb,km_lb,giohd_lb,giohd_tk_lb,giohd_md_lb,nguoinhan_lb;
    @FXML
    private DatePicker st_time,lst_time,tab2_tungay,tab2_denngay;
    @FXML
    private ListView<String> date_ls;
    @FXML
    private RadioButton all_rd,nhap_rd,xuat_rd;
    @FXML
    private CheckBox mucgiaCk;
    @FXML
    private TextField search_by_name_tf;
    @FXML
    private ComboBox<NguonNx> dvi_ref_cbb;
    @FXML
    private CheckBox hideCkb;
    @FXML
    private HBox importBtn;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all_rd.setSelected(true);
        hoverBtn();
        initVietnameseDate();
        initTableSize();
        initRootUnitLable();
        initNnxCombobox();
        initStartDate();
        initLocalDateList();
        setCellFactoryForLeger_table();
        initLedgerList();
    }

    private void setCellFactoryForTable() {
        cttb_stt.setSortable(false);
        cttb_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(details.indexOf(column.getValue())+1).asString());
        cttb_txd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        cttb_loai.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("chung_loai"));
        cttb_dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        cttb_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        cttb_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        cttb_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
            cttb_px.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phainhap_str"));
            cttb_tx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thucnhap_str"));
        }else{
            cttb_px.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phaixuat_str"));
            cttb_tx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thucxuat_str"));
        }
        cttb_thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
    }

    private void initLedgerList() {
        LocalDate st = st_time.getValue();
        LocalDate et = lst_time.getValue();
        if (validDate(st,et)){
            ledgerSelectList = ledgerService.findAllLedgerDto(st,et,DashboardController.ref_Dv.getId());
            setItemToTableView(ledgerSelectList);
        }
    }
    private void getLedgerByDate(LocalDate d){
        setItemToTableView(ledgerSelectList.stream().filter(x->x.getFrom_date().equals(d)).toList());
    }
    private void setItemToTableView(List<Ledger> ls){
        ledgers_table.setItems(FXCollections.observableList(ls));
        ledgers_table.refresh();
    }
    private void setCellFactoryForLeger_table(){
        ledgers_col_stt.setSortable(false);
        ledgers_col_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(ledgers_table.getItems().indexOf(column.getValue())+1).asString());
        ledgers_col_id.setCellValueFactory(new PropertyValueFactory<Ledger, String>("id"));
        ledgers_col_so.setCellValueFactory(new PropertyValueFactory<Ledger, String>("bill_id"));
        ledgers_col_stdate.setCellValueFactory(new PropertyValueFactory<Ledger, String>("from_date"));
        ledgers_col_edate.setCellValueFactory(new PropertyValueFactory<Ledger, String>("end_date"));
        ledgers_col_lenhkh.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lenh_so"));
        ledgers_col_loainx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("loai_phieu"));
        ledgers_col_dvn.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_nhan"));
        ledgers_col_dvx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_xuat"));
        ledgers_col_xmt.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lpt"));
        ledgers_col_nv.setCellValueFactory(new PropertyValueFactory<Ledger, String>("nhiemvu"));
        ledgers_col_note.setCellValueFactory(new PropertyValueFactory<Ledger, String>("note"));
        ledgers_col_createtime.setCellValueFactory(new PropertyValueFactory<Ledger, String>("timestamp"));
    }
    private void initLocalDateList() {
        dateLoading();
    }
    private void setLocalDateList(List<String> ls){
        date_ls.setItems(FXCollections.observableList(ls));
    }
    private void initStartDate() {
        st_time.setValue(ConnectLan.pre_acc.getSd());
        lst_time.setValue(ConnectLan.pre_acc.getEd());
        tab2_tungay.setValue(ConnectLan.pre_acc.getSd());
        tab2_denngay.setValue(ConnectLan.pre_acc.getEd());
    }
    private void initNnxCombobox() {
        ComponentUtil.setItemsToComboBox(dvi_ref_cbb,nguonNxService.findByAllBy(),NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvi_ref_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
    }
    private void hoverBtn() {
        Common.hoverButton(ref_to_root ,"#027a20");
        importBtn.setOnMouseEntered(e -> importBtn.setStyle("-fx-background-color: #027a20; -fx-border-color: #000000; -fx-border-width:3;-fx-background-radius:10;-fx-border-radius:10"));
        importBtn.setOnMouseExited(e -> importBtn.setStyle("-fx-background-color: #027a20;-fx-border-color:#a8a8a8;-fx-background-radius:10;-fx-border-radius:10"));
    }
    private void initVietnameseDate() {
        CommonFactory.setVi_DatePicker(st_time);
        CommonFactory.setVi_DatePicker(lst_time);
        CommonFactory.setVi_DatePicker(tab2_tungay);
        CommonFactory.setVi_DatePicker(tab2_denngay);
    }
    private void initRootUnitLable() {
        root_unit_lable.setText(nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()).get(0).getTen());
        tab1_dvi_label.setText(nguonNxService.findByStatus(StatusCons.ROOT_STATUS.getName()).get(0).getTen());
        unitClickedLable.setText("-----");
    }
    private void initTableSize() {
        ledgers_table.setPrefWidth(DashboardController.screenWidth-500);
        ledgers_table.setPrefHeight(DashboardController.screenHeigh-800);
        chitiet_tb.setPrefWidth(DashboardController.screenWidth-300);
        chitiet_tb.setPrefHeight(DashboardController.screenHeigh-800);
        doichieu_table_1.setPrefWidth(DashboardController.screenWidth-300);
        doichieu_table_1.setPrefHeight(DashboardController.screenHeigh-500);
        doichieu_table_2.setPrefWidth(DashboardController.screenWidth-300);
        doichieu_table_2.setPrefHeight(DashboardController.screenHeigh-500);
        tonkho_tb.setPrefWidth(DashboardController.screenWidth-300);
        tonkho_tb.setPrefHeight(DashboardController.screenHeigh-400);
    }
    @FXML
    public void select_date_Clicked(MouseEvent mouseEvent) {
        String d = date_ls.getSelectionModel().getSelectedItems().get(0).toString();
        currentDateSelected = stringToDate(d);
        getLedgerByDate(currentDateSelected);
    }
    @FXML
    public void all_radio_action(ActionEvent actionEvent) {
        setItemToTableView(ledgerSelectList.stream().filter(x->x.getFrom_date().equals(currentDateSelected)).toList());
    }
    @FXML
    public void nhap_radio_action(ActionEvent actionEvent) {
        setItemToTableView(ledgerSelectList.stream().filter(x->x.getFrom_date().equals(currentDateSelected)).filter(x->x.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())).toList());
    }
    @FXML
    public void xuat_radio_action(ActionEvent actionEvent) {
        setItemToTableView(ledgerSelectList.stream().filter(x->x.getFrom_date().equals(currentDateSelected)).filter(x->x.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName())).toList());
    }
    @FXML
    public void select_ledger_table(MouseEvent mouseEvent) {
        Ledger l = ledgers_table.getSelectionModel().getSelectedItem();
        if (l!=null){
            ledger = l;
            setLEdgerDetailLabel(ledger);
            setCellFactoryForTable();
            List<LedgerDetails> details = ledgerService.getLedgerDetailById(ledger.getId());
            details.stream().forEach(x->{
                x.setDongia_str(TextToNumber.textToNum_2digits(x.getDon_gia()));
                x.setThucxuat_str(TextToNumber.textToNum_2digits(x.getThuc_xuat()));
                x.setPhaixuat_str(TextToNumber.textToNum_2digits(x.getPhai_xuat()));
                x.setThucnhap_str(TextToNumber.textToNum_2digits(x.getThuc_nhap()));
                x.setThanhtien_str(TextToNumber.textToNum_2digits(x.getThanhtien()));
            });
            setLedgerDetailTable(details);
        }
    }
    private void setLEdgerDetailLabel(Ledger l){
        ctnv_lb.setText(l.getNhiemvu());
        xmt_lb.setText(l.getLpt());
        loaixmt_lb.setText(l.getLpt_2());
        km_lb.setText(TextToNumber.textToNum(String.valueOf(l.getSo_km())));
        giohd_lb.setText(l.getGiohd_md());
        giohd_md_lb.setText(l.getGiohd_md());
        giohd_tk_lb.setText(l.getGiohd_tk());
        nguoinhan_lb.setText(l.getNguoi_nhan());
    }
    private void setLedgerDetailTable(List<LedgerDetails> ls){
        chitiet_tb.refresh();
        chitiet_tb.setItems(FXCollections.observableList(ls));
    }
//    private void showDetailsScreen(){
//        primaryStage = new Stage();
//        Common.openNewStage2("ledger_details.fxml",primaryStage,"Chi tiết phiếu", StageStyle.UTILITY);
//    }
    @FXML
    public void doichieu_table_1Clicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void tungayAction(ActionEvent actionEvent) {
    }
    @FXML
    public void denngayAction(ActionEvent actionEvent) {
    }
    @FXML
    public void ref_to_rootAction(ActionEvent actionEvent) {
    }
    @FXML
    public void dvi_ref_cbbAction(ActionEvent actionEvent) {
    }
    @FXML
    public void doichieu_table_2Click(MouseEvent mouseEvent) {
    }
    @FXML
    public void importBtnClick(MouseEvent mouseEvent) {
    }
    @FXML
    public void search_by_name_Click(MouseEvent mouseEvent) {
    }
    @FXML
    public void search_by_name_tfAction(ActionEvent actionEvent) {
    }
    @FXML
    public void mucgiaCkAction(ActionEvent actionEvent) {
    }
    @FXML
    public void dateLoadingClick(MouseEvent mouseEvent) {
        dateLoading();
    }

    private void dateLoading() {
        LocalDate st = st_time.getValue();
        LocalDate et = lst_time.getValue();
        listDate(st,et);
    }

    private void listDate(LocalDate st,LocalDate et){
        if (validDate(st,et)){
            selectedDateLs = new ArrayList<>();
            List<LocalDate> localDateList = st.datesUntil(et)
                    .toList();
            localDateList.forEach(x->{
                selectedDateLs.add(x.format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName())));
            });
            setLocalDateList(selectedDateLs);
        }else{
            DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
        }
    }
    private boolean validDate(LocalDate st,LocalDate et){
        if (st!=null){
            if (et!=null){
                if (et.isAfter(st)){
                    lst_time.setStyle(null);
                    st_time.setStyle(null);
                    return true;
                }
            }else{
                DialogMessage.errorShowing(MessageCons.NOT_SELECT_END_TIME.getName());
                lst_time.setStyle(CommonFactory.styleErrorField);
            }
        }else{
            DialogMessage.errorShowing(MessageCons.NOT_SELECT_START_TIME.getName());
            st_time.setStyle(CommonFactory.styleErrorField);
        }
        return false;
    }
    private LocalDate stringToDate(String date){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName());
        return LocalDate.parse(date,dtf);
    }
    @FXML
    public void outClick(MouseEvent mouseEvent) {
        setItemToTableView(new ArrayList<>());
    }
    @FXML
    public void hideDay(ActionEvent actionEvent) {
        if (hideCkb.isSelected()){
            LocalDate st = st_time.getValue();
            initLedgerList();
            List<LocalDate> ls = ledgerSelectList.stream().map(Ledger::getFrom_date).filter(fromDate -> fromDate.isAfter(st)).distinct().toList();
            setLocalDateList(ls.stream().map(x->x.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).toList());
        }else{
            setLocalDateList(selectedDateLs);
        }
    }
}
