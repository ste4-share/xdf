package com.xdf.xd_f371.controller;

import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.util.Common;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class InitProgressBar implements Initializable {
    public static Stage stage;
    @FXML
    private ProgressBar pbar;
    @FXML
    private Label percens_lb;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        count();
    }

    private void count(){
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                int total = 100;  // Total steps for the task
                for (int i = 0; i <= total; i++) {
                    if (isCancelled()) {
                        break; // Stop task if it's cancelled
                    }
                    updateProgress(i, total);  // Update the progress bar
                    Thread.sleep(20);  // Simulate a long-running task
                }
                return null;
            }
            @Override
            protected void succeeded() {
                MainApplicationApp.rootStage.close();
                stage = new Stage();
                try {
                    stage.initStyle(StageStyle.TRANSPARENT);
                    Common.openNewStage_show("connect_lan.fxml", stage,"Connection to Server in LAN Network",MainApplicationApp.context);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        };
        // Bind the progress of the ProgressBar to the task's progress
        pbar.progressProperty().bind(task.progressProperty());
        // Bind the percentage label to the task's progress
        task.progressProperty().addListener((obs, oldProgress, newProgress) -> {
            double percent = newProgress.doubleValue() * 100;
            percens_lb.setText(String.format("%.0f%%", percent)); // Format as whole number
        });
        // Create a new Thread to run the task
        Thread taskThread = new Thread(task);
        taskThread.setDaemon(true);  // Set the thread to daemon, so it stops when the app closes
        taskThread.start();
    }
}
