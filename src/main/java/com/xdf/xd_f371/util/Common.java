package com.xdf.xd_f371.util;


import com.xdf.xd_f371.MainApplicationApp;
import com.xdf.xd_f371.controller.ConnectLan;
import com.xdf.xd_f371.controller.DashboardController;
import com.xdf.xd_f371.entity.LoaiPhuongTien;
import com.xdf.xd_f371.fatory.CommonFactory;
import com.zaxxer.hikari.HikariDataSource;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.xssf.usermodel.XSSFFormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ConfigurableApplicationContext;

import javax.sql.DataSource;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class Common {
    public static LoaiPhuongTien loaipt;

    public static void openNewStage(String fxml_url, Stage stage, String title, StageStyle s){
        Parent root = null;
        try {
            root = (Parent) DashboardController.getNodeBySource(fxml_url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(s);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.showAndWait();
    }
    public static void openNewStage2(String fxml_url, Stage stage, String title, StageStyle s){
        stage.getIcons().add(new Image(Objects.requireNonNull(MainApplicationApp.class.getResourceAsStream("img/icon_app4.png"))));
        Parent root = null;
        try {
            root = (Parent) DashboardController.getNodeBySource(fxml_url);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.initStyle(s);
        stage.initModality(Modality.NONE);
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
    public static void openDesktop(){
        try {
            File directoryPath = new File(ConnectLan.pre_path);
            Desktop desktop = Desktop.getDesktop();
            desktop.open(directoryPath);
        } catch (IOException io) {
            throw new RuntimeException(io);
        }
    }
    public static void getLoading(Stage primaryStage){
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle(
                "-fx-background-color: transparent;"
                        + "-fx-progress-color: green;"
                        + "-fx-border-color: transparent;"
        );
        progressIndicator.setVisible(true);
        StackPane root = new StackPane(progressIndicator);
        root.setStyle("-fx-background-color: transparent;");
        Scene scene = new Scene(root, 200, 200);
        scene.setFill(Color.TRANSPARENT);
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setOpacity(1);
        primaryStage.initModality(Modality.APPLICATION_MODAL);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.setScene(scene);
        primaryStage.show();
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
                Common.openDesktop();
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
                success.run();
                exception.printStackTrace();
                DialogMessage.errorShowing("Không thể cập nhật báo cáo trong khi đang mở file excel. Vui lòng đóng file excel hiện tại rồi thử lại.");
            }
        });
        Thread thread = new Thread(loadingTask);
        thread.setDaemon(true);
        thread.start();
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static boolean isDirectory(String path){
        try {
            Path paths = Paths.get(path);
            if (Files.exists(paths)) {
                return true;
            } else {
                return false;
            }
        } catch (RuntimeException e) {
            return false;
        }
    }
    public static boolean date_valid(DatePicker from, DatePicker to){
        LocalDate sd = from.getValue();
        LocalDate ed = to.getValue();
        if (sd!=null){
            if (ed!=null){
                if (sd.isAfter(ed)){
                    return true;
                }else{
                    from.setStyle(CommonFactory.styleErrorField);
                    to.setStyle(CommonFactory.styleErrorField);
                    DialogMessage.errorShowing("ngay bat dau dung truoc ngay ket thuc");
                }
            }else{
                to.setStyle(CommonFactory.styleErrorField);
                DialogMessage.errorShowing("Khong duoc de trong");
            }
        }else{
            from.setStyle(CommonFactory.styleErrorField);
            DialogMessage.errorShowing("Khong duoc de trong");
        }
        return false;
    }
    public static void copyFileExcel(String sour, String target){
        deleteExcel(target);
        File source = new File(sour);
        if (source.exists()){
            try {
                Files.copy(Paths.get(sour), Paths.get(target), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }
    private static boolean deleteExcel(String file_name){
        File file = new File(file_name);
        try {
            if (file.exists()){
                return file.delete();
            }
            return false;
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
