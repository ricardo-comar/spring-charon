package com.github.ricardocomar.springcharon.appowner.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.github.ricardocomar.springcharon.appowner.repository.entity.PurchaseItemEntity;
import com.github.ricardocomar.springcharon.appowner.sync.model.PurchaseItem;

@Mapper(componentModel = "spring")
public interface PurchaseItemMapper {

	PurchaseItem fromEntity(PurchaseItemEntity entity);

	List<PurchaseItem> fromEntity(List<PurchaseItemEntity> entity);

}
