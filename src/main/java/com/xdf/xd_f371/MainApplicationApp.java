package com.xdf.xd_f371;

import com.xdf.xd_f371.util.Common;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

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
        Thread.setDefaultUncaughtExceptionHandler((thread, throwable) -> {
            System.err.println("Uncaught exception in thread " + thread.getName() + ": " + throwable.getMessage());
            throwable.printStackTrace();
        });
        rootStage = stage;
        rootStage.initStyle(StageStyle.TRANSPARENT);
        rootStage.getIcons().add(new Image(Objects.requireNonNull(MainApplicationApp.class.getResourceAsStream("img/icon_app4.png"))));
        Common.openNewStage_show("initProgressBar.fxml", rootStage,null,context);
    }
    @Override
    public void stop() {
        context.close();
    }
    public static void main(String[] args) {
        launch(args);
    }
}