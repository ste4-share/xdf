package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.dto.ChitieuDTO;
import com.xdf.xd_f371.dto.StatusActive;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.Norm;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.service.PhuongTienService;
import com.xdf.xd_f371.service.impl.PhuongTienImp;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class AddBtnPt implements Initializable {

    private static int lpt_id=0;

    @FXML
    TextField pt_name, quantity,h,km,md,tk,ct_tk,ct_md,ct_km,soluong;
    @FXML
    ComboBox<LoaiPhuongTien> lpt_cmb;
    @FXML
    ComboBox<StatusActive> status_cbb;

    private PhuongTienService phuongTienService = new PhuongTienImp();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initLoaiPt();
        initStatus();
        initField();
    }

    private void initField() {
        h.setText(String.valueOf(NormController.normDto.getDm_xm_gio()));
        km.setText(String.valueOf(NormController.normDto.getDm_xm_km()));
        md.setText(String.valueOf(NormController.normDto.getDm_md_gio()));
        tk.setText(String.valueOf(NormController.normDto.getDm_tk_gio()));
        ct_tk.setText("");
        ct_md.setText("2:31");
        ct_km.setText("200");
        soluong.setText("900");
        pt_name.setText(NormController.normDto.getNamePt());
        quantity.setText(String.valueOf(NormController.normDto.getQuantity()));
        status_cbb.getSelectionModel().select(phuongTienService.findStatusByName(NormController.normDto.getStatus()));
        lpt_cmb.getSelectionModel().select(phuongTienService.findPtById(NormController.normDto.getLoaiphuongtien_id()));
    }

    private void initStatus() {
        status_cbb.setItems(FXCollections.observableList(phuongTienService.getAllStatus()));
        status_cbb.setConverter(new StringConverter<StatusActive>() {
            @Override
            public String toString(StatusActive statusActive) {
                return statusActive==null ? ""  : statusActive.getStatusName();
            }

            @Override
            public StatusActive fromString(String s) {
                return phuongTienService.findStatusByName(s);
            }
        });
        status_cbb.getSelectionModel().selectFirst();
    }

    private void initLoaiPt() {
        lpt_cmb.setItems(FXCollections.observableList(phuongTienService.getAllLoaiPt()));
        lpt_cmb.setConverter(new StringConverter<LoaiPhuongTien>() {
            @Override
            public String toString(LoaiPhuongTien loaiPhuongTien) {
                if (loaiPhuongTien!=null){
                    lpt_id=loaiPhuongTien.getId();
                }
                return loaiPhuongTien==null ? "" : loaiPhuongTien.getTypeName();
            }

            @Override
            public LoaiPhuongTien fromString(String s) {
                return phuongTienService.findPtById(lpt_id);
            }
        });
        lpt_cmb.getSelectionModel().selectFirst();
    }

    @FXML
    public void addBtn(ActionEvent actionEvent) {
        if(DialogMessage.callAlert()== ButtonType.OK){
            PhuongTien phuongTien = new PhuongTien();
            if(NormController.normDto.getPt_id()==0){
                phuongTien.setName(pt_name.getText());
                phuongTien.setQuantity(Integer.parseInt(quantity.getText()));
                phuongTien.setNguonnx_id(NormController.nguonnx_id);
                phuongTien.setStatus(status_cbb.getValue().getStatusName());
                phuongTien.setLoaiphuongtien_id(lpt_cmb.getValue().getId());
                int ptId= phuongTienService.createNew(phuongTien);

                createNewChiTieu(ptId);
                if (phuongTienService.createNewNorm(new Norm(Integer.parseInt(md.getText()), Integer.parseInt(tk.getText()), Integer.parseInt(h.getText()),Integer.parseInt(km.getText()), ptId, DashboardController.findByTime.getId())) ==1){
                    DialogMessage.callAlertWithMessage("Thông báo", "Thông báo","Thêm phương tiện thành công");
                    NormController.norm_stage.close();
                }
            }else{
                // update phuong tien
                phuongTien.setName(pt_name.getText());
                phuongTien.setId(NormController.normDto.getPt_id());
                phuongTien.setQuantity(Integer.parseInt(quantity.getText()));
                phuongTien.setStatus(status_cbb.getValue().getStatusName());
                phuongTien.setLoaiphuongtien_id(lpt_cmb.getValue().getId());
                phuongTienService.updateNew(phuongTien);
                // update dinhmuc
                createNewChiTieu(NormController.normDto.getPt_id());
                if (phuongTienService.createNewNorm(new Norm(Integer.parseInt(md.getText()), Integer.parseInt(tk.getText()), Integer.parseInt(h.getText()),Integer.parseInt(km.getText()), NormController.normDto.getPt_id(), DashboardController.findByTime.getId())) ==1){
                    DialogMessage.callAlertWithMessage("Thông báo", "Thông báo","Cập nhật phương tiện thành công");
                    NormController.norm_stage.close();
                }
            }
        }
    }

    private void createNewChiTieu(int ptId){
        ChitieuDTO chitieuDTO = new ChitieuDTO();
        chitieuDTO.setQuarter_id(DashboardController.findByTime.getId());
        chitieuDTO.setPhuongtien_id(ptId);
        chitieuDTO.setSoluong(Integer.parseInt(soluong.getText()));
        chitieuDTO.setHanmuc_km(Integer.parseInt(ct_km.getText()));
        chitieuDTO.setHanmuc_md(ct_md.getText());
        chitieuDTO.setHanmuc_tk(ct_tk.getText());
        phuongTienService.createNewChiTieu(chitieuDTO);
    }
    @FXML
    public void cancelBtn(ActionEvent actionEvent) {
        NormController.norm_stage.close();
    }
}
