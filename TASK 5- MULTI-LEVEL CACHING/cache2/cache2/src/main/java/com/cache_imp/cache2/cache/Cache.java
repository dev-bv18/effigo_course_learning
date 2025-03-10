package com.cache_imp.cache2.cache;

import java.util.Collection;

public interface Cache<K, V> {
    V get(K key);
    void put(K key, V value);
    void evict(K key);

    void evictAll();

    Collection<K> getAllKeys();
}