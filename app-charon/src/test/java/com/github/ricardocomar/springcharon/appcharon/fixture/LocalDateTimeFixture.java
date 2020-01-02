package com.github.ricardocomar.springcharon.appcharon.fixture;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeFixture {

	public static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmssSSSSS");

	public static final LocalDateTime LDF_NOW = LocalDateTime.now();
	public static final LocalDateTime LDF_10_DAYS_PAST = LDF_NOW.minusDays(10);

}
