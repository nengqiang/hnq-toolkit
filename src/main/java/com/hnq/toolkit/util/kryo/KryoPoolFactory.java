package com.hnq.toolkit.util.kryo;

import com.esotericsoftware.kryo.Kryo;
import com.hnq.toolkit.util.concurrent.ObjectPoolFactory;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * Kryo对象池管理类
 *
 * @author henengqiang
 * @date 2019/07/01
 */
public class KryoPoolFactory {

    private static final GenericObjectPool<Kryo> OBJECT_POOL;

    static {
        GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
        conf.setMinIdle(1);
        conf.setMaxIdle(5);
        conf.setMaxTotal(50);

        OBJECT_POOL = ObjectPoolFactory.createObjectPool(Kryo.class, conf);
    }

    private KryoPoolFactory() {
    }

    public static GenericObjectPool<Kryo> get() {
        return OBJECT_POOL;
    }

}
