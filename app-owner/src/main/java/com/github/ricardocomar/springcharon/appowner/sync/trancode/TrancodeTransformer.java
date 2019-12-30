
package com.github.ricardocomar.springcharon.appowner.sync.trancode;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.beanio.Marshaller;
import org.beanio.StreamFactory;
import org.beanio.builder.FixedLengthParserBuilder;
import org.beanio.builder.StreamBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.ricardocomar.springcharon.appowner.sync.model.Purchase;
import com.github.ricardocomar.springcharon.appowner.sync.trancode.handler.BigDecimalPositionalTypeHandler;
import com.github.ricardocomar.springcharon.appowner.sync.trancode.handler.LocalDateTimeTypeHandler;
import com.github.ricardocomar.springcharon.appowner.sync.trancode.handler.LocalDateTypeHandler;

@Component
public class TrancodeTransformer {

	private static final Logger LOGGER = LoggerFactory.getLogger(TrancodeTransformer.class);

	private final Map<Class<?>, Marshaller> marshMap = Collections.synchronizedMap(new HashMap<>());

	public TrancodeTransformer() {
		initMappers("TRANPURC-1", Purchase.class);
	}

	private void initMappers(final String transaction, final Class<?> modelClass) {
		final StreamBuilder builder = new StreamBuilder(transaction).format("fixedlength")
				.addTypeHandler(LocalDate.class, LocalDateTypeHandler.builder().format("yyyyMMdd").build())
				.addTypeHandler(LocalDateTime.class,
						LocalDateTimeTypeHandler.builder().format("yyyyMMdd-HHmmssSSSSS").build())
				.addTypeHandler(BigDecimal.class,
						BigDecimalPositionalTypeHandler.builder().precision(8).scale(2).build())
				.parser(new FixedLengthParserBuilder()).addRecord(modelClass);

		LOGGER.debug("Creating {} Unmarshallers and Marshallers", transaction);

		final StreamFactory factory = StreamFactory.newInstance();
		factory.define(builder);

		marshMap.put(modelClass, factory.createMarshaller(transaction));
	}

	public String toTrancode(final Purchase input) {
		LOGGER.debug("Transforming bean into trancode: {}", input);
		if (!marshMap.containsKey(input.getClass())) {
			throw new RuntimeException("Unexpected input class " + input.getClass().getName());
		}

		final String output = marshMap.get(input.getClass()).marshal(input).toString();
		LOGGER.debug("Resulted trancode: {}", output);

		return output;
	}
}
