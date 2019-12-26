package com.github.ricardocomar.springcharon.appmirror.mapper;

import org.apache.avro.generic.GenericRecord;

import com.github.ricardocomar.springcharon.appmirror.model.ConsumerModel;

public interface ConsumerAvroMapper<A extends GenericRecord> {

	ConsumerModel fromAvro(A avro);
}
