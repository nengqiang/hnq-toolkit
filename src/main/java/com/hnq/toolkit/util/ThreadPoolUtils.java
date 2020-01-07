package com.hnq.toolkit.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author henengqiang
 * @date 2019/03/29
 */
public class ThreadPoolUtils {

    /**
     * 根据cpu的数量动态的配置核心线程数和最大线程数
     */
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    /**
     * 核心线程数 = CPU核心数 + 1
     * 对于计算密集型的任务，在拥有N_cpu = Runtime.getRuntime().availableProcessors()个处理器的系统上，
     * 当线程池的大小为N_cpu + 1 时，通常能实现最优的利用率
     *
     * 对于IO密集型任务，1.尽可能配多 CPU核心数 * 2， 2.CPU核心数 / (1-阻塞系数(0.8~0.9))
     */
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    /**
     * 线程池最大线程数 = CPU核心数 * 2 + 1
     */
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    /**
     * 非核心线程闲置时超时
     */
    private static final int KEEP_ALIVE = 60;

    private static ThreadPoolExecutor threadPool;

    /**
     * namedThreadFactory
     */
    private static ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("hanif-pool-%d").build();

    /**
     * 无返回值直接执行
     */
    public static void execute(Runnable runnable) {
        getThreadPool().execute(runnable);
    }

    /**
     * 返回值直接执行
     */
    public static <T> Future<T> submit(Callable<T> callable) {
        return getThreadPool().submit(callable);
    }

    /**
     * 关闭线程池
     */
    public static void shutdown() {
        getThreadPool().shutdown();
    }

    /**
     * 获取线程池
     *
     * @return 线程池对象
     */
    public static ThreadPoolExecutor getThreadPool() {
        if (threadPool != null) {
            return threadPool;
        } else {
            synchronized (ThreadPoolUtils.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPoolExecutor(
                            CORE_POOL_SIZE,
                            MAXIMUM_POOL_SIZE,
                            KEEP_ALIVE,
                            TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(32),
                            namedThreadFactory,
                            new ThreadPoolExecutor.CallerRunsPolicy()
                    );
                }
                return threadPool;
            }
        }
    }

}
