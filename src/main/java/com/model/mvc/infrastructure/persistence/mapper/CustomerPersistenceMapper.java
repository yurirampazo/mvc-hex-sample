package com.model.mvc.infrastructure.persistence.mapper;

import com.model.mvc.domain.model.Customer;
import com.model.mvc.infrastructure.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {AddressPersistenceMapper.class})
public interface CustomerPersistenceMapper {

  @Mapping(target = "addresses", ignore = true)
  Customer toDomainGetAll(CustomerEntity entity);

  Customer toDomainGetById(CustomerEntity entity);

  CustomerEntity toEntity(Customer domain);
}