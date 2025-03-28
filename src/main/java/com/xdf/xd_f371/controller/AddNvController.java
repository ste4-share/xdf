package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiNVCons;
import com.xdf.xd_f371.entity.LoaiNhiemVu;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.entity.Team;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.repo.HanmucNhiemvuTauBayRepo;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class AddNvController implements Initializable {
    @FXML
    private ComboBox<Team> cbb_team;
    @FXML
    private ComboBox<LoaiNhiemVu> cbb_lnv;
    @FXML
    private TextField nv,ct,xang,diezel,daubay,id,hacap;
    @FXML
    private Button add_btn,cancel_btn;
    @Autowired
    private ChitietNhiemvuService nhiemvuService;
    @Autowired
    private HanmucNhiemvuService hanmucNhiemvuService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initVar();
        initTeam();
        initLoaiNvCbb();
    }
    private void initVar(){
        xang.setText("0");
        diezel.setText("0");
        daubay.setText("0");
        hacap.setText("0");
    }
    private boolean isValid(){
        if (!Common.isNumber(xang.getText())){
            xang.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing("Xăng không chứa ký tự.");
            return false;
        }else if (!Common.isNumber(daubay.getText())){
            daubay.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing("Dầu bay không chứa ký tự.");
            return false;
        }else if (!Common.isNumber(id.getText())){
            id.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing("Id không chứa ký tự.");
            return false;
        }else if (!Common.isNumber(diezel.getText())){
            diezel.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing("Diezel không chứa ký tự.");
            return false;
        }else if (!Common.isNumber(hacap.getText())){
            hacap.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing("Hacap không chứa ký tự.");
            return false;
        }
        xang.setStyle(null);
        daubay.setStyle(null);
        diezel.setStyle(null);
        hacap.setStyle(null);
        return true;
    }
    private void initLoaiNvCbb() {
        ComponentUtil.setItemsToComboBox(cbb_lnv,nhiemvuService.getAllLoaiNv(),LoaiNhiemVu::getTask_name,input-> nhiemvuService.findLoaiNvByName(input).orElse(null));
        cbb_lnv.getSelectionModel().selectFirst();
    }
    private void initTeam() {
        ComponentUtil.setItemsToComboBox(cbb_team,nhiemvuService.findAllTeam(),Team::getTeam_code,input-> nhiemvuService.findByCode(input));
        cbb_team.getSelectionModel().selectFirst();
    }
    @FXML
    public void teamAction(ActionEvent actionEvent) {
    }
    @FXML
    public void nv_clicked(MouseEvent mouseEvent) {
        nv.setStyle(null);
        nv.selectAll();
    }
    @FXML
    public void ct_clicked(MouseEvent mouseEvent) {
        ct.selectAll();
        ct.setStyle(null);
    }
    @FXML
    public void addAction(ActionEvent actionEvent) {
        LoaiNhiemVu lnv = cbb_lnv.getSelectionModel().getSelectedItem();
        Team t = cbb_team.getSelectionModel().getSelectedItem();
        NguonNx nx = DashboardController.ref_Dv;
        if (lnv!=null && t!=null && nx!=null){
            if (DialogMessage.callAlertWithMessage(null, "Tạo mới Nhiemvu", "Xác nhận tạo mới", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
                if (isValid()){
                    nhiemvuService.saveNhiemvu(Integer.parseInt(id.getText()),t.getId(),nv.getText(),lnv,ct.getText(),nx,xang.getText(),diezel.getText(),daubay.getText(),hacap.getText());
                    NhiemvuController.nvStage.close();
                }
            }
        }
    }

    @FXML
    public void cancelAction(ActionEvent actionEvent) {
        NhiemvuController.nvStage.close();
    }
    @FXML
    public void lnvAction(ActionEvent actionEvent) {
    }
    @FXML
    public void id_clicked(MouseEvent mouseEvent) {
        id.selectAll();
        id.setStyle(null);
    }
}
