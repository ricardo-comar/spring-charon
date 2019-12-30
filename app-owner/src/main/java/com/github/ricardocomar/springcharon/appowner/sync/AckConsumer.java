package com.github.ricardocomar.springcharon.appowner.sync;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.springcharon.appowner.config.AppProperties;
import com.github.ricardocomar.springcharon.appowner.repository.SyncControlRepository;
import com.github.ricardocomar.springcharon.appowner.repository.entity.SyncControlEntity;
import com.github.ricardocomar.springcharon.appowner.repository.entity.SyncState;

@Component
public class AckConsumer {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AckConsumer.class);

	@Autowired
	private SyncControlRepository syncRepo;

	@JmsListener(destination = "queue.ack")
	public void handle(final String message,
			@Header(AppProperties.HEADER_SYNC_ID) final String syncId) {

		LOGGER.info("Received Message, will be processed ({})", message);

		final SyncControlEntity entity = syncRepo.findBySyncId(syncId).stream().findFirst().get();
		if (entity == null) {
			LOGGER.error("SyncControlEntity not found for syncId \"{}\"", syncId);
			return;
		}

		entity.setAckTime(LocalDateTime.now());
		entity.setState(SyncState.ACK);

		syncRepo.save(entity);

	}
}
