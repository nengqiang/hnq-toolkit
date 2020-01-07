package com.hnq.toolkit.util.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableMap;
import com.google.common.util.concurrent.UncheckedExecutionException;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author henengqiang
 * @date 2019/09/19
 */
public class GuavaCache<K, V> implements Cache<K, V> {

    private final Cache<K, V> instance;

    public GuavaCache(long timeout, @Nonnull TimeUnit unit, int maxSize) {
        this.instance = CacheBuilder
                .newBuilder()
                .maximumSize(maxSize)
                .expireAfterAccess(timeout, unit)
                .softValues()
                .build();
    }

    public static GuavaCache<Object, Object> get() {
        return Holder.INSTANCE;
    }

    public static <K, V> GuavaCache<K, V> getInstance(long timeout, @Nonnull TimeUnit unit, int maxSize) {
        return new GuavaCache<>(timeout, unit, maxSize);
    }

    @Override
    public V getIfPresent(@Nonnull Object key) {
        return instance.getIfPresent(key);
    }

    @Override
    public V get(@Nonnull K key, @Nonnull Callable<? extends V> valueLoader)
            throws ExecutionException {
        return instance.get(key, valueLoader);
    }

    @Override
    public ImmutableMap<K, V> getAllPresent(@Nonnull Iterable<?> keys) {
        return instance.getAllPresent(keys);
    }

    @Override
    public void put(@Nonnull K key, @Nonnull V value) {
        instance.put(key, value);
    }

    @Override
    public void putAll(@Nonnull Map<? extends K, ? extends V> m) {
        instance.putAll(m);
    }

    @Override
    public void invalidate(@Nonnull Object key) {
        instance.invalidate(key);
    }

    @Override
    public void invalidateAll(@Nonnull Iterable<?> keys) {
        instance.invalidateAll(keys);
    }

    @Override
    public void invalidateAll() {
        instance.invalidateAll();
    }

    @Override
    public long size() {
        return instance.size();
    }

    @Override
    public CacheStats stats() {
        return instance.stats();
    }

    @Override
    public ConcurrentMap<K, V> asMap() {
        return instance.asMap();
    }

    @Override
    public void cleanUp() {
        instance.cleanUp();
    }

    public V getUnchecked(@Nonnull K key, @Nonnull Callable<? extends V> valueLoader) {
        try {
            return get(key, valueLoader);
        } catch (ExecutionException e) {
            throw new UncheckedExecutionException(e.getCause());
        }
    }

    private static class Holder {

        private static final GuavaCache<Object, Object> INSTANCE = new GuavaCache<>(1, TimeUnit.HOURS, 100);

        private Holder() {
        }
    }
}
