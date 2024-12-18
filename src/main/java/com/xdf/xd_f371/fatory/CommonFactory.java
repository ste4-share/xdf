package com.xdf.xd_f371.fatory;

import com.xdf.xd_f371.dto.LoaiXangDauDto;
import com.xdf.xd_f371.entity.LichsuXNK;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.service.*;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Component
public class CommonFactory {
    protected static int inventory_quantity = 0;
    protected static List<Tcn> tcnx_ls = new ArrayList<>();
    protected String styleErrorField = "-fx-border-color: red ; -fx-border-width: 2px ;";
    @Autowired
    protected LichsuService lichsuService;
    @Autowired
    protected LedgerService ledgerService;
    @Autowired
    protected MucgiaService mucgiaService;
    @Autowired
    protected NguonNxService nguonNxService;
    @Autowired
    protected TructhuocService tructhuocService;

    protected void createNewTransaction(LedgerDetails ledgerDetails, int tontruoc, int tonsau){
        LichsuXNK lichsuXNK = new LichsuXNK();
        lichsuXNK.setTen_xd(ledgerDetails.getTen_xd());
        lichsuXNK.setSoluong(ledgerDetails.getThuc_xuat());
        lichsuXNK.setTontruoc(tontruoc);
        lichsuXNK.setTonsau(tonsau);
        lichsuXNK.setMucgia(String.valueOf(ledgerDetails.getDon_gia()));
        lichsuService.save(lichsuXNK);
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
    protected void hoverButton(Button button, String color) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: "+color+"; -fx-border-color: #000000; -fx-border-width:3;-fx-background-radius:10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: "+color+";-fx-background-radius:10"));
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
        FxUtilTest.autoCompleteComboBoxPlus(cbb, (typedText, itemToCompare) -> itemToCompare.getTenxd().toLowerCase().contains(typedText.toLowerCase()));
        cbb.setConverter(new StringConverter<LoaiXangDauDto>() {
            @Override
            public String toString(LoaiXangDauDto object) {
                return object == null ? "": object.getTenxd();
            }
            @Override
            public LoaiXangDauDto fromString(String string) {
                return loaiXdService.findAllTenxdDto(string).orElse(null);
            }
        });

        cbb.getItems().addAll(loaiXdService.findAllOrderby());
        cbb.getSelectionModel().selectFirst();
    }
}
