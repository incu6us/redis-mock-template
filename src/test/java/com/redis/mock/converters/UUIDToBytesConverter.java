package com.redis.mock.converters;

import org.springframework.core.convert.converter.Converter;

import java.util.UUID;

public class UUIDToBytesConverter implements Converter<UUID, byte[]> {
    @Override
    public byte[] convert(UUID source) {
        return source.toString().getBytes();
    }
}
