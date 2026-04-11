package com.model.hex.infrastructure.client;

import com.model.hex.domain.ports.out.AddressClientPort;
import com.model.hex.infrastructure.web.dto.AddressResponseDTO;
import com.model.hex.infrastructure.web.dto.ViaCepResponseDTO;
import com.model.hex.infrastructure.web.mapper.AddressMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressClientAdapter implements AddressClientPort {

  private final ViaCepClient viaCepClient;
  private final AddressMapper addressMapper;

  @Override
  public AddressResponseDTO findByZipCode(String zipCode) {
    ViaCepResponseDTO response = viaCepClient.getViaCepAddress(zipCode);
    return addressMapper.toAddressDto(response);
  }
}