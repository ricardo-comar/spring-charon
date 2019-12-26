package com.github.ricardocomar.springcharon.appmirror.mapper;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import com.github.ricardocomar.springbootetl.model.PurchaseItemAvro;
import com.github.ricardocomar.springcharon.appmirror.model.PurchaseItem;

@Mapper(componentModel = "spring")
public abstract class PurchaseItemMapper
		implements ConsumerAvroMapper<PurchaseItemAvro>, ConsumerModelMapper<PurchaseItem> {

	@Override
	public abstract PurchaseItemAvro fromModel(PurchaseItem model);

	@InheritInverseConfiguration
	@Override
	public abstract PurchaseItem fromAvro(PurchaseItemAvro avro);
}
