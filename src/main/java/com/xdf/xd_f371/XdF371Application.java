package com.xdf.xd_f371;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class XdF371Application extends Application {
	public static Scene rootScence;
	public static Stage rootStage;

	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception {
		Parent root = null;
		try {
			root = FXMLLoader.load(getClass().getResource("dashboard2.fxml"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		rootScence = new Scene(root);
		rootScence.setFill(Color.TRANSPARENT);
		stage.setScene(rootScence);
//        stage.setMaximized(true);
		stage.setTitle("Xăng dầu F371");
		rootStage = stage;
		stage.show();
	}
}
