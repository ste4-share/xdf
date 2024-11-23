package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.SpotDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.model.ChungloaiMap;
import com.xdf.xd_f371.model.MockDataMap;
import com.xdf.xd_f371.repo.QuarterRepository;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.service.impl.*;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.*;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@Component
public class TonkhoController implements Initializable {

    public static Stage quarter_stage;
    public static Stage ChangeSScd_stage;
    private static List<SpotDto> tkt;
    private static Quarter findByTime;
    public static SpotDto pickTonKho = new SpotDto();
    private static Map<String, String> chungloaiXd = new HashMap<>();
    @FXML
    public TableView<SpotDto> tb_tonkho;
    @FXML
    public TableView<Inventory> tb_quater_inv;
    @FXML
    public TableColumn<SpotDto, String> col_stt_tk,col_maxd_tk,col_tenxd_tk,col_nvdx_tk,col_sscd_tk,col_sum_tk;
    @FXML
    public TableColumn<Inventory, String> col_stt_qt,col_ma_qt, col_tenxd_qt, col_chungloai_qt,col_nvdx_tdk, col_sscd_tdk, col_sum_tdk,col_nvdx_tck,col_sscd_tck,col_sum_tck;
    @FXML
    private TableColumn<Inventory, String> col_nvdx_pre,col_sscd_pre,col_sum_pre;
    @FXML
    private TextField tf_search_inv_qt, search_inventory;
    @FXML
    private DatePicker start_date_qt, end_date_qt;
    @FXML
    private Button addNewQuarter_btn, createQuarterBtn, cancel_quaterbtn,printBcNxt;
    @FXML
    private ComboBox<Quarter> cbb_quarter;
    @FXML
    private Label lb_end_date,lb_start_date;

    @Autowired
    private QuarterRepository quarterRepository;
    private TonKhoService tonKhoService = new TonkhoImp();
    private LoaiXdService loaiXdService = new LoaiXdImp();
    private TrucThuocService trucThuocService = new TrucThuocImp();
    private MucgiaService mucgiaService = new MucgiaImp();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tkt = new ArrayList<>();
        pickTonKho = new SpotDto();
        findByTime = new Quarter();
        chungloaiXd = loaiXdService.getChungLoaiCount();
        findByTime = quarterRepository.findByCurrentTime(LocalDate.now()).get();
        setQuarterListToCbb();

