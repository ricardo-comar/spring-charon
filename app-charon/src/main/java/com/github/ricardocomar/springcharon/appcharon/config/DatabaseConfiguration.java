package com.github.ricardocomar.springcharon.appcharon.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.ricardocomar.springcharon.appcharon.sync.repository.SyncControlRepository;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity;

@EnableJpaRepositories(basePackageClasses = SyncControlRepository.class)
@EntityScan(basePackageClasses = SyncControlEntity.class)
@EnableTransactionManagement
public class DatabaseConfiguration {
}