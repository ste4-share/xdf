package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.HanmucNhiemvu2Dto;
import com.xdf.xd_f371.entity.ChitietNhiemVu;
import com.xdf.xd_f371.entity.HanmucNhiemvu2;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
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
import java.time.LocalDate;
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
    TextField xang_tf,diezel_tf,daubay_tf,hacap_tf;

    @Autowired
    ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    HanmucNhiemvuService hanmucNhiemvuService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initNhiemvuCbb();
        initChitietNhiemvuCbb(chitietNhiemvuService.findAllCtnv());
        initField();
    }
    private void initField(){
        HanmucNhiemvu2Dto hm = NhiemvuController.hm2;
        if (hm!=null){
            xang_tf.setText(String.format("%.0f", hm.getXang()));
            diezel_tf.setText(String.format("%.0f", hm.getDiezel()));
            daubay_tf.setText(String.format("%.0f", hm.getDaubay()));
            hacap_tf.setText(String.format("%.0f", hm.getHacap()));
            Optional<NhiemVu> nv = chitietNhiemvuService.findByName(hm.getTenNv(),StatusCons.ACTIVED.getName());
            nv.ifPresent(nhiemVu -> nv_cbb.getSelectionModel().select(nhiemVu));
            if (nv.isPresent()) {
                List<ChitietNhiemVu> ct = DashboardController.ctnv_ls_all_.stream().filter(x-> x.getNhiemvu_id()==nv.get().getId()).toList();
                initChitietNhiemvuCbb(ct);
            }
        }
    }

    private void initNhiemvuCbb(){
        ComponentUtil.setItemsToComboBox(nv_cbb,DashboardController.nv_ls,NhiemVu::getTenNv,
                input->DashboardController.nv_ls.stream().filter(x-> x.getTenNv().equals(input)).findFirst().orElse(null));
        nv_cbb.getSelectionModel().selectFirst();
    }
    private void initChitietNhiemvuCbb(List<ChitietNhiemVu> ls){
        NhiemVu nv = nv_cbb.getSelectionModel().getSelectedItem();
        if (nv!=null){
            ComponentUtil.setItemsToComboBox(ct_cbb,ls,ChitietNhiemVu::getNhiemvu,input->DashboardController.ctnv_ls_all_.stream().filter(x-> x.getNhiemvu().equals(input)).findFirst().orElse(null));
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
            ChitietNhiemVu ct = ct_cbb.getSelectionModel().getSelectedItem();
            if (ct!=null){
                if (isDetectAss(hm.getNhiemvu_id(),ct)){
                    saveXdB(hm,ct);
                    hanmucNhiemvuService.save(new HanmucNhiemvu2(hm));
                } else {
                    Optional<HanmucNhiemvu2> exitst = hanmucNhiemvuService.findByUnique(hm.getYears(),ct.getId());
                    saveXdB(hm,ct);
                    if (exitst.isPresent()) {
                        hm.setId(exitst.get().getId());
                        hanmucNhiemvuService.save(new HanmucNhiemvu2(hm));
                    } else {
                        hanmucNhiemvuService.save(new HanmucNhiemvu2(hm.getDvi_id(),hm.getNhiemvu_id(), hm.getDiezel(), hm.getDaubay(), hm.getXang(),hm.getHacap()));
                    }
                }
            }
            NhiemvuController.nvStage.close();
        } catch (Exception e) {
            DialogMessage.errorShowing(e.getMessage());
        }
    }
    private void saveXdB(HanmucNhiemvu2Dto hm,ChitietNhiemVu ct){
        hm.setNhiemvu_id(ct.getId());
        hm.setXang(Double.parseDouble(xang_tf.getText()));
        hm.setDiezel(Double.parseDouble(diezel_tf.getText()));
        hm.setDaubay(Double.parseDouble(daubay_tf.getText()));
        hm.setHacap(Double.parseDouble(hacap_tf.getText()));
    }
    private boolean isDetectAss(int ctnv_id,ChitietNhiemVu ct){

        if (ct!=null){
            if (ct.getId()==ctnv_id){
                return true;
            }
        }
        return false;
    }
    private boolean isvalid(){
        if (Common.isLongNumber(hacap_tf.getText()) && Common.isLongNumber(xang_tf.getText()) && Common.isLongNumber(daubay_tf.getText()) && Common.isLongNumber(diezel_tf.getText())){
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
    @FXML
    public void ctAction(ActionEvent actionEvent) {
        ChitietNhiemVu ct = ct_cbb.getSelectionModel().getSelectedItem();
        HanmucNhiemvu2Dto hm = NhiemvuController.hm2;
        if (ct!=null ){
            if (hm!=null){
                Optional<HanmucNhiemvu2> exitst = hanmucNhiemvuService.findByUnique(hm.getYears(),ct.getId());
                if (exitst.isPresent()){
                    setVal(exitst.get());
                }else {
                    resetF();
                }
            }else{
                Optional<HanmucNhiemvu2> exitst = hanmucNhiemvuService.findByUnique(LocalDate.now().getYear(),ct.getId());
                if (exitst.isPresent()){
                    setVal(exitst.get());
                }else {
                    resetF();
                }
            }
        }
    }
    private void resetF(){
        xang_tf.setText("0");
        diezel_tf.setText("0");
        daubay_tf.setText("0");
        hacap_tf.setText("0");
    }
    private void setVal(HanmucNhiemvu2 hm){
        xang_tf.setText(String.valueOf(hm.getXang()));
        diezel_tf.setText(String.valueOf(hm.getDiezel()));
        daubay_tf.setText(String.valueOf(hm.getDaubay()));
    }
}
