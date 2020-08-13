package cn.mccreefei.springboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;

/**
 * @author MccreeFei
 * @create 2020-08-13 下午2:10
 */
@Configuration
public class LuaConfig {
    @Bean("limitLua")
    public RedisScript<Long> limitLuaScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(Long.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/limit.lua")));
        return redisScript;
    }

    @Bean("zsetLimitLua")
    public RedisScript<List> zsetLimitLuaScript() {
        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(List.class);
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("lua/zsetLimit.lua")));
        return redisScript;
    }
}
