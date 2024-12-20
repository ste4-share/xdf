package com.xdf.xd_f371.controller;


import com.xdf.xd_f371.entity.TrucThuoc;
import com.xdf.xd_f371.service.TructhuocService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class AddAffForm implements Initializable {
    public static int aff_id_selected = 0;

    @Autowired
    private TructhuocService tructhuocService;

    @FXML
    Button addBtn,exitBtn;
    @FXML
    ComboBox<TrucThuoc> affilatedUnitCb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fillDataToAfflatedUnitCombobox();
    }

    private void fillDataToAfflatedUnitCombobox() {
        affilatedUnitCb.setItems(FXCollections.observableList(tructhuocService.findAll()));
        affilatedUnitCb.getSelectionModel().selectFirst();
        affilatedUnitCb.setConverter(new StringConverter<TrucThuoc>() {
            @Override
            public String toString(TrucThuoc trucThuoc) {
                return trucThuoc==null ? "": trucThuoc.getName();
            }

            @Override
            public TrucThuoc fromString(String s) {
                return tructhuocService.findById(affilatedUnitCb.getSelectionModel().getSelectedItem().getId()).orElse(null);
            }
        });
    }

    @FXML
    public void AddAff(ActionEvent actionEvent) {
        aff_id_selected = affilatedUnitCb.getValue().getId();
    }

    @FXML
    public void exitAct(ActionEvent actionEvent) {
    }
}
