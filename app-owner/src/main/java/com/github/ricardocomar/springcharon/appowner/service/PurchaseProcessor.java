package com.github.ricardocomar.springcharon.appowner.service;

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
import com.github.ricardocomar.springcharon.appowner.sync.EventUpdateNotifier;
import com.github.ricardocomar.springcharon.appowner.sync.model.ModelUpdateEvent;
import com.github.ricardocomar.springcharon.appowner.sync.model.Purchase;
import com.github.ricardocomar.springcharon.appowner.sync.trancode.TrancodeTransformer;

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
		
		final PurchaseEntity entity = repo.findByRequestId(request.getId()).get();
		final Purchase model = mapper.fromEntity(entity);

		notifier.notifyUpdate(new ModelUpdateEvent(model.getTransaction(), transformer.toTrancode(model)));
	}

	@Transactional(value = TxType.REQUIRED)
	public void handle(final PurchaseItemRequest request) {

	}

}