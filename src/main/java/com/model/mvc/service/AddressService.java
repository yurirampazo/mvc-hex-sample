package com.model.mvc.service;


import com.model.mvc.mapper.AddressMapper;
import com.model.mvc.model.Address;
import com.model.mvc.model.dto.CreateAddressRequestDTO;
import com.model.mvc.model.dto.UpdateAddressRequestDTO;
import com.model.mvc.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService {

  private final AddressRepository repository;
  private final AddressMapper mapper;

  public AddressService(AddressRepository repository, AddressMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public Address create(CreateAddressRequestDTO dto) {
    Address address = mapper.fromCreateDto(dto);
    return repository.save(address);
  }

  public Address update(UpdateAddressRequestDTO dto) {
    Address address = repository.findById(dto.getId())
        .orElseThrow(() -> new RuntimeException("Address not found"));

    mapper.updateFromDto(dto, address);
    return repository.save(address);
  }

  public Address getEntityById(Integer id) {
    return repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Address not found"));
  }
}


