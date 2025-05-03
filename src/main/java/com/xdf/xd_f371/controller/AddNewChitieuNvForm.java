package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.LoaiNVCons;
import com.xdf.xd_f371.dto.HanmucNhiemvuTaubayDto;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.NguonNx;
import com.xdf.xd_f371.entity.NhiemvuTaubay;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.cons.LoaiPTEnum;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.HanmucNhiemvuService;
import com.xdf.xd_f371.service.NguonNxService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class AddNewChitieuNvForm implements Initializable {

    @FXML
    private ComboBox<NguonNx> dvi_cmb;
    @FXML
    private ComboBox<NhiemVuDto> nv_cmb;
    @FXML
    private ComboBox<PhuongTien> pt_cbb;
    @FXML
    private Button savebtn,cancelBtn;
    @FXML
    private TextField tk,md,nl;

    @Autowired
    private NguonNxService nguonNxService;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private HanmucNhiemvuService hanmucNhiemvuService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initChiTietNhiemVu();
        initPT();
        initNguonnxCbb();
        initField();
        Common.hoverButton(cancelBtn,"#ffffff");
        Common.hoverButton(savebtn,"#ffffff");
    }
    private void initPT() {
        ComponentUtil.setItemsToComboBox(pt_cbb,phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle(), DashboardController.ref_Dv.getId()),
                PhuongTien::getName, input->phuongtienService.findPhuongTienByName(input).orElse(null));
        pt_cbb.getSelectionModel().selectFirst();
    }
    private void initField(){
        HanmucNhiemvuTaubayDto hm  = NhiemvuController.hm;
        if (hm==null){
            tk.setText("00:00");
            md.setText("00:00");
            nl.setText("0");
        }else{
            pt_cbb.setDisable(true);
            dvi_cmb.setDisable(true);
            nv_cmb.setDisable(true);
            tk.setText(hm.getTk());
            md.setText(hm.getMd());
            nl.setText(String.valueOf(hm.getNhienlieu()));
            dvi_cmb.getSelectionModel().select(nguonNxService.findByTen(hm.getDonvi()).orElse(null));
            pt_cbb.getSelectionModel().select(phuongtienService.findById(hm.getPt_id()).orElse(null));
            nv_cmb.getSelectionModel().select(chitietNhiemvuService.findByTenNhiemvuDto(hm.getCt_nhiemvu()).orElse(null));
        }
    }
    private void initNguonnxCbb() {
        PhuongTien pt = pt_cbb.getSelectionModel().getSelectedItem();
        if (pt!=null){
            List<NguonNx> nguonNx = hanmucNhiemvuService.getAllDviTructhuocByTaubay(pt.getId(),LocalDate.now().getYear());
            ComponentUtil.setItemsToComboBox(dvi_cmb,nguonNx,NguonNx::getTen,input->nguonNxService.findByTen(input).orElse(null));
            dvi_cmb.getSelectionModel().selectFirst();
        }
    }
    private void initChiTietNhiemVu() {
        ComponentUtil.setItemsToComboBox(nv_cmb,chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName()),NhiemVuDto::getChitiet,
                input->chitietNhiemvuService.findByTenNhiemvuDto(input).orElse(null));
        nv_cmb.getSelectionModel().selectFirst();
    }
    @FXML
    public void savechitieu(ActionEvent actionEvent) {
        NguonNx n = dvi_cmb.getSelectionModel().getSelectedItem();
        NhiemVuDto ct  = nv_cmb.getSelectionModel().getSelectedItem();
        PhuongTien p = pt_cbb.getSelectionModel().getSelectedItem();
        if (n!=null && ct!=null && p!=null){
            HanmucNhiemvuTaubayDto hm = NhiemvuController.hm;
            Optional<HanmucNhiemvuTaubayDto> unique = hanmucNhiemvuService.findHmUnique(LocalDate.now().getYear(), p.getId(),ct.getCtnv_id(),n.getId());
            if (unique.isPresent()){
                hanmucNhiemvuService.save(new NhiemvuTaubay(unique.get().getNvtb_id(),n.getId(),p.getId(),ct.getCtnv_id(),
                        tk.getText(),md.getText(),Long.parseLong(nl.getText())));
            }else{
                if (hm==null){
                    hanmucNhiemvuService.save(new NhiemvuTaubay(n.getId(),p.getId(),ct.getCtnv_id(),tk.getText(),md.getText(),Long.parseLong(nl.getText())));
                    NhiemvuController.nvStage.close();
                }else{
                    hanmucNhiemvuService.save(new NhiemvuTaubay(hm.getNvtb_id(),n.getId(),p.getId(),ct.getCtnv_id(),tk.getText(),md.getText(),Long.parseLong(nl.getText())));
                }
            }
            DialogMessage.successShowing("Luu thanh cong");
            NhiemvuController.nvStage.close();
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        NhiemvuController.nvStage.close();
    }
    @FXML
    public void ptActon(ActionEvent actionEvent) {
        initNguonnxCbb();
    }
}
