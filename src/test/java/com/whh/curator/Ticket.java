package com.whh.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.retry.ExponentialBackoffRetry;

import java.util.concurrent.TimeUnit;

public class Ticket implements Runnable {
    private int tickets = 10;

    private InterProcessMutex lock;

    public Ticket() {
        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString("121.40.137.85:2181")
                .connectionTimeoutMs(5 * 100)
                .sessionTimeoutMs(5 * 100)
                .namespace("whh")
                .retryPolicy(new ExponentialBackoffRetry(5000, 1)).build();
        client.start();
        this.lock = new InterProcessMutex(client, "/lock");
    }

    @Override
    public void run() {
        while (true) {
            try {
                lock.acquire(3, TimeUnit.SECONDS);
                if (tickets > 0) {
                    System.out.println(Thread.currentThread() + ":" + tickets);
                    tickets--;
                } else break;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    lock.release();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
