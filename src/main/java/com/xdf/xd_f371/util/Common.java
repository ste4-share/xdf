package com.xdf.xd_f371.util;


import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.controller.DashboardController;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Common {

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

    public static void openNewStage_show(String fxml_url, Stage stage, String title, ConfigurableApplicationContext context) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource(fxml_url));
        fxmlLoader.setControllerFactory(context::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle(title);
        stage.setScene(scene);
        stage.show();
    }
    public static void setHint(TextField textField, List<String> strls) {
        TextFields.bindAutoCompletion(textField, t -> {
            return strls.stream().filter(elem
                    -> {
                return elem.toLowerCase().contains(t.getUserText().toLowerCase().trim());
            }).collect(Collectors.toList());
        });
    }
    public static LocalDate localdateToDate(Date input){
        return LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());
    }
    public static void hoverButton(Button button, String color) {
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: "+color+"; -fx-border-color: #000000; -fx-border-width:3;-fx-background-radius:10;-fx-border-radius:10"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: "+color+";-fx-border-color:#a8a8a8;-fx-background-radius:10;-fx-border-radius:10"));
    }
    public static boolean isNumber(String in) {
        return in.matches("[^A-Za-z][0-9]{0,18}");
    }
    public static void task(Runnable task_call,Runnable success) {
        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                task_call.run();
                return null;
            }
            @Override
            protected void succeeded() {
                success.run();
            }
            @Override
            protected void failed() {
                DialogMessage.message("Error", "An unexpected error occurred","An unexpected error has occurred", Alert.AlertType.ERROR);
            }
        };
        // Start loading task in the background
        new Thread(loadingTask).start();
    }
    public static void LoadingUI(Stage primaryStage){
        // Create the ProgressIndicator
        ProgressIndicator progressIndicator = new ProgressIndicator();

        // Set the style for ProgressIndicator to make it background transparent
        progressIndicator.setStyle(
                "-fx-background-color: transparent;"   // Remove the background color
                        + "-fx-progress-color: #da0b0b;"         // Set the progress color (customize as needed)
                        + "-fx-border-color: transparent;"     // Remove the border around the ProgressIndicator
        );

        // Set up the layout for the root (StackPane)
        StackPane root = new StackPane(progressIndicator);
        root.setStyle("-fx-background-color: transparent;");  // Make the layout transparent

        // Create a Scene with a transparent background
        Scene scene = new Scene(root, 200, 200);
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT); // Make the scene transparent

        // Make the window (Stage) fully transparent and remove the border/title bar
        primaryStage.initStyle(StageStyle.TRANSPARENT);  // Remove window decorations and make it transparent
        primaryStage.setOpacity(1);  // Ensure the window is fully visible

        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
