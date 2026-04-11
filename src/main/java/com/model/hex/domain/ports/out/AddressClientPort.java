package com.model.hex.domain.ports.out;

import com.model.hex.infrastructure.web.dto.AddressResponseDTO;

public interface AddressClientPort {
  AddressResponseDTO findByZipCode(String zipCode);
}