module com.example.quienesquien {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.example.quienesquien.Controlador to javafx.fxml;

    exports com.example.quienesquien.Controlador;
    exports com.example.quienesquien.Modelos;
    exports com.example.quienesquien;
}