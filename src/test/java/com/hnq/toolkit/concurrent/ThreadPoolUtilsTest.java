package com.hnq.toolkit.concurrent;

import com.hnq.toolkit.concurrent.ThreadPoolUtils;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author henengqiang
 * @date 2019/05/16
 */
class ThreadPoolUtilsTest {

    @Test
    void hello() {
        System.out.println("Hello");
    }

    @Test
    void threadSwitchingTest() throws InterruptedException {
        Lock lock = new ReentrantLock();
        Condition subThreadCondition = lock.newCondition();
        boolean shouldSubThread = false;
    }

    static class TaskA implements Runnable {

        private Integer i;

        void setI(Integer i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("TaskA: " + i);
        }

    }

    static class TaskB implements Runnable {

        private Integer i;

        void setI(Integer i) {
            this.i = i;
        }

        @Override
        public void run() {
            System.out.println("TaskB: " + i);
        }

    }

    @Test
    void threadPoolUtilsTest() throws ExecutionException, InterruptedException {
        long start = System.currentTimeMillis();
        List<Future<String>> futureList = Lists.newArrayList();
        // 发送10次消息
        for (int i = 0; i < 10; i++) {
            try {
                Future<String> messageFuture = ThreadPoolUtils.submit(new TestCallable(String.format("这是第{%s}条消息", i)));
                futureList.add(messageFuture);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        for (Future<String> message : futureList) {
            String messageData = message.get();
            System.out.println(messageData);
        }
        System.out.println(String.format("共计耗时{%s}毫秒", System.currentTimeMillis() - start));
        ThreadPoolUtils.shutdown();
    }

    static class TestCallable implements Callable<String> {

        private String message;

        TestCallable(String message) {
            this.message = message;
        }

        @Override
        public String call() throws Exception {
            Thread.sleep(300);
            System.out.println(String.format("打印消息%s", message));
            return "OK";
        }
    }

}