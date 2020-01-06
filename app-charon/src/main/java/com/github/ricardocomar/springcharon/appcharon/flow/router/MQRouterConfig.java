package com.github.ricardocomar.springcharon.appcharon.flow.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

@MessageEndpoint
public class MQRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(MQRouterConfig.class);

	@Router(inputChannel = "jmsPurchaseRouterChannel")
	public String purchaseMQEnricher(final Message<String> msg) {

		final String destination = "purchaseFilterChannel";
		LOGGER.info("Message will be routed to: ", destination);

		return destination;
	}

}
