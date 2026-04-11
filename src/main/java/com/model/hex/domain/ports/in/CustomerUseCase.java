package com.model.hex.domain.ports.in;

import com.model.hex.infrastructure.web.dto.CreateCustomerRequestDTO;
import com.model.hex.infrastructure.web.dto.CustomerResponseDTO;
import com.model.hex.infrastructure.web.dto.UpdateCustomerRequestDTO;

import java.util.List;

public interface CustomerUseCase {
  CustomerResponseDTO getById(Integer id);

  List<CustomerResponseDTO> getAll();

  CustomerResponseDTO create(CreateCustomerRequestDTO dto);

  CustomerResponseDTO update(Integer id, UpdateCustomerRequestDTO dto);

  void delete(Integer id);
}
