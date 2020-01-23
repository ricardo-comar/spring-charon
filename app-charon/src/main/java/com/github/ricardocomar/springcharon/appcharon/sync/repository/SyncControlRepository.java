package com.github.ricardocomar.springcharon.appcharon.sync.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity;

public interface SyncControlRepository extends CrudRepository<SyncControlEntity, String> {

	List<SyncControlEntity> findByDomainOrderBySequenceDesc(String domain);

}
