package com.xdf.xd_f371;

import com.xdf.xd_f371.controller.ErrorController;
import com.xdf.xd_f371.util.Common;
import com.xdf.xd_f371.util.DialogMessage;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainApplicationApp extends Application {
    public static ConfigurableApplicationContext context;
    public static Stage rootStage;

    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(XdF371Application.class);
        builder.headless(false);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {

        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1); // Indeterminate spinner

        // Set up the layout
        StackPane root = new StackPane(progressIndicator);
        // Create scene with no decoration (transparent window)
        Scene scene = new Scene(root, 100, 100); // Set the desired size
        Stage primaryStage = new Stage();
        primaryStage.setScene(scene);
        // Set the window to be borderless and transparent
        primaryStage.initStyle(StageStyle.UTILITY); // Removes window borders
        primaryStage.setOpacity(1); // Set the opacity to make the background transparent

        primaryStage.setScene(scene);
        primaryStage.show();
        Common.task(this::sleep3Second,
                ()->showLogin(primaryStage),
                ()->DialogMessage.message("Error", "An unexpected error occurred","An unexpected error has occurred", Alert.AlertType.ERROR));}
    private void showLogin(Stage pro){
        pro.close();  // Close splash screen
        try {
            showMainUI(pro);  // Show main UI
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void sleep3Second(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showMainUI(Stage stage) throws IOException {
//        Thread.setDefaultUncaughtExceptionHandler(MainApplicationApp::showError);
        rootStage = stage;
        Common.openNewStage_show("connect_lan.fxml", rootStage,"Connection to Server in LAN Network",context);
    }

    private static void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            DialogMessage.message("Error", "An unexpected error occurred in " + t,"An unexpected error has occurred", Alert.AlertType.ERROR);
        }
    }

    private static void showErrorDialog(Throwable e) {
        StringWriter errorMsg = new StringWriter();
        e.printStackTrace(new PrintWriter(errorMsg));
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(MainApplicationApp.class.getResource("Error.fxml"));
        try {
            Parent root = loader.load();
            ((ErrorController)loader.getController()).setErrorText(errorMsg.toString());
            dialog.setScene(new Scene(root, 350, 200));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

}