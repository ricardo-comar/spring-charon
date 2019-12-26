package com.github.ricardocomar.springcharon.appmirror.mapper;

import org.apache.avro.generic.GenericRecord;

import com.github.ricardocomar.springcharon.appmirror.model.ConsumerModel;

public interface ConsumerModelMapper<M extends ConsumerModel> {

	GenericRecord fromModel(M model);

}
