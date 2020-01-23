package com.github.ricardocomar.springcharon.appcharon.flow.router;

import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;
import com.github.ricardocomar.springcharon.appcharon.model.Purchase;

@MessageEndpoint
public class FlowRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(FlowRouterConfig.class);

	@Router(inputChannel = CharonFlowConstants.FLOW_9_ROUTING_FLOW_CHANNEL)
	public List<String> modelRouterChannel(final Message<Purchase> msg) {

		final String inboundType = (String) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE);
		switch (inboundType) {

		case "TRANPURC-1":
			return Collections.singletonList(CharonFlowConstants.FLOW_10_ROUTING_NOTIFICATION_CHANNEL);

		default: 
			LOGGER.info("Unexpected inbound type: " + inboundType);
			
		}

		return Collections.singletonList(CharonFlowConstants.FLOW_SEQ_BREAKER_CHANNEL);
		
	}

}
