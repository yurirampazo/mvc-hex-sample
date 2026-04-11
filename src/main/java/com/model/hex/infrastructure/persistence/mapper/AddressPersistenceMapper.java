package com.model.hex.infrastructure.persistence.mapper;

import com.model.hex.domain.model.Address;
import com.model.hex.infrastructure.persistence.entity.AddressEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressPersistenceMapper {

  @Mapping(target = "customers", ignore = true)
  Address toDomain(AddressEntity entity);

  @Mapping(target = "customers", ignore = true)
  AddressEntity toEntity(Address domain);
}