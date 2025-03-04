package com.xdf.xd_f371.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;
@Component
public class PriceViewController implements Initializable {
    @FXML
    private ListView<String> pricelw;
    @FXML
    private Label sumlb;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initInventoryList();
        initLabel();
    }

    private void initLabel() {
        sumlb.setText("0");
    }

    private void initInventoryList() {
    }

}
