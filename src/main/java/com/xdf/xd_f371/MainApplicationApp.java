package com.xdf.xd_f371;

import com.xdf.xd_f371.controller.ErrorController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

public class MainApplicationApp extends Application {
    public static ConfigurableApplicationContext context;
    public static Scene rootScence;
    public static Stage rootStage;
    @Override
    public void init() {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(XdF371Application.class);
        context = builder.run(getParameters().getRaw().toArray(new String[0]));
    }

    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(MainApplicationApp::showError);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource("dashboard2.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        rootScence = new Scene(fxmlLoader.load());
        rootScence.setFill(Color.TRANSPARENT);
        stage.setMaximized(true);
        stage.setTitle("Xăng dầu F371");
        stage.setScene(rootScence);
        rootStage = stage;
        stage.show();
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in "+t);

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
            dialog.setScene(new Scene(root, 450, 400));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }



    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
