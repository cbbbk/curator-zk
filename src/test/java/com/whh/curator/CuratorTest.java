package com.whh.curator;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

public class CuratorTest {
    private CuratorFramework client;

    @BeforeEach
    public void before() {
        client = CuratorFrameworkFactory.builder()
                .connectString("121.40.137.85:2181")
                .connectionTimeoutMs(5 * 100)
                .namespace("whh")
                .sessionTimeoutMs(5 * 100)
                .retryPolicy(new ExponentialBackoffRetry(5000, 1)).build();
        client.start();
    }

    @AfterEach
    public void after() {
        if (client != null) {
            client.close();
        }
    }

    @Test
    public void testCreate() throws Exception {
        // 创建结点没有指定数据，默认当前客户端ip地址
        // 多级结点 creatingParentsIfNeeded()
        String s = client.create()
                .creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/app2/p1", "pp".getBytes(StandardCharsets.UTF_8));
        System.out.println(s);
    }

    @Test
    public void testGet() throws Exception {
        Stat stat = new Stat();
        byte[] bytes = client.getData().storingStatIn(stat).forPath("/app2/p1");
        //List<String> list = client.getChildren().forPath("/");
        System.out.println(new String(bytes));
        System.out.println(stat);
    }

    @Test
    public void testSet() throws Exception {
        Stat stat = new Stat();
        client.getData().storingStatIn(stat).forPath("/app2/p1");
        client.setData().withVersion(stat.getVersion()).forPath("/app2/p1", "haha".getBytes());
    }

    @Test
    public void testDelete() throws Exception {
        // client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/app2");
        client.delete().guaranteed().inBackground((curatorFramework, curatorEvent) -> System.out.println(curatorEvent)).forPath("/app2");
    }

    @Test
    public void testWatcher1() throws Exception {
        NodeCache nodeCache = new NodeCache(client, "/app1");
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                System.out.print("Node Change：");
                byte[] data = nodeCache.getCurrentData().getData();
                System.out.println(new String(data));
            }
        });
        nodeCache.start(true);
        while (true) {

        }
    }

    @Test
    public void testWatcher2() throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(client, "/app2", true);
        pathChildrenCache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                System.out.println(event);
                if (event.getType().equals(PathChildrenCacheEvent.Type.CHILD_UPDATED)) {
                    byte[] data = event.getData().getData();
                    System.out.println(new String(data));
                }
            }
        });
        pathChildrenCache.start();
        while (true) {

        }
    }

    @Test
    public void testWatcher3() throws Exception {
        TreeCache treeCache = new TreeCache(client, "/app2");
        treeCache.getListenable().addListener(new TreeCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, TreeCacheEvent event) throws Exception {
                System.out.println(event);
                if (event.getType().equals(TreeCacheEvent.Type.NODE_UPDATED)) {
                    byte[] data = event.getData().getData();
                    System.out.println(new String(data));
                }
            }
        });
        treeCache.start();
        while (true) {

        }
    }
}
