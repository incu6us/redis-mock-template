package com.redis.mock;

import com.redis.mock.core.RedisMockTemplateConfiguration;
import org.springframework.boot.test.autoconfigure.OverrideAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@OverrideAutoConfiguration(enabled = true)
@RedisMockConvertersConfiguration
@Import(RedisMockTemplateConfiguration.class)
public @interface EnableRedisMockTemplate {

    @AliasFor(annotation = RedisMockConvertersConfiguration.class, attribute = "classes")
    Class<?>[] converters() default {};
}
