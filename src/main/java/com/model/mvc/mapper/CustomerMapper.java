package com.model.mvc.mapper;

import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.CreateCustomerRequestDTO;
import com.model.mvc.model.dto.CustomerResponseDTO;
import com.model.mvc.model.dto.UpdateCustomerRequestDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

  CustomerResponseDTO toCustomerResponseDto(Customer customer);

  Customer toCustomerCreatingResource(CreateCustomerRequestDTO createCustomerRequestDTO);

  Customer toCustomerUpdatingResource(UpdateCustomerRequestDTO updateCustomerRequestDTO);
}
