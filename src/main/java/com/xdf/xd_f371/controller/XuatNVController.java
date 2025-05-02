package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.*;
import com.xdf.xd_f371.dto.AssignmentBillDto;
import com.xdf.xd_f371.dto.NhiemVuDto;
import com.xdf.xd_f371.entity.*;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.ChitietNhiemvuService;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.service.UnitXmtService;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
@Component
public class XuatNVController extends CommonFactory implements Initializable {
    private List<PhuongTien> ptls = new ArrayList<>();
    private List<NhiemVuDto> nhiemvuLs = new ArrayList<>();
    private List<UnitXmt> unitXmts = new ArrayList<>();
    @FXML
    private TextField sokm,sogio, sophut;
    @FXML
    private RadioButton md_rd, xe_rd,mb_rd,may_rd,tk_rd;
    @FXML
    private Label loai_xmt,time_active,e_giohd;
    @FXML
    private CheckBox xbnChk;
    @FXML
    private ComboBox<NguonNx> dvx_cbb;
    @FXML
    private Tooltip dinhmuc_tooltip;
    @FXML
    private ComboBox<PhuongTien> xmt_cbb;
    @FXML
    private ComboBox<NhiemVuDto> nv_cbb;
    @FXML
    private ComboBox<UnitXmt> licenceCbb;
    @FXML
    private HBox lgb_hb;

    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private ChitietNhiemvuService chitietNhiemvuService;
    @Autowired
    private UnitXmtService unitXmtService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        assignmentBillDto = new AssignmentBillDto();
        xe_rd.setSelected(true);
        md_rd.setSelected(true);
        lgb_hb.setDisable(true);
        ptls = phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.XE.getNameVehicle(),DashboardController.ref_Dv.getId());
        initXmtCbb(ptls);
        nhiemvuLs = chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_KHAC.getName());
        initChitietNhiemvu(nhiemvuLs);
        setItemNxx(dvnLs);
        initLicence();
        initDinhmucToolTip();
        initLoaiXmt();
        predictBillNumber(LoaiPhieuCons.PHIEU_XUAT.getName(), so);
        initEditLedger();
    }
    private void initEditLedger() {
        if (LedgerController.ledger_edit!=null){
            l = LedgerController.ledger_edit;
            String bid = l.getBill_id()==null ? "" : l.getBill_id();
            so.setText(bid);
            nguoinhan.setText(l.getNguoi_nhan());
            lenhso.setText(l.getLenh_so());
            nv_cbb.getSelectionModel().select(DashboardController.ctnv_ls_all.stream().filter(x->x.getCtnv_id()==l.getNhiemvu_id()).findFirst().orElse(null));
            licenceCbb.getSelectionModel().select(DashboardController.unitxmt_ls_all.stream().filter(x->x.getId().equals(l.getXmt_id())).findFirst().orElse(null));
            initDinhmucToolTip();
            xmt_cbb.getSelectionModel().select(DashboardController.xmt_ls.stream().filter(x->x.getId()==l.getPt_id()).findFirst().orElse(null));
            if (l.getLoaigiobay().equals(TypeCons.MAT_DAT.getName())){
                md_rd.setSelected(true);
                sogio.setText(l.getGiohd_md().split(":")[0]);
                sophut.setText(l.getGiohd_md().split(":")[1]);
            }else{
                tk_rd.setSelected(true);
                sogio.setText(l.getGiohd_tk().split(":")[0]);
                sophut.setText(l.getGiohd_tk().split(":")[1]);
            }
            if (l.getDvi_baono()==0){
                dvx_cbb.getSelectionModel().select(DashboardController.units_ls.stream().filter(x->x.getId()==l.getDvi_xuat_id()).findFirst().orElse(null));
                xbnChk.setSelected(false);
            }else{
                dvx_cbb.getSelectionModel().select(DashboardController.units_ls.stream().filter(x->x.getId()==l.getDvi_baono()).findFirst().orElse(null));
                xbnChk.setSelected(true);
            }
            if (l.getLpt_2().equals(LoaiPTEnum.XE.getNameVehicle())){
                xe_rd.setSelected(true);
            } else if (l.getLpt_2().equals(LoaiPTEnum.MAY.getNameVehicle())) {
                may_rd.setSelected(true);
            }else{
                mb_rd.setSelected(true);
            }
            loai_xmt.setText("Loại xe-máy-tàu: "+l.getLpt_2());
            sokm.setText(String.valueOf(l.getSo_km()));
        }
    }
    @FXML
    public void soKeyRealed(KeyEvent keyEvent) {
        so.setStyle(null);
        if (so.getText().isBlank()){
            so.setStyle(CommonFactory.styleErrorField);
        }
    }
    @FXML
    public void so_clicked(MouseEvent mouseEvent) {
        so.selectAll();
        so.setStyle(null);
    }
    @FXML
    public void lenhkhKr(KeyEvent keyEvent) {
        lenhso.setStyle(null);
        if (!lenhso.getText().isBlank()){
            if (ledgers.stream().anyMatch(l->l.getLenh_so().equals(lenhso.getText().trim()))){
                lenhso.setStyle(CommonFactory.styleErrorField);
            }
        }
    }
    @FXML
    public void lenhkhClicked(MouseEvent mouseEvent) {
        lenhso.selectAll();
    }
    @FXML
    public void mbRadioSel(ActionEvent actionEvent) {
        initXmtCbb(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAYBAY.getNameVehicle(),DashboardController.ref_Dv.getId()));
        initChitietNhiemvu(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_BAY.getName()));
        lgb_hb.setDisable(false);
    }
    @FXML
    public void xeRadioSelec(ActionEvent actionEvent) {
        initXmtCbb(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.XE.getNameVehicle(),DashboardController.ref_Dv.getId()));
        initChitietNhiemvu(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_KHAC.getName()));
        lgb_hb.setDisable(true);
        md_rd.setSelected(true);
    }
    @FXML
    public void mayRadioSelec(ActionEvent actionEvent) {
        initXmtCbb(phuongtienService.findPhuongTienByLoaiPhuongTien(LoaiPTEnum.MAY.getNameVehicle(),DashboardController.ref_Dv.getId()));
        initChitietNhiemvu(chitietNhiemvuService.findAllDtoById(LoaiNVCons.NV_KHAC.getName()));
        lgb_hb.setDisable(true);
        md_rd.setSelected(true);
    }
    @FXML
    public void so_km_clicked(MouseEvent mouseEvent) {
        cleanErrorField(sokm);
    }
    @FXML
    public void km_keyrealese(KeyEvent keyEvent) {
        validateToSettingStyle(sokm);
    }
    @FXML
    public void giohd_clicked(MouseEvent mouseEvent) {
        cleanErrorField(sogio);
    }
    @FXML
    public void phuthd_clicked(MouseEvent mouseEvent) {
        cleanErrorField(sophut);
    }
    @FXML
    public void giohd_key(KeyEvent keyEvent) {
        validateToSettingStyle(sogio);
        time_active.setText(getStrInterval());
    }
    @FXML
    public void phut_key(KeyEvent keyEvent) {
        if (!sophut.getText().isBlank()){
            if (Integer.parseInt(sophut.getText()) <60 && Integer.parseInt(sophut.getText()) >=0){
                validateToSettingStyle(sophut);
                time_active.setText(getStrInterval());
            }
        }
    }
    @FXML
    public void xmtCbbAction(ActionEvent actionEvent) {
        xmt_cbb.setStyle(null);
        initLicence();
        initLoaiXmt();
    }
    @FXML
    public void nv_cbbACation(ActionEvent actionEvent) {
        nv_cbb.setStyle(null);
    }
    @FXML
    public void licenceCbbAction(ActionEvent actionEvent) {
        initDinhmucToolTip();
    }
    protected boolean isValidField(){
        if (!lenhso.getText().isBlank()){
            if (!so.getText().isBlank()){
                return true;
            }else{
                DialogMessage.errorShowing("Cần nhập số.");
                so.setStyle(CommonFactory.styleErrorField);
            }
        }else{
            DialogMessage.errorShowing("Cần nhập lệnh.");
            lenhso.setStyle(CommonFactory.styleErrorField);
        }
        return false;
    }
    public AssignmentBillDto getInfor_valid(){
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();
        NhiemVuDto nhiemVuDto = nv_cbb.getSelectionModel().getSelectedItem();
        UnitXmt u = licenceCbb.getSelectionModel().getSelectedItem();
        if (pt!=null){
            if (nhiemVuDto!=null){
                if (u!=null){
                    if (isValidField()){
                        LoaiPhuongTien loaiPhuongTien = phuongtienService.findLptById(pt.getLoaiphuongtien_id());
                        return new AssignmentBillDto(pt,loaiPhuongTien,u,nhiemVuDto,sokm.getText().isBlank() ? 0 : Integer.parseInt(sokm.getText()),md_rd.isSelected() ? TypeCons.MAT_DAT.getName() : TypeCons.TREN_KHONG.getName(),
                                getStrInterval(),so.getText(),lenhso.getText(),nguoinhan.getText(),dvx_cbb.getSelectionModel().getSelectedItem().getId());
                    }
                }else{
                    DialogMessage.errorShowing("Dinh muc không xác định");
                    licenceCbb.setStyle(CommonFactory.styleErrorField);
                }
            }else{
                DialogMessage.errorShowing("Nhiem vu không xác định");
                nv_cbb.setStyle(CommonFactory.styleErrorField);
            }
        }else {
            DialogMessage.errorShowing("Xe May Tau không xác định");
            xmt_cbb.setStyle(CommonFactory.styleErrorField);
        }
        return null;
    }
    private void initLoaiXmt() {
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();
        if (pt!=null){
            LoaiPhuongTien lpt = phuongtienService.findLptById(pt.getLoaiphuongtien_id());
            if (lpt!=null){
                loai_xmt.setText("Loại xe-máy-tàu:"+lpt.getTypeName());
            }
        }
    }
    private void initDinhmucToolTip() {
        UnitXmt u = licenceCbb.getSelectionModel().getSelectedItem();
        if (u!=null){
            if (mb_rd.isSelected()){
                dinhmuc_tooltip.setText("-Đ.mức trên không: "+u.getDm_tk()+"(Lit/h) \n -Đ.mức mặt đất: "+u.getDm_md()+" (Lit/h)");
            }else{
                dinhmuc_tooltip.setText("-Đ.mức h.động theo giờ: "+u.getDm_hours()+"(Lit/h) \n -Đ.mức h.động theo km: "+u.getDm_km()+" (Lit/100km)");
            }
        }
    }
    private void initLicence(){
        PhuongTien pt = xmt_cbb.getSelectionModel().getSelectedItem();
        if (pt!=null){
            unitXmts = unitXmtService.findByUnitIdAndPtId(DashboardController.ref_Dv.getId(),pt.getId());
            initLicenceCbb(unitXmts);
        }
    }
    private void initLicenceCbb(List<UnitXmt> ls) {
        ComponentUtil.setItemsToComboBox(licenceCbb,ls,UnitXmt::getLicence_plate_number, input -> ls.stream().filter(x->x.getLicence_plate_number().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(licenceCbb, (typedText, itemToCompare) -> itemToCompare.getLicence_plate_number().toLowerCase().contains(typedText.toLowerCase()));
        licenceCbb.getSelectionModel().selectFirst();
    }
    private void initChitietNhiemvu(List<NhiemVuDto> ls) {
        ComponentUtil.setItemsToComboBox(nv_cbb,ls,NhiemVuDto::getChitiet, input -> ls.stream().filter(x->x.getChitiet().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(nv_cbb, (typedText, itemToCompare) -> itemToCompare.getChitiet().toLowerCase().contains(typedText.toLowerCase()));
        nv_cbb.getSelectionModel().selectFirst();
    }
    private void initXmtCbb(List<PhuongTien> phuongTiens) {
        ComponentUtil.setItemsToComboBox(xmt_cbb,phuongTiens,PhuongTien::getName, input -> phuongTiens.stream().filter(x->x.getName().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(xmt_cbb, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        xmt_cbb.getSelectionModel().selectFirst();
    }
    private String getStrInterval(){
        String hour_str = sogio.getText().trim().isBlank() ? "0" : sogio.getText();
        String m_str = sophut.getText().trim().isBlank() ? "0" : sophut.getText();

        int hour1 = getHourMinute(Integer.parseInt(m_str)).get(0);
        int minute = getHourMinute(Integer.parseInt(m_str)).get(1);
        int sum_hour = Integer.parseInt(hour_str) + hour1;

        if (minute <10){
            return sum_hour+":0"+minute+":00";
        }
        if (sum_hour<10){
            return "0"+sum_hour+":"+minute+":00";
        }
        return sum_hour+":"+minute+":00";
    }
    private List<Integer> getHourMinute(int minute){
        if (minute>=0 && minute <60){
            return List.of(0,minute);
        }
        else if (minute>=60){
            int hour = minute/60;
            int remainder_minute = minute%60;
            return List.of(hour, remainder_minute);
        }
        return List.of();
    }
    private void setItemNxx(List<NguonNx> nxx){
        ComponentUtil.setItemsToComboBox(dvx_cbb,nxx,NguonNx::getTen, input -> nxx.stream().filter(x->x.getTen().equals(input)).findFirst().orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(dvx_cbb, (typedText, itemToCompare) -> itemToCompare.getTen().toLowerCase().contains(typedText.toLowerCase()));
        dvx_cbb.getSelectionModel().selectFirst();
    }
    @FXML
    public void dvx_cbbAction(ActionEvent actionEvent) {

    }
    @FXML
    public void xbnChkAction(ActionEvent actionEvent) {
        if (xbnChk.isSelected()){
            dvx_cbb.setDisable(false);
            setItemNxx(dvvcLs);
        }else{
            dvx_cbb.setDisable(true);
            setItemNxx(dvnLs);
        }
    }
}
