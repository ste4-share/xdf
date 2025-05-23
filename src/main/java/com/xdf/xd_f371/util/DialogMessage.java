package com.xdf.xd_f371.util;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;

import java.util.Optional;

public class DialogMessage {
    public static void message(String title, String content, String header, Alert.AlertType alertType){
        Alert a = new Alert(alertType);
        a.initModality(Modality.APPLICATION_MODAL);
        a.setTitle(title);
        a.setContentText(content);
        a.setHeaderText(header);
        a.showAndWait();
    }

    public static ButtonType callAlert(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(null);
        alert.setHeaderText("Xác nhận");
        alert.setContentText("Xác nhận thêm mới");

        Optional<ButtonType> result = alert.showAndWait();
        return result.get();
    }

    public static ButtonType callAlertWithMessage(String title, String header, String content, Alert.AlertType alertType){
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Optional<ButtonType> result = alert.showAndWait();
        return result.orElse(null);
    }
    public static void errorShowing(String m){
        DialogMessage.message(null, null, m, Alert.AlertType.WARNING);
    }
    public static void successShowing(String message){
        DialogMessage.message(null, message,null , Alert.AlertType.INFORMATION);
    }
}
