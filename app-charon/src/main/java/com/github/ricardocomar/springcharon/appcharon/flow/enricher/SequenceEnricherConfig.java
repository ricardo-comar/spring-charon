package com.github.ricardocomar.springcharon.appcharon.flow.enricher;

import java.text.MessageFormat;
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
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity.SyncState;

import br.com.fluentvalidator.context.ValidationResult;

@Configuration
public class SequenceEnricherConfig {

	@Bean
	@Transformer(inputChannel = CharonFlowConstants.FLOW_3_INBOUND_UPDATE_SEQ_CHANNEL, outputChannel = CharonFlowConstants.FLOW_4_INBOUND_VALIDATOR_CHANNEL)
	public HeaderEnricher updateSequenceEnricher() {

		final String expression = MessageFormat.format(
				"@syncControlService.updateControl(headers['{0}'], headers['{1}'], {2})",
				SpringIntegrationConfig.X_MSG_HEADER_SYNC_DOMAIN, SpringIntegrationConfig.X_MSG_HEADER_SYNC_SEQUENCE,
				SyncState.OK.name());

		final Map<String, HeaderValueMessageProcessor<?>> headersMap = new HashMap<>();
		headersMap.put(SpringIntegrationConfig.X_MSG_HEADER_SYNC_UPDATE_RESULT,
				new ExpressionEvaluatingHeaderValueMessageProcessor<>(expression,
						ValidationResult.class));

		return new HeaderEnricher(headersMap);
	}

}
