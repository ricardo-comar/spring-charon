package com.github.ricardocomar.springcharon.appcharon.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.integration.config.EnableIntegration;

@Configuration
@EnableIntegration
public class SpringIntegrationConfig {

	public static final String X_MSG_HEADER_INBOUND_TYPE = "X-Charon-Inbound-Type";
	public static final String X_MSG_HEADER_OUTBOUND_TYPE = "X-Charon-Outbound-Type";

}
