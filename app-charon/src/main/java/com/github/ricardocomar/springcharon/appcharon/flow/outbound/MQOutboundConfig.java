package com.github.ricardocomar.springcharon.appcharon.flow.outbound;

import javax.jms.ConnectionFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.jms.config.JmsChannelFactoryBean;
import org.springframework.jms.support.converter.MessagingMessageConverter;

@Configuration
public class MQOutboundConfig {

	@Bean(destroyMethod = "destroy", name = "jmsRetryOutboundChannel")
	public JmsChannelFactoryBean jmsRetryOutboundChannel(final ConnectionFactory jmsConnFactory) {
		final JmsChannelFactoryBean factory = new JmsChannelFactoryBean(true);
		factory.setMessageConverter(new MessagingMessageConverter());
		factory.setConnectionFactory(jmsConnFactory);
		factory.setSessionTransacted(true);
		factory.setDestinationName("sync.purchase.retry");
		return factory;
	}

}
