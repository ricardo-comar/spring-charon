package com.github.ricardocomar.springcharon.appcharon.flow.router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.model.PurchaseAvro;

@MessageEndpoint
public class ModelRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelRouterConfig.class);

	@Router(inputChannel = "modelRouterChannel")
	public String purchaseAvroEnricher(final Message<PurchaseAvro> msg) {

		final String inboundType = (String) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE);
		switch (inboundType) {

		case "TRANPURC-1":
			return "purchaseSequenceChannel";

		default: 
			LOGGER.info("Unexpected inbound type: " + inboundType);
			
		}

		return "modelDiscardChannel";
		
	}

}
