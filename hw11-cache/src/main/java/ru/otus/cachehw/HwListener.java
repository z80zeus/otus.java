package ru.otus.cachehw;

/**
 * Прослушиватель событий кэша.
 * @param <K> Ключ кэша.
 * @param <V> Значение кэша.
 */
public interface HwListener<K, V> {
    /**
     * Оповещение о событии в кэше.
     * @param key Ключ кэша, по которому произошло событие.
     * @param value Значение кэша, по которому произошло событие.
     * @param action Событие: BeforePut, AfterPut, BeforeRemove, AfterRemove, BeforeGet, AfterGet.
     */
    void notify(K key, V value, String action);
}
