package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.cons.Purpose;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.entity.LedgerDetails;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.model.*;
import com.xdf.xd_f371.repo.InventoryRepo;
import com.xdf.xd_f371.repo.LoaiXangDauRepo;
import com.xdf.xd_f371.repo.NguonNxRepo;
import com.xdf.xd_f371.repo.TcnRepo;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.StringConverter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
@Component
public class NhapController extends CommonFactory implements Initializable {
    private static List<LedgerDetails> ls_socai;
    private static int dvvc_id =0;
    private static int lxd_id_combobox_selected =0;
    private static ValidateFiledBol validateFiledBol = new ValidateFiledBol(true, true,true, true,true, true,true, true,true, true,true, true,true, true,true,true,true);

    @FXML
    private TextField soTf, recvTf,tcNhap,lenhKHso,soXe,
            donGiaTf, thucNhap,phaiNhap,tThucTe, vcf,tyTrong;
    @FXML
    private Label lb_tontheoxd;
    @FXML
    private DatePicker tungay, denngay;
    @FXML
    private Button addbtn, delbtn, refresh_btn,editbtn;
    @FXML
    private TableColumn<LedgerDetails, String> tbTT,tbTenXD,tbDonGia, tbPx,tbNhietDo, tbTyTrong, tbVCf, tbTx, tbThanhTien;
    @FXML
    private TableView<LedgerDetails> tableView;
    @FXML
    private ComboBox<NguonNx> cmb_dvvc, cmb_dvn;
    @FXML
    private ComboBox<LoaiXangDauDto> cmb_tenxd;

    @Autowired
    private NguonNxRepo nguonNxRepo;
    @Autowired
    private LoaiXangDauRepo loaiXangDauRepo;
    @Autowired
    private InventoryRepo inventoryRepo;
    @Autowired
    private TcnRepo tcnRepo;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ls_socai = new ArrayList<>();

        tableView.setItems(FXCollections.observableArrayList(new ArrayList<>()));
        tcnx_ls = tcnRepo.findByLoaiphieu(LoaiPhieuCons.PHIEU_NHAP.getName());

        setTenXDToCombobox();
        setDvvcCombobox();
        setDvnCombobox();

