package com.github.ricardocomar.springcharon.appcharon.flow.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.model.PurchaseAvro;

@MessageEndpoint
public class PurchaseRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseRouterConfig.class);

	@Router(inputChannel = "purchaseRouterChannel")
	public String purchaseAvroEnricher(final Message<PurchaseAvro> msg) {

		final String destination = "purchaseTransformerChannel";
		LOGGER.info("Message will be routed to: ", destination);

		return destination;
	}

}
