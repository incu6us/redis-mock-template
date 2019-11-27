redis-mock-template
---

<a href="https://github.com/incu6us/redis-mock-template"><img alt="test status" src="https://github.com/incu6us/redis-mock-template/workflows/test/badge.svg"></a>
<a href="https://github.com/incu6us/redis-mock-template"><img alt="build status" src="https://github.com/incu6us/redis-mock-template/workflows/build/badge.svg"></a>


Redis Stateful Mock for testing specific cases(based on [mock-jedis](https://github.com/50onRed/mock-jedis)).

### Add dependency to project:
```
<dependency>
  <groupId>com.github.incu6us.redis</groupId>
  <artifactId>redis-mock-template</artifactId>
  <version>0.0.1</version>
</dependency>
```

### Example of usage:
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
