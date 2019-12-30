package com.github.ricardocomar.springcharon.appowner.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.ricardocomar.springcharon.appowner.repository.SyncControlRepository;
import com.github.ricardocomar.springcharon.appowner.repository.entity.SyncControlEntity;

@EnableJpaRepositories(basePackageClasses = SyncControlRepository.class)
@EntityScan(basePackageClasses = SyncControlEntity.class)
@EnableTransactionManagement
public class DatabaseConfiguration {
}