package com.test.gitradar.handlers;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.Cache;
import org.springframework.cache.interceptor.CacheErrorHandler;

public class CustomCacheErrorHandler implements CacheErrorHandler {

    private static final Logger log = LoggerFactory.getLogger(CustomCacheErrorHandler.class);

    @Override
    public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {
        log.warn("[CACHE WARNING] GET ERROR");
    }

    @Override
    public void handleCachePutError(RuntimeException exception, Cache cache, Object key, @Nullable Object value) {
        log.warn("[CACHE WARNING] PUT ERROR");
    }

    @Override
    public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {
        log.warn("[CACHE WARNING] DELETE ERROR"); // Deletes specific record in cache
    }

    @Override
    public void handleCacheClearError(RuntimeException exception, Cache cache) {
        log.warn("[CACHE WARNING] CLEAR ERROR"); // Clears the whole cache container
    }
}
