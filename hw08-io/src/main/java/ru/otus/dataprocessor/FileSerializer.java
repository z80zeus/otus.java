package ru.otus.dataprocessor;

import com.google.gson.Gson;

import java.io.*;
import java.util.Map;

/**
 * Сериализатор данных в json-файл.
 */
public class FileSerializer implements Serializer {

    public FileSerializer(String fileName) {
        this.filename = fileName;
    }

    /**
     * Сеарилизовать словарь String->Double в файл filename.
     * @param data Словарь для сеарилизации.
     */
    @Override
    public void serialize(Map<String, Double> data) {
        final var gson = new Gson();
        try (var jsonFile = new FileWriter(filename)) {
            final var json = gson.toJson(data);
            jsonFile.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final String filename;
}
