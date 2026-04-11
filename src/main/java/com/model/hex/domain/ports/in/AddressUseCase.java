package com.model.hex.domain.ports.in;

import com.model.hex.domain.model.Address;
import com.model.hex.infrastructure.web.dto.AddressResponseDTO;
import com.model.hex.infrastructure.web.dto.CreateAddressRequestDTO;
import com.model.hex.infrastructure.web.dto.UpdateAddressRequestDTO;

public interface AddressUseCase {
  Address create(CreateAddressRequestDTO dto);

  Address update(UpdateAddressRequestDTO dto);

  Address getEntityById(Integer id);

  AddressResponseDTO findByZipCode(String zipCode);
}