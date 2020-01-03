package com.github.ricardocomar.springcharon.appowner.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ricardocomar.springcharon.appowner.entrypoint.model.PurchaseItemRequest;
import com.github.ricardocomar.springcharon.appowner.entrypoint.model.PurchaseRequest;
import com.github.ricardocomar.springcharon.appowner.exception.UnavailableResponseException;
import com.github.ricardocomar.springcharon.appowner.mapper.PurchaseMapper;
import com.github.ricardocomar.springcharon.appowner.repository.PurchaseRepository;
import com.github.ricardocomar.springcharon.appowner.repository.entity.PurchaseEntity;
import com.github.ricardocomar.springcharon.appowner.repository.entity.PurchaseEntity.PurchaseStatus;
import com.github.ricardocomar.springcharon.appowner.sync.EventUpdateNotifier;
import com.github.ricardocomar.springcharon.appowner.sync.model.ModelUpdateEvent;
import com.github.ricardocomar.springcharon.appowner.sync.model.Purchase;
import com.github.ricardocomar.springcharon.appowner.sync.trancode.TrancodeTransformer;
import com.google.common.base.Optional;

@Service
public class PurchaseProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseProcessor.class);

	@Autowired
	private PurchaseRepository repo;

	@Autowired
	private PurchaseMapper mapper;

	@Autowired
	private TrancodeTransformer transformer;

	@Autowired
	private EventUpdateNotifier notifier;

	@Transactional(value = TxType.REQUIRED)
	public void handle(final PurchaseRequest request) throws UnavailableResponseException {

		LOGGER.debug("Message to be processed: {}", request);
		
		final PurchaseEntity entity = repo.findByRequestId(request.getId()).orElse(new PurchaseEntity());
		entity.setCustomer(request.getCustomer());
		entity.setRequestId(request.getId());
		entity.setTotalValue(Optional.fromNullable(entity.getTotalValue()).or(BigDecimal.ZERO));
		entity.setStatus(PurchaseStatus.valueOf(request.getStatus()));
		entity.setDate(request.getDate());
		repo.save(entity);

		final Purchase model = mapper.fromEntity(entity);

		notifier.notifyUpdate(new ModelUpdateEvent(model.getTransaction(), transformer.toTrancode(model)));
	}

	@Transactional(value = TxType.REQUIRED)
	public void handle(final PurchaseItemRequest request) {

	}

}