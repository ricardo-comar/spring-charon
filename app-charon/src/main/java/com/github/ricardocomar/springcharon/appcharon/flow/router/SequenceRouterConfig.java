package com.github.ricardocomar.springcharon.appcharon.flow.router;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Router;
import org.springframework.messaging.Message;

import com.github.ricardocomar.springcharon.appcharon.config.SpringIntegrationConfig;
import com.github.ricardocomar.springcharon.appcharon.flow.CharonFlowConstants;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity;
import com.github.ricardocomar.springcharon.appcharon.sync.repository.entity.SyncControlEntity.SyncState;

@Configuration
public class SequenceRouterConfig {

	private static final Logger LOGGER = LoggerFactory.getLogger(SequenceRouterConfig.class);

	private static final Integer MAX_RETRY = 10;

	@SuppressWarnings("unchecked")
	@Router(inputChannel = CharonFlowConstants.FLOW_2_INBOUND_SEQ_ROUTER_CHANNEL)
	public String jmsSequenceRouterChannel(final Message<String> msg) {

		LOGGER.info("Message sequence will be verified: {}", msg);

		final String domain = (String) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_INBOUND_TYPE);
		final Integer syncSequence = (Integer) msg.getHeaders().get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_SEQUENCE);
		final Integer retryCount = (Integer) msg.getHeaders()
				.get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_RETRY_COUNT);

		if (retryCount > MAX_RETRY) {
			LOGGER.error(
					"Message from domain \"{}\" and sequence \"{}\" was retried too many times ({}), will be discarded",
					domain, syncSequence, retryCount);
			return CharonFlowConstants.FLOW_SEQ_BREAKER_CHANNEL;
		}

		final Optional<SyncControlEntity> controlOpt = (Optional<SyncControlEntity>) msg.getHeaders()
				.get(SpringIntegrationConfig.X_MSG_HEADER_SYNC_CONTROL);
		LOGGER.info("Latest SyncControl for domain ({}) found: {}", domain, controlOpt);

		if (!controlOpt.isPresent()) {

			if (syncSequence > 1) {
				LOGGER.info("SyncControl not found an sequence is not first, will be delayed");
				return CharonFlowConstants.FLOW_SEQ_RETRY_CHANNEL; // OK
			}

		} else {

			final SyncControlEntity entity = controlOpt.get();
			if (!SyncState.OK.equals(entity.getState())) {

				LOGGER.error("Domain \"{}\" state is \"{}\", will be discarded", domain, entity.getState());
				return CharonFlowConstants.FLOW_SEQ_BREAKER_CHANNEL;
			}

			final Integer syncGap = syncSequence - entity.getSequence();
			if (syncGap < 1) {

				LOGGER.error("Gap ({}) is negative, did not supposed to happen...", syncGap);
				return CharonFlowConstants.FLOW_DISCARD_CHANNEL;

			} else if (syncGap > 1) {

				LOGGER.info("Gap ({}) is greater than 1, will be delayed", syncGap);
				return CharonFlowConstants.FLOW_SEQ_RETRY_CHANNEL; // OK

			}
		}

		LOGGER.info("Sequence is next, will be processed");
		return CharonFlowConstants.FLOW_3_INBOUND_UPDATE_SEQ_CHANNEL;

	}

}
