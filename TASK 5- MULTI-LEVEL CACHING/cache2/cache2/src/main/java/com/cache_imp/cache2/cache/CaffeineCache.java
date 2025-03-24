package com.cache_imp.cache2.cache;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class CaffeineCache<K, V> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(CaffeineCache.class);

    private final com.github.benmanes.caffeine.cache.Cache<K, V> cache;

    public CaffeineCache() {
        cache = Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }

    @Override
    public V get(K key) {
        V value = cache.getIfPresent(key);
        if (value != null) {
            logger.info(" Cache HIT for key: {}", key);
        } else {
            logger.warn(" Cache MISS for key: {}", key);
        }
        return value;
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
        logger.info("Added to Caffeine Cache - Key: {}", key);
    }

    @Override
    public void evict(K key) {
        cache.invalidate(key);
        logger.info("Evicted from Caffeine Cache - Key: {}", key);
    }

    @Override
    public void evictAll() {
        cache.invalidateAll();
        logger.info("Cleared all entries from Caffeine Cache.");
    }

    @Override
    public Set<K> getAllKeys() {
        return cache.asMap().keySet();
    }
}
