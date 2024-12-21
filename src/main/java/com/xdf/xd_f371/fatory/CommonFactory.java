package com.xdf.xd_f371.fatory;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CommonFactory {
    protected static int inventory_quantity = 0;
    protected static List<Tcn> tcnx_ls = new ArrayList<>();
    public static String styleErrorField = "-fx-border-color: red ; -fx-border-width: 2px ;";
    @Autowired
    protected LedgerService ledgerService;
    @Autowired
    protected NguonNxService nguonNxService;
    @Autowired
    protected TructhuocService tructhuocService;
    @Autowired
    protected InventoryService inventoryService;
    @FXML
    protected TableView<LedgerDetails> tbView;
    @FXML
    protected TableColumn<LedgerDetails, String> stt, tenxd, dongia,col_phainx,col_nhietdo,col_tytrong,col_vcf,col_thucnx,col_thanhtien;

    protected List<String> validateField(Object object){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        for (ConstraintViolation<Object> violation : violations) {
            return List.of(violation.getPropertyPath().toString());
        }
        return new ArrayList<>();
    }
    protected void cleanErrorField(TextField field){
        field.selectAll();
        field.setStyle(null);
    }
    protected void validateToSettingStyle(TextField tf) {
        if (!isNumber(tf.getText())){
            tf.setStyle(styleErrorField);
        }else{
            tf.setStyle(null);
        }
    }
    protected boolean isNumber(String in) {
        return in.matches("[^0A-Za-z][0-9]{0,18}");
    }
    protected boolean outfieldValid(TextField tf, String mes){
        if (tf.getText().trim().equals("")) {
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
    protected void setcellFactory(String pnx, String tnx){
        stt.setSortable(false);
        stt.setCellValueFactory(column-> new ReadOnlyObjectWrapper<>(tbView.getItems().indexOf(column.getValue())+1).asString());
        tenxd.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ten_xd"));
        dongia.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("dongia_str"));
        col_phainx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>(pnx));
        col_thucnx.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>(tnx));
        col_nhietdo.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("nhiet_do_tt"));
        col_vcf.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("he_so_vcf"));
        col_tytrong.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("ty_trong"));
        col_thanhtien.setCellValueFactory(new PropertyValueFactory<LedgerDetails, String>("thanhtien_str"));
    }
}
