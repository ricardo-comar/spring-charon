package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.handler.DelayHandler;
import org.springframework.integration.transformer.HeaderEnricher;
import org.springframework.integration.transformer.support.ExpressionEvaluatingHeaderValueMessageProcessor;
import org.springframework.integration.transformer.support.HeaderValueMessageProcessor;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;

@Configuration
public class MQRetryEnricherConfig {

	@ServiceActivator(inputChannel = CharonFlowConstants.FLOW_SEQ_RETRY_CHANNEL)
	@Bean
	public DelayHandler delayer() {
		final DelayHandler handler = new DelayHandler("delayer.messageRetry");
		handler.setDefaultDelay(3_000L);
		handler.setOutputChannelName(CharonFlowConstants.FLOW_SEQ_RETRY_CHANNEL + "Delayed");
		handler.setLoggingEnabled(true);
		return handler;
	}

	@Bean
	@Transformer(inputChannel = CharonFlowConstants.FLOW_SEQ_RETRY_CHANNEL
			+ "Delayed", outputChannel = CharonFlowConstants.FLOW_OUTBOUND_JMS_RETRY_CHANNEL)
	public HeaderEnricher jmsOutboundRetryChannel() {

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_RETRY_COUNT,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>(
						"@syncControlService.getRetryCount(headers['"
								+ SpringIntegrationConfig.X_MSG_HEADER_SYNC_RETRY_COUNT + "'])",
						Integer.class));

		final HeaderEnricher headerEnricher = new HeaderEnricher(headersMap);
		headerEnricher.setDefaultOverwrite(true);
		return headerEnricher;
	}

}
