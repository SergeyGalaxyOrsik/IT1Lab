module org.example.encryptionlaba1 {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.example.encryptionlaba1.controllers to javafx.fxml; // Открываем контроллеры для JavaFX

    exports org.example.encryptionlaba1;
}
