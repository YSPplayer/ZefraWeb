package com.zefra.service;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ServerThreadPool implements ServletContextListener {
    public static ExecutorService executor = Executors.newFixedThreadPool(2);
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {}
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // 停止线程池
        executor.shutdown();
        try {
            // 等待线程池中的任务完成
            if (!executor.awaitTermination(10, TimeUnit.SECONDS)) {
                // 超过指定时间后强制关闭线程池
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            // 异常处理
            Thread.currentThread().interrupt();
        }
        System.out.println("终止线程完毕");
    }
}
