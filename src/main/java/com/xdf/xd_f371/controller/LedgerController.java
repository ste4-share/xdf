package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.InvDto3;
import com.xdf.xd_f371.entity.Ledger;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.repo.ReportDAO;
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
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
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

@Component
public class LedgerController implements Initializable {
    private final String file_name = "data.xlsx";
    private List<Ledger> ledgerSelectList = new ArrayList<>();
    private List<String> selectedDateLs = new ArrayList<>();
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
        initNnxCombobox(nguonNxService.findByAllBy());
        initStartDate();
        initLocalDateList();
        setCellFactoryForLeger_table();
        setCellFactoryForRefTable();
        setCellFactoryForRootTable();
        initLedgerList();
        initRootLedgerList();
    }

    private void initRootLedgerList() {
        LocalDate tungay = tab2_tungay.getValue();
        LocalDate denngay = tab2_denngay.getValue();
        List<Ledger> ls = ledgerService.findAllLedgerDto(tungay,denngay,DashboardController.ref_Dv.getId());
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
    private void initNnxCombobox(List<NguonNx> nnxls) {
        ComponentUtil.setItemsToComboBox(dvi_ref_cbb,nnxls,NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
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

    @FXML
    public void export_fileClicked(MouseEvent mouseEvent) {
        CommonFactory.setSelectDirectory(DashboardController.primaryStage);
        mapLEdgerDataToFile();
    }
    @FXML
    public void importFileClicked(MouseEvent mouseEvent) {
        importDataToTable();
    }
    private void importDataToTable() {
        File file = CommonFactory.setSelectFileDirectory(DashboardController.primaryStage);
        List<Ledger> l = importDataToListLEdger(file);
        List<NguonNx> n = nguonNxService.findAllById(l.get(0).getRoot_id());
        if (!n.isEmpty()){
            dvi_ref_cbb.getSelectionModel().select(n.get(0));
        }
        setItemsTo_RefTable(l);
        List<LedgerDetails> ld = importDataToListLedger_Detail(file);
        System.out.println("ld"+ld.size());
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
            XSSFSheet sheet = wb.createSheet("SOCAI_DATA");
            ReportDAO reportDAO = new ReportDAO();
            List<Object[]> nxtls = reportDAO.findByWhatEver("select * from ledgers where status like 'ACTIVE'");
            setCellExcel(wb,sheet,nxtls,ledgerService.getColumnNames_LEDGER());

            XSSFSheet sheet2 = wb.createSheet("CHITIETSOCAI_DATA");
            List<Object[]> nxtls2 = reportDAO.findByWhatEver("select ld from ledger_details ld join ledgers l on ld.ledger_id=l.id where status like 'ACTIVE'");
            setCellExcel(wb,sheet2,nxtls2,ledgerService.getColumnNames_LEDGER_DETAIL());

            if (DialogMessage.callAlertWithMessage(null,null,"successfully", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
                Common.openDesktop(CommonFactory.pre_path);
            }

            fis.close();
            FileOutputStream fileOutputStream = new FileOutputStream(CommonFactory.pre_path+"\\"+file_name);
            XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
            wb.write(fileOutputStream);
            fileOutputStream.close();
            wb.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    private void setCellExcel(XSSFWorkbook wb,XSSFSheet sheet,List<Object[]> nxtls,List<String> titles){
        XSSFRow title_row = sheet.createRow(0);
        for (int i =0; i< titles.size();i++){
            XSSFCell cell = title_row.createCell(i);
            XSSFCellStyle style = wb.createCellStyle();
            setCellBorderStyle(style,cell);
            setBoldFont(wb,style,cell);
            cell.setCellValue(titles.get(i));
        }
        for(int i =0; i< nxtls.size(); i++) {
            Object[] rows_data = nxtls.get(i);
            XSSFRow row = sheet.createRow(i+1);

            for (int j =0;j<rows_data.length;j++){
                String val = rows_data[j]==null ?"" : rows_data[j].toString();
                XSSFCell cell = row.createCell(j);
                XSSFCellStyle style = wb.createCellStyle();
                setCellBorderStyle(style,cell);
                if(Common.isLongNumber(val)){
                    setDataFormat(wb,style,cell,"#,##0");
                    cell.setCellValue(new BigDecimal(val).longValue());
                }
                else if(Common.isDoubleNumber(val)){
                    setDataFormat(wb,style,cell,"#,##0.00");
                    BigDecimal bigDecimal = new BigDecimal(val).setScale(2, RoundingMode.HALF_UP);
                    cell.setCellValue(bigDecimal.doubleValue());
                }else{
                    cell.setCellValue(val);
                }
            }
        }
    }
    private void setDataFormat(XSSFWorkbook wb,CellStyle style,XSSFCell cell,String format_text){
        XSSFDataFormat format = wb.createDataFormat();
        style.setDataFormat(format.getFormat(format_text));
        cell.setCellStyle(style);
    }
    private void setBoldFont(XSSFWorkbook wb,CellStyle style,XSSFCell cell){
        XSSFFont font = wb.createFont();
        font.setBold(true);
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 11);
        style.setFont(font);
        cell.setCellStyle(style);
    }
    private void setCellBorderStyle(CellStyle style,XSSFCell cell){
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);
        cell.setCellStyle(style);
    }
    private Ledger importCellToLEdger(Row row){
        Ledger l =new Ledger();
        if (row!=null){
            Cell id = row.getCell(0);
            Cell bill_id = row.getCell(1);
            Cell amount = row.getCell(2);
            Cell from_date = row.getCell(3);
            Cell end_date = row.getCell(4);
            Cell status = row.getCell(5);
            Cell sl_tieuthu_md = row.getCell(6);
            Cell sl_tieuthu_tk = row.getCell(7);
            Cell dvi_nhan_id = row.getCell(8);
            Cell dvi_xuat_id = row.getCell(9);
            Cell loai_phieu = row.getCell(10);
            Cell dvi_nhan = row.getCell(11);
            Cell dvi_xuat = row.getCell(12);
            Cell loaigiobay = row.getCell(13);
            Cell nguoinhan = row.getCell(14);
            Cell soxe = row.getCell(15);
            Cell lenhso = row.getCell(16);
            Cell nhiemvu = row.getCell(17);
            Cell nhiemvuId = row.getCell(18);
            Cell sokm = row.getCell(19);
            Cell tcn_id = row.getCell(20);
            Cell timestamp = row.getCell(21);
            Cell giohd_md = row.getCell(22);
            Cell giohd_tk = row.getCell(23);
            Cell loainv = row.getCell(24);
            Cell tructhuoc = row.getCell(25);
            Cell lpt = row.getCell(26);
            Cell lpt2 = row.getCell(27);
            Cell version = row.getCell(28);
            Cell create_by = row.getCell(29);
            Cell root_id = row.getCell(30);
            Cell pt_id = row.getCell(31);
            Cell note = row.getCell(32);

            l.setId((long) id.getNumericCellValue());
            l.setBill_id((int) bill_id.getNumericCellValue());
            l.setAmount(amount.getNumericCellValue());
            l.setFrom_date(stringToLocalDate(from_date.getStringCellValue()));
            l.setEnd_date(stringToLocalDate(end_date.getStringCellValue()));
            l.setStatus(status.getStringCellValue());
            l.setSl_tieuthu_md(sl_tieuthu_md.getNumericCellValue());
            l.setSl_tieuthu_tk(sl_tieuthu_tk.getNumericCellValue());
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
            l.setVersion((int) version.getNumericCellValue());
            l.setCreate_by((int) create_by.getNumericCellValue());
            l.setRoot_id((int) root_id.getNumericCellValue());
            l.setPt_id((int) pt_id.getNumericCellValue());
            l.setNote(note.getStringCellValue());
        }
        return l;
    }
    private LedgerDetails importCellToLEdger_detail(Row row){
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

            l.setId((long) id.getNumericCellValue());
            l.setMa_xd(maxd.getStringCellValue());
            l.setTen_xd(tenxd.getStringCellValue());
            l.setChung_loai(chungloai.getStringCellValue());
            l.setChat_luong(chatluong.getStringCellValue());
            l.setPhai_xuat(phaixuat.getNumericCellValue());
            l.setThuc_xuat(thucxuat.getNumericCellValue());
            l.setDon_gia(dongia.getNumericCellValue());
            l.setLoaixd_id((int) loaixd_id.getNumericCellValue());
            l.setPhuongtien_id((int) phuongtienid.getNumericCellValue());
            l.setLedger_id((long) ledger_id.getNumericCellValue());
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
        return l;
    }
    private List<Ledger> importDataToListLEdger(File f){
        List<Ledger> ledgerList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(f);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {
            
             XSSFSheet sheet = wb.getSheetAt(0); // Read first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            boolean isHeader = true; // Skip the header row
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isHeader) {
                    isHeader = false; // Skip header
                    continue;
                }
                ledgerList.add(importCellToLEdger(row));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ledgerList;
    }

    private List<LedgerDetails> importDataToListLedger_Detail(File f){
        List<LedgerDetails> ledgerDetailsList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(f);
             XSSFWorkbook wb = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = wb.getSheetAt(1); // Read first sheet
            Iterator<Row> rowIterator = sheet.iterator();

            boolean isHeader = true; // Skip the header row
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (isHeader) {
                    isHeader = false; // Skip header
                    continue;
                }
                ledgerDetailsList.add(importCellToLEdger_detail(row));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
        return ledgerDetailsList;
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
        String trimmedDateStr = date_text.substring(0, 19); // Removes microseconds
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.parse(trimmedDateStr, formatter);
    }
    @FXML
    public void ref_tableClick(MouseEvent mouseEvent) {
    }
    @FXML
    public void root_tableClicked(MouseEvent mouseEvent) {
    }
}
