package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.Category;
import com.xdf.xd_f371.entity.NguonnxTitle;
import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.model.MockDataMap;
import com.xdf.xd_f371.service.CategoryService;
import com.xdf.xd_f371.service.InvReportDetailService;
import com.xdf.xd_f371.service.NguonNXService;
import com.xdf.xd_f371.service.TrucThuocService;
import com.xdf.xd_f371.service.impl.CategoryImp;
import com.xdf.xd_f371.service.impl.NguonNXImp;
import com.xdf.xd_f371.service.impl.TrucThuocImp;
import com.xdf.xd_f371.service.impl.invReportDetailImp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UnitDetailController implements Initializable {
    public static Stage unit_detail_stage;
    private static int tt_id =0;
    private TrucThuocService trucThuocService = new TrucThuocImp();
    private NguonNXService nguonNXService = new NguonNXImp();
    private CategoryService categoryService = new CategoryImp();
    private InvReportDetailService invReportDetailService = new invReportDetailImp();
    @FXML
    TextField unit_name_tf;
    @FXML
    RadioButton n_radio,x_radio,all_radio;
    @FXML
    ComboBox<TrucThuoc> tructhuoc_cbb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        unit_name_tf.setText(DonviController.selectedUnit.getTen());
        setRadiobutton();
        setTructhuocCombobox();
    }

    private void setTructhuocCombobox(){
        tructhuoc_cbb.setItems(FXCollections.observableList(trucThuocService.getAll()));
        tructhuoc_cbb.setConverter(new StringConverter<TrucThuoc>() {
            @Override
            public String toString(TrucThuoc trucThuoc) {
                if (trucThuoc!=null){
                    tt_id=trucThuoc.getId();
                }
                return trucThuoc==null ? "" : trucThuoc.getName();
            }

            @Override
            public TrucThuoc fromString(String s) {
                return trucThuocService.findById(tt_id);
            }
        });
        tructhuoc_cbb.getSelectionModel().select(trucThuocService.findById(DonviController.selectedUnit.getTructhuoc_id()));
    }
    private void setRadiobutton() {
        String lp = DonviController.selectedUnit.getLoaiphieu();
        if(lp.contains("NHAP") && lp.contains("XUAT")){
            all_radio.setSelected(true);
        } else if(lp.contains("NHAP")){
            n_radio.setSelected(true);
        } else if(lp.contains("XUAT")){
            x_radio.setSelected(true);
        }
    }

    @FXML
    public void exitScreen(ActionEvent actionEvent) {
        DonviController.unit_stage.close();
    }

    @FXML
    public void saveUnit(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("LƯU");
        alert.setHeaderText("Lưu thay đổi");
        alert.setContentText("Xác nhận Lưu thay đổi?");
        if (alert.showAndWait().get() == ButtonType.OK){
            updateNguonnxTitle();
            isChangeBillType();
            MockDataMap.initInventoryMap();
        }
        DonviController.unit_stage.close();
    }

    private void updateNguonnxTitle(){
        NguonnxTitle nguonnxTitle = new NguonnxTitle();
        nguonnxTitle.setTitle_id(tructhuoc_cbb.getSelectionModel().getSelectedItem().getId());
        nguonnxTitle.setGroup_id(2);
        nguonnxTitle.setNguonnx_id(DonviController.selectedUnit.getId());
        nguonNXService.updateNguonnxTitle(nguonnxTitle);
    }

    private void setNEwCategoryChangeCode(Category category, String header3, String code){
        category.setHeader_lv3(header3);
        category.setCode(code);
        category.setType_title(code);
        categoryService.updateAndDoNotConflic(category);
    }

    private void isChangeBillType(){
        if (all_radio.isSelected()){
            List<Category> category_X = categoryService.getCategoryByTructhuocId(DonviController.selectedUnit.getTructhuoc_id());
            if (categoryService.deleteBytructhuocId(DonviController.selectedUnit.getTructhuoc_id()) > 0){
                setNEwCategoryChangeCode(category_X.get(0), "Nhập", "NHAP");
                setNEwCategoryChangeCode(category_X.get(0), "Xuất", "XUAT");
            }else{
                throw new RuntimeException("category_by_tructhuoc is null");
            }
        }else if (n_radio.isSelected()){
            List<Category> category_X = categoryService.getCategoryByTructhuocId(DonviController.selectedUnit.getTructhuoc_id());
            if (categoryService.deleteBytructhuocId(DonviController.selectedUnit.getTructhuoc_id()) > 0){
                setNEwCategoryChangeCode(category_X.get(0), "Nhập", "NHAP");
            }else{
                throw new RuntimeException("category_by_tructhuoc is null");
            }
        }else if (x_radio.isSelected()){
            List<Category> category_X = categoryService.getCategoryByTructhuocId(DonviController.selectedUnit.getTructhuoc_id());
            if (categoryService.deleteBytructhuocId(DonviController.selectedUnit.getTructhuoc_id()) > 0){
                setNEwCategoryChangeCode(category_X.get(0), "Xuất", "XUAT");
            }else{
                throw new RuntimeException("category_by_tructhuoc is null");
            }
        }
    }
}
