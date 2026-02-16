package com.example.quienesquien.Controlador;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import com.example.quienesquien.Modelos.RegistroPartida;

public class InicioController {

    @FXML
    private Button btnIniciar;

    @FXML
    private TextField txtNombre;

    @FXML
    private TableView<RegistroPartida> tblPartidas;

    @FXML
    private TableColumn<RegistroPartida, String> colNombre;

    @FXML
    private TableColumn<RegistroPartida, Integer> colPuntuacion;

    @FXML
    public void initialize() {
        if (btnIniciar != null) {
            btnIniciar.setOnAction(event -> cargarJuego());
        }

        if (colNombre != null && colPuntuacion != null && tblPartidas != null) {
            colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            colPuntuacion.setCellValueFactory(new PropertyValueFactory<>("puntuacion"));
            cargarPuntuaciones();
        }
    }

    private void cargarPuntuaciones() {
        List<RegistroPartida> lista = new ArrayList<>();
        String ruta = "src/main/java/com/example/quienesquien/almacenaje/historial.txt";

        try {
            if (Files.exists(Paths.get(ruta))) {
                List<String> lineas = Files.readAllLines(Paths.get(ruta));
                for (String linea : lineas) {
                    try {
                        // Formato esperado: "Nombre - Preguntas restantes: X"
                        String[] partes = linea.split(" - Preguntas restantes: ");
                        if (partes.length == 2) {
                            String nombre = partes[0].trim();
                            int puntuacion = Integer.parseInt(partes[1].trim());
                            lista.add(new RegistroPartida(nombre, puntuacion));
                        }
                    } catch (Exception e) {
                        System.err.println("Error parseando linea: " + linea);
                    }
                }

                // Ordenar de mayor a menor puntuacion
                lista.sort(Comparator.comparingInt(RegistroPartida::getPuntuacion).reversed());

                ObservableList<RegistroPartida> items = FXCollections.observableArrayList(lista);
                tblPartidas.setItems(items);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarJuego() {
        try {
            // Cargar el archivo FXML del juego principal
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/quienesquien/Vistas/Juego.fxml"));
            Parent root = loader.load();

            // Obtener el controlador del juego y pasarle el nombre
            com.example.quienesquien.Controlador.JuegoController gameController = loader.getController();
            String nombre = txtNombre.getText();
            if (nombre != null && !nombre.trim().isEmpty()) {
                gameController.setNombreJugador(nombre);
            } else {
                gameController.setNombreJugador("Jugador Anónimo");
            }

            // Obtener el Stage actual desde el botón
            Stage stage = (Stage) btnIniciar.getScene().getWindow();

            // Configurar la nueva escena
            Scene scene = new Scene(root, 1280, 720);
            stage.setScene(scene);
            stage.setTitle("¿Quién es Quién? - Jugando");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
