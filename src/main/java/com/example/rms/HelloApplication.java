package com.example.rms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class HelloApplication extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("log.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("LoginDesign.css")).toExternalForm());
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("cardDesign.css")).toExternalForm());
//        scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("mainFormDesign.css")).toExternalForm());

        scene.getStylesheets().add(getClass().getResource("LoginDesign.css").toExternalForm());
        stage.setTitle("Restaurant Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}