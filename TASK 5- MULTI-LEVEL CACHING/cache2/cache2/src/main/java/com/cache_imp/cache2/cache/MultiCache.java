package com.cache_imp.cache2.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MultiCache<K, V> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(MultiCache.class);

    private final List<Cache<K, V>> caches;  // Using generic Cache type here

    public MultiCache(List<Cache<K, V>> caches) {
        if (caches == null || caches.isEmpty()) {
            throw new IllegalArgumentException("Caches cannot be null or empty");
        }
        this.caches = caches;
    }

    @Override
    public V get(K key) {
        for (Cache<K, V> cache : caches) {
            V value = cache.get(key);
            if (value != null) {
                logger.info("✅ Found in cache: {}", cache.getClass().getSimpleName());

                // Only promote to the top cache (if it's not already the top cache)
                if (cache != caches.get(0)) {
                    logger.info("⬆️ Promoting to top cache: {}", caches.get(0).getClass().getSimpleName());
                    caches.get(0).put(key, value); // Promote to the top cache (Caffeine)
                }
                return value;
            }
        }
        return null;
    }

    @Override
    public void put(K key, V value) {
        caches.forEach(cache -> {
            cache.put(key, value);
            logger.info("✅ Saved to cache: {}", cache.getClass().getSimpleName());
        });
    }

    @Override
    public void evict(K key) {
        caches.forEach(cache -> {
            cache.evict(key);
            logger.info("❌ Evicted from cache: {}", cache.getClass().getSimpleName());
        });
    }

    @Override
    public void evictAll() {
        caches.forEach(Cache::evictAll);
        logger.info("✅ Cleared all caches.");
    }

    @Override
    public Collection<K> getAllKeys() {
        Set<K> allKeys = new HashSet<>();
        for (Cache<K, V> cache : caches) {
            Collection<K> keys = cache.getAllKeys();
            if (keys != null) {
                allKeys.addAll(keys);
            }
        }
        return allKeys;
    }
}
