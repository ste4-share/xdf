package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.MessageCons;
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
    TextField pt_name, quantity,h,km,md,tk,pt_id;
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
        if (Common.loaipt!=null){
            cbb_loai.getSelectionModel().select(Common.loaipt);
        }else{
            cbb_loai.getSelectionModel().selectFirst();
        }
    }
    private void initField() {
        DinhMucPhuongTienDto dm = DinhMucPhuongTienController.dinhMucPhuongTienDto;
        initNnx();
        if (dm.getPhuongtien_id()!=0){
            pt_id.setText(String.valueOf(dm.getPhuongtien_id()));
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
    private boolean savePtDm(DinhMucPhuongTienDto dm){
        try {
            if (isvalidField()){
                dm.setName_pt(pt_name.getText());
                dm.setQuantity(Double.parseDouble(quantity.getText()));
                dm.setDm_xm_gio(Double.parseDouble(h.getText()));
                dm.setDm_xm_km(Double.parseDouble(km.getText()));
                dm.setDm_md_gio(Double.parseDouble(md.getText()));
                dm.setDm_tk_gio(Double.parseDouble(tk.getText()));
                dm.setLoaiphuongtien_id(cbb_loai.getSelectionModel().getSelectedItem().getId());
                phuongtienService.savePt_DM(Integer.parseInt(pt_id.getText()),dm,dvi_cbb.getSelectionModel().getSelectedItem().getId());
                return true;
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return false;
    }
    private boolean isvalidField(){
        if (!pt_id.getText().trim().isEmpty()){
            try {
                Integer i = Integer.parseInt(pt_id.getText());
                return true;
            } catch (NumberFormatException e) {
                pt_id.setStyle(CommonFactory.styleErrorField);
                DialogMessage.errorShowing("ID không để trống hoặc chứa ký tự, vui lòng thử lại sau.");
            }
        }
        return false;
    }
    @FXML
    public void addBtn(ActionEvent actionEvent) {
        DinhMucPhuongTienDto dm = DinhMucPhuongTienController.dinhMucPhuongTienDto;
        if(DialogMessage.callAlert()== ButtonType.OK){
            if (error_lb.getText()==null){
                if (savePtDm(dm)) {
                    DialogMessage.callAlertWithMessage(null, "Thông báo", "Thêm phương tiện thành công", Alert.AlertType.CONFIRMATION);
                    DinhMucPhuongTienController.norm_stage.close();
                } else {
                    DialogMessage.callAlertWithMessage(null, null, MessageCons.CO_LOI_XAY_RA.getName(), Alert.AlertType.WARNING);
                }
            }else{
                DialogMessage.callAlertWithMessage(null, null, MessageCons.CO_LOI_XAY_RA.getName(), Alert.AlertType.WARNING);
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
    @FXML
    public void tenpt_kr(KeyEvent keyEvent) {
        String t = pt_name.getText();
        if (t!=null){
            if (!t.isEmpty()){
                if (DinhMucPhuongTienController.ls.stream().anyMatch(x->x.getName_pt().toLowerCase().equals(t.toLowerCase()))){
                    pt_name.setStyle(CommonFactory.styleErrorField);
                    error_lb.setText("Tên phương tiện đã tồn tại.");
                }else{
                    pt_name.setStyle(null);
                    error_lb.setText(null);
                }
            }
        }
    }
    @FXML
    public void cbb_loaiAction(ActionEvent actionEvent) {
         LoaiPhuongTien lpt = cbb_loai.getSelectionModel().getSelectedItem();
         if (lpt!=null){
             Common.loaipt = lpt;
         }
    }
    @FXML
    public void idClicked(MouseEvent mouseEvent) {
        pt_id.selectAll();
        pt_id.setStyle(null);
    }
    @FXML
    public void xmtClicked(MouseEvent mouseEvent) {
        pt_name.selectAll();
        pt_name.setStyle(null);
    }
}
