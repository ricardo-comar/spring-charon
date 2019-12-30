package com.github.ricardocomar.springcharon.appowner.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.github.ricardocomar.springcharon.appowner.repository.entity.SyncControlEntity;

public interface SyncControlRepository extends CrudRepository<SyncControlEntity, Long> {

	List<SyncControlEntity> findByDomainOrderBySequenceDesc(String domain);

	List<SyncControlEntity> findBySyncId(String syncId);
}
