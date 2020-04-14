package com.github.incu6us.redis.mock;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Value
@RedisHash
public class TestModel {
    String id;
    String data;
}
