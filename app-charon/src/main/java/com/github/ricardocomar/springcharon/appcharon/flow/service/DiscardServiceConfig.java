package com.github.ricardocomar.springcharon.appcharon.flow.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Filter;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.handler.LoggingHandler;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;
import com.github.ricardocomar.springcharon.appcharon.sync.SyncControlService;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity.SyncState;

@Configuration
public class DiscardServiceConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(DiscardServiceConfig.class);

	@Autowired
	private SyncControlService service;

	@SuppressWarnings("unchecked")
	@Filter(inputChannel = CharonFlowConstants.FLOW_SEQ_BREAKER_CHANNEL, outputChannel = CharonFlowConstants.FLOW_OUTBOUND_SEQ_BREAK_NOTIFICATION, discardChannel = CharonFlowConstants.FLOW_DISCARD_CHANNEL)
	public boolean sequenceBreakerFilter(final Message<String> msg) {

		LOGGER.warn("Message will be discarded: {}", msg);
		final String domain = (String) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE);
		final Integer syncSequence = (Integer) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_SEQUENCE);
		final Optional<SyncControlEntity> controlOpt = Optional.ofNullable(
				(SyncControlEntity) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_CONTROL));

		final SyncState state = SyncState.OK.equals( //
				controlOpt.orElse(SyncControlEntity.builder().state(SyncState.OK).build()).getState())
						? SyncState.INVALID
						: SyncState.DISCARDED;

		service.updateControl(domain, syncSequence, state.name());

		return SyncState.INVALID.equals(state);
	}

	@Bean // TODO: trocar por criador da mensagem de notificação
	@ServiceActivator(inputChannel = CharonFlowConstants.FLOW_OUTBOUND_SEQ_BREAK_NOTIFICATION)
	public LoggingHandler jmsSequenceBreakNotificationChannel() {
		final LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.WARN);
		adapter.setLoggerName("CHARON_LOGGER");
		adapter.setLogExpressionString("'Producer will be notified of sequence break:' + payload");
		return adapter;
	}

	@Bean
	@ServiceActivator(inputChannel = CharonFlowConstants.FLOW_DISCARD_CHANNEL)
	public LoggingHandler discardChannelBean() {
		final LoggingHandler adapter = new LoggingHandler(LoggingHandler.Level.WARN);
		adapter.setLoggerName("CHARON_LOGGER");
		adapter.setLogExpressionString(
				"headers['" + SpringIntegrationConfig.X_MSG_HEADER_SYNC_ID
						+ "'] + ': will be discarded - payload:' + payload");
		return adapter;
	}

}
