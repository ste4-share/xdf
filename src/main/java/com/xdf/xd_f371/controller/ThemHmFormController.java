package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.ChitietNhiemVuDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.model.LoaiPTEnum;
import com.xdf.xd_f371.repo.*;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ThemHmFormController implements Initializable {

    @FXML
    private TextField tk,md,nl;
    @FXML
    private ComboBox<NguonNx> dvi_cbb;
    @FXML
    private ComboBox<ChitietNhiemVuDto> nv_cbb;
    @FXML
    private ComboBox<PhuongTien> pt_cbb;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private HanmucNhiemvuService hmRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNguonnxCbb();
        initNhiemvuCbb();
        initPhuongtienCbb();
        tk.setText("00:00");
        md.setText("00:00");
        nl.setText("0");
    }

    private void initPhuongtienCbb() {
        pt_cbb.setItems(FXCollections.observableArrayList(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle())));
        pt_cbb.setConverter(new StringConverter<PhuongTien>() {
            @Override
            public String toString(PhuongTien object) {
                return object==null?"":object.getName();
            }

            @Override
            public PhuongTien fromString(String string) {
                return phuongtienService.findById(pt_cbb.getSelectionModel().getSelectedItem().getId()).orElse(null);
            }
        });
    }

    private void initNhiemvuCbb() {
        nv_cbb.setItems(FXCollections.observableList(chitietNhiemvuService.findAllByLoaiNv(1,3)));
        nv_cbb.setConverter(new StringConverter<ChitietNhiemVuDto>() {
            @Override
            public String toString(ChitietNhiemVuDto object) {
                return object==null ? "" : object.getCtnv();
            }

            @Override
            public ChitietNhiemVuDto fromString(String string) {
                return chitietNhiemvuService.findByTenNhiemvu(string).orElse(null);
            }
        });
        nv_cbb.getSelectionModel().selectFirst();
    }

    private void initNguonnxCbb() {
        dvi_cbb.setItems(FXCollections.observableList(nguonNxService.findByAllBy()));
        dvi_cbb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxService.findById(dvi_cbb.getSelectionModel().getSelectedItem().getId()).orElse(null);
            }
        });
        dvi_cbb.getSelectionModel().selectFirst();
    }


    @FXML
    public void saveBtn(ActionEvent actionEvent) {
        NhiemvuTaubay nhiemvuTaubay = new NhiemvuTaubay(dvi_cbb.getSelectionModel().getSelectedItem().getId(),
                pt_cbb.getSelectionModel().getSelectedItem()==null?0:pt_cbb.getSelectionModel().getSelectedItem().getId(),
                nv_cbb.getSelectionModel().getSelectedItem().getCt_id(),
                DashboardController.findByTime.getId(),
                tk.getText(),md.getText(),Long.parseLong(nl.getText()));
        hmRepo.save(nhiemvuTaubay);
        DialogMessage.message("Thong bao","them thanhcong","Thanhcong", Alert.AlertType.INFORMATION);
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        NhiemvuController.nvStage.close();
    }
}
