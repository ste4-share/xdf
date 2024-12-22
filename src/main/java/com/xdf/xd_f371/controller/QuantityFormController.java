package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.fatory.CommonFactory;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import com.xdf.xd_f371.util.TextToNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class QuantityFormController implements Initializable {
    private static int sl_ton;
    @FXML
    Label slton_lb,notion_lb;
    @FXML
    TextField quantity_tf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notion_lb.setText(null);
        sl_ton = ChangingController.quantity;
        slton_lb.setText(TextToNumber.textToNum(String.valueOf(sl_ton)));
    }
    @FXML
    public void maxBtn(ActionEvent actionEvent) {
        quantity_tf.setText(String.valueOf(ChangingController.quantity));
    }
    @FXML
    public void saveBtn(ActionEvent actionEvent) {
        if (notion_lb.getText()==null){
            ChangingController.quantity_convert = Integer.parseInt(quantity_tf.getText());
            ChangingController.addAff_stage.close();
        }else {
            ChangingController.quantity_convert = Integer.parseInt(quantity_tf.getText());
            DialogMessage.message("Error", "An unexpected error occurred in ","An unexpected error has occurred", Alert.AlertType.WARNING);
        }
    }
    @FXML
    public void exitBtn(ActionEvent actionEvent) {
        ChangingController.addAff_stage.close();
    }
    @FXML
    public void setQuantityTextF(KeyEvent keyEvent) {
        String q= quantity_tf.getText();
        if (!q.trim().isEmpty()){
            if (Common.isNumber(q)) {
                int qt = Integer.parseInt(q);
                int cal = sl_ton - qt;
                if (cal >= 0) {
                    setErrorText(null,null);
                    slton_lb.setText(TextToNumber.textToNum(String.valueOf(cal)));
                } else {
                    setErrorText("Số lượng vượt mức tồn đang có.",CommonFactory.styleErrorField);
                }
            }else{
                setErrorText("Vui lòng nhập số.", CommonFactory.styleErrorField);
            }
        }else{
            setErrorText(null,null);
        }
    }
    @FXML
    public void sl_selected(MouseEvent mouseEvent) {
        quantity_tf.selectAll();
    }
    private void setErrorText(String no, String q){
        notion_lb.setText(no);
        quantity_tf.setStyle(q);
    }
}
