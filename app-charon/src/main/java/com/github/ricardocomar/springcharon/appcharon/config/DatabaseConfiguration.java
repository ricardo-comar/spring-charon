package com.github.ricardocomar.springcharon.appcharon.config;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.ricardocomar.springcharon.appcharon.sync.repository.SyncControlRepository;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity;

@Configuration
@EnableJpaRepositories(basePackageClasses = SyncControlRepository.class)
@EntityScan(basePackageClasses = SyncControlEntity.class)
@EnableTransactionManagement
public class DatabaseConfiguration {

	@Bean
	public PlatformTransactionManager transactionManager(@Autowired final EntityManagerFactory emf) {
		final JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(emf);
		return transactionManager;
	}
}