package ru.otus.cachehw;


import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private enum Events {
        BeforePut ("BeforePut"),
        AfterPut ("AfterPut"),
        BeforeRemove ("BeforeRemove"),
        AfterRemove ("AfterRemove"),
        BeforeGet("BeforeGet");

        private final String title;
        Events(String title) {
            this.title = title;
        }
    }

    private long cacheMissing = 0;
    private long cacheMaxSize = 0;
    private final WeakHashMap<K,V> hashMap = new WeakHashMap<>();
    private final WeakHashMap<HwListener<K,V>, Integer> listeners = new WeakHashMap<>();

    @Override
    public void put(K key, V value) {
        listeners.forEach((listener, dummy) -> listener.notify(key, value, Events.BeforePut.title));
        hashMap.put(key,value);
        listeners.forEach((listener, dummy) -> listener.notify(key, value, Events.AfterPut.title));

        cacheMaxSize = Math.max(cacheMaxSize, hashMap.size());
    }

    @Override
    public void remove(K key) {
        listeners.forEach((listener, dummy) -> listener.notify(key, hashMap.get(key), Events.BeforeRemove.title));

        if (hashMap.containsKey(key)) hashMap.remove(key);
        else ++cacheMissing;

        listeners.forEach((listener, dummy) -> listener.notify(key, null, Events.AfterRemove.title));

        cacheMaxSize = Math.max(cacheMaxSize, hashMap.size());
    }

    @Override
    public V get(K key) {
        cacheMaxSize = Math.max(cacheMaxSize, hashMap.size());

        listeners.forEach((listener, dummy) -> listener.notify(key, hashMap.get(key), Events.BeforeGet.title));

        if (hashMap.containsKey(key)) return hashMap.get(key);

        ++cacheMissing;
        return null;
    }

    public boolean containsKey(K key) {
        return hashMap.containsKey(key);
    }

    public long getCacheSize() {
        return hashMap.size();
    }

    public long getCacheMaxSize() {
        return cacheMaxSize;
    }

    public void setCacheMaxSize(long cacheMaxSize) {
        this.cacheMaxSize = cacheMaxSize;
    }

    public long getCacheMissing() {
        return cacheMissing;
    }

    public void setCacheMissing(long cacheMissing) {
        this.cacheMissing = cacheMissing;
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.put(listener, null);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
