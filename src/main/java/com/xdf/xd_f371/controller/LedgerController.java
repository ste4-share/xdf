package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.InvDto3;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.fatory.ExportFactory;
import com.xdf.xd_f371.repo.ReportDAO;
import com.xdf.xd_f371.service.*;
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
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;

@Component
public class LedgerController implements Initializable {
    private final String file_name = "data.xlsx";
    private List<Ledger> ledgerSelectList = new ArrayList<>();
    private List<Ledger> refLedgerList = new ArrayList<>();
    private List<TransactionHistory> refTransactionHistorysList = new ArrayList<>();
    private List<LedgerDetails> refLedgerDetailList = new ArrayList<>();
    private LocalDate currentDateSelected;
    public static Stage primaryStage;
    public static Ledger ledger = new Ledger();

    @Autowired
    private LedgerService ledgerService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private PhuongtienService phuongtienService;
    @FXML
    private Button ref_to_root;
    @FXML
    private TableView<Ledger> ledgers_table,root_table,ref_table;
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
    private Label tab1_dvi_label,root_unit_lable,unitClickedLable,ctnv_lb,xmt_lb,loaixmt_lb,km_lb,giohd_lb,giohd_tk_lb,giohd_md_lb,nguoinhan_lb,amount_lb;
    @FXML
    private DatePicker st_time,lst_time,tab2_tungay,tab2_denngay;
    @FXML
    private ListView<String> date_ls;
    @FXML
    private RadioButton all_rd,nhap_rd,xuat_rd;
    @FXML
    private CheckBox mucgiaCk,tdvChk;
    @FXML
    private TextField search_by_name_tf;
    @FXML
    private ComboBox<NguonNx> dvi_ref_cbb;
    @FXML
    private HBox importBtn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        all_rd.setSelected(true);
        hoverBtn();
        initVietnameseDate();
        initTableSize();
        initRootUnitLable();
        initNnxCombobox(nguonNxService.findByAllBy());
        initStartDate();
        initLocalDateList();
        setCellFactoryForLeger_table();
        setCellFactoryForRefTable();
        setCellFactoryForRootTable();
        initLedgerList();
        initRootLedgerList();
    }
    private void updateData() {
        initLedgerList();
    }
    private void initRootLedgerList() {
        LocalDate tungay = tab2_tungay.getValue();
        LocalDate denngay = tab2_denngay.getValue();
        List<Ledger> ls = ledgerService.findAllLedgerDto(tungay,denngay);
        setItemsTo_Roottable(ls);
    }

    private void setItemsTo_Roottable(List<Ledger> ls) {
        root_table.setItems(FXCollections.observableList(ls));
        root_table.refresh();
    }

    private void setCellFactoryForRootTable() {
        root_col_stt.setSortable(false);
        root_col_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(root_table.getItems().indexOf(column.getValue())+1).asString());
        root_col_id.setCellValueFactory(new PropertyValueFactory<Ledger, String>("id"));
        root_col_so.setCellValueFactory(new PropertyValueFactory<Ledger, String>("bill_id"));
        root_col_lenh.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lenh_so"));
        root_col_loainx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("loai_phieu"));
        root_col_dvnhap.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_nhan"));
        root_col_dvx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_xuat"));
        root_col_xmt.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lpt"));
        root_col_nv.setCellValueFactory(new PropertyValueFactory<Ledger, String>("nhiemvu"));
        root_col_note.setCellValueFactory(new PropertyValueFactory<Ledger, String>("note"));
    }

    private void setCellFactoryForRefTable() {
        ref_col_stt.setSortable(false);
        ref_col_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(ref_table.getItems().indexOf(column.getValue())+1).asString());
        ref_col_id.setCellValueFactory(new PropertyValueFactory<Ledger, String>("id"));
        ref_col_so.setCellValueFactory(new PropertyValueFactory<Ledger, String>("bill_id"));
        ref_col_lenhkh.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lenh_so"));
        ref_col_loainx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("loai_phieu"));
        ref_col_dvn.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_nhan"));
        ref_col_dvx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_xuat"));
        ref_col_xmt.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lpt"));
        ref_col_nv.setCellValueFactory(new PropertyValueFactory<Ledger, String>("nhiemvu"));
        ref_col_note.setCellValueFactory(new PropertyValueFactory<Ledger, String>("note"));
    }
    private void setItemsTo_RefTable(List<Ledger> ls){
        ref_table.refresh();
        ref_table.setItems(FXCollections.observableList(ls));
    }

    private void setCellFactoryForTable() {
        cttb_stt.setSortable(false);
        cttb_stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(chitiet_tb.getItems().indexOf(column.getValue())+1).asString());
        cttb_txd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        cttb_loai.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("chung_loai"));
        cttb_dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        cttb_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        cttb_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        cttb_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        cttb_px.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("soluongpx_str"));
        cttb_tx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("soluong_str"));
        cttb_thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
    }
    private void initLedgerList() {
        LocalDate st = st_time.getValue();
        LocalDate et = lst_time.getValue();
        if (validDate(st,et)){
            if (tdvChk.isSelected()){
                ledgerSelectList = new ArrayList<>();
                ledgerService.findAllLedgerDto(st,et).forEach(x->{
                    ledgerSelectList.add(new Ledger(x));
                });
                setItemToTableView(ledgerSelectList);
            }else{
                ledgerSelectList = new ArrayList<>();
                ledgerService.findAllLedgerDto(st,et,DashboardController.ref_Dv.getId()).forEach(x->{
                    ledgerSelectList.add(new Ledger(x));
                });
                setItemToTableView(ledgerSelectList);
            }
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
        ledgers_col_so.setCellValueFactory(new PropertyValueFactory<Ledger, String>("billnumber"));
        ledgers_col_stdate.setCellValueFactory(new PropertyValueFactory<Ledger, String>("from_date_str"));
        ledgers_col_edate.setCellValueFactory(new PropertyValueFactory<Ledger, String>("end_date_str"));
        ledgers_col_lenhkh.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lenh_so"));
        ledgers_col_loainx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("loai_phieu"));
        ledgers_col_dvn.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_nhan"));
        ledgers_col_dvx.setCellValueFactory(new PropertyValueFactory<Ledger, String>("dvi_xuat"));
        ledgers_col_xmt.setCellValueFactory(new PropertyValueFactory<Ledger, String>("lpt"));
        ledgers_col_nv.setCellValueFactory(new PropertyValueFactory<Ledger, String>("nhiemvu"));
        ledgers_col_note.setCellValueFactory(new PropertyValueFactory<Ledger, String>("note"));
        ledgers_col_createtime.setCellValueFactory(new PropertyValueFactory<Ledger, String>("timestamp_str"));
        ledgers_table.setRowFactory(tv -> new TableRow<Ledger>() {
            @Override
            protected void updateItem(Ledger ledger, boolean empty) {
                super.updateItem(ledger, empty);

                if (ledger == null || empty) {
                    setStyle(null);
                } else {
                    if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())) {
                        setStyle("-fx-background-color: #5f94e8;"+
                                "-fx-border-color: transparent transparent black transparent; " +
                                "-fx-border-width: 0px 0px 1px;");
                    }else if (ledger.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_XUAT.getName())) {
                        setStyle("-fx-background-color: #fa4655;"+
                                "-fx-border-color: transparent transparent black transparent; " +
                                "-fx-border-width: 0px 0px 1px;");
                    }
                }
            }
        });
    }
    private void initLocalDateList() {
        dateLoading();
    }
    private void setLocalDateList(List<String> ls){
        date_ls.setItems(FXCollections.observableList(ls));
    }
    private void initStartDate() {
        st_time.setValue(DashboardController.ref_Quarter.getStart_date());
        lst_time.setValue(DashboardController.ref_Quarter.getEnd_date());
        tab2_tungay.setValue(DashboardController.ref_Quarter.getStart_date());
        tab2_denngay.setValue(DashboardController.ref_Quarter.getEnd_date());
    }
    private void initNnxCombobox(List<NguonNx> nnxls) {
        ComponentUtil.setItemsToComboBox(dvi_ref_cbb,nnxls,NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvi_ref_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
    }
    private void hoverBtn() {
        Common.hoverButton(ref_to_root ,"#ffffff");
        importBtn.setOnMouseEntered(e -> importBtn.setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-width:3;-fx-background-radius:10;-fx-border-radius:10"));
        importBtn.setOnMouseExited(e -> importBtn.setStyle("-fx-background-color: #ffffff;-fx-border-color:#a8a8a8;-fx-background-radius:10;-fx-border-radius:10"));
    }
    private void initVietnameseDate() {
        CommonFactory.setVi_DatePicker(st_time);
        CommonFactory.setVi_DatePicker(lst_time);
        CommonFactory.setVi_DatePicker(tab2_tungay);
        CommonFactory.setVi_DatePicker(tab2_denngay);
    }
    private void initRootUnitLable() {
        root_unit_lable.setText("Toàn đơn vị");
        tab1_dvi_label.setText(DashboardController.ref_Dv.getTen());
        unitClickedLable.setText("-----");
    }
    private void initTableSize() {
        ledgers_table.setPrefWidth(DashboardController.screenWidth-500);
        ledgers_table.setPrefHeight(DashboardController.screenHeigh-800);
        chitiet_tb.setPrefWidth(DashboardController.screenWidth-300);
        chitiet_tb.setPrefHeight(DashboardController.screenHeigh-900);
        root_table.setPrefWidth(DashboardController.screenWidth-800);
        root_table.setPrefHeight(DashboardController.screenHeigh-500);
        ref_table.setPrefWidth(DashboardController.screenWidth-800);
        ref_table.setPrefHeight(DashboardController.screenHeigh-500);
        tonkho_tb.setPrefWidth(DashboardController.screenWidth-300);
        tonkho_tb.setPrefHeight(DashboardController.screenHeigh-800);
    }
    @FXML
    public void select_date_Clicked(MouseEvent mouseEvent) {
        String d = date_ls.getSelectionModel().getSelectedItems().get(0).toString();
        currentDateSelected = stringToDate(d);
        getLedgerByDate(currentDateSelected);
    }
    @FXML
    public void all_radio_action(ActionEvent actionEvent) {
        setItemToTableView(ledgerSelectList.stream().filter(x->x.getFrom_date().equals(currentDateSelected)).sorted(Comparator.comparing(Ledger::getTimestamp)).toList());
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
                x.setSoluong_str(TextToNumber.textToNum_2digits(x.getSoluong()));
                x.setSoluongpx_str(TextToNumber.textToNum_2digits(x.getSoluong_px()));
                x.setThucnhap_str(TextToNumber.textToNum_2digits(x.getThuc_nhap()));
                x.setPhainhap_str(TextToNumber.textToNum_2digits(x.getPhai_nhap()));
                x.setThanhtien_str(TextToNumber.textToNum_2digits(x.getThanhtien()));
            });
            setLedgerDetailTable(details);
            if(mouseEvent.getClickCount()==2){
                showDetailsScreen();
            }
        }
    }
    private void setLEdgerDetailLabel(Ledger l){
        ctnv_lb.setText(l.getNhiemvu());
        Optional<PhuongTien> pt = phuongtienService.findById(l.getPt_id());
        pt.ifPresent(x->xmt_lb.setText(x.getName()));
        loaixmt_lb.setText(l.getLpt());
        km_lb.setText(TextToNumber.textToNum(String.valueOf(l.getSo_km())));
        giohd_lb.setText(l.getGiohd_md());
        giohd_md_lb.setText(l.getGiohd_md());
        giohd_tk_lb.setText(l.getGiohd_tk());
        nguoinhan_lb.setText(l.getNguoi_nhan());
        amount_lb.setText(TextToNumber.textToNum_2digits(l.getAmount()));
    }
    private void setLedgerDetailTable(List<LedgerDetails> ls){
        chitiet_tb.refresh();
        chitiet_tb.setItems(FXCollections.observableList(ls));
    }
    private void showDetailsScreen(){
        primaryStage = new Stage();
        Common.openNewStage2("ledger_details.fxml",primaryStage,"Chi tiết phiếu", StageStyle.UTILITY);
    }
    @FXML
    public void tungayAction(ActionEvent actionEvent) {
    }
    @FXML
    public void denngayAction(ActionEvent actionEvent) {
    }
    @FXML
    public void ref_to_rootAction(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null,null,"Bạn có muốn nhập dữ liệu từ đơn vị tham chiếu cho toàn đơn vị không?", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            try {
                for (Ledger l : refLedgerList) {
                    List<LedgerDetails> ldl = refLedgerDetailList.stream().filter(x -> x.getLedger_id().equals(l.getId())).toList();
                    l.setLedgerDetails(ldl);
                    ledgerService.save(l);
                }
                DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
                initRootLedgerList();
            }catch (Exception e){
                DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
                e.printStackTrace();
            }
        }
    }
    @FXML
    public void dvi_ref_cbbAction(ActionEvent actionEvent) {
    }
    @FXML
    public void importBtnClick(MouseEvent mouseEvent) {
        importDataToTable();
    }
    @FXML
    public void export_fileClicked(MouseEvent mouseEvent) {
        CommonFactory.setSelectDirectory(DashboardController.primaryStage);
        if (CommonFactory.pre_path!=null){
            mapLEdgerDataToFile();
        }
    }
    @FXML
    public void search_by_name_Click(MouseEvent mouseEvent) {
    }
    @FXML
    public void search_by_name_tfAction(ActionEvent actionEvent) {
    }
    @FXML
    public void mucgiaCkAction(ActionEvent actionEvent) {
        if (mucgiaCk.isSelected()){

        }else{

        }
    }
    @FXML
    public void dateLoadingClick(MouseEvent mouseEvent) {
        dateLoading();
    }

    private void dateLoading() {
        LocalDate st = st_time.getValue();
        initLedgerList();
        List<LocalDate> ls = ledgerSelectList.stream().map(Ledger::getFrom_date).filter(fromDate -> fromDate.isAfter(st)).distinct().toList();
        setLocalDateList(ls.stream().map(x->x.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).toList());
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
    private void importDataToTable() {
        refLedgerList = new ArrayList<>();
        refLedgerDetailList = new ArrayList<>();
        refTransactionHistorysList = new ArrayList<>();
        File file = CommonFactory.setSelectFileDirectory(DashboardController.primaryStage);
        if (file!=null){
            importDataToList(file,refLedgerList, this::importCellToLEdger);
            if (refLedgerList.isEmpty()){
                DialogMessage.successShowing("Dữ liệu nhập từ file data.xlsx trống.");
            }else{
                dvi_ref_cbb.getSelectionModel().select(DashboardController.ref_Dv.getId());
                setItemsTo_RefTable(refLedgerList);
                importDataToList(file, refLedgerDetailList,this::importCellToLEdger_detail);
                importDataToList(file,refTransactionHistorysList,this::importCellToTransactionHistory);
            }
        }
    }
    private void mapLEdgerDataToFile(){
        try {
            String file_n = CommonFactory.pre_path+"\\"+file_name;
            File file = new File(file_n);
            if (file.exists()){
                Common.deleteExcel(file_n);
            }
            file.createNewFile();
            FileInputStream fis = new FileInputStream(file);
            XSSFWorkbook wb = new XSSFWorkbook();
            ReportDAO reportDAO = new ReportDAO();

            mapQuySheet(wb);
            mapLedger(wb,reportDAO);
            mapLedgerDetail(wb,reportDAO);
            mapTransactionHistory(wb,reportDAO);
            mapHanmucNhiemvu(wb,reportDAO);
            mapHanmucNhiemvuTaubay(wb,reportDAO);

            fis.close();
            FileOutputStream fileOutputStream = new FileOutputStream(CommonFactory.pre_path+"\\"+file_name);
            XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            wb.write(fileOutputStream);
            fileOutputStream.close();
            wb.close();
            if (DialogMessage.callAlertWithMessage(null,null,"successfully", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                Common.openDesktop(CommonFactory.pre_path);
            }
        } catch (Exception e) {
            DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
        }
    }

    private void mapHanmucNhiemvuTaubay(XSSFWorkbook wb, ReportDAO reportDAO) {
        XSSFSheet sheet2 = wb.createSheet("HANMUC_NHIEMVU_TAUBAY_DATA");
        List<Object[]> nxtls2 = reportDAO.findByWhatEver("select * from hanmuc_nhiemvu_taubay");
        setCellExcel(wb,sheet2,nxtls2,ledgerService.getColumnNames_HANMUCNHIEMVU_TAUBAY());
    }

    private void mapHanmucNhiemvu(XSSFWorkbook wb, ReportDAO reportDAO) {
        XSSFSheet sheet2 = wb.createSheet("HANMUC_NHIEMVU_DATA");
        List<Object[]> nxtls2 = reportDAO.findByWhatEver("select * from hanmuc_nhiemvu2");
        setCellExcel(wb,sheet2,nxtls2,ledgerService.getColumnNames_HMNV());
    }
    private void mapLedgerDetail(XSSFWorkbook wb,ReportDAO reportDAO) {
        XSSFSheet sheet2 = wb.createSheet("CHITIETSOCAI_DATA");
        List<Object[]> nxtls2 = reportDAO.findByWhatEver("select ld from ledger_details ld join ledgers l on ld.ledger_id=l.id where status like 'ACTIVE' and root_id="+DashboardController.ref_Dv.getId()+" and l.from_date between '"+st_time.getValue()+"' and '"+lst_time.getValue()+"'");
        setCellExcel(wb,sheet2,nxtls2,ledgerService.getColumnNames_LEDGER_DETAIL());
    }
    private void mapLedger(XSSFWorkbook wb,ReportDAO reportDAO) {
        XSSFSheet sheet = wb.createSheet("SOCAI_DATA");
        List<Object[]> nxtls = reportDAO.findByWhatEver("select * from ledgers l where status like 'ACTIVE' and l.from_date between '"+st_time.getValue()+"' and '"+lst_time.getValue()+"' and root_id="+DashboardController.ref_Dv.getId());
        setCellExcel(wb,sheet,nxtls,ledgerService.getColumnNames_LEDGER());
    }
    private void mapTransactionHistory(XSSFWorkbook wb,ReportDAO reportDAO) {
        XSSFSheet sheet = wb.createSheet("LICHSU_LUANCHUYEN_DATA");
        List<Object[]> nxtls = reportDAO.findByWhatEver("select * from transaction_history th where th.date between '"+st_time.getValue()+"' and '"+lst_time.getValue()+"'");
        setCellExcel(wb,sheet,nxtls,ledgerService.getColumnNames_TRANSACTION_HISTORY());
    }
    private void mapQuySheet(XSSFWorkbook wb) {
        XSSFSheet quy = wb.createSheet("QUY_DATA");
        XSSFRow title_row = quy.createRow(0);
        XSSFCell cell = title_row.createCell(0);
        XSSFCellStyle style = wb.createCellStyle();
        ExportFactory.setCellBorderStyle(style,BorderStyle.THIN);
        ExportFactory.setBoldFont(wb,style);
        cell.setCellStyle(style);
        cell.setCellValue("THÔNG TIN QUÝ");
        XSSFCell cell1 = title_row.createCell(1);
        cell1.setCellValue("Từ ngày:");
        XSSFCell fd = title_row.createCell(2);
        fd.setCellValue(st_time.getValue().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName())));
        XSSFCell cell2 = title_row.createCell(3);
        cell2.setCellValue("Đến ngày:");
        XSSFCell ed = title_row.createCell(4);
        ed.setCellValue(lst_time.getValue().format(DateTimeFormatter.ofPattern(ConfigCons.FORMAT_DATE.getName())));

        XSSFRow dv_row = quy.createRow(1);
        XSSFCell cell_dv1 = dv_row.createCell(0);
        ExportFactory.setBoldFont(wb,style);
        cell_dv1.setCellStyle(style);
        cell_dv1.setCellValue("Đơn vị:");
        XSSFCell cell_dv2 = dv_row.createCell(1);
        cell_dv2.setCellValue(DashboardController.ref_Dv.getTen());
    }
    private void setCellExcel(XSSFWorkbook wb,XSSFSheet sheet,List<Object[]> nxtls,List<String> titles){
        XSSFRow title_row = sheet.createRow(0);
        for (int i =0; i< titles.size();i++){
            XSSFCell cell = title_row.createCell(i);
            XSSFCellStyle style = wb.createCellStyle();
            ExportFactory.setCellBorderStyle(style,BorderStyle.THIN);
            ExportFactory.setBoldFont(wb,style);
            cell.setCellStyle(style);
            cell.setCellValue(titles.get(i));
        }
        for(int i =0; i< nxtls.size(); i++) {
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(i+1);

            for (int j =0;j<rows_data.length;j++){
                String val = rows_data[j]==null ?"" : rows_data[j].toString();
                XSSFCell cell = row.createCell(j);
                XSSFCellStyle style = wb.createCellStyle();
                ExportFactory.setCellBorderStyle(style,BorderStyle.THIN);
                if(j==1){
                    cell.setCellValue(val);
                }
                else if(Common.isLongNumber(val)){
                    ExportFactory.setDataFormat(wb,style,"#,##0");
                    cell.setCellValue(new BigDecimal(val).longValue());
                }
                else if(Common.isDoubleNumber(val)){
                    ExportFactory.setDataFormat(wb,style,"#,##0.00");
                    BigDecimal bigDecimal = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
                    cell.setCellValue(bigDecimal.doubleValue());
                }else{
                    cell.setCellValue(val);
                }
                cell.setCellStyle(style);
            }
        }
    }

    private <T extends BaseObject> T importCellToLEdger(Row row){
        Ledger l =new Ledger();
        if (row!=null){
            Cell id = row.getCell(0);
            Cell bill_id = row.getCell(1);
            Cell amount = row.getCell(2);
            Cell from_date = row.getCell(3);
            Cell end_date = row.getCell(4);
            Cell status = row.getCell(5);
            Cell dvi_nhan_id = row.getCell(6);
            Cell dvi_xuat_id = row.getCell(7);
            Cell loai_phieu = row.getCell(8);
            Cell dvi_nhan = row.getCell(9);
            Cell dvi_xuat = row.getCell(10);
            Cell loaigiobay = row.getCell(11);
            Cell nguoinhan = row.getCell(12);
            Cell soxe = row.getCell(13);
            Cell lenhso = row.getCell(14);
            Cell nhiemvu = row.getCell(15);
            Cell nhiemvuId = row.getCell(16);
            Cell sokm = row.getCell(17);
            Cell tcn_id = row.getCell(18);
            Cell timestamp = row.getCell(19);
            Cell giohd_md = row.getCell(20);
            Cell giohd_tk = row.getCell(21);
            Cell loainv = row.getCell(22);
            Cell tructhuoc = row.getCell(23);
            Cell lpt = row.getCell(24);
            Cell lpt2 = row.getCell(25);
            Cell create_by = row.getCell(26);
            Cell root_id = row.getCell(27);
            Cell pt_id = row.getCell(28);
            Cell note = row.getCell(29);
            Cell year = row.getCell(30);

            l.setId(id.getStringCellValue());
            l.setBill_id(bill_id.getStringCellValue());
            l.setAmount(amount.getNumericCellValue());
            l.setFrom_date(stringToLocalDate(from_date.getStringCellValue()));
            l.setEnd_date(stringToLocalDate(end_date.getStringCellValue()));
            l.setStatus(status.getStringCellValue());
            l.setDvi_nhan_id((int) dvi_nhan_id.getNumericCellValue());
            l.setDvi_xuat_id((int) dvi_xuat_id.getNumericCellValue());
            l.setLoai_phieu(loai_phieu.getStringCellValue());
            l.setDvi_nhan(dvi_nhan.getStringCellValue());
            l.setDvi_xuat(dvi_xuat.getStringCellValue());
            l.setLoaigiobay(loaigiobay.getStringCellValue());
            l.setNguoi_nhan(nguoinhan.getStringCellValue());
            l.setSo_xe(soxe.getStringCellValue());
            l.setLenh_so(lenhso.getStringCellValue());
            l.setNhiemvu(nhiemvu.getStringCellValue());
            l.setNhiemvu_id((int)  nhiemvuId.getNumericCellValue());
            l.setSo_km((int) sokm.getNumericCellValue());
            l.setTcn_id((int) tcn_id.getNumericCellValue());
            l.setTimestamp(stringToLocalDatetime(timestamp.getStringCellValue()));
            l.setGiohd_md(giohd_md.getStringCellValue());
            l.setGiohd_tk(giohd_tk.getStringCellValue());
            l.setLoainv(loainv.getStringCellValue());
            l.setTructhuoc(tructhuoc.getStringCellValue());
            l.setLpt(lpt.getStringCellValue());
            l.setLpt_2(lpt2.getStringCellValue());
            l.setCreate_by((int) create_by.getNumericCellValue());
            l.setRoot_id((int) root_id.getNumericCellValue());
            l.setPt_id((int) pt_id.getNumericCellValue());
            l.setNote(note.getStringCellValue());
            l.setYear((int) year.getNumericCellValue());
        }
        return (T) l;
    }
    private <T extends BaseObject> T importCellToLEdger_detail(Row row){
        LedgerDetails l =new LedgerDetails();
        if (row!=null){
            Cell maxd = row.getCell(0);
            Cell tenxd = row.getCell(1);
            Cell chungloai = row.getCell(2);
            Cell chatluong = row.getCell(3);
            Cell phaixuat = row.getCell(4);
            Cell thucxuat = row.getCell(5);
            Cell dongia = row.getCell(6);
            Cell id = row.getCell(7);
            Cell loaixd_id = row.getCell(8);
            Cell phuongtienid = row.getCell(9);
            Cell ledger_id = row.getCell(10);
            Cell thucxuattk = row.getCell(11);
            Cell soluong = row.getCell(12);
            Cell thuc_nhap = row.getCell(13);
            Cell phai_nhap = row.getCell(14);
            Cell thanhtien = row.getCell(15);
            Cell haohutsl = row.getCell(16);
            Cell nl_km = row.getCell(17);
            Cell nl_gio = row.getCell(18);
            Cell sl_px = row.getCell(19);
            Cell sscd_nvdx = row.getCell(20);
            Cell tytrong = row.getCell(21);
            Cell nhietdo = row.getCell(22);
            Cell vcf = row.getCell(23);
            Cell nhap_nvdx = row.getCell(24);
            Cell nhap_sscd = row.getCell(25);
            Cell xuat_nvdx = row.getCell(26);
            Cell xuat_sscd = row.getCell(27);

            l.setId( id.getStringCellValue());
            l.setMa_xd(maxd.getStringCellValue());
            l.setTen_xd(tenxd.getStringCellValue());
            l.setChung_loai(chungloai.getStringCellValue());
            l.setChat_luong(chatluong.getStringCellValue());
            l.setPhai_xuat(phaixuat.getNumericCellValue());
            l.setThuc_xuat(thucxuat.getNumericCellValue());
            l.setDon_gia(dongia.getNumericCellValue());
            l.setLoaixd_id((int) loaixd_id.getNumericCellValue());
            l.setPhuongtien_id((int) phuongtienid.getNumericCellValue());
            l.setLedger_id(ledger_id.getStringCellValue());
            l.setThuc_xuat_tk(thucxuattk.getNumericCellValue());
            l.setSoluong(soluong.getNumericCellValue());
            l.setThuc_nhap(thuc_nhap.getNumericCellValue());
            l.setPhai_nhap(phai_nhap.getNumericCellValue());
            l.setThanhtien(thanhtien.getNumericCellValue());
            l.setHaohut_sl((int) haohutsl.getNumericCellValue());
            l.setNl_km(Long.parseLong(nl_km.getStringCellValue().isEmpty() ? "0" : nl_km.getStringCellValue()));
            l.setNl_gio(Long.parseLong(nl_gio.getStringCellValue().isEmpty() ? "0" : nl_gio.getStringCellValue()));
            l.setSoluong_px((long)sl_px.getNumericCellValue());
            l.setSscd_nvdx(sscd_nvdx.getStringCellValue());
            l.setTy_trong(tytrong.getNumericCellValue());
            l.setNhiet_do_tt(nhietdo.getNumericCellValue());
            l.setHe_so_vcf(vcf.getNumericCellValue());
            l.setNhap_nvdx(nhap_nvdx.getNumericCellValue());
            l.setNhap_sscd(nhap_sscd.getNumericCellValue());
            l.setXuat_nvdx(xuat_nvdx.getNumericCellValue());
            l.setXuat_sscd(xuat_sscd.getNumericCellValue());
        }
        return (T) l;
    }
    private <T extends BaseObject> T importCellToTransactionHistory(Row row){
        TransactionHistory i =new TransactionHistory();
        if (row!=null){
            Cell id = row.getCell(0);
            Cell xd_id = row.getCell(1);
            Cell loaiphieu = row.getCell(2);
            Cell date = row.getCell(3);
            Cell mucgia = row.getCell(4);
            Cell soluong = row.getCell(5);
            Cell tructhuoc = row.getCell(6);
            Cell tonkhotong = row.getCell(7);
            Cell tonkhogia = row.getCell(8);
            Cell index = row.getCell(9);
            Cell created_at = row.getCell(10);
            Cell soluong_tt = row.getCell(11);
            Cell tonkh_sscd = row.getCell(12);
            Cell tonkh_gia_sscd = row.getCell(13);
            Cell ledger_id = row.getCell(14);

            i.setId( id.getStringCellValue());
            i.setXd_id((int)xd_id.getNumericCellValue());
            i.setLoaiphieu(loaiphieu.getStringCellValue());
            i.setDate(LocalDate.parse(date.getStringCellValue()));
            i.setMucgia(mucgia.getNumericCellValue());
            i.setSoluong(soluong.getNumericCellValue());
            i.setTructhuoc(tructhuoc.getStringCellValue());
            i.setTonkhotong(tonkhotong.getNumericCellValue());
            i.setTonkho_gia(tonkhogia.getNumericCellValue());
            i.setIndex((long) index.getNumericCellValue());
            i.setSoluong_tt(soluong_tt.getNumericCellValue());
            i.setTonkh_sscd(tonkh_sscd.getNumericCellValue());
            i.setTonkh_gia_sscd(tonkh_gia_sscd.getNumericCellValue());
            i.setLedger_id(ledger_id.getStringCellValue());
            i.setCreated_at(LocalDateTime.parse(created_at.getStringCellValue()));
        }
        return (T) i;
    }

    private <T extends BaseObject> List<T>  importDataToList(File f, List<T> ls, Function<Row,T> importCell){
        try (FileInputStream fis = new FileInputStream(f);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = wb.getSheetAt(1);
            Iterator<Row> rowIterator = sheet.iterator();

            boolean isHeader = true;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
                ls.add(importCell.apply(row));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ls;
    }
    private LocalDate stringToLocalDate(String date_text){
        if (date_text.isEmpty()){
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date_text, formatter);
    }
    private LocalDateTime stringToLocalDatetime(String date_text){
        if (date_text.isEmpty()){
            return null;
        }
        String trimmedDateStr = date_text.substring(0, 19);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(trimmedDateStr, formatter);
    }
    @FXML
    public void ref_tableClick(MouseEvent mouseEvent) {
    }
    @FXML
    public void root_tableClicked(MouseEvent mouseEvent) {
    }
    @FXML
    public void importBtnAction(ActionEvent actionEvent) {
        primaryStage = new Stage();
        primaryStage.setOnHidden(event -> {
            ConnectLan.primaryStage.toFront();
            ConnectLan.primaryStage.requestFocus();
        });
        Common.openNewStage("nhap.fxml", primaryStage,null, StageStyle.UTILITY);
        updateData();
    }
    @FXML
    public void exportBtnAction(ActionEvent actionEvent) {
        primaryStage = new Stage();
        primaryStage.setOnHidden(event -> {
            ConnectLan.primaryStage.toFront();
            ConnectLan.primaryStage.requestFocus();
        });
        Common.openNewStage("xuat.fxml", primaryStage,null,StageStyle.UTILITY);
        updateData();
    }
    @FXML
    public void tdvChkAction(ActionEvent actionEvent) {
        if (tdvChk.isSelected()){
            tab1_dvi_label.setDisable(true);
        }else{
            tab1_dvi_label.setDisable(false);
        }
        dateLoading();
    }
    @FXML
    public void editMiAction(ActionEvent actionEvent) {
        Ledger l = ledgers_table.getSelectionModel().getSelectedItem();

        primaryStage = new Stage();
        primaryStage.setOnHidden(event -> {
            ConnectLan.primaryStage.toFront();
            ConnectLan.primaryStage.requestFocus();
        });
        if (l.getLoai_phieu().equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
            Common.openNewStage("nhap.fxml", primaryStage,null, StageStyle.UTILITY);
            updateData();
        }else{
            Common.openNewStage("xuat.fxml", primaryStage,null, StageStyle.UTILITY);
            updateData();
        }

    }
}
