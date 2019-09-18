package com.gooalgene.wutbiolab.util;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.gooalgene.wutbiolab.constant.CommonConstants;
import com.gooalgene.wutbiolab.exception.WutbiolabException;
import com.gooalgene.wutbiolab.response.common.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;

import java.util.concurrent.TimeUnit;

@Slf4j
public class CacheUtil {

    public static void setToken(String key,Object value){
        set(CommonConstants.TOKEN_CACHE,key,value);
    }
    public static Object getToken(String key){
        return get(CommonConstants.TOKEN_CACHE, key);
    }

    public static void deleteToken(String key){
        delete(CommonConstants.TOKEN_CACHE,key);
    }
    public static CacheManager getCacheManager(){
        CacheManager cacheManager = SpringContextHolder.getBean(CacheManager.class);
        if(cacheManager==null){
            throw new WutbiolabException(ResultCode.CACHE_MANAGER_NOT_FOUND);
        }
        return cacheManager;
    }


    public static void set(String cacheName,String key,Object value){
        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        cache.put(key,value);
    }

    public static Object get(String cacheName,String key){
        if(key==null){
            return null;
        }
        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);

        Cache.ValueWrapper valueWrapper = cache.get(key);
        if(valueWrapper==null){
            return null;
        }
        return valueWrapper.get();
    }
    public static void delete(String cacheName,String key){
        CacheManager cacheManager = getCacheManager();
        Cache cache = cacheManager.getCache(cacheName);
        cache.evict(key);
    }


    public static void main(String[] args) {
        CaffeineCache caffeineCache = new CaffeineCache(CommonConstants.TOKEN_CACHE,
                Caffeine.newBuilder().recordStats()
                        .expireAfterAccess(7200, TimeUnit.SECONDS)
                        .maximumSize(2000)
                        .build());

        caffeineCache.putIfAbsent("xx","xx");
        Object xx = caffeineCache.get("xx");
        Object o = ((Cache.ValueWrapper) xx).get();
        caffeineCache.evict("xx");
        Object xx2 =caffeineCache.get("xx");
        Object o1 = ((Cache.ValueWrapper) xx2).get();
        System.out.println(1);
    }
}

