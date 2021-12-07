package ru.otus.dataprocessor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.otus.model.Measurement;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Загрузчик json-файла.
 */
public class FileLoader implements Loader {

    /**
     * Конструктору передаётся имя файла для загрузки.
     * @param fileName Имя json-файла из которого будет производиться чтение json.
     */
    public FileLoader(String fileName) {
        this.filename = fileName;
    }

    /**
     * Функция парсит файл, имя которого было передано в конструкторе как json и возвращает его содержимое в виде
     * списка объектов Measurement.
     * @return Список объектов Measurement или null в случае неудачи.
     */
    @Override
    public List<Measurement> load() {
        final var gson = new Gson();
        var collectionType = new TypeToken<List<Measurement>>(){}.getType();
        try (final var jsonStream = FileLoader.class.getClassLoader().getResourceAsStream(filename)) {
            var json = new BufferedReader(new InputStreamReader(jsonStream)).
                    lines().
                    map(line -> gson.fromJson(line,
                            collectionType)).findFirst();
            if (json.isEmpty())
                return null;
            //noinspection unchecked
            return (List<Measurement>) json.get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private final String filename;
}
