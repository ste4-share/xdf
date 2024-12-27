package com.xdf.xd_f371.util;


import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.cons.SubQuery;
import com.xdf.xd_f371.controller.DashboardController;
import jakarta.validation.constraints.Null;
import javafx.application.Platform;
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
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.controlsfx.control.textfield.TextFields;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
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
        stage.initStyle(StageStyle.TRANSPARENT);
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
    public static void getLoading(Stage primaryStage){
        // Create the ProgressIndicator
        ProgressIndicator progressIndicator = new ProgressIndicator();

        // Set the style for ProgressIndicator to make it background transparent
        progressIndicator.setStyle(
                "-fx-background-color: transparent;"   // Remove the background color
                        + "-fx-progress-color: green;"         // Set the progress color (customize as needed)
                        + "-fx-border-color: transparent;"     // Remove the border around the ProgressIndicator
        );
        progressIndicator.setVisible(true);
        // Set up the layout for the root (StackPane)
        StackPane root = new StackPane(progressIndicator);
        root.setStyle("-fx-background-color: transparent;");  // Make the layout transparent

        // Create a Scene with a transparent background
        Scene scene = new Scene(root, 200, 200);
        scene.setFill(Color.TRANSPARENT); // Make the scene transparent

        // Make the window (Stage) fully transparent and remove the border/title bar
        primaryStage.initStyle(StageStyle.TRANSPARENT);  // Remove window decorations and make it transparent
        primaryStage.setOpacity(1);  // Ensure the window is fully visible
        primaryStage.initModality(Modality.APPLICATION_MODAL); // Make the stage modal
        primaryStage.setAlwaysOnTop(true); // Optional: Keep it above other stages
        // Set the scene and show the stage
        primaryStage.setScene(scene);
        primaryStage.show();
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
    public static void task(Runnable task_call,Runnable success,Runnable message) {

        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                task_call.run();
                return null;
            }
            @Override
            protected void succeeded() {
                success.run();
                message.run();
            }
            @Override
            protected void cancelled() {
                success.run();
                DialogMessage.message("Timeout", "The task took too long and has been cancelled.","Task Timed Out", Alert.AlertType.WARNING);
            }
        };
        loadingTask.setOnFailed(workerStateEvent -> {
            Throwable exception = loadingTask.getException();
            if (exception != null) {
                DialogMessage.errorShowing(exception.getMessage());
            }
        });
        // Start loading task in the background
        Thread thread = new Thread(loadingTask);
        thread.setDaemon(true);
        thread.start();
        // Set a timeout for the task
        setTaskTimeout(loadingTask, 30, TimeUnit.SECONDS);
    }
    private static void setTaskTimeout(Task<?> task, long timeout, TimeUnit unit) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.schedule(() -> {
            if (!task.isDone()) {
                task.cancel();
            }
            scheduler.shutdown();
        }, timeout, unit);
    }
    public static void mapExcelFile(String file_name,Function<XSSFWorkbook,Integer> mapData_createNew,Function<XSSFWorkbook,Integer> mapData_get){
        try{
            File file = new File(file_name);
            if (!file.exists()){
                file.createNewFile();
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook();
                mapData_createNew.apply(wb);
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }else{
                FileInputStream fis = new FileInputStream(file);
                XSSFWorkbook wb = new XSSFWorkbook(fis);
                mapData_get.apply(wb);
                fis.close();
                FileOutputStream fileOutputStream = new FileOutputStream(file_name);
                XSSFFormulaEvaluator.evaluateAllFormulaCells(wb);
                wb.write(fileOutputStream);
                fileOutputStream.close();
                wb.close();
            }

        } catch (IOException e) {
            DialogMessage.message("THÔNG BÁO LỖI", e.getMessage(), "Có lỗi xảy ra!", Alert.AlertType.ERROR);
            throw new RuntimeException(e);
        }
    }
}
