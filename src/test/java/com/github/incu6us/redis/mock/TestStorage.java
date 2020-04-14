package com.github.incu6us.redis.mock;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TestStorage extends CrudRepository<TestModel, String> {
}
