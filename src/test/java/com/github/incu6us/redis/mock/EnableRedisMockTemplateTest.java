package com.github.incu6us.redis.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableRedisMockTemplate
public class EnableRedisMockTemplateTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void isRedisTemplateLoaded() {
        assertThat(redisTemplate).isNotNull();
    }

    @Test
    public void storeAndFind() {
        redisTemplate.opsForHash().put("test-key", "test-hash-key", "value123");
        assertThat(redisTemplate.opsForHash().get("test-key", "test-hash-key")).isEqualTo("value123");
    }

    @Test
    public void delete() {
        redisTemplate.opsForHash().put("test-key", "test-hash-key", "value123");
        redisTemplate.opsForHash().delete("test-key", "test-hash-key");

        assertThat(redisTemplate.opsForHash().get("test-key", "test-hash-key")).isNull();
    }
}
