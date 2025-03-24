package com.cache_imp.cache2.controller;

import com.cache_imp.cache2.cache.CaffeineCache;
import com.cache_imp.cache2.cache.MultiCache;
import com.cache_imp.cache2.cache.RedisCache;
import com.cache_imp.cache2.entity.User;
import com.cache_imp.cache2.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping("/api/cache")
public class CacheController {

    private static final Logger logger = LoggerFactory.getLogger(CacheController.class);

    private final MultiCache<String, User> multiCache;
    private final CaffeineCache<String, User> caffeineCache;
    private final RedisCache redisCache;
    private final UserService userService;

    @Autowired
    public CacheController(CaffeineCache<String, User> caffeineCache, RedisCache redisCache, UserService userService) {
        this.caffeineCache = caffeineCache;
        this.redisCache = redisCache;

        this.multiCache = new MultiCache<>(List.of(caffeineCache, redisCache));
        this.userService = userService;
    }

    @PostMapping("/add")
    public String addUserToCacheAndDatabase(@RequestBody User user) {
        logger.info("Adding user '{}' to cache and database...", user.getUsername());
        multiCache.put(user.getUsername(), user);
        logger.info("User '{}' added to cache successfully.", user.getUsername());

        userService.addUserToDatabase(user);
        logger.info("User '{}' added to the database successfully.", user.getUsername());

        return "User added to cache and database successfully.";
    }

    @GetMapping("/get/{username}")
    public User getUserFromCache(@PathVariable String username) {
        logger.info("Fetching user '{}' from cache...", username);

        User user = multiCache.get(username);
        if (user != null) {
            logger.info("User '{}' found in cache.", username);
            return user;
        }

        logger.warn("User '{}' not found in cache, fetching from database...", username);
        user = userService.getUserFromDatabase(username);
        if (user != null) {
            multiCache.put(username, user);
            logger.info("User '{}' fetched from database and cached.", username);
        } else {
            logger.warn("User '{}' not found in database either.", username);
        }

        return user;
    }

    @DeleteMapping("/evict/{username}")
    public String evictUserFromCache(@PathVariable String username) {
        logger.info("Evicting user '{}' from cache...", username);
        multiCache.evict(username);
        logger.info("User '{}' evicted from cache successfully.", username);
        return "User evicted from cache successfully.";
    }

    @DeleteMapping("/clear")
    public String clearAllCache() {
        logger.info("Clearing all caches...");
        multiCache.evictAll();
        logger.info("All caches cleared successfully.");
        return "All caches cleared successfully.";
    }

    @GetMapping("/exists/{username}")
    public boolean checkIfUserExistsInDatabase(@PathVariable String username) {
        boolean exists = userService.checkIfUserExists(username);
        if (exists) {
            logger.info("User '{}' exists in the database.", username);
        } else {
            logger.warn("User '{}' does not exist in the database.", username);
        }
        return exists;
    }

    @DeleteMapping("/delete/{username}")
    public String deleteUserFromCacheAndDatabase(@PathVariable String username) {
        logger.info("Deleting user '{}' from cache and database...", username);
        multiCache.evict(username);
        logger.info("User '{}' evicted from cache.", username);

        boolean deleted = userService.deleteUserFromDatabase(username);
        if (deleted) {
            logger.info("User '{}' deleted from database successfully.", username);
        } else {
            logger.warn("User '{}' could not be deleted from database.", username);
        }

        return "User deleted from cache and database successfully.";
    }

    @PutMapping("/update/{username}")
    public String updateUserInCacheAndDatabase(@PathVariable String username, @RequestBody User user) {
        logger.info("Updating user '{}' in cache and database...", username);

        multiCache.put(username, user);
        logger.info("User '{}' updated in cache.", username);

        User updatedUser = userService.updateUserInDatabase(username, user);
        if (updatedUser != null) {
            logger.info("User '{}' updated in database successfully.", username);
        } else {
            logger.warn("User '{}' could not be updated in database.", username);
        }

        return "User updated in cache and database successfully.";
    }

    @GetMapping("/all/from-db")
    public List<User> getAllUsersFromDatabase() {
        logger.info("Fetching all users from database...");
        return userService.getAllUsers();
    }

    @GetMapping("/all/from-cache")
    public List<User> getAllUsersFromCache() {
        logger.info("Fetching all users from cache...");
        return multiCache.getAllKeys().stream()
                .map(multiCache::get)
                .toList();
    }

    @GetMapping("/caffeine/keys")
    public Set<String> getCaffeineKeys() {
        logger.info("Fetching all keys from Caffeine cache...");
        return caffeineCache.getAllKeys();
    }
}


