package ru.otus.cachehw;

/**
 * Кэш
 * @param <K> Ключ.
 * @param <V> Значение.
 */
public interface HwCache<K, V> {

    /**
     * Поместить значение в кэш.
     * @param key Ключ, с которым ассоциировано значение.
     * @param value Значение, которое следует поместить в кэш.
     */
    void put(K key, V value);

    /**
     * Удалить значение из кэша.
     * @param key Ключ, с которым ассоциировано удаляемое значение.
     */
    void remove(K key);

    /**
     * Получить значение из кэша.
     * @param key Ключ, с которым ассоциировано требуемое значение.
     * @return Значение, ассоциированное с ключом key.
     */
    V get(K key);

    /**
     * Добавить прослушивателя событий в кэше.
     * @param listener Прослушиватель.
     */
    void addListener(HwListener<K, V> listener);

    /**
     * Удалить прослушивателя событий в кэше.
     * @param listener Удаляемый прослушиватель.
     */
    void removeListener(HwListener<K, V> listener);
}
