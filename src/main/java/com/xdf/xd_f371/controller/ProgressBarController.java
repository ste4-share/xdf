package com.xdf.xd_f371.controller;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class ProgressBarController implements Initializable {

    @FXML
    private ProgressBar progresbar;
    @FXML
    private Label percentage_lb;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Task task = taskWorker(10);
        progresbar.progressProperty().bind(task.progressProperty());
    }

    private Task taskWorker(int second) {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < second; i++) {
                    updateProgress(i, second);
                    Thread.sleep(1000);
                }
                return true;
            }
        };
    }

}
