package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.dto.DinhMucPhuongTienDto;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.service.DinhmucService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private Label nnx_lb;
    @FXML
    private ComboBox<LoaiPhuongTien> cbb_loai;
    @FXML
    private ComboBox<NguonNx> cbb_dvi;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private DinhmucService dinhmucService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initField();
        Common.hoverButton(saveBtn, "#ffffff");
        Common.hoverButton(cancelBtn, "#ffffff");
    }

    private void initField() {
        DinhMucPhuongTienDto dm = DinhMucPhuongTienController.dinhMucPhuongTienDto;
        h.setText(String.valueOf(dm.getDm_xm_gio()));
        km.setText(String.valueOf(dm.getDm_xm_km()));
        md.setText(String.valueOf(dm.getDm_md_gio()));
        tk.setText(String.valueOf(dm.getDm_tk_gio()));
        pt_name.setText(dm.getName_pt());
        quantity.setText(String.valueOf(dm.getQuantity()));
    }
    @FXML
    public void addBtn(ActionEvent actionEvent) {
        if(DialogMessage.callAlert()== ButtonType.OK){
            DinhMucPhuongTienDto dm = DinhMucPhuongTienController.dinhMucPhuongTienDto;
            dm.setName(pt_name.getText());
            dm.setQuantity(Integer.parseInt(quantity.getText()));
            dm.setDm_xm_gio(Integer.parseInt(h.getText()));
            dm.setDm_xm_km(Integer.parseInt(km.getText()));
            dm.setDm_md_gio(Integer.parseInt(md.getText()));
            dm.setDm_tk_gio(Integer.parseInt(tk.getText()));
            dm.setLoaiphuongtien_id(cbb_loai.getSelectionModel().getSelectedItem().getId());
            phuongtienService.savePt_DM(dm,dm.getNnx_id());
            DialogMessage.callAlertWithMessage("Thông báo", "Thông báo", "Thêm phương tiện thành công", Alert.AlertType.CONFIRMATION);
            DinhMucPhuongTienController.norm_stage.close();
        }
    }

    @FXML
    public void cancelBtn(ActionEvent actionEvent) {
        DinhMucPhuongTienController.norm_stage.close();
    }
}
