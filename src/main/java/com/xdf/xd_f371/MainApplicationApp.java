package com.xdf.xd_f371;

import com.xdf.xd_f371.util.Common;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

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
        rootStage = stage;
        rootStage.initStyle(StageStyle.TRANSPARENT);
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