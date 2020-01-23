package com.github.ricardocomar.springcharon.appcharon.flow.router;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;
import com.github.ricardocomar.springcharon.appcharon.model.Purchase;

@MessageEndpoint
public class NotificationRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(NotificationRouterConfig.class);

	@Router(inputChannel = CharonFlowConstants.FLOW_10_ROUTING_NOTIFICATION_CHANNEL)
	public List<String> purchaseRouterChannel(final Message<Purchase> msg) {

		final List<String> destination = Collections
				.singletonList(CharonFlowConstants.FLOW_11_OUTBOUND_MIRROR_ENRICHER_CHANNEL);// "purchaseTransformerChannel";
		LOGGER.info("Message will be routed to: {}", destination);

		return destination;
	}

}
