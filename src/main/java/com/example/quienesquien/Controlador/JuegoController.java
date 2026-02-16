package com.example.quienesquien.Controlador;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;
import java.io.*;
import static javafx.collections.FXCollections.observableArrayList;
import java.net.URL;
import java.util.ResourceBundle;

public class JuegoController implements Initializable {

    @FXML
    private ComboBox<String> comboPreguntas;

    @FXML
    private Button btnEnviar;

    @FXML
    private ImageView imagenGrande;

    @FXML
    private javafx.scene.control.Label lblIntentos;

    @FXML
    private javafx.scene.control.Label lblRespuesta;

    @FXML
    private AnchorPane paneTelevision;

    @FXML
    private javafx.scene.control.Label lblTvTexto;

    @FXML
    private Button btnTvContinuar;
    @FXML
    private Button btnAyuda;
    @FXML
    private AnchorPane paneAyuda;
    @FXML
    private Button btnCerrarAyuda;

    private int preguntasRestantes = 5;
    private String nombreJugador = "Jugador";

    // A esta función no se le da uso en este archivo pero es usada en
    // InicioController
    public void setNombreJugador(String nombre) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            this.nombreJugador = nombre;
        }
    }

    private void guardarPartida() {
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("src/main/java/com/example/quienesquien/almacenaje/historial.txt", true))) {
            writer.write(nombreJugador + " - Preguntas restantes: " + preguntasRestantes);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error al guardar el historial: " + e.getMessage());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Inicio la lista de preguntas que voy a usar en la aplicación
        ObservableList<String> preguntas = observableArrayList(
                "¿Es humano?",
                "¿Puede hablar?",
                "¿Está vivo?",
                "¿Es peleador?",
                "¿Tiene poderes?",
                "¿Su serie es de antes del 2010?",
                "¿Es el protagonista?",
                "¿Es menor de edad?",
                "¿Es un animal?",
                "¿Tiene pelo?",
                "¿Lleva ropa?",
                "¿Puede volar?",
                "¿Es de Nickelodeon?");
        // Añado la lista con las preguntas al desplegable
        comboPreguntas.setItems(preguntas);
        // Inicio la lista de personajes
        inicializarPersonajes();
        // Selecciono el personaje oculto
        seleccionarPersonajeOculto();

        // Añado el evento al botón de enviar
        // Añado el evento al botón de enviar
        btnEnviar.setOnAction(event -> verificarPregunta());

        if (btnAyuda != null) {
            btnAyuda.setOnAction(event -> mostrarAyuda());
        }

        if (btnCerrarAyuda != null) {
            btnCerrarAyuda.setOnAction(event -> {
                if (paneAyuda != null)
                    paneAyuda.setVisible(false);
                gestionarInteraccionesAyuda(true);
            });
        }

        // Compruebo que el boto nse cargo correctamente
        if (btnEnviar != null) {
            // Le digo a java que espere hasta que toda la pagina este cargada para usar
            // este codigo
            javafx.application.Platform.runLater(() -> {
                if (btnEnviar.getParent() instanceof AnchorPane) {
                    // Casteo el padre del boton a AnchorPane
                    AnchorPane root = (AnchorPane) btnEnviar.getParent();
                    // Configuro las imagenes
                    configurarImagenes(root);
                }
            });
        }

    }

    private java.util.List<com.example.quienesquien.Modelos.Personaje> listaPersonajes;
    private com.example.quienesquien.Modelos.Personaje personajeOculto;

    private void inicializarPersonajes() {
        listaPersonajes = new java.util.ArrayList<>();
        // (Nombre, Imagen, Humano, Habla, Vivo, Peleador, Poderes, <2010, Prota, Menor,
        // Animal, Pelo, Ropa, Vuela, Nick)

        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Danny", "danny", true, true, true, true,
                true, true, true, true, false, true, true, true, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Patricio", "patricio", false, true, true,
                false, false, true, false, false, true, false, true, false, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Bloo", "bloo", false, true, true, false,
                false, true, true, true, false, false, false, false, false));
        listaPersonajes
                .add(new com.example.quienesquien.Modelos.Personaje("Aelita", "Aleitamini", true, true, true, true,
                        true, true, true, true, false, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Jimmy", "jimmy", true, true, true, false,
                false, true, true, true, false, true, true, true, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Zak", "zak", true, true, true, true, true,
                false, true, true, false, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Cloe", "cloe", true, true, true, true, true,
                false, false, true, false, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Ben", "ben", true, true, true, true, true,
                true, true, true, false, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Aang", "Aang", true, true, true, true, true,
                true, true, true, false, false, true, true, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Calavera", "calavera", false, true, false,
                true, true, true, true, false, false, false, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Bob Esponja", "bobesponja", false, true,
                true, false, false, true, true, false, true, false, true, false, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Gir", "gir", false, true, false, false,
                false, true, false, true, false, false, true, true, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Jake", "jake", false, true, true, true,
                true, false, true, false, true, true, false, false, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Coraje", "coraje", false, false, true,
                false, false, true, true, false, true, true, false, false, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Jane", "jane", false, true, false, false,
                false, true, true, true, false, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Kim", "kim", true, true, true, true, false,
                true, true, true, false, true, true, false, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Timmy", "timmy", true, true, true, false,
                false, true, true, true, false, true, true, true, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("N1", "n1", true, true, true, true, false,
                true, true, true, false, false, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Pucca", "pucca", true, false, true, true,
                true, true, true, true, false, true, true, false, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Goddard", "goddard", false, false, false,
                false, false, true, false, true, true, false, false, true, true));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Chowder", "chowder", false, true, true,
                false, false, true, true, true, true, true, true, false, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Perry", "perry", false, false, true, true,
                false, true, false, false, true, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Mojo Jojo", "Mojo_jojo", false, true, true,
                true, false, true, false, false, true, true, true, true, false));
        listaPersonajes.add(new com.example.quienesquien.Modelos.Personaje("Ultra T", "ultraT", false, true, true, true,
                true, true, false, false, false, false, false, true, false));
    }

    private void seleccionarPersonajeOculto() {
        if (!listaPersonajes.isEmpty()) {
            personajeOculto = listaPersonajes.get(new java.util.Random().nextInt(listaPersonajes.size()));
            System.out.println("Personaje oculto: " + personajeOculto.getNombre());
        }
    }

    private void verificarPregunta() {
        String pregunta = comboPreguntas.getValue();
        if (pregunta == null || personajeOculto == null)
            return;

        boolean respuestaReal = false;

        switch (pregunta) {
            case "¿Es humano?":
                respuestaReal = personajeOculto.isEsHumano();
                break;
            case "¿Puede hablar?":
                respuestaReal = personajeOculto.isPuedeHablar();
                break;
            case "¿Está vivo?":
                respuestaReal = personajeOculto.isEstaVivo();
                break;
            case "¿Es peleador?":
                respuestaReal = personajeOculto.isEsPeleador();
                break;
            case "¿Tiene poderes?":
                respuestaReal = personajeOculto.isTienePoderes();
                break;
            case "¿Su serie es de antes del 2010?":
                respuestaReal = personajeOculto.isEsSerieAntes2010();
                break;
            case "¿Es el protagonista?":
                respuestaReal = personajeOculto.isEsProtagonista();
                break;
            case "¿Es menor de edad?":
                respuestaReal = personajeOculto.isEsMenorDeEdad();
                break;
            case "¿Es un animal?":
                respuestaReal = personajeOculto.isEsAnimal();
                break;
            case "¿Tiene pelo?":
                respuestaReal = personajeOculto.isTienePelo();
                break;
            case "¿Lleva ropa?":
                respuestaReal = personajeOculto.isLlevaRopa();
                break;
            case "¿Puede volar?":
                respuestaReal = personajeOculto.isPuedeVolar();
                break;
            case "¿Es de Nickelodeon?":
                respuestaReal = personajeOculto.isEsDeNickelodeon();
                break;
        }

        System.out.println("Pregunta: " + pregunta + " -> Respuesta: " + respuestaReal);

        if (lblRespuesta != null) {
            lblRespuesta.setText(respuestaReal ? "SÍ" : "NO");
            if (respuestaReal) {
                lblRespuesta.setStyle(
                        "-fx-text-fill: #00FF00; -fx-font-weight: bold; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 3, 0, 0, 1);");
            } else {
                lblRespuesta.setStyle(
                        "-fx-text-fill: #FF0000; -fx-font-weight: bold; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 3, 0, 0, 1);");
            }
        }

        // Restamos una pregunta disponible (sea acierto o fallo)
        preguntasRestantes--;
        if (lblIntentos != null) {
            lblIntentos.setText("Preguntas: " + preguntasRestantes);
        }

        if (preguntasRestantes <= 0) {
            comboPreguntas.setDisable(true);
            if (btnEnviar != null)
                btnEnviar.setDisable(true);

            if (paneTelevision != null && lblTvTexto != null) {
                // Desactivar interacciones temporalmente
                if (btnEnviar.getParent() instanceof AnchorPane) {
                    AnchorPane root = (AnchorPane) btnEnviar.getParent();
                    for (Node node : root.getChildren()) {
                        if (node instanceof ImageView) {
                            ImageView img = (ImageView) node;
                            if (img != imagenGrande && img.getFitHeight() < 500) {
                                img.setOnMouseEntered(null);
                                img.setOnMouseExited(null);
                                img.setOnMouseClicked(null);
                                img.setCursor(javafx.scene.Cursor.DEFAULT);
                            }
                        }
                    }
                }

                lblTvTexto.setText("¡LÍMITE ALCANZADO!\n\nSIN PREGUNTAS.\nHAZ CLIC EN UN PERSONAJE.");
                lblTvTexto.setStyle("-fx-text-fill: #FFA500;");
                paneTelevision.setVisible(true);
                paneTelevision.toFront();

                if (btnTvContinuar != null) {
                    btnTvContinuar.setOnAction(e -> {
                        paneTelevision.setVisible(false);
                        // Reactivar interacciones para que el usuario pueda jugar
                        if (btnEnviar.getParent() instanceof AnchorPane) {
                            configurarImagenes((AnchorPane) btnEnviar.getParent());
                        }
                    });
                }
            } else {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("¡Límite alcanzado!");
                alert.setContentText("Ya no puedes hacer más preguntas. ¡Haz clic en un personaje para adivinar!");
                alert.showAndWait();
            }
        }

        eliminarDescartados(pregunta, respuestaReal);
    }

    private void eliminarDescartados(String pregunta, boolean valorCorrecto) {
        if (btnEnviar.getParent() instanceof AnchorPane) {
            AnchorPane root = (AnchorPane) btnEnviar.getParent();

            for (Node node : root.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView iv = (ImageView) node;
                    if (iv == imagenGrande || iv.getFitHeight() > 500)
                        continue;

                    // Identificar personaje
                    com.example.quienesquien.Modelos.Personaje p = obtenerPersonajePorImagen(iv);
                    if (p != null) {
                        boolean valorPersonaje = false;
                        switch (pregunta) {
                            case "¿Es humano?":
                                valorPersonaje = p.isEsHumano();
                                break;
                            case "¿Puede hablar?":
                                valorPersonaje = p.isPuedeHablar();
                                break;
                            case "¿Está vivo?":
                                valorPersonaje = p.isEstaVivo();
                                break;
                            case "¿Es peleador?":
                                valorPersonaje = p.isEsPeleador();
                                break;
                            case "¿Tiene poderes?":
                                valorPersonaje = p.isTienePoderes();
                                break;
                            case "¿Su serie es de antes del 2010?":
                                valorPersonaje = p.isEsSerieAntes2010();
                                break;
                            case "¿Es el protagonista?":
                                valorPersonaje = p.isEsProtagonista();
                                break;
                            case "¿Es menor de edad?":
                                valorPersonaje = p.isEsMenorDeEdad();
                                break;
                            case "¿Es un animal?":
                                valorPersonaje = p.isEsAnimal();
                                break;
                            case "¿Tiene pelo?":
                                valorPersonaje = p.isTienePelo();
                                break;
                            case "¿Lleva ropa?":
                                valorPersonaje = p.isLlevaRopa();
                                break;
                            case "¿Puede volar?":
                                valorPersonaje = p.isPuedeVolar();
                                break;
                            case "¿Es de Nickelodeon?":
                                valorPersonaje = p.isEsDeNickelodeon();
                                break;
                        }

                        // Lógica estándar del juego:
                        // Eliminar a los que NO coincidan con la característica del personaje oculto.
                        // Si el oculto TIENE la característica (valorCorrecto = true), eliminamos a los
                        // que NO la tienen (valorPersonaje = false).
                        // Si el oculto NO la tiene (valorCorrecto = false), eliminamos a los que SÍ la
                        // tienen (valorPersonaje = true).
                        if (valorPersonaje != valorCorrecto) {
                            desactivarPersonajeUI(iv, root);
                        }
                    }
                }
            }
        }
    }

    private void desactivarPersonajeUI(ImageView iv, AnchorPane root) {
        // Opacidad reducida
        iv.setOpacity(0.3);
        // Desactivar zoom (quita eventos)
        iv.setOnMouseEntered(null);
        iv.setOnMouseExited(null);
        iv.setCursor(javafx.scene.Cursor.DEFAULT);

        // Calcular centro de la imagen para buscar su fondo contenedor
        double imgCenterX = iv.getLayoutX() + iv.getLayoutBounds().getWidth() / 2;
        double imgCenterY = iv.getLayoutY() + iv.getLayoutBounds().getHeight() / 2;

        // Buscar el Label de fondo
        for (Node n : root.getChildren()) {
            if (n instanceof javafx.scene.control.Label) {
                javafx.scene.control.Label lbl = (javafx.scene.control.Label) n;

                // Filtros básicos para ignorar labels de título o gigantes
                if (lbl.getText() != null && !lbl.getText().isEmpty())
                    continue; // Descartar etiquetas con texto (títulos)
                if (lbl.getWidth() > 300 || lbl.getHeight() > 300)
                    continue; // Descartar fondos grandes

                // Verificar si el centro de la imagen cae dentro de este Label
                double lblX = lbl.getLayoutX();
                double lblY = lbl.getLayoutY();
                double lblW = lbl.getWidth() > 0 ? lbl.getWidth() : lbl.getPrefWidth();
                double lblH = lbl.getHeight() > 0 ? lbl.getHeight() : lbl.getPrefHeight();

                if (imgCenterX >= lblX && imgCenterX <= (lblX + lblW) &&
                        imgCenterY >= lblY && imgCenterY <= (lblY + lblH)) {

                    // Guardar estilo original antes de cambiarlo
                    if (!lbl.getProperties().containsKey("originalStyle")) {
                        lbl.getProperties().put("originalStyle", lbl.getStyle());
                    }

                    lbl.setStyle("-fx-background-color: #808080; -fx-background-radius: 15; -fx-effect: none;");
                }
            }
        }
    }

    // Esta función coloca las imagenes en el tablero
    private void configurarImagenes(AnchorPane root) {
        // Paso por cada nodo del ArnchorPnae
        for (Node node : root.getChildren()) {
            // Compruebo si es un ImageView
            if (node instanceof ImageView) {
                // Si lo es lo guardo en una variable
                ImageView iv = (ImageView) node;

                // Filtramos para afectar solo a las cartas (pequeñas) y no al fondo o
                // previsualizacion
                if (iv != imagenGrande && iv.getFitHeight() < 500) {
                    // Si la imagen es la pequeña le cargo los eventos
                    iv.setOnMouseEntered(event -> aumentarImagen(iv));
                    iv.setOnMouseExited(event -> restaurarImagen(iv));
                    iv.setOnMouseClicked(event -> verificarVictoria(iv));
                    iv.setCursor(javafx.scene.Cursor.HAND);
                }
            }
        }
    }

    private void verificarVictoria(ImageView iv) {
        // Ignorar clics en personajes eliminados
        if (iv.getOpacity() < 1.0)
            return;

        com.example.quienesquien.Modelos.Personaje p = obtenerPersonajePorImagen(iv);
        if (p == null || personajeOculto == null)
            return;

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText(null);

        // Desactivar interacciones de todas las cartas para evitar superposiciones
        // (z-index)
        if (iv.getParent() instanceof AnchorPane) {
            AnchorPane root = (AnchorPane) iv.getParent();
            for (Node node : root.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView img = (ImageView) node;
                    if (img != imagenGrande && img.getFitHeight() < 500) {
                        img.setOnMouseEntered(null);
                        img.setOnMouseExited(null);
                        img.setOnMouseClicked(null);
                        img.setCursor(javafx.scene.Cursor.DEFAULT);
                    }
                }
            }
        }

        if (p.getNombre().equals(personajeOculto.getNombre())) {
            // Victory -> Show TV screen
            if (paneTelevision != null && lblTvTexto != null) {
                guardarPartida();
                lblTvTexto.setText("¡VICTORIA!\n\n" + p.getNombre().toUpperCase() + " HA SIDO ELIMINADO DE LA SERIE");
                lblTvTexto.setStyle("-fx-text-fill: #00ff00;");
                paneTelevision.setVisible(true);
                paneTelevision.toFront();

                if (btnTvContinuar != null) {
                    btnTvContinuar.setOnAction(e -> {
                        paneTelevision.setVisible(false);
                        reiniciarJuego();
                    });
                }
                return; // Wait for user to click continue
            } else {
                // Fallback if FXML not loaded correctly
                alert.setTitle("¡Victoria!");
                alert.setContentText(
                        "¡Felicidades! Has adivinado correctamente. El personaje era " + personajeOculto.getNombre());
            }

        } else {
            // Defeat -> Show TV screen
            if (paneTelevision != null && lblTvTexto != null) {
                guardarPartida();
                lblTvTexto
                        .setText("¡HAS FALLADO!\n\nPOR TU FALLO HAN ELIMINADO A UN PERSONAJE INOCENTE\n(El oculto era "
                                + personajeOculto.getNombre().toUpperCase() + ")");
                lblTvTexto.setStyle("-fx-text-fill: #ff0000;");
                paneTelevision.setVisible(true);
                paneTelevision.toFront();

                if (btnTvContinuar != null) {
                    btnTvContinuar.setOnAction(e -> {
                        paneTelevision.setVisible(false);
                        reiniciarJuego();
                    });
                }
                return; // Wait for user to click continue
            } else {
                alert.setTitle("Derrota");
                alert.setAlertType(AlertType.ERROR);
                alert.setContentText("Has fallado. El personaje oculto era " + personajeOculto.getNombre());
            }
        }

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            reiniciarJuego();
        }
    }

    private void reiniciarJuego() {
        seleccionarPersonajeOculto();
        preguntasRestantes = 5;
        if (lblIntentos != null) {
            lblIntentos.setText("Preguntas: " + preguntasRestantes);
        }
        if (paneTelevision != null)
            paneTelevision.setVisible(false);
        if (comboPreguntas != null)
            comboPreguntas.setDisable(false);
        if (btnEnviar != null)
            btnEnviar.setDisable(false);
        if (lblRespuesta != null)
            lblRespuesta.setText("");

        if (btnEnviar != null && btnEnviar.getParent() instanceof AnchorPane) {
            AnchorPane root = (AnchorPane) btnEnviar.getParent();
            for (Node node : root.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView iv = (ImageView) node;
                    if (iv == imagenGrande || iv.getFitHeight() > 500)
                        continue;

                    // Restaurar opacidad y eventos
                    iv.setOpacity(1.0);
                    iv.setCursor(javafx.scene.Cursor.HAND);
                    iv.setOnMouseEntered(event -> aumentarImagen(iv));
                    iv.setOnMouseExited(event -> restaurarImagen(iv));
                    iv.setOnMouseClicked(event -> verificarVictoria(iv));
                } else if (node instanceof javafx.scene.control.Label) {
                    // Restaurar fondo de etiquetas si se cambió
                    javafx.scene.control.Label lbl = (javafx.scene.control.Label) node;

                    if (lbl.getProperties().containsKey("originalStyle")) {
                        lbl.setStyle((String) lbl.getProperties().get("originalStyle"));
                    } else if (lbl.getStyle().contains("-fx-background-color: #808080")) {
                        // Fallback por si acaso
                        lbl.setStyle(
                                "-fx-background-color: linear-gradient(to bottom right, #7a82eb, #6972E0); -fx-background-radius: 15; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 5, 0, 2, 2);");
                    }
                }
            }
        }
    }

    // Método auxiliar para obtener el objeto Personaje dada su ImageView (usando
    // nombre de archivo)
    private com.example.quienesquien.Modelos.Personaje obtenerPersonajePorImagen(ImageView iv) {
        String url = null;

        // Si tiene originalUrl (está en zoom), usamos esa
        if (iv.getProperties().containsKey("originalUrl")) {
            url = (String) iv.getProperties().get("originalUrl");
        }

        // Si no, usamos la actual
        if (url == null && iv.getImage() != null) {
            url = iv.getImage().getUrl();
        }

        if (url == null)
            return null;

        for (com.example.quienesquien.Modelos.Personaje p : listaPersonajes) {
            // Buscamos coincidencia flexible
            // La imagen en el modelo es por ejemplo "danny"
            // La url contiene "dannymini" o "danny"
            if (url.toLowerCase().contains(p.getImagen().toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    private void aumentarImagen(ImageView iv) {
        // Guardar estado original si no existe
        if (!iv.getProperties().containsKey("originalX"))

        {
            iv.getProperties().put("originalX", iv.getLayoutX());
            iv.getProperties().put("originalY", iv.getLayoutY());
            iv.getProperties().put("originalUrl", iv.getImage().getUrl());
        }

        // Calcular el centro visual actual
        double centerX = iv.getLayoutX() + iv.getLayoutBounds().getWidth() / 2;
        double centerY = iv.getLayoutY() + iv.getLayoutBounds().getHeight() / 2;

        iv.toFront();

        // Escalar
        ScaleTransition st = new ScaleTransition(Duration.millis(200), iv);
        st.setToX(1.5);
        st.setToY(1.5);
        st.play();

        // Cambiar a imagen HD
        Image currentImage = iv.getImage();
        if (currentImage != null) {
            String url = currentImage.getUrl();
            if (url != null && url.contains("mini")) {
                String newUrl = url.replace("mini", "");
                if (url.contains("Aleitamini")) {
                    newUrl = url.replace("Aleitamini", "Aelita");
                }

                try {
                    // Cargar síncronamente para poder recalcular posición
                    Image bigImage = new Image(newUrl, false);
                    if (!bigImage.isError()) {
                        iv.setImage(bigImage);
                        iv.setViewport(null);

                        // Recalcular Layout X/Y para mantener el centro
                        double newWidth = iv.getLayoutBounds().getWidth();
                        double newHeight = iv.getLayoutBounds().getHeight();

                        iv.setLayoutX(centerX - newWidth / 2);
                        iv.setLayoutY(centerY - newHeight / 2);
                    }
                } catch (Exception e) {
                }
            }
        }
    }

    private void restaurarImagen(ImageView iv) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), iv);
        st.setToX(1.0);
        st.setToY(1.0);
        st.play();

        // Restaurar posición e imagen original
        if (iv.getProperties().containsKey("originalUrl")) {
            String originalUrl = (String) iv.getProperties().get("originalUrl");
            Double originalX = (Double) iv.getProperties().get("originalX");
            Double originalY = (Double) iv.getProperties().get("originalY");

            try {
                iv.setImage(new Image(originalUrl, false));
                iv.setViewport(null);
                if (originalX != null)
                    iv.setLayoutX(originalX);
                if (originalY != null)
                    iv.setLayoutY(originalY);
            } catch (Exception e) {
            }
        }
    }

    private void mostrarAyuda() {
        if (paneAyuda != null) {
            paneAyuda.setVisible(true);
            paneAyuda.toFront();
            gestionarInteraccionesAyuda(false);
        }
    }

    private void gestionarInteraccionesAyuda(boolean activar) {
        if (btnEnviar != null && btnEnviar.getParent() instanceof AnchorPane) {
            AnchorPane root = (AnchorPane) btnEnviar.getParent();
            for (Node node : root.getChildren()) {
                if (node instanceof ImageView) {
                    ImageView img = (ImageView) node;
                    if (img != imagenGrande && img.getFitHeight() < 500) {
                        img.setDisable(!activar);
                    }
                }
            }
            comboPreguntas.setDisable(!activar);
            btnEnviar.setDisable(!activar);
        }
    }
}
