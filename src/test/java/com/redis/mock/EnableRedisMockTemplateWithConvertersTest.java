package com.redis.mock;

import com.redis.mock.converters.UUIDToBytesConverter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisKeyValueAdapter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@EnableRedisMockTemplate(converters = {UUIDToBytesConverter.class})
public class EnableRedisMockTemplateWithConvertersTest {

    @Autowired
    private UUIDToBytesConverter uuidToBytesConverter;

    @Autowired
    private RedisKeyValueAdapter redisKeyValueAdapter;

    @Test
    public void converterBeanTest() {
        assertThat(uuidToBytesConverter.convert(UUID.randomUUID())).isInstanceOf(byte[].class);
    }

    @Test
    public void redisKVAdapter_withCustomConverterTest() {
        assertThat(redisKeyValueAdapter.toBytes(UUID.randomUUID())).isInstanceOf(byte[].class);
    }
}
