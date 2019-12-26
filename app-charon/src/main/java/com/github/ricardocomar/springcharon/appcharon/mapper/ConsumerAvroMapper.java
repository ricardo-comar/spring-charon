package com.github.ricardocomar.springcharon.appcharon.mapper;

import org.apache.avro.generic.GenericRecord;

import com.github.ricardocomar.springcharon.appcharon.model.ConsumerModel;

public interface ConsumerAvroMapper<A extends GenericRecord> {

	ConsumerModel fromAvro(A avro);
}
