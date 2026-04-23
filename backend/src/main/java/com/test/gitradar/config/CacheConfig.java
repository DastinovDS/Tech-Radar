package com.test.gitradar.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.test.gitradar.handlers.CustomCacheErrorHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {

    @Value("${SERVER_CACHE_TLL}")
    private int cacheTll;

    @Bean
    public CacheErrorHandler cacheErrorHandler() {
        return new CustomCacheErrorHandler();
    }

    @Bean
    public CacheManager cacheManager() {
        Caffeine<Object, Object> caffeine = Caffeine.newBuilder()
                .expireAfterWrite(cacheTll, TimeUnit.SECONDS)
                .maximumSize(200);

        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();

        caffeineCacheManager.setCaffeine(caffeine);

        return caffeineCacheManager;
    }
}
