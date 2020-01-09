package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.model.Purchase;

@Configuration
public class PurchaseSequenceFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseSequenceFilterConfig.class);

//	private static final ObjectMapper MAPPER = new ObjectMapper() //
//			.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) //
//			.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false) //
//			.registerModule(new ParameterNamesModule()) //
//			.registerModule(new Jdk8Module()) //
//			.registerModule(new JavaTimeModule());

	@Filter(inputChannel = "purchaseSequenceChannel", outputChannel = "purchaseRouterChannel", discardChannel = "purchaseReorderChannel")
	public boolean purchaseSequenceChannel(final Message<Purchase> msg) {

		LOGGER.info("Message sequence will be checked: {}", msg);

		final Purchase purchase = msg.getPayload();

		purchase.setSyncKey(purchase.getId());
		purchase.setSyncTransaction((String) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_ID));
		purchase.setSyncSequence("10");

		return true;
	}

	@ServiceActivator(inputChannel = "purchaseReorderChannel")
	public void purchaseReorderChannel(final Message<Purchase> msg) {

		LOGGER.warn("Message out of sequence, will be denied: {}", msg);
		throw new RuntimeException("denying message...");
	}

}
