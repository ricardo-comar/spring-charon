package com.github.ricardocomar.springcharon.appowner.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.github.ricardocomar.springcharon.appowner.entrypoint.model.ProcessRequest;
import com.github.ricardocomar.springcharon.appowner.exception.UnavailableResponseException;
import com.github.ricardocomar.springcharon.appowner.sync.model.ModelUpdateEvent;

@Service
public class ConcurrentProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentProcessor.class);

	@Autowired
	private ApplicationContext appCtx;

	public void handle(final ProcessRequest request) throws UnavailableResponseException {

		LOGGER.debug("Message to be processed: {}", request);

		appCtx.publishEvent(new ModelUpdateEvent("REQUEST", request.getPayload()));
	}

}
