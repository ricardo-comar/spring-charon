package com.github.ricardocomar.springcharon.appcharon.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.github.ricardocomar.springcharon.appcharon.model.Purchase;
import com.github.ricardocomar.springcharon.model.PurchaseAvro;

@Mapper(componentModel = "spring", uses = { PurchaseItemMapper.class })
public abstract class PurchaseMapper implements ConsumerAvroMapper<PurchaseAvro>, ConsumerModelMapper<Purchase> {

	@Override
	@Mappings({
			@Mapping(target = "date", expression = "java(model.getDate().format(java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd-HHmmssSSSSS\")))"),
			@Mapping(target = "syncTransaction", constant = "AAA"),
			@Mapping(target = "syncKey", constant = "AAA-123"),
			@Mapping(target = "syncSequence", constant = "10") })
	public abstract PurchaseAvro fromModel(Purchase model);

	@InheritInverseConfiguration
	@Mappings({
			@Mapping(target = "date", expression = "java(java.time.LocalDateTime.parse(avro.getDate(), java.time.format.DateTimeFormatter.ofPattern(\"yyyyMMdd-HHmmssSSSSS\")))") })
	@Override
	public abstract Purchase fromAvro(PurchaseAvro avro);
}
