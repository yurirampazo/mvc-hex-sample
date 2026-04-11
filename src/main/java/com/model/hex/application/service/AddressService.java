package com.model.hex.application.service;

import com.model.hex.domain.model.Address;
import com.model.hex.domain.ports.in.AddressUseCase;
import com.model.hex.domain.ports.out.AddressClientPort;
import com.model.hex.domain.ports.out.AddressRepositoryPort;
import com.model.hex.infrastructure.web.dto.AddressResponseDTO;
import com.model.hex.infrastructure.web.dto.CreateAddressRequestDTO;
import com.model.hex.infrastructure.web.dto.UpdateAddressRequestDTO;
import com.model.hex.infrastructure.web.mapper.AddressMapper;
import org.springframework.stereotype.Service;

@Service
public class AddressService implements AddressUseCase {

  private final AddressRepositoryPort repository;
  private final AddressMapper mapper;
  private final AddressClientPort addressClient;

  public AddressService(AddressRepositoryPort repository,
                        AddressMapper mapper,
                        AddressClientPort addressClient) {
    this.repository = repository;
    this.mapper = mapper;
    this.addressClient = addressClient;
  }

  @Override
  public Address create(CreateAddressRequestDTO dto) {
    Address address = mapper.fromCreateDto(dto);
    return repository.save(address);
  }

  @Override
  public Address update(UpdateAddressRequestDTO dto) {
    Address address = repository.findById(dto.getId())
        .orElseThrow(() -> new RuntimeException("Address not found"));
    mapper.updateFromDto(dto, address);
    return repository.save(address);
  }

  @Override
  public Address getEntityById(Integer id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Address not found"));
  }

  @Override
  public AddressResponseDTO findByZipCode(String zipCode) {
    if (zipCode.isBlank()) {
      throw new IllegalArgumentException("ZipCode must not be null or blank");
    }
    return addressClient.findByZipCode(zipCode);
  }
}

