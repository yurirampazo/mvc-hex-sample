package com.model.mvc.domain.ports.in;

import com.model.mvc.infrastructure.web.dto.CreateCustomerRequestDTO;
import com.model.mvc.infrastructure.web.dto.CustomerResponseDTO;
import com.model.mvc.infrastructure.web.dto.UpdateCustomerRequestDTO;

import java.util.List;

public interface CustomerUseCase {
  CustomerResponseDTO getById(Integer id);

  List<CustomerResponseDTO> getAll();

  CustomerResponseDTO create(CreateCustomerRequestDTO dto);

  CustomerResponseDTO update(Integer id, UpdateCustomerRequestDTO dto);

  void delete(Integer id);
}
