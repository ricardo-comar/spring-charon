package com.github.ricardocomar.springcharon.appowner.sync.trancode.handler;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import org.beanio.types.ConfigurableTypeHandler;
import org.beanio.types.TypeHandler;

import lombok.Builder;

@Builder
public class LocalDateTimeTypeHandler implements TypeHandler, ConfigurableTypeHandler {

	private final String format;

	@Override
	public Object parse(final String text) {
		return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format));
	}

	@Override
	public String format(final Object value) {
		return ((LocalDateTime) value).format(DateTimeFormatter.ofPattern(format));
	}

	@Override
	public Class<?> getType() {
		return LocalDateTime.class;
	}

	@Override
	public TypeHandler newInstance(final Properties properties) throws IllegalArgumentException {
		if (!properties.containsKey("format")) {
			throw new IllegalArgumentException("property \"format\" is mandatory");
		}
		final String formatProperty = properties.getProperty("format");
		if (formatProperty.equals(format)) {
			return this;
		}

		DateTimeFormatter.ofPattern(formatProperty);

		return LocalDateTimeTypeHandler.builder().format(properties.getProperty("format")).build();
	}

}
