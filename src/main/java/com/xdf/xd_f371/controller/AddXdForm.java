package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.cons.StatusCons;
import com.xdf.xd_f371.entity.ChungLoaiXd;
import com.xdf.xd_f371.entity.LoaiXangDau;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.service.LoaiXdService;
import com.xdf.xd_f371.util.ComponentUtil;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class AddXdForm implements Initializable {
    @Autowired
    private LoaiXdService loaiXdService;
    @FXML
    private TextField mxd,txd;
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
    private void selectTF(TextField tf){
        tf.setStyle(null);
        tf.selectAll();
    }

    @FXML
    public void cl_selected(ActionEvent actionEvent) {
        isCbbNotNull();
    }
    @FXML
    public void addNew(ActionEvent actionEvent) {
        if(DialogMessage.callAlertWithMessage("Xác nhận","Thêm mới", "Xác nhận thêm mơi.", Alert.AlertType.CONFIRMATION)==ButtonType.OK){
            if (isCbbNotNull() && !mxd.getText().trim().isEmpty() && !txd.getText().trim().isEmpty()){
                Optional<LoaiXangDau> lxd = loaiXdService.findByMaxd(mxd.getText());
                if (lxd.isEmpty()){
                    loaiXdService.save(new LoaiXangDau(mxd.getText(),txd.getText(),
                            cl_cbb.getSelectionModel().getSelectedItem().getId(),StatusCons.ACTIVED.getName()));
                    DialogMessage.message("THANH CONG","Thêm mới thành công","Thành công", Alert.AlertType.INFORMATION);
                    TonkhoController.tk_stage.close();
                }else {
                    mxd.setStyle(CommonFactory.styleErrorField);
                    DialogMessage.message("Lỗi","Mã loại xăng dầu đã tồn tại. Vui lòng thử lại sau","Lỗi", Alert.AlertType.WARNING);
                }

            }else{
                DialogMessage.message("ERROR","Điền đầy đủ các trường vào ô trống","Thêm không thành công", Alert.AlertType.WARNING);
            }
        }
    }
    @FXML
    public void cancel(ActionEvent actionEvent) {
        TonkhoController.tk_stage.close();
    }
    @FXML
    public void mxdclicked(MouseEvent mouseEvent) {
        selectTF(mxd);
    }

}
