package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.ChitieuDTO;
import com.xdf.xd_f371.entity.DinhMuc;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.repo.DinhMucRepo;
import com.xdf.xd_f371.repo.PhuongtienRepo;
import com.xdf.xd_f371.service.PhuongTienService;
import com.xdf.xd_f371.service.impl.PhuongTienImp;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class AddBtnPt implements Initializable {

    @FXML
    TextField pt_name, quantity,h,km,md,tk,ct_tk,ct_md,ct_km,soluong;

    private PhuongTienService phuongTienService = new PhuongTienImp();
    @Autowired
    private PhuongtienRepo phuongtienRepo;
    @Autowired
    private DinhMucRepo dinhMucRepo;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initField();
    }

    private void initField() {
        h.setText(String.valueOf(DinhMucPhuongTienController.dinhMucPhuongTienDto.getDm_xm_gio()));
        km.setText(String.valueOf(DinhMucPhuongTienController.dinhMucPhuongTienDto.getDm_xm_km()));
        md.setText(String.valueOf(DinhMucPhuongTienController.dinhMucPhuongTienDto.getDm_md_gio()));
        tk.setText(String.valueOf(DinhMucPhuongTienController.dinhMucPhuongTienDto.getDm_tk_gio()));
        ct_tk.setText("");
        ct_md.setText("2:31");
        ct_km.setText("200");
        soluong.setText("900");
        pt_name.setText(DinhMucPhuongTienController.dinhMucPhuongTienDto.getName_pt());
        quantity.setText(String.valueOf(DinhMucPhuongTienController.dinhMucPhuongTienDto.getQuantity()));
    }
    @FXML
    public void addBtn(ActionEvent actionEvent) {
        if(DialogMessage.callAlert()== ButtonType.OK){
            PhuongTien phuongTien = new PhuongTien();
            if(DinhMucPhuongTienController.dinhMucPhuongTienDto.getPhuongtien_id()==0){
                phuongTien.setName(pt_name.getText());
                phuongTien.setQuantity(Integer.parseInt(quantity.getText()));
                phuongTien.setNguonnx_id(DinhMucPhuongTienController.nguonnx_id);
                phuongTien.setStatus("ACTIVE");
                phuongTien.setLoaiphuongtien_id(DinhMucPhuongTienController.dinhMucPhuongTienDto.getLoaiphuongtien_id());
                int ptId= phuongTienService.createNew(phuongTien);

                if (phuongTienService.createNewNorm(new DinhMuc(Integer.parseInt(md.getText()), Integer.parseInt(tk.getText()), Integer.parseInt(h.getText()),Integer.parseInt(km.getText()), ptId, DashboardController.findByTime.getId())) ==1){
                    DialogMessage.callAlertWithMessage("Thông báo", "Thông báo","Thêm phương tiện thành công");
                    DinhMucPhuongTienController.norm_stage.close();
                }
            }else{
                // update phuong tien
                phuongTien.setName(pt_name.getText());
                phuongTien.setId(DinhMucPhuongTienController.dinhMucPhuongTienDto.getLoaiphuongtien_id());
                phuongTien.setQuantity(Integer.parseInt(quantity.getText()));
                phuongTien.setLoaiphuongtien_id(DinhMucPhuongTienController.dinhMucPhuongTienDto.getLoaiphuongtien_id());
                phuongtienRepo.save(phuongTien);
                // update dinhmuc
                if (phuongTienService.createNewNorm(new DinhMuc(Integer.parseInt(md.getText()),
                        Integer.parseInt(tk.getText()), Integer.parseInt(h.getText()),
                        Integer.parseInt(km.getText()), DinhMucPhuongTienController.dinhMucPhuongTienDto.getPhuongtien_id(),
                        DashboardController.findByTime.getId())) ==1){
                    DialogMessage.callAlertWithMessage("Thông báo", "Thông báo","Cập nhật phương tiện thành công");
                    DinhMucPhuongTienController.norm_stage.close();
                }
            }
        }
    }

    @FXML
    public void cancelBtn(ActionEvent actionEvent) {
        DinhMucPhuongTienController.norm_stage.close();
    }
}
