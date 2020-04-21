redis-mock-template
---

[![Maven Central](https://img.shields.io/maven-central/v/com.github.incu6us.redis/redis-mock-template.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.github.incu6us.redis%22%20AND%20a:%22redis-mock-template%22)
<a href="https://github.com/incu6us/redis-mock-template"><img alt="test status" src="https://github.com/incu6us/redis-mock-template/workflows/test/badge.svg"></a>
<a href="https://github.com/incu6us/redis-mock-template"><img alt="build status" src="https://github.com/incu6us/redis-mock-template/workflows/build/badge.svg"></a>
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/ef814db7dcdf4490a24e9008f6b7927f)](https://www.codacy.com/manual/incu6us/redis-mock-template?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=incu6us/redis-mock-template&amp;utm_campaign=Badge_Grade)

Redis Stateful Mock for testing specific cases(based on [mock-jedis](https://github.com/50onRed/mock-jedis)).

Additional details you could find on [Medium](https://link.medium.com/H6acnrS5R5).

### Add dependency to project
```xml
<dependency>
  <groupId>com.github.incu6us.redis</groupId>
  <artifactId>redis-mock-template</artifactId>
  <version>0.0.2</version>
</dependency>
```

### Example of usage
```java
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
```
