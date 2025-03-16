package org.example.encryptionlaba1.controllers;

import org.example.encryptionlaba1.models.ColumnarCipher;
import org.example.encryptionlaba1.models.VigenereCipher;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MainController {
    @FXML private TextField keyField;
    @FXML private TextArea outputArea;
    @FXML
    private TextArea inputTextArea;
    private File selectFile;

    @FXML
    private void selectFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл");
        selectFile = fileChooser.showOpenDialog(new Stage());
        try {
            String content = new String(Files.readAllBytes(Paths.get(selectFile.toURI())), "UTF-8");
            inputTextArea.setText(content);
        } catch (Exception e) {
            outputArea.setText("Ошибка при обработке файла.");
        }
    }

    @FXML
    private void encryptColumnar() {
        processEncryption(true);
    }

    @FXML
    private void decryptColumnar() {
        processEncryption(false);
    }
    @FXML
    private void saveOutputToFile() {
        saveToFile(outputArea.getText());
    }


    @FXML
    private void encryptVigenere() {
        processVigenere(true);
    }

    @FXML
    private void decryptVigenere() {
        processVigenere(false);
    }

    private void processEncryption(boolean isEncrypt) {
        String text = inputTextArea.getText();
        String key = keyField.getText();

        if (text.isEmpty() || key.isEmpty()) {
            showError("Введите текст и ключ");
            return;
        }

        String result = isEncrypt ? ColumnarCipher.encrypt(text, key) : ColumnarCipher.decrypt(text, key);
        outputArea.setText(result);
    }

    private void processVigenere(boolean isEncrypt) {
        String text = inputTextArea.getText();
        String key = keyField.getText();

        if (text.isEmpty() || key.isEmpty()) {
            showError("Введите текст и ключ");
            return;
        }

        String result = isEncrypt ? VigenereCipher.encrypt(text, key) : VigenereCipher.decrypt(text, key);
        outputArea.setText(result);
    }

//    private void processFile(boolean encrypt, boolean isColumnar) {
//        if (selectFile == null || keyField.getText().isEmpty()) {
//            outputArea.setText("Ошибка: выберите файл и введите ключ.");
//            return;
//        }
//        try {
//            String content = new String(Files.readAllBytes(Paths.get(selectFile.toURI())), "UTF-8");
//            String key = keyField.getText();
//            String result = encrypt
//                    ? (isColumnar ? ColumnarCipher.encrypt(content, key) : VigenereCipher.encrypt(content, key))
//                    : (isColumnar ? ColumnarCipher.decrypt(content, key) : VigenereCipher.decrypt(content, key));
//            outputArea.setText(result);
//        } catch (Exception e) {
//            outputArea.setText("Ошибка при обработке файла.");
//        }
//    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void saveToFile(String text) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Сохранить файл");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Текстовые файлы (*.txt)", "*.txt"));

        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write(text);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
