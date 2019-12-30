package com.github.ricardocomar.springcharon.appowner.mapper;

import org.mapstruct.Mapper;

import com.github.ricardocomar.springcharon.appowner.repository.entity.PurchaseEntity;
import com.github.ricardocomar.springcharon.appowner.sync.model.Purchase;

@Mapper(componentModel = "spring", uses = { PurchaseItemMapper.class })
public interface PurchaseMapper {

	Purchase fromEntity(PurchaseEntity entity);
}
