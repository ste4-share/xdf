package com.xdf.xd_f371.util;


import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.entity.Category;
import com.xdf.xd_f371.entity.InvReportDetail;
import com.xdf.xd_f371.entity.Inventory;
import com.xdf.xd_f371.model.ChungLoaiModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.log4j.chainsaw.Main;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Common {

    public static boolean getInvCatalogField(Category category, Inventory invetory, InvReportDetail invReportDetail){
        if (category.getType_title().equals(ChungLoaiModel.TDK_a.getNameChungloai()) && category.getCode().equals(ChungLoaiModel.NVDX_a.getNameChungloai())){
            invReportDetail.setSoluong(invetory.getTdk_nvdx());
            return true;
        } else if (category.getType_title().equals(ChungLoaiModel.TDK_a.getNameChungloai()) && category.getCode().equals(ChungLoaiModel.SSCD_a.getNameChungloai())) {
            invReportDetail.setSoluong(invetory.getTdk_sscd());
            return true;
        }
        return false;
    }
    public static void openNewStage(String fxml_url, Stage stage, String title){
        Parent root = null;
        try {
            root = (Parent) DashboardController.getNodeBySource(fxml_url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(StageStyle.DECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.showAndWait();
    }

    public static void openDashBoard(String fxml_url, Stage stage, String title) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource("dashboard2.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setMaximized(true);
        stage.setTitle("Xăng dầu F371");
        stage.setScene(scene);
        stage.show();
    }


}
