package com.github.ricardocomar.springcharon.appowner.repository.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "sync_control")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SyncControlEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
	@GenericGenerator(name = "native", strategy = "native")
	@Column(name = "id", updatable = false, nullable = false)
	private Integer id;

	@Column(unique = true, nullable = false, updatable = false)
	private String syncId;

	@Column(nullable = false, updatable = false)
	private Integer sequence;

	@Column(nullable = false, updatable = false)
	private String domain;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SyncState state;

	@Column(nullable = false, updatable = false)
	private String payload;

	@Column(nullable = false, updatable = false)
	private LocalDateTime sentTime;

	private LocalDateTime ackTime;

	public enum SyncState {
		SENT, ACK;
	}

}
