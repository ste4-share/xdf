package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.ChitietNhiemVuDto;
import com.xdf.xd_f371.entity.HanmucNhiemvu;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.repo.ChitietNhiemvuRepo;
import com.xdf.xd_f371.repo.HanmucNhiemvuRepo;
import com.xdf.xd_f371.repo.NguonNxRepo;
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
public class AddNewChitieuNvForm implements Initializable {

    @FXML
    private ComboBox<NguonNx> dvi_cmb;
    @FXML
    private ComboBox<ChitietNhiemVuDto> nv_cmb;
    @FXML
    private TextField tk,md,nl;

    @Autowired
    private NguonNxRepo nguonNxRepo;
    @Autowired
    private ChitietNhiemvuRepo chitietNhiemvuRepo;
    @Autowired
    private HanmucNhiemvuRepo hanmucNhiemvuRepo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNguonnxCbb();
        initChiTietNhiemVu();
        tk.setText("00:00");
        md.setText("00:00");
        nl.setText("0");
    }

    private void initNguonnxCbb() {
        dvi_cmb.setItems(FXCollections.observableList(nguonNxRepo.findByAllBy()));
        dvi_cmb.setConverter(new StringConverter<NguonNx>() {
            @Override
            public String toString(NguonNx object) {
                return object==null ? "" : object.getTen();
            }

            @Override
            public NguonNx fromString(String string) {
                return nguonNxRepo.findByTen(dvi_cmb.getValue().getTen()).orElse(null);
            }
        });
        dvi_cmb.getSelectionModel().selectFirst();
    }

    private void initChiTietNhiemVu() {
        nv_cmb.setItems(FXCollections.observableList(chitietNhiemvuRepo.findAllByLoaiNv(1,3)));
        nv_cmb.setConverter(new StringConverter<ChitietNhiemVuDto>() {
            @Override
            public String toString(ChitietNhiemVuDto object) {
                return object==null ? "" : object.getCtnv();
            }

            @Override
            public ChitietNhiemVuDto fromString(String string) {
                return chitietNhiemvuRepo.findByTenNhiemvu(string).orElse(null);
            }
        });
    }

    @FXML
    public void savechitieu(ActionEvent actionEvent) {
        HanmucNhiemvu hanmucNhiemvu = new HanmucNhiemvu();
        hanmucNhiemvu.setUnit_id(dvi_cmb.getSelectionModel().getSelectedItem().getId());
        hanmucNhiemvu.setQuarter_id(DashboardController.findByTime.getId());
        hanmucNhiemvu.setCt_md(md.getText());
        hanmucNhiemvu.setCt_tk(tk.getText());
        hanmucNhiemvu.setConsumpt(Integer.parseInt(nl.getText()));
        hanmucNhiemvu.setCtnhiemvu_id(nv_cmb.getSelectionModel().getSelectedItem().getCt_id());
        hanmucNhiemvuRepo.save(hanmucNhiemvu);
        NhiemvuController.nvStage.close();
    }
}