        refresh_btn.setOnAction(event -> {
            clearHH();
            addbtn.setDisable(false);
        });
        setUpForSearchCompleteTion();
        setTonKhoLabel();
    }

    private void setUpForSearchCompleteTion(){
        List<String> search_arr = new ArrayList<>();

        for(int i = 0; i< tcnx_ls.size(); i++){
            search_arr.add(tcnx_ls.get(i).getName());
        }
        TextFields.bindAutoCompletion(tcNhap, t -> {
            return search_arr.stream().filter(elem
                    -> {
                return elem.toLowerCase().startsWith(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });
    }

    private void setTenXDToCombobox(){
        cmb_tenxd.setConverter(new StringConverter<LoaiXangDauDto>() {
            @Override
            public String toString(LoaiXangDauDto loaiXangDau) {
                return loaiXangDau==null ? "" : loaiXangDau.getTenxd();
            }

            @Override
            public LoaiXangDauDto fromString(String s) {
                return loaiXangDauRepo.findById(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id()).orElse(null);
            }
        });
        cmb_tenxd.setItems(FXCollections.observableList(loaiXangDauRepo.findAllBy()));
        cmb_tenxd.getSelectionModel().selectFirst();
        cmb_tenxd.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                lxd_id_combobox_selected = cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id();
                setTonKhoLabel();
            }
        });
    }

    private void setDvvcCombobox(){
        cmb_dvvc.setItems(FXCollections.observableList(nguonNxRepo.findByStatus("NORMAL")));
        cmb_dvvc.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }
            @Override
            public NguonNx fromString(String string) {
                return nguonNxRepo.findById(dvvc_id).orElse(null);
            }
        });

        cmb_dvvc.getSelectionModel().selectFirst();
    }

    private void setDvnCombobox() {
        cmb_dvn.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxRepo.findById(cmb_dvn.getValue().getId()).orElse(null);
            }
        });
        cmb_dvn.setItems(FXCollections.observableList(nguonNxRepo.findByStatus("ROOT")));
        cmb_dvn.getSelectionModel().selectFirst();
    }

    private LedgerDetails getDataFromField(){
        LedgerDetails ledgerDetails = new LedgerDetails();
        ledgerDetails.setMa_xd(cmb_tenxd.getSelectionModel().getSelectedItem().getMaxd());
        ledgerDetails.setTen_xd(cmb_tenxd.getSelectionModel().getSelectedItem().getTenxd());
        ledgerDetails.setLoaixd_id(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id());
        ledgerDetails.setDon_gia(Integer.parseInt(donGiaTf.getText()));
        ledgerDetails.setPhai_nhap(phaiNhap.getText().isEmpty() ? 0 : Integer.parseInt(phaiNhap.getText()));
        ledgerDetails.setThuc_nhap(Integer.parseInt(thucNhap.getText()));
        ledgerDetails.setNhiet_do_tt(tThucTe.getText().isEmpty() ? 0 : Double.parseDouble(tThucTe.getText()));
        ledgerDetails.setHe_so_vcf(vcf.getText().isEmpty() ? 0 : Integer.parseInt(vcf.getText()));
        ledgerDetails.setTy_trong(tyTrong.getText().isEmpty() ? 0 : Double.parseDouble(tyTrong.getText()));
        ledgerDetails.setSoluong(Integer.parseInt(thucNhap.getText()));
        ledgerDetails.setThanhtien((long) Integer.parseInt(thucNhap.getText()) * Integer.parseInt(donGiaTf.getText()));
        return ledgerDetails;
    }

    private void fillDataToTextField(LedgerDetails ledgerDetails){
        cmb_tenxd.setValue(loaiXangDauRepo.findById(ledgerDetails.getLoaixd_id()).orElse(null));
        donGiaTf.setText(String.valueOf(ledgerDetails.getDon_gia()));
        phaiNhap.setText(String.valueOf(ledgerDetails.getPhai_nhap()));
        thucNhap.setText(String.valueOf(ledgerDetails.getThuc_nhap()));
        tThucTe.setText(String.valueOf(ledgerDetails.getNhiet_do_tt()));
        vcf.setText(String.valueOf(ledgerDetails.getHe_so_vcf()));
        tyTrong.setText(String.valueOf(ledgerDetails.getTy_trong()));
    }

    private void addNewPreparedledgerDetail(LedgerDetails ledgerDetails){
        tbTT.setSortable(false);
        tbTT.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tableView.getItems().indexOf(column.getValue())+1).asString());
        tbTenXD.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        tbDonGia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("don_gia"));
        tbPx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("phai_xuat"));
        tbTx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thuc_xuat"));
        tbNhietDo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        tbVCf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        tbTyTrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        tbThanhTien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien"));
        ls_socai.add(ledgerDetails);
        tableView.setItems(FXCollections.observableList(ls_socai));
    }

    @FXML
    private void btnInsert(ActionEvent event){
        addNewPreparedledgerDetail(getDataFromField());
        clearHH();
    }

    @FXML
    private void btnImport(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage("NHẬP", "TẠO PHIẾU NHẬP", "Xác nhận tạo phiếu nhập",Alert.AlertType.CONFIRMATION) == ButtonType.OK){
            Ledger l = createNewLedger();
            ls_socai.forEach(ld -> {
                ld.setLedger_id(l.getId());
//                saveLichsunxk(soCaiDto);
                saveMucGia(ld, l);
                updateInventory(l, ld);
                ledgerDetailRepo.save(ld);
            });
            if (DialogMessage.callAlertWithMessage("THÔNG BÁO", "Thành công", "Thêm phiếu nhập thành công",Alert.AlertType.INFORMATION) == ButtonType.OK){
                DashboardController.primaryStage.close();
            }
        }
    }

    private void updateInventory(Ledger l, LedgerDetails ld) {
        Inventory i = inventoryRepo.findById(l.getInventoryId()).orElseThrow();
        i.setPre_nvdx(i.getPre_nvdx() + ld.getThuc_nhap());
        inventoryRepo.save(i);
    }

    private void saveLichsunxk(LedgerDetails soCaiDto) {
        Inventory inventory = inventoryRepo.findByPetro_idAndQuarter_id(soCaiDto.getLoaixd_id(), DashboardController.findByTime.getId()).orElse(null);
        int tonsau = inventory.getPre_nvdx()+ soCaiDto.getThuc_nhap();
        int tontruoc = inventory.getPre_nvdx();
        createNewTransaction(soCaiDto, tontruoc, tonsau);
    }

    private Mucgia saveMucGia(LedgerDetails ld, Ledger l){
        Mucgia m = mucGiaRepo.findAllMucgiaUnique(Purpose.NVDX.getName(), ld.getLoaixd_id(), DashboardController.findByTime.getId(), ld.getDon_gia()).orElse(null);
        if (m == null){
            return mucGiaRepo.save(new Mucgia(ld.getDon_gia(), ld.getThuc_nhap(),l.getQuarter_id(),ld.getLoaixd_id(),l.getInventoryId(),Purpose.NVDX.getName(),MucGiaEnum.IN_STOCK.getStatus()));
        }
        m.setAmount(m.getAmount() + ld.getThuc_nhap());
        return mucGiaRepo.save(m);
    }

    private Ledger createNewLedger() {
        Ledger ledger = new Ledger();
        ledger.setBill_id(Integer.parseInt(soTf.getText()));
        ledger.setQuarter_id(DashboardController.findByTime.getId());
        ledger.setAmount(ls_socai.stream().mapToLong(x->(x.getThuc_nhap()*x.getDon_gia())).sum());
        ledger.setFrom_date(java.sql.Date.valueOf(tungay.getValue()));
        ledger.setEnd_date(java.sql.Date.valueOf(denngay.getValue()));
        ledger.setStatus("ACTIVE");
        ledger.setInventoryId(inventoryRepo.findByPetro_idAndQuarter_id(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id(), DashboardController.findByTime.getId()).orElseThrow().getId());
        ledger.setDvi_nhan(cmb_dvn.getValue().getTen());
        ledger.setDvi_xuat(cmb_dvvc.getValue().getTen());
        ledger.setLoai_phieu("NHAP");
        ledger.setDvi_nhan_id(cmb_dvn.getValue().getId());
        ledger.setDvi_xuat_id(cmb_dvvc.getValue().getId());
        ledger.setNguoi_nhan(recvTf.getText());
        ledger.setSo_xe(soXe.getText());
        ledger.setLenh_so(lenhKHso.getText());
        ledger.setTcn_id(tcnRepo.findByName(tcNhap.getText()).orElseThrow().getId());
        return ledgersRepo.save(ledger);
    }


    @FXML
    public void delAction(ActionEvent actionEvent) {
        ButtonType delete = new ButtonType("Delete");
        ButtonType cancel = new ButtonType("Cancel");
        Alert a = new Alert(Alert.AlertType.NONE, "", delete, cancel);
        a.setTitle("Delete");
        a.setContentText("Do you really want delete number ");
        a.showAndWait().ifPresent(response -> {
            if (response==delete){
                int index = 1;
                for (LedgerDetails i : ls_socai){
                    index= index +1;
                }
                ObservableList<LedgerDetails> observableList = FXCollections.observableList(ls_socai);
                tableView.setItems(observableList);
                delbtn.setDisable(true);
                editbtn.setDisable(true);
                clearHH();
                if (ls_socai.isEmpty()){
                    addbtn.setDisable(false);
                    tableView.refresh();
                }

            } else if (response==cancel) {
                System.out.println("CAncel");
            }
        });
    }

    @FXML
    public void editAction(ActionEvent actionEvent) {
        ButtonType edit = new ButtonType("Sửa");
        ButtonType cancel = new ButtonType("Hủy " +
                "bỏ");
        Alert a = new Alert(Alert.AlertType.WARNING, "", edit, cancel);
        a.setTitle("" +
                "Sửa");
        a.setContentText("Xác nhận chỉnh " +
                "sửa");
        a.showAndWait().ifPresent(response -> {
            if (response==edit){
                LedgerDetails ledgerDetails = getDataFromField();
                delbtn.setDisable(true);
                editbtn.setDisable(true);
                addbtn.setDisable(false);
                clearHH();
                ObservableList<LedgerDetails> observableList = FXCollections.observableList(ls_socai);
                tableView.setItems(observableList);
            } else if (response==cancel) {
                System.out.println("CAncel");
            }
        });
    }
    private void clearHH(){
        donGiaTf.clear();
        phaiNhap.clear();
        thucNhap.clear();
        tThucTe.clear();
        vcf.clear();
        tyTrong.clear();
    }


    private void insertToDataExcel() {
        openDataExcelFile();
    }

    private void openDataExcelFile(){
        String file_name = "data.xlsm";
        String sheetName = "socai";
        try{
            File file = new File(file_name);
            XSSFWorkbook wb = null;
            if (file.exists()) {

                FileInputStream fileInputStream = new FileInputStream(file);
                wb = new XSSFWorkbook(fileInputStream);
                new XSSFWorkbook(new FileInputStream(file));
                // Now creating Sheets using sheet object
                XSSFSheet sheet1 = wb.getSheet(sheetName);

                fillDataToSocaiSheet(sheet1, wb);
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);

                wb.write(fileOutputStream);
                fileOutputStream.close();
                try {
                    Runtime.getRuntime().exec("cmd /c start excel "+ file_name);
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

    private void fillDataToSocaiSheet(XSSFSheet sheet, XSSFWorkbook wb) {
        int begin_data_current = 2;
        List<Tcn> soCaiDtoList = tcnRepo.findAll();
        for(int i =0; i< soCaiDtoList.size(); i++){
            Tcn soCaiDto = soCaiDtoList.get(i);
            XSSFRow row = sheet.createRow(begin_data_current+i);
            row.createCell(1).setCellValue(soCaiDto.getId());
            row.createCell(2).setCellValue(soCaiDto.getName());
            row.createCell(3).setCellValue(soCaiDto.getStatus());
            row.createCell(4).setCellValue(soCaiDto.getConcert());
        }
    }


    @FXML
    public void btnCancel(ActionEvent actionEvent) {
        DashboardController.primaryStage.close();
    }

    @FXML
    public void soValid(KeyEvent keyEvent) {
        String text = soTf.getText();
        if (!text.matches("[0-9]{0,5}")){
            soTf.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setSo(false);
        }else {
            soTf.setStyle(null);
            validateFiledBol.setSo(true);
        }
    }

    @FXML
    public void validate_nnh(KeyEvent keyEvent) {
        String text = recvTf.getText();
        if (!text.matches(".{0,50}")){
            recvTf.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setNguoinhanhang(false);
        }else{
            recvTf.setStyle(null);
            validateFiledBol.setNguoinhanhang(true);
        }
    }

    public void validate_dongia(KeyEvent keyEvent) {
        String text = donGiaTf.getText();
        if (!text.matches("[^0A-Za-z][0-9]{0,18}")){
            donGiaTf.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setDongia(false);
        }else{
            donGiaTf.setStyle(null);
            validateFiledBol.setDongia(true);
        }
    }

    public void validate_phaixuat(KeyEvent keyEvent) {
        String text = phaiNhap.getText();
        if (!text.matches("[^0A-Za-z][0-9]{0,9}")){
            phaiNhap.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setPhaixuat(false);
        }else{
            phaiNhap.setStyle(null);
            validateFiledBol.setPhaixuat(true);
        }
    }

    public void validate_tcn(KeyEvent keyEvent) {
        String text = tcNhap.getText();
        if (!text.matches(".{0,50}")){
            tcNhap.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setTinhchatnhap(false);
        }else{
            tcNhap.setStyle(null);
            validateFiledBol.setTinhchatnhap(true);
        }
    }

    public void validate_lenhso(KeyEvent keyEvent) {
        String text = lenhKHso.getText();
        if (!text.matches(".{0,50}")){
            lenhKHso.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setLenhso(false);
        }else{
            lenhKHso.setStyle(null);
            validateFiledBol.setLenhso(true);
        }
    }

    public void validate_thucxuat(KeyEvent keyEvent) {
        String text = thucNhap.getText();
        if (!text.matches("[^0A-Za-z][0-9]{0,9}")){
            thucNhap.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setThucxuat(false);
        }else{
            thucNhap.setStyle(null);
            validateFiledBol.setThucxuat(true);
        }
    }

    public void validate_nhietdo(KeyEvent keyEvent) {
        String text = tThucTe.getText();
        if (!text.matches("[^0A-Za-z][0-9]{0,5}")) {
            tThucTe.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setNhietdo(false);
        } else {
            tThucTe.setStyle(null);
            validateFiledBol.setNhietdo(true);
        }
    }

    public void validate_tytrong(KeyEvent keyEvent) {
        String text = tyTrong.getText();
        if (!text.matches("[^0A-Za-z][0-9]{0,5}")){
            tyTrong.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setTytrong(false);
        }else{
            tyTrong.setStyle(null);
            validateFiledBol.setTytrong(true);
        }
    }

    public void validate_vcf(KeyEvent keyEvent) {
        String text = vcf.getText();
        if (!text.matches("[^0A-Za-z][0-9]{0,5}")){
            vcf.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setVcf(false);
        }else{
            vcf.setStyle(null);
            validateFiledBol.setVcf(true);
        }
    }

    public void validate_soxe(KeyEvent keyEvent) {
        String text = soXe.getText();
        if (!text.matches(".{0,8}")){
            soXe.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            validateFiledBol.setSoxe(false);
        }else{
            soXe.setStyle(null);
            validateFiledBol.setSoxe(true);
        }
    }

    @FXML
    public void changedItemLoaiXd(ActionEvent actionEvent) {
        setTonKhoLabel();
    }

    private void setTonKhoLabel(){
        Inventory i = inventoryRepo.findByPetro_idAndQuarter_id(cmb_tenxd.getSelectionModel().getSelectedItem().getXd_id(), DashboardController.findByTime.getId()).orElseThrow();
        lb_tontheoxd.setText("Số lượng tồn: "+ i.getPre_nvdx() +" (Lit)");
    }

    @FXML
    public void printTestData(ActionEvent actionEvent) {
        insertToDataExcel();
    }

    @FXML
    public void select_item(MouseEvent mouseEvent) {
        try {
            LedgerDetails ledgerDetails =  tableView.getSelectionModel().getSelectedItem();
            fillDataToTextField(ledgerDetails);

        }catch (NullPointerException nullPointerException){
            nullPointerException.printStackTrace();
        }
    }
}
