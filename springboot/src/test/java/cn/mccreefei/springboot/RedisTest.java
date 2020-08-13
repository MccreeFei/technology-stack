package cn.mccreefei.springboot;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author MccreeFei
 * @create 2020-08-13 上午11:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Resource
    @Qualifier("limitLua")
    private RedisScript<Long> limitLuaScript;
    @Resource
    @Qualifier("zsetLimitLua")
    private RedisScript<List> zsetLimitLuaScript;
    @Test
    public void testRedis() {
        String key = "redisKey";
        String value = "redisValue";
        redisTemplate.opsForValue().set(key, value);
        String fromValue = redisTemplate.opsForValue().get(key);
        Assert.assertEquals(fromValue, value);
        redisTemplate.delete(key);
        Assert.assertNull(redisTemplate.opsForValue().get(key));
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void testLimitLuaScript() {
        //60s内调用两次以内返回1 大于两次返回0
        List<String> keys = Collections.singletonList("limitLua");
        String expire = "60";
        String limitTimes = "2";
        long first = redisTemplate.execute(limitLuaScript, keys, expire, limitTimes);
        long second = redisTemplate.execute(limitLuaScript, keys, expire, limitTimes);
        long third = redisTemplate.execute(limitLuaScript, keys, expire, limitTimes);
        Assert.assertEquals(first, 1L);
        Assert.assertEquals(second, 1L);
        Assert.assertEquals(third, 0L);
        redisTemplate.delete(keys);

    }

    @Test
    @SuppressWarnings("all")
    public void testZsetLimitLuaScript() {
        //zset 取出不大于size字节数的数据并删除， 如果只有1条则忽视size限制
        String key = "zsetLimitLua";
        String valueNineBytes = "MccreeFei";
        String valueTenBytes = "helloWorld";
        String valueFiveBytes = "chris";
        redisTemplate.delete(key);
        redisTemplate.opsForZSet().add(key, valueTenBytes, 2.0);
        redisTemplate.opsForZSet().add(key, valueNineBytes, 3.0);
        redisTemplate.opsForZSet().add(key, valueFiveBytes, 1.0);
        List<String> keys = Collections.singletonList(key);

        //test 16 bytes
        String limitBytes = "16";
        List res = redisTemplate.execute(zsetLimitLuaScript, keys, limitBytes);
        Assert.assertEquals(Objects.requireNonNull(res).size(), 2);
        Assert.assertTrue(res.containsAll(Arrays.asList(valueFiveBytes, valueTenBytes)));
        Assert.assertFalse(redisTemplate.opsForZSet().range(key, 0, -1).contains(valueTenBytes));
        Assert.assertTrue(redisTemplate.opsForZSet().range(key, 0, -1).contains(valueNineBytes));

        redisTemplate.delete(key);

        redisTemplate.opsForZSet().add(key, valueTenBytes, 2.0);
        redisTemplate.opsForZSet().add(key, valueNineBytes, 3.0);
        redisTemplate.opsForZSet().add(key, valueFiveBytes, 1.0);
        // test 4 bytes
        limitBytes = "4";
        res = redisTemplate.execute(zsetLimitLuaScript, keys, limitBytes);
        Assert.assertEquals(Objects.requireNonNull(res).size(), 1);
        Assert.assertTrue(res.contains(valueFiveBytes));
        Assert.assertFalse(res.contains(valueNineBytes));
        redisTemplate.delete(key);

        // test zset null
        res = redisTemplate.execute(zsetLimitLuaScript, keys, limitBytes);
        Assert.assertEquals(Objects.requireNonNull(res).size(), 0);


    }

}
