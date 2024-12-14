package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddNewHanMucNhiemvu implements Initializable {
    @FXML
    ComboBox<NhiemVu> nv_cbb;
    @FXML
    ComboBox<ChitietNhiemVu> ct_cbb;
    @FXML
    TextField xang_tf,diezel_tf,daubay_tf;

    @Autowired
    ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    HanmucNhiemvuService hanmucNhiemvuService;
    @Autowired
    NguonNxService nguonNxService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNhiemvuCbb();
        xang_tf.setText("0");
        daubay_tf.setText("0");
        diezel_tf.setText("0");
    }

    private void initNhiemvuCbb(){
        nv_cbb.setItems(FXCollections.observableList(chitietNhiemvuService.findAll()));
        nv_cbb.setConverter(new StringConverter<NhiemVu>() {
            @Override
            public String toString(NhiemVu object) {
                return object==null ? "" : object.getTenNv();
            }

            @Override
            public NhiemVu fromString(String string) {
                return chitietNhiemvuService.findByIdNhiemvu(nv_cbb.getSelectionModel().getSelectedItem().getId()).orElse(null);
            }
        });
        nv_cbb.getSelectionModel().selectFirst();
    }
    private void initChitietNhiemvuCbb(){
        ct_cbb.setItems(FXCollections.observableList(chitietNhiemvuService.findByNhiemvuId(nv_cbb.getSelectionModel().getSelectedItem().getId())));
        ct_cbb.setConverter(new StringConverter<ChitietNhiemVu>() {
            @Override
            public String toString(ChitietNhiemVu object) {
                return object==null ? "" : object.getNhiemvu();
            }

            @Override
            public ChitietNhiemVu fromString(String string) {
                return chitietNhiemvuService.findById(ct_cbb.getSelectionModel().getSelectedItem().getId()).orElse(null);
            }
        });
        ct_cbb.getSelectionModel().selectFirst();
    }
    @FXML
    public void save(ActionEvent actionEvent) {
        hanmucNhiemvuService.save(new HanmucNhiemvu2(DashboardController.findByTime.getId(),
                nguonNxService.findByStatus(StatusEnum.ROOT_STATUS.getName()).get(0).getId(),nv_cbb.getSelectionModel().getSelectedItem().getId(),
                Long.parseLong(diezel_tf.getText()),Long.parseLong(daubay_tf.getText()),Long.parseLong(xang_tf.getText())));
        NhiemvuController.nvStage.close();
    }

    @FXML
    public void cancel(ActionEvent actionEvent) {
        NhiemvuController.nvStage.close();
    }

    @FXML
    public void nvselected(ActionEvent actionEvent) {
        initChitietNhiemvuCbb();
    }
}
