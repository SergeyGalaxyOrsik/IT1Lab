package org.example.encryptionlaba1.models;

import java.util.Map;

public class VigenereCipher {
    private static final String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";

    // Метод для создания матрицы Виженера
    public static char[][] createVigenereMatrix() {
        char[][] matrix = new char[ALPHABET.length()][ALPHABET.length()];

        // Заполняем матрицу сдвигающимися строками
        for (int i = 0; i < ALPHABET.length(); i++) {
            for (int j = 0; j < ALPHABET.length(); j++) {
                // Каждая строка сдвигается на 1 позицию
                matrix[i][j] = ALPHABET.charAt((i + j) % ALPHABET.length());
            }
        }
        return matrix;
    }
    public static String encrypt(String text, String key) {
        // Заменяем все символы, кроме русских букв и нижнего регистра
        text = text.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();
        key = key.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();

        // Создание расширенного ключа
        StringBuilder extendedKey = new StringBuilder(key);
        while (extendedKey.length() < text.length()) {
            extendedKey.append(text.charAt(extendedKey.length() - key.length()));
        }
        extendedKey.setLength(text.length());
        System.out.println("Extended Key: " + extendedKey);

        StringBuilder encrypted = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            int index = (ALPHABET.indexOf(text.charAt(i))+ALPHABET.indexOf(extendedKey.charAt(i))) % ALPHABET.length();
            encrypted.append(ALPHABET.charAt(index));
        }


        return encrypted.toString();
    }

    public static String decrypt(String text, String key) {
        key = key.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();
        StringBuilder decrypted = new StringBuilder();

        int keyIndex = 0;
        int resIndex = 0;
        StringBuilder keyBuilder = new StringBuilder(key.toLowerCase());
        while (keyIndex < text.length())
        {
            if (keyIndex == keyBuilder.length())
                keyBuilder.append(decrypted.charAt(resIndex++));
            int index = (ALPHABET.indexOf(text.charAt(keyIndex))- ALPHABET.indexOf(keyBuilder.charAt(keyIndex)) + ALPHABET.length()) % ALPHABET.length();
            decrypted.append(ALPHABET.charAt(index));
            keyIndex++;
        }
        key = keyBuilder.toString();

        return decrypted.toString();
    }
}
