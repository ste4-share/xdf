package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.model.StatusEnum;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.List;
import java.util.Optional;
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
        initChitietNhiemvuCbb(chitietNhiemvuService.findAllCtnv());
        initField();
    }
    private void initField(){
        HanmucNhiemvu2Dto hm = NhiemvuController.hm2;
        if (hm!=null){
            xang_tf.setText(String.valueOf(hm.getXang()));
            diezel_tf.setText(String.valueOf(hm.getDiezel()));
            daubay_tf.setText(String.valueOf(hm.getDaubay()));
            Optional<NhiemVu> nv = chitietNhiemvuService.findByName(hm.getTenNv(),StatusCons.ACTIVED.getName());
            nv.ifPresent(nhiemVu -> nv_cbb.getSelectionModel().select(nhiemVu));
            List<ChitietNhiemVu> ct = chitietNhiemvuService.findByNhiemvuId(nv.get().getId());
            initChitietNhiemvuCbb(ct);
        }
    }

    private void initNhiemvuCbb(){
        ComponentUtil.setItemsToComboBox(nv_cbb,chitietNhiemvuService.findAll(),NhiemVu::getTenNv,input->chitietNhiemvuService.findByName(input, StatusCons.ACTIVED.getName()).orElse(null));
        nv_cbb.getSelectionModel().selectFirst();
    }
    private void initChitietNhiemvuCbb(List<ChitietNhiemVu> ls){
        NhiemVu nv = nv_cbb.getSelectionModel().getSelectedItem();
        if (nv!=null){
            ComponentUtil.setItemsToComboBox(ct_cbb,ls,ChitietNhiemVu::getNhiemvu,input->chitietNhiemvuService.findByNhiemvu(input,nv.getId()).orElse(null));
            ct_cbb.getSelectionModel().selectFirst();
        }
    }
    @FXML
    public void save(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null, "Xac nhan them moi","Them moi", Alert.AlertType.CONFIRMATION) == ButtonType.OK){
            if (isvalid()){
                saveHm();
                DialogMessage.successShowing("Luu thanh cong");
                NhiemvuController.nvStage.close();
            }else{
                DialogMessage.errorShowing("Sai dinh dang, vui long thu lai.");
            }
        }
    }
    private void saveHm(){
        try {
            HanmucNhiemvu2Dto hm = NhiemvuController.hm2;
            hm.setXang(Long.parseLong(xang_tf.getText()));
            hm.setDiezel(Long.parseLong(diezel_tf.getText()));
            hm.setDaubay(Long.parseLong(daubay_tf.getText()));
            hanmucNhiemvuService.save(new HanmucNhiemvu2(hm));
            NhiemvuController.nvStage.close();
        }catch (Exception e){
            DialogMessage.errorShowing(e.getMessage());
        }
    }
    private boolean isvalid(){
        if (Common.isNumber(xang_tf.getText()) && Common.isNumber(daubay_tf.getText()) && Common.isNumber(diezel_tf.getText())){
            return true;
        }
        return false;
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        NhiemvuController.nvStage.close();
    }
    @FXML
    public void nvselected(ActionEvent actionEvent) {
        NhiemVu nv =nv_cbb.getSelectionModel().getSelectedItem();
        if (nv!=null){
            initChitietNhiemvuCbb(chitietNhiemvuService.findByNhiemvuId(nv.getId()));
        }
    }
}
