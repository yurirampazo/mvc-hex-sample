package com.model.mvc.mapper;

import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.CreateCustomerRequestDTO;
import com.model.mvc.model.dto.CustomerResponseDTO;
import com.model.mvc.model.dto.UpdateCustomerRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = AddressMapper.class)
public interface CustomerMapper {

  CustomerResponseDTO toCustomerResponseDto(Customer customer);

  @Mapping(target = "id", ignore = true)
  Customer toCustomerCreatingResource(CreateCustomerRequestDTO createCustomerRequestDTO);

  @Mapping(target = "id", ignore = true)
  Customer toCustomerUpdatingResource(UpdateCustomerRequestDTO updateCustomerRequestDTO);
}
