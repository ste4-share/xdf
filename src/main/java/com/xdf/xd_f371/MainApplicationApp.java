package com.xdf.xd_f371;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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

    @Override
    public void stop() {
        context.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
