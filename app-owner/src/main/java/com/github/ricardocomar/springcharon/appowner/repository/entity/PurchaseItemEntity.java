package com.github.ricardocomar.springcharon.appowner.repository.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseItemEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(nullable = false)
	private String sku;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private BigDecimal value;

}