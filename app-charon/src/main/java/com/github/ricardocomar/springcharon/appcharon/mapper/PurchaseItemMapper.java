package com.github.ricardocomar.springcharon.appcharon.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.github.ricardocomar.springcharon.appcharon.model.PurchaseItem;
import com.github.ricardocomar.springcharon.model.PurchaseItemAvro;

@Mapper(componentModel = "spring")
public abstract class PurchaseItemMapper
		implements ConsumerAvroMapper<PurchaseItemAvro>, ConsumerModelMapper<PurchaseItem> {

	@Override
	public abstract PurchaseItemAvro fromModel(PurchaseItem model);

	@InheritInverseConfiguration
	@Override
	public abstract PurchaseItem fromAvro(PurchaseItemAvro avro);
}
