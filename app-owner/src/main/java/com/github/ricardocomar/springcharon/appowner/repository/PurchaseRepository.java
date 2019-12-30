package com.github.ricardocomar.springcharon.appowner.repository;

import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;

import com.github.ricardocomar.springcharon.appowner.repository.entity.PurchaseEntity;

public interface PurchaseRepository extends CrudRepository<PurchaseEntity, Long> {

	@Override
	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "1000") })
	Optional<PurchaseEntity> findById(Long id);

	@Lock(LockModeType.PESSIMISTIC_READ)
	@QueryHints({ @QueryHint(name = "javax.persistence.lock.timeout", value = "1000") })
	Optional<PurchaseEntity> findByRequestId(String requestId);

}
