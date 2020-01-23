package com.github.ricardocomar.springcharon.appcharon.sync.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sync_control_charon")
public class SyncControlEntity {

	@Id
	@Column(nullable = false, updatable = false)
	private String domain;

	@Column(nullable = false)
	private Integer sequence;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private SyncState state;

	public enum SyncState {
		OK, INVALID, DISCARDED;
	}

}
