package com.github.ricardocomar.springcharon.appowner.sync.model;

import lombok.Value;

@Value
public class ModelUpdateEvent {

	private String key;
	private String payload;
}
