package com.cache_imp.cache2.cache;

import com.cache_imp.cache2.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.Cursor;

import java.util.HashSet;
import java.util.Set;

@Component
public class RedisCache implements Cache<String, User> {

    private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

    private final RedisTemplate<String, User> redisTemplate;

    @Autowired
    public RedisCache(RedisTemplate<String, User> redisTemplate) {
        this.redisTemplate = redisTemplate;
        logger.info(" RedisCache initialized with RedisTemplate: {}", redisTemplate);
    }

    @Override
    public User get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void put(String key, User value) {
        redisTemplate.opsForValue().set(key, value);
        logger.info("Added to Redis Cache - Key: {}", key);
    }

    @Override
    public void evict(String key) {
        redisTemplate.delete(key);
        logger.info("Evicted from Redis Cache - Key: {}", key);
    }

    @Override
    public void evictAll() {
        redisTemplate.getConnectionFactory().getConnection().flushAll();
        logger.info("✅ Cleared all entries from Redis Cache.");
    }

    @Override
    public Set<String> getAllKeys() {
        Set<String> keys = new HashSet<>();
        Cursor<byte[]> cursor = redisTemplate.getConnectionFactory().getConnection().scan(ScanOptions.scanOptions().build());

        while (cursor.hasNext()) {
            keys.add(new String(cursor.next()));
        }

        logger.info("Retrieved {} keys from Redis Cache.", keys.size());
        return keys;
    }
}
