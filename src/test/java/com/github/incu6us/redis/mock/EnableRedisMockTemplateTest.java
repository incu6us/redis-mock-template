package com.github.incu6us.redis.mock;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableRedisMockTemplate
public class EnableRedisMockTemplateTest {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private TestStorage storage;
    

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

    @Test
    public void createAndGetUsingRepository() {
        storage.save(TestModel.builder()
                .id("1")
                .data("test")
                .build());

        assertThat(storage.findById("1")).isEqualTo(Optional.of(TestModel.builder()
                .id("1")
                .data("test")
                .build()));
    }
}
