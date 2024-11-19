package com.xdf.xd_f371.util;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ProgressBarShowing {
    public static ProgressIndicator progressIndicator = new ProgressIndicator();
    public static Stage progressStage = new Stage();
    public static void show(Task<Void> task){
        progressIndicator.setProgress(1.0);
        progressIndicator.setMinWidth(75);
        progressIndicator.setMinHeight(75);
        System.out.println("total" + task.totalWorkProperty().getValue());
        System.out.println("workdone" + task.workDoneProperty().getValue());
        System.out.println("progess" + task.progressProperty().getValue());
        progressIndicator.progressProperty().bind(task.progressProperty());
        Scene scene = new Scene(progressIndicator);
        progressStage.setScene(scene);
        progressStage.initStyle(StageStyle.UNDECORATED);
        progressStage.show();
        progressIndicator.progressProperty().addListener(observable -> {
            if (progressIndicator.getProgress() >= 1.0-0.0005) {
                progressIndicator.setStyle("-fx-accent: forestgreen;");
            }
        });
    }
}
