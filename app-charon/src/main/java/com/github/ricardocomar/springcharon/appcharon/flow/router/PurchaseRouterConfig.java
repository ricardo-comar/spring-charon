package com.github.ricardocomar.springcharon.appcharon.flow.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.model.Purchase;

@MessageEndpoint
public class PurchaseRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseRouterConfig.class);

	@Router(inputChannel = "purchaseRouterChannel")
	public String purchaseRouterChannel(final Message<Purchase> msg) {

		final String destination = "purchaseOutboundEnricherChannel";// "purchaseTransformerChannel";
		LOGGER.info("Message will be routed to: {}", destination);

		return destination;
	}

}
