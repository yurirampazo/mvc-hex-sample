package com.model.hex.infrastructure.web.mapper;

import com.model.hex.domain.model.Customer;
import com.model.hex.infrastructure.web.dto.CreateCustomerRequestDTO;
import com.model.hex.infrastructure.web.dto.UpdateCustomerRequestDTO;
import com.model.hex.infrastructure.web.dto.CustomerResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {

  CustomerResponseDTO toResponse(Customer customer);

  @Mapping(target = "id", ignore = true)
  @Mapping(target = "addresses", ignore = true)
  Customer fromCreateDto(CreateCustomerRequestDTO dto);

  @Mapping(target = "id", ignore = true)
  void updateFromDto(UpdateCustomerRequestDTO dto, @MappingTarget Customer customer);
}