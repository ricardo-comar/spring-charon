package com.github.ricardocomar.springcharon.appcharon.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.beanio.annotation.Field;
import org.beanio.annotation.Record;
import org.beanio.annotation.Segment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Record
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase implements ConsumerMainModel {

	private transient String syncTransaction;
	private transient String syncKey;
	private transient String syncSequence;

	@Field(ordinal = 0, length = 10)
	@Builder.Default
	private String transaction = "TRANPURC-1";

	@Field(ordinal = 1, length = 12)
	private String id;

	@Field(ordinal = 2, length = 30)
	private String customer;

	@Field(ordinal = 3, length = 10, format = "10,2")
	private BigDecimal totalValue;

	@Field(ordinal = 4, length = 10)
	private PurchaseStatus status;

	@Field(ordinal = 5, format = "yyyyMMdd-HHmmssSSSSS", length = 20)
	private LocalDateTime date;

	@Segment(ordinal = 6, collection = ArrayList.class, minOccurs = 0, maxOccurs = 4, until = 200)
	private List<PurchaseItem> items;

	public enum PurchaseStatus {
		OPEN, PAID, CANCELLED, RETURNED, DELIVERED;
	}


}