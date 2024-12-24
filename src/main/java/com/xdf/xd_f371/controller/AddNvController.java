package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.entity.LoaiNhiemVu;
import com.xdf.xd_f371.entity.Team;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
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
    private TextField lnv_tf,nv,ct;
    @FXML
    private Button add_btn,cancel_btn;
    @Autowired
    private ChitietNhiemvuService nhiemvuService;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initTeam();
        initLoaiNv();
    }
    private void initLoaiNv() {
        Common.setHint(lnv_tf,nhiemvuService.getAllLoaiNv().stream().map(LoaiNhiemVu::getTask_name).toList());
    }
    private void initTeam() {
        ComponentUtil.setItemsToComboBox(cbb_team,nhiemvuService.findAllTeam(),Team::getTeam_code,input-> nhiemvuService.findByCode(input));
        cbb_team.getSelectionModel().selectFirst();
    }
    @FXML
    public void teamAction(ActionEvent actionEvent) {
    }
    @FXML
    public void lnv_clicked(MouseEvent mouseEvent) {
        lnv_tf.selectAll();
    }
    @FXML
    public void nv_clicked(MouseEvent mouseEvent) {
        nv.selectAll();
    }
    @FXML
    public void ct_clicked(MouseEvent mouseEvent) {
        ct.selectAll();
    }
    @FXML
    public void addAction(ActionEvent actionEvent) {
        if (DialogMessage.callAlertWithMessage(null, "Tạo mới Nhiemvu", "Xác nhận tạo mới", Alert.AlertType.CONFIRMATION) == ButtonType.OK) {
            nhiemvuService.saveNhiemvu(cbb_team.getSelectionModel().getSelectedItem().getId(),nv.getText(),lnv_tf.getText(),ct.getText());
            DialogMessage.message(null, "Them thanh cong",
                    "Thanh cong", Alert.AlertType.INFORMATION);
            NhiemvuController.nvStage.close();
        }
    }
    @FXML
    public void cancelAction(ActionEvent actionEvent) {
        NhiemvuController.nvStage.close();
    }
}
