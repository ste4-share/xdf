package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class AddNvController implements Initializable {
    public static Stage primaryState;
    @FXML
    private ComboBox<Team> cbb_team;
    @FXML
    private ComboBox<LoaiNhiemVu> cbb_lnv;
    @FXML
    private TextField ct,xang,diezel,daubay,id,hacap;
    @FXML
    private Button add_btn,cancel_btn;
    @FXML
    private ComboBox<NhiemVu> nv_cbb;

    @Autowired
    private ChitietNhiemvuService nhiemvuService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initVar();
        initTeam();
        initLoaiNvCbb();
        initNvCbb();
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

    private void initNvCbb() {
        ComponentUtil.setItemsToComboBox(nv_cbb,DashboardController.nv_ls,NhiemVu::getTenNv,input-> DashboardController.nv_ls.stream().filter(x-> x.getTenNv().equals(input)).findFirst().orElse(null));
        nv_cbb.getSelectionModel().selectFirst();
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
                    if (nv_cbb.getSelectionModel().getSelectedItem()!=null){
                        nhiemvuService.saveNhiemvu(cbb_team.getSelectionModel().getSelectedItem().getTeam_code(),Integer.parseInt(id.getText()),t.getId(),nv_cbb.getSelectionModel().getSelectedItem().getTenNv(),lnv,ct.getText(),nx,xang.getText(),diezel.getText(),daubay.getText(),hacap.getText());
                    }else{
                        DialogMessage.errorShowing(MessageCons.CO_LOI_XAY_RA.getName());
                    }
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
    @FXML
    public void nv_cbbAct(ActionEvent actionEvent) {}
    @FXML
    public void add_nvAction(ActionEvent actionEvent) {
        primaryState = new Stage();
        primaryState.initStyle(StageStyle.UTILITY);
        Common.openNewStage("addnewnv.fxml", primaryState,null, StageStyle.DECORATED);
    }
}
