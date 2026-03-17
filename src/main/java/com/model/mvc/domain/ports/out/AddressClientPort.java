package com.model.mvc.domain.ports.out;

import com.model.mvc.infrastructure.web.dto.AddressResponseDTO;

public interface AddressClientPort {
  AddressResponseDTO findByZipCode(String zipCode);
}