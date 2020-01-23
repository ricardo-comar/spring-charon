package com.github.ricardocomar.springcharon.appowner.sync;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessagePostProcessor;
import org.springframework.stereotype.Service;

import com.github.ricardocomar.springcharon.appowner.config.AppProperties;
import com.github.ricardocomar.springcharon.appowner.repository.SyncControlRepository;
import com.github.ricardocomar.springcharon.appowner.repository.entity.SyncControlEntity;
import com.github.ricardocomar.springcharon.appowner.repository.entity.SyncControlEntity.SyncState;
import com.github.ricardocomar.springcharon.appowner.sync.model.ModelUpdateEvent;

@Service
public class EventUpdateNotifier {

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private SyncControlRepository syncRepo;

	@Transactional(TxType.REQUIRES_NEW)
	public void notifyUpdate(final ModelUpdateEvent model) {
		final String syncId = UUID.randomUUID().toString();

		final Integer syncSeq = syncRepo.findByDomainOrderBySequenceDesc(model.getKey()) //
				.stream().findFirst().orElse(SyncControlEntity.builder().sequence(0).build()) //
				.getSequence() + 1;

		final SyncControlEntity syncEntity = SyncControlEntity.builder() //
				.domain(model.getKey()) //
				.syncId(syncId) //
				.payload(model.getPayload()) //
				.sentTime(LocalDateTime.now()) //
				.sequence(syncSeq) //
				.state(SyncState.SENT) //
				.build();
		syncRepo.save(syncEntity);

		jmsTemplate.convertAndSend("sync.purchase", model.getPayload(), new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(final Message message) throws JMSException {
				message.setStringProperty(AppProperties.HEADER_CHARON_TRANSACTION, "TRANPURC-1");
				message.setStringProperty(AppProperties.HEADER_SYNC_ID, syncId);
				message.setStringProperty(AppProperties.HEADER_SYNC_DOMAIN, "Purchase");
				message.setIntProperty(AppProperties.HEADER_SYNC_SEQUENCE, syncSeq);
				return message;
			}
		});

	}
}
