package ru.otus.cachehw;

public class DummyCache<T,V> implements HwCache<T,V>{
    @Override
    public void put(T key, V value) {
    }

    @Override
    public void remove(T key) {

    }

    @Override
    public V get(T key) {
        return null;
    }

    @Override
    public void addListener(HwListener<T, V> listener) {

    }

    @Override
    public void removeListener(HwListener<T, V> listener) {

    }
}
