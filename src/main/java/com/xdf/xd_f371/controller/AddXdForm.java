package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.ChungLoaiXd;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.InventoryService;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import jakarta.persistence.Column;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.text.html.Option;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class AddXdForm implements Initializable {

    @Autowired
    private LoaiXdService loaiXdService;
    @Autowired
    private InventoryService inventoryService;
    @FXML
    private TextField mxd,txd,tdk_nvdx,tdk_sscd;
    @FXML
    private Label code;
    @FXML
    private Button add_btn,cancel_btn;
    @FXML
    private ComboBox<ChungLoaiXd> cl_cbb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        code.setText(null);

        initChungloaiCbb();
    }

    private void initChungloaiCbb() {
        ComponentUtil.setItemsToComboBox(cl_cbb,loaiXdService.findAllChungLoaiXd(),ChungLoaiXd::getCode2,input->loaiXdService.findByCode(code.getText()).orElse(null));
        cl_cbb.getSelectionModel().selectFirst();
        isCbbNotNull();
    }

    private boolean isCbbNotNull(){
        ChungLoaiXd cl = cl_cbb.getSelectionModel().getSelectedItem();
        if (cl_cbb.getSelectionModel().getSelectedItem()!=null){
            code.setText(cl.getCode());
            return true;
        }
        return false;
    }

    private void keyr(TextField tf){
        if (!tf.getText().trim().isEmpty()){
            if (isNum()){
                tf.setStyle(null);
            }
        }
    }
    @FXML
    public void cl_selected(ActionEvent actionEvent) {
        isCbbNotNull();
    }
    private boolean isNum(){
        if (!Common.isNumber(tdk_nvdx.getText())){
            tdk_nvdx.setStyle(CommonFactory.styleErrorField);
            return false;
        }if (!Common.isNumber(tdk_sscd.getText())){
            tdk_sscd.setStyle(CommonFactory.styleErrorField);
            return false;
        }
        return true;
    }
    @FXML
    public void addNew(ActionEvent actionEvent) {
        if(DialogMessage.callAlertWithMessage("Xác nhận","Thêm mới", "Xác nhận thêm mơi.", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            if (isNum()){
                if (isCbbNotNull() && !mxd.getText().trim().isEmpty() && !txd.getText().trim().isEmpty()){
                    Optional<LoaiXangDau> lxd = loaiXdService.findByMaxd(mxd.getText());
                    if (lxd.isEmpty()){
                        if (loaiXdService.saveLxdAndInventory(new LoaiXangDau(mxd.getText(),txd.getText(),
                                        cl_cbb.getSelectionModel().getSelectedItem().getId(),StatusCons.ACTIVED.getName()),
                                Integer.parseInt(tdk_nvdx.getText()),Integer.parseInt(tdk_sscd.getText()))){
                            DialogMessage.message("THANH CONG","Thêm mới thành công","Thành công", Alert.AlertType.INFORMATION);
                        }else {
                            DialogMessage.message("Lỗi","Có lỗi xảy ra, vui lòng thử lại sau","Lỗi", Alert.AlertType.WARNING);
                        }
                    }else {
                        mxd.setStyle(CommonFactory.styleErrorField);
                        DialogMessage.message("Lỗi","Mã loại xăng dầu đã tồn tại. Vui lòng thử lại sau","Lỗi", Alert.AlertType.WARNING);
                    }

                }else{
                    DialogMessage.message("ERROR","Điền đầy đủ các trường vào ô trống","Thêm không thành công", Alert.AlertType.WARNING);
                }
            }

        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        TonkhoController.tk_stage.close();
    }
    @FXML
    public void kr_sscd(KeyEvent keyEvent) {
        keyr(tdk_sscd);
    }
    @FXML
    public void kr_nvdx(KeyEvent keyEvent) {
        keyr(tdk_nvdx);
    }
    @FXML
    public void sscdClicked(MouseEvent mouseEvent) {
        selectTF(tdk_sscd);
    }
    @FXML
    public void nvdxClicked(MouseEvent mouseEvent) {
        selectTF(tdk_nvdx);
    }
    @FXML
    public void mxdclicked(MouseEvent mouseEvent) {
        selectTF(mxd);
    }
    private void selectTF(TextField tf){
        tf.setStyle(null);
        tf.selectAll();
    }
}
