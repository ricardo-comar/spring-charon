package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

@Configuration
public class MQEnricherConfig {

	@Bean
	@Transformer(inputChannel = CharonFlowConstants.FLOW_1_INBOUND_JMS_ENRICHER_CHANNEL, outputChannel = CharonFlowConstants.FLOW_2_INBOUND_SEQ_ROUTER_CHANNEL)
	public HeaderEnricher jmsPurchaseEnricher() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("headers['X-Charon-Transaction']", String.class));
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_ID,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("headers['X-Sync-Id']", String.class));
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_DOMAIN,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("headers['X-Sync-Domain']", String.class));
//		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_CONTROL,
//				new ExpressionEvaluatingHeaderValueMessageProcessor<>(
//						"@syncControlService.getCurrentControl(headers['X-Sync-Domain'])", String.class));
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_SEQUENCE,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>("headers['X-Sync-Sequence']", Integer.class));
		
		return new HeaderEnricher(headersMap);
	}

}
