package ru.otus.cachehw;

import org.apache.commons.collections.ArrayStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author sergey
 * created on 14.12.18.
 */
public class MyCache<K, V> implements HwCache<K, V> {
    private final Map<K, V> cache = new WeakHashMap<>();
    private final List<HwListener<K, V>> listeners = new ArrayList<>();
//Надо реализовать эти методы

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, value, "added to cache");
        }
    }

    @Override
    public void remove(K key) {
        var value = cache.get(key);
        if (value != null) {
            cache.remove(key);
        }
        for (HwListener<K, V> listener : listeners) {
            listener.notify(key, value, "removed from cache");
        }
    }

    @Override
    public V get(K key) {
        return cache.get(key);
    }

    @Override
    public void addListener(HwListener<K, V> listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener<K, V> listener) {
        listeners.remove(listener);
    }
}
