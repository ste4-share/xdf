package com.xdf.xd_f371.fatory;

import com.xdf.xd_f371.cons.ConfigCons;
import com.xdf.xd_f371.cons.LoaiPhieuCons;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.dto.AssignmentBillDto;
import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.dto.UnitBillDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import com.xdf.xd_f371.util.TextToNumber;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CommonFactory implements Initializable {
    protected static AssignmentBillDto assignmentBillDto = null;
    protected static UnitBillDto unitBillDto = null;
    public static String pre_path;
    protected static Stage primaryStage;
    protected static double inventory_quantity = 0;
    protected Configuration config = null;
    protected List<InventoryUnits> i = new ArrayList<>();
    protected static List<Ledger> ledgers = new ArrayList<>();
    protected static List<LedgerDetails> ls_socai;
    protected static List<Tcn> tcnx_ls = new ArrayList<>();
    public static String styleErrorField = "-fx-border-color: red ; -fx-border-width: 2px ;";
    @Autowired
    protected LedgerService ledgerService;
    @Autowired
    protected NguonNxService nguonNxService;
    @Autowired
    protected InventoryUnitService inventoryUnitService;
    @Autowired
    protected TructhuocService tructhuocService;
    @Autowired
    protected TcnService tcnService;
    @Autowired
    protected LoaiXdService loaiXdService;
    @Autowired
    protected ConfigurationService configurationService;
    @FXML
    protected TableView<LedgerDetails> tbView;
    @FXML
    protected TextArea note;
    @FXML
    protected Label lb_tontheoxd;
    @FXML
    protected TextField so,nguoinhan,lenhso,soxe;
    @FXML
    protected RadioButton nvdx_rd;
    @FXML
    protected DatePicker tungay, denngay;
    @FXML
    protected TableColumn<LedgerDetails, String> stt, tenxd, dongia,col_phainx,col_nhietdo,col_tytrong,col_vcf,col_thucnx,col_thanhtien;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLabelVar();
        initLegersList();
        initInventoryUnit();
        setcellFactory();
    }

    private void initLabelVar() {
        setVi_DatePicker(tungay);
        setVi_DatePicker(denngay);
        ls_socai = new ArrayList<>();
        tungay.setValue(LocalDate.now());
        note.setText(null);
    }

    private void initLegersList() {
        if (DashboardController.ref_Dv!=null){
            ledgers = ledgerService.findAllLedgerByUnit(DashboardController.ref_Dv.getId());
        }
        else ledgers = ledgerService.findAllLedgerActive();
    }

    protected void initInventoryUnit(){
        Optional<Configuration> c = configurationService.findByParam(ConfigCons.ROOT_ID.getName());
        c.ifPresent(configuration -> config = configuration);
    }
    protected List<String> validateField(Object object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        for (ConstraintViolation<Object> violation : violations) {
            return List.of(violation.getPropertyPath().toString());
        }
        return new ArrayList<>();
    }
    protected void setInvLabel(LoaiXangDauDto lxd){
        i = inventoryUnitService.getInventoryByUnitByPetro(Long.parseLong(config.getValue()),lxd.getXd_id());
        if (!i.isEmpty()){
                double sum_inventory_nvdx = i.stream().mapToDouble(InventoryUnits::getNvdx_quantity).sum();
                setTonKhoLabel(sum_inventory_nvdx);
        } else {
            setTonKhoLabel(0);
        }
    }
    protected void setTonKhoLabel(double i){
        inventory_quantity = i;
        lb_tontheoxd.setText("Số lượng tồn: "+ TextToNumber.textToNum_2digits(inventory_quantity) +" (Lit)");
    }
    protected void cleanErrorField(TextField field){
        field.selectAll();
        field.setStyle(null);
    }
    protected void validateToSettingStyle(TextField tf) {
        if (isNumber(tf.getText()) || isNumberDouble(tf.getText())){
            tf.setStyle(null);
        }else{
            tf.setStyle(styleErrorField);
        }
    }
    protected boolean isNumber(String in) {
        return in.matches("[^0A-Za-z][0-9]{0,18}");
    }
    protected boolean isNumberDouble(String in) {
        return in.matches("^([+-]?\\d*\\.?\\d*)$");
    }
    protected boolean outfieldValid(TextField tf, String mes){
        if (tf.getText().isBlank()) {
            DialogMessage.message("Lỗi", mes,
                    "Nhập sai định dạng.", Alert.AlertType.ERROR);
            tf.setStyle(styleErrorField);
            return true;
        }
        return false;
    }
    protected void setXangDauCombobox(ComboBox<LoaiXangDauDto> cbb, LoaiXdService loaiXdService){
        ComponentUtil.setItemsToComboBox(cbb,loaiXdService.findAllOrderby(),LoaiXangDauDto::getTenxd, input -> loaiXdService.findAllTenxdDto(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(cbb, (typedText, itemToCompare) -> itemToCompare.getTenxd().toLowerCase().contains(typedText.toLowerCase()));
        cbb.getSelectionModel().selectFirst();
    }
    protected void setNguonnxCombobox(ComboBox<NguonNx> cbb, List<NguonNx> nguonNxList){
        ComponentUtil.setItemsToComboBox(cbb,nguonNxList,NguonNx::getTen, input -> nguonNxService.findByTen(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
    }
    protected void setcellFactory(){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tbView.getItems().indexOf(column.getValue())+1).asString());
        tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        col_phainx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("soluongpx_str"));
        col_thucnx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("soluong_str"));
        col_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        col_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        col_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        col_thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
    }
    protected boolean isNotDuplicate(int xd_id,double dongia,double tx, double px,String lp){
        for (int i =0; i< ls_socai.size(); i++){
            LedgerDetails ld = ls_socai.get(i);
            if (ld.getLoaixd_id() == xd_id && ld.getDon_gia()==dongia){
                if (lp.equals(LoaiPhieuCons.PHIEU_NHAP.getName())){
                    ld.setThuc_nhap(ld.getThuc_nhap()+tx);
                    ld.setPhai_nhap(ld.getPhai_nhap()+px);
                    ld.setSoluong(ld.getThuc_nhap()+tx);
                    ld.setSoluong_px(ld.getPhai_nhap() + px);
                    ld.setThucnhap_str(TextToNumber.textToNum_2digits(ld.getThuc_nhap()));
                    ld.setPhainhap_str(TextToNumber.textToNum_2digits(ld.getPhai_nhap()));
                    ld.setThanhtien_str(TextToNumber.textToNum_2digits(ld.getThuc_nhap()*ld.getDon_gia()));
                    ls_socai.set(i, ld);
                }else{
                    ld.setThuc_xuat(ld.getThuc_xuat() + tx);
                    ld.setPhai_xuat(ld.getPhai_xuat() + px);
                    ld.setSoluong(ld.getThuc_xuat() + tx);
                    ld.setSoluong_px(ld.getPhai_xuat() + px);
                    ld.setThucxuat_str(TextToNumber.textToNum_2digits(ld.getThuc_xuat()));
                    ld.setPhaixuat_str(TextToNumber.textToNum_2digits(ld.getPhai_xuat()));
                    ld.setThanhtien_str(TextToNumber.textToNum_2digits(ld.getThuc_xuat()*ld.getDon_gia()));
                    ls_socai.set(i, ld);
                }
                return false;
            }
        }
        return true;
    }
    public static void setVi_DatePicker(DatePicker datePicker){
        Locale.setDefault(Locale.forLanguageTag("vi"));
        datePicker.setPromptText("dd/MM/yyyy");
        DateTimeFormatter vietnameseFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.forLanguageTag("vi"));
        datePicker.setConverter(new javafx.util.StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                return date != null ? vietnameseFormatter.format(date) : "";
            }
            @Override
            public LocalDate fromString(String string) {
                return string != null && !string.isEmpty()
                        ? LocalDate.parse(string, vietnameseFormatter)
                        : null;
            }
        });
    }
    public static void setSelectDirectory(Stage primaryStage){
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Directory");

        File selectedDirectory = directoryChooser.showDialog(primaryStage);

        if (selectedDirectory != null) {
            pre_path= selectedDirectory.getAbsolutePath();
            selectedDirectory.getAbsoluteFile();
        } else {
            pre_path = null;
        }
    }
    public static File setSelectFileDirectory(Stage stage){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");
        // Add file filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Excel Files (*.xlsx)", "*.xlsx")
        );
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            pre_path = selectedFile.getAbsolutePath();
            return selectedFile.getAbsoluteFile();
        }
        return null;
    }
    protected String generateId(int year, int root_id, String lenh_so,String lp){
        String y = String.valueOf(year);
        String rid = String.valueOf(root_id);
        Long time = System.currentTimeMillis();
        return y.concat("_"+rid).concat("_"+lenh_so).concat("_"+lp+"_").concat(String.valueOf(time));
    }
}