        setTonkhoTongToCol();
        setTonkhoTongToCol2();
        fillDataToTableTonkho();
        setClickToTonTb();
    }

    private void setQuarterListToCbb(){
        lb_start_date.setTextFill(Color.rgb(33, 12, 162));
        lb_end_date.setTextFill(Color.rgb(33, 12, 162));
        try {
            cbb_quarter.setConverter(new StringConverter<Quarter>() {
                @Override
                public String toString(Quarter object) {
                    return object==null ? "" : object.getName();
                }

                @Override
                public Quarter fromString(String string) {
                    return quarterRepository.findByName(string).get();
                }
            });

            ObservableList<Quarter> observableArrayList =
                    FXCollections.observableArrayList(quarterRepository.findAll());
            cbb_quarter.setItems(observableArrayList);
            cbb_quarter.getSelectionModel().select(findByTime);
            lb_end_date.setText(cbb_quarter.getValue().getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
            lb_start_date.setText(cbb_quarter.getValue().getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }

    @FXML
    public void addNewQuarter(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../quater_form.fxml"));
        Scene scene = new Scene(root);
        quarter_stage = new Stage();
        quarter_stage.setScene(scene);
        quarter_stage.initStyle(StageStyle.DECORATED);
        quarter_stage.initModality(Modality.APPLICATION_MODAL);
        quarter_stage.setTitle("Tạo Quý");
        quarter_stage.show();
    }

    @FXML
    public void selectQuarter(ActionEvent actionEvent) {
        Quarter selected = cbb_quarter.getValue();
        lb_end_date.setText(selected.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_start_date.setText(selected.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        fillDataToTable(selected);
    }
    private void fillDataToTableTonkho(){
        tkt = mucgiaService.getAllSpots(findByTime.getId());
        searching(tkt.stream().map(x->x.getTenxd()).toList());
        for (SpotDto spotDto : tkt) {
            spotDto.setNvdx_total(TextToNumber.textToNum(spotDto.getNvdx_total()));
            spotDto.setSscd_total(TextToNumber.textToNum(spotDto.getSscd_total()));
            spotDto.setTotal(TextToNumber.textToNum(spotDto.getTotal()));
        }
        ObservableList<SpotDto> observableList = FXCollections.observableArrayList(tkt);
        tb_tonkho.setItems(observableList);
    }
    private void setTonkhoTongToCol(){
        col_stt_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("stt"));
        col_maxd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("maxd"));
        col_tenxd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("tenxd"));
        col_nvdx_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("nvdx_total"));
        col_sscd_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("sscd_total"));
        col_sum_tk.setCellValueFactory(new PropertyValueFactory<SpotDto, String>("total"));
    }
    private boolean addedBySelection = false;
    private void searching(List<String> search_arr){
        TextFields.bindAutoCompletion(search_inventory,t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase());
            }).collect(Collectors.toList());
        });
        search_inventory.setOnKeyPressed(e -> {
            addedBySelection = false;
        });

        search_inventory.setOnKeyReleased(e -> {
            if (search_inventory.getText().trim().isEmpty()){
                fillDataToTableTonkho();
            }
            addedBySelection = true;
        });

        search_inventory.textProperty().addListener(e -> {
            if (addedBySelection) {
                List<SpotDto> tkt_buf = tkt.stream().filter(tonkhoTong -> tonkhoTong.getTenxd().equals(search_inventory.getText())).toList();
                ObservableList<SpotDto> observableList = FXCollections.observableArrayList(tkt_buf);
                tb_tonkho.setItems(observableList);
                addedBySelection = false;
            }
        });
    }

    private void fillDataToTable(Quarter selected){
        List<Inventory> inventories = tonKhoService.getAllInventory(DashboardController.findByTime.getId());
        for(int i =0; i< inventories.size(); i++){
            Inventory inventory = inventories.get(i);
            LoaiXangDau loaiXangDau = loaiXdService.findLoaiXdByID_non(inventory.getPetro_id());


            inventory.setPetroleumName(loaiXangDau.getTenxd());
            inventory.setChungloai(loaiXangDau.getChungloai());
            inventory.setStt(i+1);

            inventory.setTcK_nvdx_str(TextToNumber.textToNum(String.valueOf(inventory.getTcK_nvdx()).equals("") ? "0" : String.valueOf(inventory.getTcK_nvdx())));
            inventory.setTck_sscd_str(TextToNumber.textToNum(String.valueOf(inventory.getTck_sscd()).equals("") ? "0" : String.valueOf(inventory.getTck_sscd())));
            int sum_tck = inventory.getTcK_nvdx() + inventory.getTck_sscd();
            inventory.setTck_sum_str(TextToNumber.textToNum(String.valueOf(sum_tck).equals("") ? "0" : String.valueOf(sum_tck)));

            inventory.setTdk_sscd_str(TextToNumber.textToNum(String.valueOf(inventory.getTdk_sscd()).equals("") ? "0" : String.valueOf(inventory.getTdk_sscd())));
            inventory.setTdk_nvdx_str(TextToNumber.textToNum(String.valueOf(inventory.getTdk_nvdx()).equals("") ? "0" : String.valueOf(inventory.getTdk_nvdx())));
            int sum_tdk = inventory.getTdk_sscd() + inventory.getTdk_nvdx();
            inventory.setTdk_sum_str(TextToNumber.textToNum(String.valueOf(sum_tdk).equals("") ? "0" : String.valueOf(sum_tdk)));

            inventory.setPre_nvdx_str(TextToNumber.textToNum(String.valueOf(inventory.getPre_nvdx()).equals("") ? "0" : String.valueOf(inventory.getPre_nvdx())));
            inventory.setPre_sscd_str(TextToNumber.textToNum(String.valueOf(inventory.getPre_sscd()).equals("") ? "0" : String.valueOf(inventory.getPre_sscd())));
            int sum_pre = inventory.getPre_sscd() + inventory.getPre_nvdx();
            inventory.setPre_sum_str(TextToNumber.textToNum(String.valueOf(sum_pre).equals("") ? "0" : String.valueOf(sum_pre)));
        };
        ObservableList<Inventory> observableList = FXCollections.observableArrayList(inventories);
        tb_quater_inv.setItems(observableList);
    }

    private void setTonkhoTongToCol2(){
        col_stt_qt.setCellValueFactory(new PropertyValueFactory<Inventory, String>("stt"));
        col_tenxd_qt.setCellValueFactory(new PropertyValueFactory<Inventory, String>("petroleumName"));
        col_chungloai_qt.setCellValueFactory(new PropertyValueFactory<Inventory, String>("chungloai"));
        col_nvdx_tdk.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tdk_nvdx_str"));
        col_sscd_tdk.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tdk_sscd_str"));
        col_sum_tdk.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tdk_sum_str"));
        col_nvdx_tck.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tcK_nvdx_str"));
        col_sscd_tck.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tck_sscd_str"));
        col_sum_tck.setCellValueFactory(new PropertyValueFactory<Inventory, String>("tck_sum_str"));
        col_nvdx_pre.setCellValueFactory(new PropertyValueFactory<Inventory, String>("pre_nvdx_str"));
        col_sscd_pre.setCellValueFactory(new PropertyValueFactory<Inventory, String>("pre_sscd_str"));
        col_sum_pre.setCellValueFactory(new PropertyValueFactory<Inventory, String>("pre_sum_str"));
    }

    @FXML
    public void changeTabTheoQuy(Event event) {
        Quarter selected = cbb_quarter.getValue();
        lb_end_date.setText(selected.getEnd_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        lb_start_date.setText(selected.getStart_date().format(DateTimeFormatter.ofPattern("dd-MM-YYYY")));
        fillDataToTable(selected);
    }

    public void setClickToTonTb() {
        tb_tonkho.setOnMouseClicked(event1 -> {
            pickTonKho = tb_tonkho.getSelectionModel().getSelectedItem();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("../changesscd-form.fxml"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Scene scene = new Scene(root);
            ChangeSScd_stage = new Stage();
            ChangeSScd_stage.setScene(scene);
            ChangeSScd_stage.initStyle(StageStyle.DECORATED);
            ChangeSScd_stage.initModality(Modality.APPLICATION_MODAL);
            ChangeSScd_stage.showAndWait();
            fillDataToTableTonkho();
        });
    }

    @FXML
    public void printtingBcNXT(ActionEvent actionEvent) {
        printing_xnt();
    }

    private void printing_xnt(){
        String file_name = "bao_cao_xnt.xlsx";
        String source_name = "baocao.xlsx";
        String sheetName = "NXT";
        copyFileExcel(source_name, file_name);
        try{
            File file = new File(file_name);
            XSSFWorkbook wb = null;
            if (file.exists()) {

                FileInputStream fileInputStream = new FileInputStream(file);
                wb = new XSSFWorkbook(fileInputStream);
                new XSSFWorkbook(new FileInputStream(file));
                // Now creating Sheets using sheet object
                XSSFSheet sheet1 = wb.getSheet(sheetName);

                fillDataToNXTSheet(sheet1, wb);
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                try {
                    Runtime.getRuntime().exec("start excel "+ file_name);
                }catch (IOException io){
                    throw new RuntimeException(io);
                }

            }
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setMerge(XSSFSheet sheet, int numRow, int untilRow, int numCol, int untilCol, boolean border) {
        CellRangeAddress cellMerge = new CellRangeAddress(numRow, untilRow, numCol, untilCol);
        sheet.addMergedRegion(cellMerge);
        if (border) {
            setBordersToMergedCells(sheet, cellMerge);
        }
    }

    protected void setBordersToMergedCells(XSSFSheet sheet, CellRangeAddress rangeAddress) {
        RegionUtil.setBorderTop(BorderStyle.THIN, rangeAddress, sheet);
        RegionUtil.setBorderLeft(BorderStyle.THIN, rangeAddress, sheet);
        RegionUtil.setBorderRight(BorderStyle.THIN, rangeAddress, sheet);
        RegionUtil.setBorderBottom(BorderStyle.THIN, rangeAddress, sheet);

    }

    private void setTitleCEl(XSSFSheet sheet, XSSFWorkbook wb){

        int row_title =4;
        int begin_col_NHAP = 6;
        List<TrucThuoc> trucThuocLisTNHAP = trucThuocService.getAllByType("NHAP");

        XSSFRow row5 = sheet.createRow(row_title);
        XSSFRow row6 = sheet.createRow(row_title+1);
        // stt
        XSSFCell col_stt = row5.createCell(1);
        col_stt.setCellStyle(setDefaulCellStyle(wb));
        col_stt.setCellValue("STT");
        setMerge(sheet,row_title, row_title+2,begin_col_NHAP-5, begin_col_NHAP-5, true);
        setCellAlightment(col_stt);

        //ten xd
        XSSFCell col_tenxd = row5.createCell(2);
        col_tenxd.setCellStyle(setDefaulCellStyle(wb));
        col_tenxd.setCellValue("TÊN MÃ KÝ HIỆU XĂNG DẦU");
        setMerge(sheet,row_title, row_title+2,begin_col_NHAP-4, begin_col_NHAP-4, true);
        setCellAlightment(col_tenxd);

        // ton dau ky
        XSSFCell col_tontruoc = row5.createCell(3);
        col_tontruoc.setCellStyle(setDefaulCellStyle(wb));
        col_tontruoc.setCellValue("Tồn đầu kỳ");
        setMerge(sheet,row_title, row_title,begin_col_NHAP-3, begin_col_NHAP-1, true);
        setCellAlightment(col_tontruoc);
        //nhap
        XSSFCell cell_nhap_tt = row5.createCell(6);
        cell_nhap_tt.setCellStyle(setDefaulCellStyle(wb));
        cell_nhap_tt.setCellValue("Nhập");
        setMerge(sheet, row_title, row_title, begin_col_NHAP, begin_col_NHAP+trucThuocLisTNHAP.size(), true);
        setCellAlightment(cell_nhap_tt);

        //
        XSSFCell col_NVDX = row6.createCell(3);
        col_NVDX.setCellStyle(setDefaulCellStyle(wb));
        col_NVDX.setCellValue("NVDX");
        setMerge(sheet,row_title+1, row_title+2,begin_col_NHAP-3, begin_col_NHAP-3, true);
        setCellAlightment(col_NVDX);

        XSSFCell col_SSCD = row6.createCell(4);
        col_SSCD.setCellStyle(setDefaulCellStyle(wb));
        col_SSCD.setCellValue("SSCD");
        setMerge(sheet,row_title+1, row_title+2,begin_col_NHAP-2, begin_col_NHAP-2, true);
        setCellAlightment(col_SSCD);

        XSSFCell col_tondk_tong = row6.createCell(5);
        col_tondk_tong.setCellStyle(setDefaulCellStyle(wb));
        col_tondk_tong.setCellValue("Tổng");
        setMerge(sheet,row_title+1, row_title+2,begin_col_NHAP-1, begin_col_NHAP-1, true);
        setCellAlightment(col_tondk_tong);

        for (int i =0 ; i < trucThuocLisTNHAP.size(); i++){
            XSSFCell cell = row6.createCell(begin_col_NHAP+i);
            cell.setCellValue(trucThuocLisTNHAP.get(i).getName());
            cell.setCellStyle(setDefaulCellStyle(wb));
            setMerge(sheet, row_title+1, row_title+2,begin_col_NHAP+i,begin_col_NHAP+i, true);
            setCellAlightment(cell);
        }
        XSSFCell col_nhap_tong = row6.createCell(begin_col_NHAP+trucThuocLisTNHAP.size());
        col_nhap_tong.setCellStyle(setDefaulCellStyle(wb));
        col_nhap_tong.setCellValue("Tổng");
        setMerge(sheet,row_title+1, row_title+2,begin_col_NHAP+trucThuocLisTNHAP.size(), begin_col_NHAP+trucThuocLisTNHAP.size(), true);
        setCellAlightment(col_nhap_tong);

        int begin_col_XUAT = begin_col_NHAP+trucThuocLisTNHAP.size()+1;
        List<TrucThuoc> trucThuocListXuat = trucThuocService.getAllByType("XUAT");
        int index_tonck = begin_col_XUAT+trucThuocListXuat.size();
        //nhap
        XSSFCell cell_xuat = row5.createCell(begin_col_XUAT);
        cell_xuat.setCellStyle(setDefaulCellStyle(wb));
        cell_xuat.setCellValue("Xuất");
        setMerge(sheet, row_title, row_title, begin_col_XUAT, index_tonck, true);
        setCellAlightment(cell_xuat);
        for (int i =0 ; i < trucThuocListXuat.size(); i++){
            XSSFCell cell = row6.createCell(begin_col_XUAT+i);
            cell.setCellValue(trucThuocListXuat.get(i).getName());
            cell.setCellStyle(setDefaulCellStyle(wb));
            setMerge(sheet, row_title+1, row_title+2,begin_col_XUAT+i,begin_col_XUAT+i, true);
            setCellAlightment(cell);
        }
        XSSFCell col_tong_x = row6.createCell(index_tonck);
        col_tong_x.setCellStyle(setDefaulCellStyle(wb));
        col_tong_x.setCellValue("Tổng");
        setMerge(sheet,row_title+1, row_title+2,index_tonck, index_tonck, true);
        setCellAlightment(col_tong_x);

        // ton cuoi ky
        XSSFCell col_tonsau = row5.createCell(index_tonck+1);
        col_tonsau.setCellStyle(setDefaulCellStyle(wb));
        col_tonsau.setCellValue("Tồn cuối kỳ");
        setMerge(sheet,row_title, row_title,index_tonck+1, index_tonck+3, true);
        setCellAlightment(col_tonsau);

        //
        XSSFCell col_NVDX_x = row6.createCell(index_tonck+1);
        col_NVDX_x.setCellStyle(setDefaulCellStyle(wb));
        col_NVDX_x.setCellValue("NVDX");
        setMerge(sheet,row_title+1, row_title+2,index_tonck+1, index_tonck+1, true);
        setCellAlightment(col_NVDX_x);

        XSSFCell col_SSCD_x = row6.createCell(index_tonck+2);
        col_SSCD_x.setCellStyle(setDefaulCellStyle(wb));
        col_SSCD_x.setCellValue("SSCD");
        setMerge(sheet,row_title+1, row_title+2,index_tonck+2, index_tonck+2, true);
        setCellAlightment(col_SSCD_x);

        XSSFCell col_tonck_tong = row6.createCell(index_tonck+3);
        col_tonck_tong.setCellStyle(setDefaulCellStyle(wb));
        col_tonck_tong.setCellValue("Tổng");
        setMerge(sheet,row_title+1, row_title+2,index_tonck+3, index_tonck+3, true);
        setCellAlightment(col_tonck_tong);
    }

    private void setCellAlightment(XSSFCell cell){
        CellUtil.setAlignment(cell,HorizontalAlignment.CENTER);
        CellUtil.setVerticalAlignment(cell, VerticalAlignment.CENTER);
    }

    private CellStyle setDefaulCellStyle(XSSFWorkbook wb){
        CellStyle cellStyle = wb.createCellStyle();
        Font font = wb.createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    private Map<String, Map<String, Integer>> setMapForNxtCell(XSSFSheet sheet){
        Map<String, Map<String, Integer>> n_MAp = new HashMap<>();

        List<String> ls_map =List.of("NL","DMN-MD", "DMN-HK");
        int root_num= 7;
        int cell_num = 2;
        for (String s : ls_map) {
            List<LoaiXangDau> loaiXangDauList = loaiXdService.findLoaiXdByType(s);
            setCEll(sheet, ChungloaiMap.type_Str_detail().get(s), root_num,cell_num);
            int rowNum = root_num+2;
            for (int i = 0; i<loaiXangDauList.size(); i++){
                setCEll(sheet, loaiXangDauList.get(i).getTenxd(), rowNum+i,cell_num);
            }
            root_num = root_num+loaiXangDauList.size()+2;
        }
        return n_MAp;
    }

    private void fillDataToNXTSheet(XSSFSheet sheet, XSSFWorkbook wb){
        setTitleCEl(sheet, wb);
        setMapForNxtCell(sheet);
    }

    private void setCEll(XSSFSheet sheet, String value, int row_num, int cell_num){
        sheet.createRow(row_num).createCell(cell_num).setCellValue(value);
    }
    private void getAndsetCEll(XSSFSheet sheet, String value, int row_num, int cell_num){
        sheet.getRow(row_num).getCell(cell_num).setCellValue(value);
    }


    public static void copyFileExcel(String sourceName, String destName){
        deleteExcel(destName);
        File source = new File(sourceName);
        File dest = new File(destName);
        if (source.exists()){
            long start = System.nanoTime();
            try {
                copyFileUsingJava7Files(source, dest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else {
            throw new RuntimeException("source file doesn't exists");
        }
    }

    private static void deleteExcel(String f){
        File file = new File(f);
        try
        {
            if(file.delete())                      //returns Boolean value
            {
                System.out.println(file.getName() + " deleted");   //getting and printing the file name
            }
            else
            {
                System.out.println("failed");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void copyFileUsingJava7Files(File source, File dest) throws IOException {
        Files.copy(source.toPath(), dest.toPath());
    }

    @FXML
    public void mockdataAction(ActionEvent actionEvent) {
        MockDataMap.mockInventoryData();
        fillDataToTableTonkho();
    }

    @FXML
    public void mapDataNxt(ActionEvent actionEvent) {
        MockDataMap.initInventoryMap();
    }


}
