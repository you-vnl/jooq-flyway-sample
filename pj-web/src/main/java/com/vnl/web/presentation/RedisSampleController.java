package com.vnl.web.presentation;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Redisのサンプル実装コントローラーです。
 */
@RestController
@RequestMapping(value = "/SampleRedis")
@RequiredArgsConstructor
public class RedisSampleController {

    private final StringRedisTemplate redisTemplate;

    @PostMapping
    public void post(@RequestBody RedisSampleData redisSampleData) {
        redisTemplate.delete("redis-tutorial:string");
        redisTemplate.opsForValue().set("redis-tutorial:string", redisSampleData.getString());

        redisTemplate.delete("redis-tutorial:list");
        redisTemplate.opsForList().rightPushAll("redis-tutorial:list", redisSampleData.getList().toArray(new String[0]));

        redisTemplate.delete("redis-tutorial:map");
        redisTemplate.opsForHash().putAll("redis-tutorial:map", redisSampleData.getMap());
    }

    @GetMapping
    public RedisSampleData get(final String identifier) {
        RedisSampleData redisSampleData = new RedisSampleData();
        redisSampleData.setString(redisTemplate.opsForValue().get(identifier));
        redisSampleData.setList(redisTemplate.opsForList().range("redis-tutorial:list", 0, -1));
        redisSampleData.setMap(redisTemplate.<String, String>opsForHash().entries("redis-tutorial:map"));
        return redisSampleData;
    }

    @Data
    static class RedisSampleData {

        private String string;

        private List<String> list;

        private Map<String, String> map;

    }
}