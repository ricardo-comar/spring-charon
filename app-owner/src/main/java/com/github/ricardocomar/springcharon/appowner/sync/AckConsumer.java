package com.github.ricardocomar.springcharon.appowner.sync;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.springcharon.appowner.config.AppProperties;

@Component
public class AckConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AckConsumer.class);

	@JmsListener(destination = "queue.ack")
	public void handle(final String message,
			@Header(AppProperties.HEADER_SYNC_ID) final String syncId) {

		LOGGER.info("Received Message, will be processed ({})", message);

	}
}
