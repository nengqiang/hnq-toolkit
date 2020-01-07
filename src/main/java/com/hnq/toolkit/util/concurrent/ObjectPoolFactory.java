package com.hnq.toolkit.util.concurrent;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import javax.annotation.Nonnull;

/**
 * The object pool factory.
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class ObjectPoolFactory {

    private ObjectPoolFactory() {
    }

    @SuppressWarnings("unchecked")
    public static <T> GenericObjectPool<T> createObjectPool(@Nonnull final Class<T> clazz, final GenericObjectPoolConfig config) {
        return new GenericObjectPool<>(new BasePooledObjectFactory<T>() {
            @Override
            public T create() throws Exception {
                return clazz.newInstance();
            }

            @Override
            public PooledObject<T> wrap(T object) {
                return new DefaultPooledObject<>(object);
            }
        }, config);
    }

}
