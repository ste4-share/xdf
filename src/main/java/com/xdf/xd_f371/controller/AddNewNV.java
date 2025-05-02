package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.LoaiNhiemVu;
import com.xdf.xd_f371.entity.NhiemVu;
import com.xdf.xd_f371.entity.Team;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddNewNV implements Initializable {
    @FXML
    private TextField nv_tf;
    @FXML
    private ComboBox<LoaiNhiemVu> type_cbb;
    @FXML
    private ComboBox<Team> team_cbb;
    @Autowired
    private ChitietNhiemvuService nhiemvuService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initLoaiNvCbb();
        initTeam();
    }
    private void initLoaiNvCbb() {
        ComponentUtil.setItemsToComboBox(type_cbb,nhiemvuService.getAllLoaiNv(), LoaiNhiemVu::getTask_name, input-> nhiemvuService.findLoaiNvByName(input).orElse(null));
        type_cbb.getSelectionModel().selectFirst();
    }
    private void initTeam() {
        ComponentUtil.setItemsToComboBox(team_cbb,nhiemvuService.findAllTeam(), Team::getTeam_code, input-> nhiemvuService.findByCode(input));
        team_cbb.getSelectionModel().selectFirst();
    }
    @FXML
    public void saveAction(ActionEvent actionEvent) {
        if (!nv_tf.getText().isBlank()){
            nhiemvuService.savenv(new NhiemVu(nv_tf.getText(), StatusCons.ACTIVED.getName(),team_cbb.getSelectionModel().getSelectedItem().getId()
                    ,type_cbb.getSelectionModel().getSelectedItem().getId(),99,99,type_cbb.getSelectionModel().getSelectedItem().getTask_name(),
                    team_cbb.getSelectionModel().getSelectedItem().getTeam_code()));
            DialogMessage.successShowing(MessageCons.THANH_CONG.getName());
            AddNvController.primaryState.close();
        }else{
            nv_tf.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing(MessageCons.LOI.getName());
        }
    }
    @FXML
    public void exitAction(ActionEvent actionEvent) {
        AddNvController.primaryState.close();
    }
    @FXML
    public void nvmclik(MouseEvent mouseEvent) {
        nv_tf.setStyle(null);
    }
}
