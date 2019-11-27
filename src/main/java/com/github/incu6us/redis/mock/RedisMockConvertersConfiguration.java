package com.github.incu6us.redis.mock;

import com.github.incu6us.redis.mock.core.RedisMockConvertersConfigurationImportSelector;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@EnableAutoConfiguration
@Import(RedisMockConvertersConfigurationImportSelector.class)
public @interface RedisMockConvertersConfiguration {
    Class<?>[] classes() default {};
}
