package com.github.ricardocomar.springcharon.appcharon.sync;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ricardocomar.springcharon.appcharon.sync.repository.SyncControlRepository;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity.SyncState;

@Service
public class SyncControlService {

	@Autowired
	private SyncControlRepository repo;

	public SyncControlEntity getCurrentControl(final Object domain) {
		return repo.findById(Optional.ofNullable((String) domain).orElse(null)).orElse(null);
	}

	public Boolean updateControl(final String domain, final Integer sequence, final String state) {

		final SyncControlEntity entity = repo.findById(domain).get();
		entity.setState(SyncState.valueOf(state));
		entity.setSequence(sequence);
		repo.save(entity);

		return true;
		// TODO save history
	}
}
