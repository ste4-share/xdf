package com.xdf.xd_f371.controller;


import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.service.TrucThuocService;
import com.xdf.xd_f371.service.impl.TrucThuocImp;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAffForm implements Initializable {
    public static int aff_id_selected = 0;
    private TrucThuocService trucThuocService = new TrucThuocImp();

    @FXML
    Button addBtn,exitBtn;
    @FXML
    ComboBox<TrucThuoc> affilatedUnitCb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataToAfflatedUnitCombobox();
    }

    private void fillDataToAfflatedUnitCombobox() {
        affilatedUnitCb.setItems(FXCollections.observableList(trucThuocService.getAll()));
        affilatedUnitCb.getSelectionModel().selectFirst();
        affilatedUnitCb.setConverter(new StringConverter<TrucThuoc>() {
            @Override
            public String toString(TrucThuoc trucThuoc) {
                if(trucThuoc!=null){
                    aff_id_selected = trucThuoc.getId();
                }
                return trucThuoc==null ? "": trucThuoc.getName();
            }

            @Override
            public TrucThuoc fromString(String s) {
                return trucThuocService.findById(aff_id_selected);
            }
        });
    }

    @FXML
    public void AddAff(ActionEvent actionEvent) {
        aff_id_selected = affilatedUnitCb.getValue().getId();
        UnitDetailController.unit_detail_stage.close();
    }

    @FXML
    public void exitAct(ActionEvent actionEvent) {
        UnitDetailController.unit_detail_stage.close();
    }
}
