package org.example.encryptionlaba1.models;

import java.util.*;

public class ColumnarCipher {
    public static String encrypt(String text, String key) {
        text = text.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();
        key = key.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();
        System.out.println("Text: " + text);
        int columns = key.length();
        int rows = (int) Math.ceil((double) text.length() / columns);
        char[][] grid = new char[rows][columns];

        for (int i = 0, index = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                grid[i][j] = index < text.length() ? text.charAt(index++) : ' ';
            }
        }

        System.out.println(Arrays.deepToString(grid));

        Integer[] order = getKeyOrder(key);
//        Arrays.sort(order);
        StringBuilder encryptedText = new StringBuilder();
        List<Integer> list = new ArrayList<>(List.of(order));
        Set<Integer> processedIndices = new HashSet<>();  // Track processed indices

        while (processedIndices.size() < order.length) {
            // Find the index of the minimum value that hasn't been processed
            int minIndex = -1;
            int minValue = Integer.MAX_VALUE;
            for (int i = 0; i < list.size(); i++) {
                int value = list.get(i);
                if (!processedIndices.contains(i) && value < minValue) {
                    minValue = value;
                    minIndex = i;
                }
            }

            // Use the minIndex to process the corresponding column in the grid
            for (int row = 0; row < rows; row++) {
                encryptedText.append(grid[row][minIndex]);
            }

            // Mark this index as processed
            processedIndices.add(minIndex);

            // For debugging, print the intermediate encrypted text
            System.out.println(encryptedText);
        }

        return encryptedText.toString();
    }

    public static String decrypt(String text, String key) {
        key = key.replaceAll("[^а-яА-ЯёЁ]", "").toLowerCase();
        int columns = key.length();
        int rows = (int) Math.ceil((double) text.length() / columns);
        char[][] grid = new char[rows][columns];

        Integer[] order = getKeyOrder(key);
        System.out.println(Arrays.toString(order));
        int index = 0;
        for (int i = 0; i < columns; i++) {
            int col = List.of(order).indexOf(i);
            System.out.println(col);
            for (int row = 0; row < rows; row++) {
                if (index < text.length()) {
                    grid[row][col] = text.charAt(index++);
                }
            }
        }


        System.out.println(Arrays.deepToString(grid));

        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                decryptedText.append(grid[i][j]);
            }
        }
        return decryptedText.toString().trim();
    }


    private static Integer[] getKeyOrder(String key) {
        int length = key.length();
        Integer[] order = new Integer[length];
        String ALPHABET = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя";
        // Преобразуем ключ в массив символов
        Character[] sortedKey = key.chars().mapToObj(c -> (char) c).toArray(Character[]::new);
        System.out.println(Arrays.toString(sortedKey));
        // Кастомный компаратор для сортировки
        Arrays.sort(sortedKey, (c1, c2) -> {
            int lp = ALPHABET.indexOf(c1);
            int rp = ALPHABET.indexOf(c2);
            if (lp != -1 && rp != -1) {
                return Integer.compare(lp, rp);
            }
            return Character.compare(c1, c2); // Обычная сортировка для остальных букв
        });

        System.out.println("Отсортированный ключ: " + Arrays.toString(sortedKey));

        // Создаём мапу: символ → очередь индексов
        Map<Character, Queue<Integer>> indexMap = new LinkedHashMap<>();
        for (int i = 0; i < length; i++) {
            indexMap.putIfAbsent(sortedKey[i], new LinkedList<>());
            indexMap.get(sortedKey[i]).add(i);
        }

        // Заполняем order, используя очередь индексов для каждого символа
        for (int i = 0; i < length; i++) {
            char c = key.charAt(i);
            order[i] = indexMap.get(c).poll(); // Получаем и удаляем следующий индекс из очереди
        }

        return order;
    }

}
