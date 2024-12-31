package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddBtnPt implements Initializable {

    @FXML
    TextField pt_name, quantity,h,km,md,tk;
    @FXML
    Button cancelBtn, saveBtn;
    @FXML
    private Label error_lb;
    @FXML
    private ComboBox<LoaiPhuongTien> cbb_loai;
    @FXML
    private ComboBox<NguonNx> dvi_cbb;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private NguonNxService nguonNxService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_lb.setText(null);
        initLpt();
        initField();
        Common.hoverButton(saveBtn, "#ffffff");
        Common.hoverButton(cancelBtn, "#ffffff");
    }
    private void initNnx() {
        ComponentUtil.setItemsToComboBox(dvi_cbb,nguonNxService.findByAllBy(),NguonNx::getTen,input->nguonNxService.findByTen(input).orElse(null));
        dvi_cbb.getSelectionModel().selectFirst();
    }
    private void initLpt() {
        ComponentUtil.setItemsToComboBox(cbb_loai,phuongtienService.findAllLpt(),LoaiPhuongTien::getTypeName,input->phuongtienService.findLptByName(input));
        cbb_loai.getSelectionModel().selectFirst();
    }
    private void initField() {
        DinhMucPhuongTienDto dm = DinhMucPhuongTienController.dinhMucPhuongTienDto;
        if (dm.getPhuongtien_id()==0){
            initNnx();
        }else {
            initNnx();
            cbb_loai.getSelectionModel().select(phuongtienService.findLptByName(dm.getTypeName()));
            dvi_cbb.getSelectionModel().select(nguonNxService.findByTen(dm.getNameDv()).orElse(null));
        }
        h.setText(String.valueOf(dm.getDm_xm_gio()));
        km.setText(String.valueOf(dm.getDm_xm_km()));
        md.setText(String.valueOf(dm.getDm_md_gio()));
        tk.setText(String.valueOf(dm.getDm_tk_gio()));
        pt_name.setText(dm.getName_pt());
        quantity.setText(String.valueOf(dm.getQuantity()));
    }
    private void isValid(TextField tf){
        if (Common.isNumber(tf.getText().trim())){
            tf.setStyle(null);
            error_lb.setText(null);
        }else {
            tf.setStyle(CommonFactory.styleErrorField);
            error_lb.setText("---Sai định dạng---");
        }
    }
    private void savePtDm(DinhMucPhuongTienDto dm){
        dm.setName_pt(pt_name.getText());
        dm.setQuantity(Integer.parseInt(quantity.getText()));
        dm.setDm_xm_gio(Integer.parseInt(h.getText()));
        dm.setDm_xm_km(Integer.parseInt(km.getText()));
        dm.setDm_md_gio(Integer.parseInt(md.getText()));
        dm.setDm_tk_gio(Integer.parseInt(tk.getText()));
        dm.setLoaiphuongtien_id(cbb_loai.getSelectionModel().getSelectedItem().getId());
        phuongtienService.savePt_DM(dm,dvi_cbb.getSelectionModel().getSelectedItem().getId());
    }
    @FXML
    public void addBtn(ActionEvent actionEvent) {
        DinhMucPhuongTienDto dm = DinhMucPhuongTienController.dinhMucPhuongTienDto;
        if(DialogMessage.callAlert()== ButtonType.OK){
            if (error_lb.getText()==null){
                savePtDm(dm);
                DialogMessage.callAlertWithMessage(null, "Thông báo", "Thêm phương tiện thành công", Alert.AlertType.CONFIRMATION);
                DinhMucPhuongTienController.norm_stage.close();
            }else{
                DialogMessage.callAlertWithMessage(null, null, "Something stills wrong!", Alert.AlertType.WARNING);
            }
        }
    }

    @FXML
    public void cancelBtn(ActionEvent actionEvent) {
        DinhMucPhuongTienController.norm_stage.close();
    }
    @FXML
    public void ms_sl(MouseEvent mouseEvent) {
        quantity.selectAll();
        tk.setStyle(null);
    }
    @FXML
    public void kr_sl(KeyEvent keyEvent) {
        isValid(quantity);
    }
    @FXML
    public void ms_gio(MouseEvent mouseEvent) {
        h.selectAll();
        tk.setStyle(null);
    }
    @FXML
    public void kr_gio(KeyEvent keyEvent) {
        isValid(h);
    }
    @FXML
    public void ms_km(MouseEvent mouseEvent) {
        km.selectAll();
        tk.setStyle(null);
    }
    @FXML
    public void kr_km(KeyEvent keyEvent) {
        isValid(km);
    }
    @FXML
    public void ms_md(MouseEvent mouseEvent) {
        md.selectAll();
        tk.setStyle(null);
    }
    @FXML
    public void kr_md(KeyEvent keyEvent) {
        isValid(md);
    }
    @FXML
    public void ms_tk(MouseEvent mouseEvent) {
        tk.selectAll();
        tk.setStyle(null);
    }
    @FXML
    public void kr_tk(KeyEvent keyEvent) {
        isValid(tk);
    }
}
