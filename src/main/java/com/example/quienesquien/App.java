package com.example.quienesquien;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main application entry point.
 */
public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Cargar el archivo FXML de inicio
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/quienesquien/Vistas/Inicio.fxml"));
        primaryStage.setTitle("¿Quién es Quién? - Inicio");
        primaryStage.setScene(new Scene(root, 1280, 720)); // Ensure size matches FXML
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
