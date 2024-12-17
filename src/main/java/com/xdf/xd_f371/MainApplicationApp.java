package com.xdf.xd_f371;

import com.xdf.xd_f371.controller.ErrorController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

        Task<Void> loadingTask = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulate loading by sleeping for a few seconds
                Thread.sleep(3000);  // Simulating loading time (e.g., 3 seconds)
                return null;
            }
            @Override
            protected void succeeded() {
                // Once the task is finished, close the splash screen and show the main UI
                Platform.runLater(() -> {
                    primaryStage.close();  // Close splash screen
                    try {
                        showMainUI(stage);  // Show main UI
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        };
        // Start loading task in the background
        new Thread(loadingTask).start();
    }

    private void showLoadingSpinner(Stage owner) {
        // Create a new stage for the loading spinner
        Stage spinnerStage = new Stage();
        spinnerStage.initOwner(owner); // Set the owner window
        spinnerStage.initModality(Modality.APPLICATION_MODAL); // Block input to owner window
        spinnerStage.initStyle(StageStyle.TRANSPARENT); // Transparent background

        // Create a ProgressIndicator
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setProgress(-1); // Indeterminate mode

        // Add the ProgressIndicator to a StackPane
        StackPane root = new StackPane(progressIndicator);
        root.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);"); // Semi-transparent background

        // Set up the scene
        Scene scene = new Scene(root);
        scene.setFill(null); // Make the scene transparent

        spinnerStage.setScene(scene);
        spinnerStage.show();
    }

    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showMainUI(Stage stage) throws IOException {
//        checkConnection();
//        Thread.setDefaultUncaughtExceptionHandler(MainApplicationApp::showError);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplicationApp.class.getResource("connect_lan.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        scene.setFill(Color.TRANSPARENT);
        stage.setTitle("Connection to Server in LAN Network");
        stage.setScene(scene);
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

}