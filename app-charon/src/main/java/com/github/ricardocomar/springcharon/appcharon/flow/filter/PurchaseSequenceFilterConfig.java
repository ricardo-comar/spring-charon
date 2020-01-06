package com.github.ricardocomar.springcharon.appcharon.flow.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.model.Purchase;

@Configuration
public class PurchaseSequenceFilterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(PurchaseSequenceFilterConfig.class);

	@Filter(inputChannel = "purchaseSequenceChannel", outputChannel = "purchaseRouterChannel", discardChannel = "purchaseReorderChannel")
	public boolean filterPurchaseSequenceMessage(final Message<Purchase> msg) {

		LOGGER.info("Message sequence will be checked: {}", msg);

		return true;
	}

	@ServiceActivator(inputChannel = "purchaseReorderChannel")
	public void discardPurchaseSequenceMessage(final Message<Purchase> msg) {

		LOGGER.warn("Message will be discarded: {}", msg);
	}
}
