package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.util.TextToNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class QuantityFormController implements Initializable {
    private static String sl_ton;
    @FXML
    Label slton_lb;
    @FXML
    TextField quantity_tf;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sl_ton = ChangingController.quantity;
        slton_lb.setText(TextToNumber.textToNum(sl_ton));
    }

    @FXML
    public void maxBtn(ActionEvent actionEvent) {
        quantity_tf.setText(ChangingController.quantity);
    }

    @FXML
    public void saveBtn(ActionEvent actionEvent) {
        if (quantity_tf.getText().trim().isEmpty()){
            ChangingController.quantity_convert = null;
        }else {
            ChangingController.quantity_convert = quantity_tf.getText();
        }
        ChangingController.addAff_stage.close();
    }

    @FXML
    public void exitBtn(ActionEvent actionEvent) {
        ChangingController.addAff_stage.close();
    }

    @FXML
    public void setQuantityTextF(KeyEvent keyEvent) {
        try {
            int qt =  Integer.parseInt(quantity_tf.getText());
            int cal = Integer.parseInt(sl_ton) - qt;
            if(cal >= 0){
                quantity_tf.setStyle(null);
                slton_lb.setText(TextToNumber.textToNum(String.valueOf(cal)));
            }else{
                quantity_tf.setStyle("-fx-border-color: red ; -fx-border-width: 2px ;");
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Lỗi định dạng nhập liệu, dữ liệu nhập phải là số.");
            alert.showAndWait();
            quantity_tf.setText("");
            throw new RuntimeException(e);
        }
    }
}
