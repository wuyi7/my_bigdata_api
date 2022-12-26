package com.wuyi.redis;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;
import java.util.Set;

/**
 * @Author: WuYi at 2022-11-24 11:04
 * @Description:
 * @Version:1.0
 */
public class RedisTest {
    private JedisPool jedisPool;
    private JedisPoolConfig config;

    @BeforeTest
    public void redisConnectionPool() {
        config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxWaitMillis(3000);
        config.setMaxTotal(50);
        config.setMinIdle(5);
        jedisPool = new JedisPool(config, "hadoop102", 6379);
    }

    @Test
    public void testConnection() {
        Jedis jedis = jedisPool.getResource();
        Set<String> keySet = jedis.keys("*");
        for (String s : keySet) {
            System.out.println(s + " ");
        }
    }
    @Test
    public void stringOpTest() {
        Jedis connection = jedisPool.getResource();
        // 1.  添加一个string类型数据，key为pv，初始值为0
        connection.set("pv", "0");

        // 2.  查询该key对应的数据
        System.out.println("原始pv为:" + connection.get("pv"));

        // 3.  修改pv为1000
        connection.set("pv", "1000");
        System.out.println("修改pv为:" + connection.get("pv"));

        // 4.  实现整形数据原子自增操作 +1
        connection.incr("pv");
        System.out.println("pv自增1：" + connection.get("pv"));

        // 5.  实现整形该数据原子自增操作 +1000
        connection.incrBy("pv", 1000);
        System.out.println("pv自增1000：" + connection.get("pv"));

    }
    @Test
    public void hashOpTest() {
        Jedis connection = jedisPool.getResource();
        // 1.  往Hash结构中添加以下商品库存
        // a)  iphone11 => 10000
        // b)  macbookpro => 9000
        connection.hset("goodsStore", "iphone11", "10000");
        connection.hset("goodsStore", "macbookpro", "9000");

        // 2.  获取Hash中所有的商品
        Map<String, String> keyValues = connection.hgetAll("goodsStore");
        for (String s : keyValues.keySet()) {
            System.out.println(s + " => " + keyValues.get(s));
        }

        // 3.  修改Hash中macbookpro数量为12000
        // 方式一：
        // connection.hset("goodsStore", "macbookpro", "12000");
        // 方式二：
        connection.hincrBy("goodsStore", "macbookpro", 3000);
        System.out.println("新增3000个库存后：macbookpro => " + connection.hget("goodsStore", "macbookpro"));

        // 4.  删除整个Hash的数据
        connection.del("goodsStore");
    }

    @AfterTest
    public void closePool() {
        jedisPool.close();
    }
}
