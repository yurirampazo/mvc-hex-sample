package com.model.mvc.domain.ports.in;

import com.model.mvc.domain.model.Address;
import com.model.mvc.infrastructure.web.dto.AddressResponseDTO;
import com.model.mvc.infrastructure.web.dto.CreateAddressRequestDTO;
import com.model.mvc.infrastructure.web.dto.UpdateAddressRequestDTO;

public interface AddressUseCase {
  Address create(CreateAddressRequestDTO dto);

  Address update(UpdateAddressRequestDTO dto);

  Address getEntityById(Integer id);

  AddressResponseDTO findByZipCode(String zipCode);
}