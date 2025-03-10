package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.entity.PhuongTien;
import com.xdf.xd_f371.entity.UnitXmt;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.PhuongtienService;
import com.xdf.xd_f371.service.UnitXmtService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.FxUtilTest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

@Component
public class AddBtnPt implements Initializable {
    private List<UnitXmt> unitXmtList = new ArrayList<>();

    @FXML
    private TextField h,km,md,tk,pt_id;
    @FXML
    private TextArea note;
    @FXML
    private Button cancelBtn, saveBtn;
    @FXML
    private Label error_lb;
    @FXML
    private ComboBox<LoaiPhuongTien> cbb_loai;
    @FXML
    private ComboBox<UnitXmt> license_plate_number;
    @FXML
    private CheckBox addNewChk;
    @FXML
    private ComboBox<PhuongTien> cbb_xmt;
    @Autowired
    private PhuongtienService phuongtienService;
    @Autowired
    private UnitXmtService unitXmtService;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        error_lb.setText(null);
        initPt(phuongtienService.findAll());
        initLpt();
        actionXmt();
        initLicensePlateCbb(unitXmtList);
        Common.hoverButton(saveBtn, "#ffffff");
        Common.hoverButton(cancelBtn, "#ffffff");
    }
    private void initPt(List<PhuongTien> ls) {
        ComponentUtil.setItemsToComboBox(cbb_xmt,ls,PhuongTien::getName,input->phuongtienService.findPhuongTienByName(input).orElse(null));
        FxUtilTest.autoCompleteComboBoxPlus(cbb_xmt, (typedText, itemToCompare) -> itemToCompare.getName().toLowerCase().contains(typedText.toLowerCase()));
        cbb_xmt.getSelectionModel().selectFirst();
    }
    private void initLicensePlateCbb(List<UnitXmt> ls) {
        ComponentUtil.setItemsToComboBox(license_plate_number,ls,UnitXmt::getLicence_plate_number,input->unitXmtService.findByLicensePlate(input));
        license_plate_number.getSelectionModel().selectFirst();
    }
    private void initLpt() {
        ComponentUtil.setItemsToComboBox(cbb_loai,phuongtienService.findAllLpt(),LoaiPhuongTien::getTypeName,input->phuongtienService.findLptByName(input));
        if (Common.loaipt!=null){
            cbb_loai.getSelectionModel().select(Common.loaipt);
        }else{
            cbb_loai.getSelectionModel().selectFirst();
        }
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
    @FXML
    public void addBtn(ActionEvent actionEvent) {
        PhuongTien pt = cbb_xmt.getSelectionModel().getSelectedItem();
        if(DialogMessage.callAlert()== ButtonType.OK){
            if (pt!=null){
                UnitXmt u = license_plate_number.getSelectionModel().getSelectedItem();
                if (u!=null){
                    phuongtienService.updateXmtUnit(new UnitXmt(pt_id.getText(),DashboardController.ref_Dv.getId(),pt.getId(),note.getText(),
                            Double.parseDouble(h.getText()),Double.parseDouble(km.getText()),Double.parseDouble(md.getText()),Double.parseDouble(tk.getText()),
                            u.getLicence_plate_number(), StatusCons.ACTIVED.getName()));
                    DinhMucPhuongTienController.norm_stage.close();
                }
            }else{
                phuongtienService.createNewXmtUnit(new UnitXmt(pt_id.getText(),DashboardController.ref_Dv.getId(),0,note.getText(),
                        Double.parseDouble(h.getText()),Double.parseDouble(km.getText()),Double.parseDouble(md.getText()),Double.parseDouble(tk.getText()),
                        license_plate_number.getEditor().getText(), StatusCons.ACTIVED.getName()),cbb_xmt.getEditor().getText(),cbb_loai.getSelectionModel().getSelectedItem().getId());
                DinhMucPhuongTienController.norm_stage.close();
            }
        }
    }
    @FXML
    public void cancelBtn(ActionEvent actionEvent) {
        DinhMucPhuongTienController.norm_stage.close();
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
    public void cbb_xmtAction(ActionEvent actionEvent) {
        actionXmt();
    }

    private void actionXmt() {
        PhuongTien pt = cbb_xmt.getSelectionModel().getSelectedItem();
        if (pt!=null){
            unitXmtList = new ArrayList<>();
            unitXmtList = unitXmtService.findByUnitIdAndPtId(DashboardController.ref_Dv.getId(),pt.getId());
            initLicensePlateCbb(unitXmtList);
            if (!unitXmtList.isEmpty()){
                setDataToField(unitXmtList.get(0));
            }
        }
    }

    @FXML
    public void license_plate_numberAction(ActionEvent actionEvent) {
        UnitXmt l = license_plate_number.getSelectionModel().getSelectedItem();
        if (l!=null){
            setDataToField(l);
        }
    }
    @FXML
    public void addNewChkAction(ActionEvent actionEvent) {
        if (addNewChk.isSelected()){
            UnitXmt u = new UnitXmt(null,DashboardController.ref_Dv.getId(),0,null,0,0,0,0,null,null);
            initPt(new ArrayList<>());
            initLicensePlateCbb(new ArrayList<>());
            setDataToField(u);
        }else{
            initPt(phuongtienService.findAll());
            actionXmt();
        }
    }

    private void setDataToField(UnitXmt unitXmt){
        pt_id.setText(String.valueOf(unitXmt.getId()));
        note.setText(unitXmt.getNote());
        h.setText(String.valueOf(unitXmt.getDm_hours()));
        km.setText(String.valueOf(unitXmt.getDm_km()));
        tk.setText(String.valueOf(unitXmt.getDm_tk()));
        md.setText(String.valueOf(unitXmt.getDm_md()));
    }
}
