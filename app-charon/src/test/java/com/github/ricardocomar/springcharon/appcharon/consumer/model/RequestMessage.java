package com.github.ricardocomar.springcharon.appcharon.consumer.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestMessage {
	private String id;
	private String origin;
	private String trancode;
	
}
