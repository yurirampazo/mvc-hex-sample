package com.model.mvc.application.service;

import com.model.mvc.domain.model.Address;
import com.model.mvc.domain.model.Customer;
import com.model.mvc.domain.ports.in.AddressUseCase;
import com.model.mvc.domain.ports.in.CustomerUseCase;
import com.model.mvc.domain.ports.out.CustomerRepositoryPort;
import com.model.mvc.infrastructure.web.dto.*;
import com.model.mvc.infrastructure.web.mapper.AddressMapper;
import com.model.mvc.infrastructure.web.mapper.CustomerMapper;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerService implements CustomerUseCase {

  private final CustomerRepositoryPort repository;
  private final AddressUseCase addressUseCase;
  private final CustomerMapper mapper;
  private final AddressMapper addressMapper;

  public CustomerService(CustomerRepositoryPort repository,
                         AddressService addressUseCase,
                         CustomerMapper mapper,
                         AddressMapper addressMapper) {
    this.repository = repository;
    this.addressUseCase = addressUseCase;
    this.mapper = mapper;
    this.addressMapper = addressMapper;
  }

  @Override
  public CustomerResponseDTO getById(Integer id) {
    return mapper.toResponse(
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"))
    );
  }

  @Override
  public List<CustomerResponseDTO> getAll() {
    return repository.findAll()
        .stream()
        .map(mapper::toResponse)
        .toList();
  }

  @Override
  public CustomerResponseDTO create(CreateCustomerRequestDTO dto) {
    Customer customer = mapper.fromCreateDto(dto);

    for (CreateAddressRequestDTO addrDto : dto.getAddresses()) {
      Address address = addressMapper.fromCreateDto(addrDto);
      customer.getAddresses().add(address);
      address.getCustomers().add(customer);
    }

    Customer saved = repository.save(customer);
    return mapper.toResponse(saved);
  }

  @Override
  public CustomerResponseDTO update(Integer id, UpdateCustomerRequestDTO dto) {
    Customer customer = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found"));

    mapper.updateFromDto(dto, customer);

    if (dto.getAddresses() != null) {
      customer.getAddresses().forEach(a -> a.getCustomers().remove(customer));
      customer.getAddresses().clear();

      for (UpdateAddressRequestDTO addrDto : dto.getAddresses()) {
        Address address = addressUseCase.update(addrDto);
        customer.getAddresses().add(address);
        address.getCustomers().add(customer);
      }
    }

    return mapper.toResponse(repository.save(customer));
  }

  @Override
  public void delete(Integer id) {
    repository.deleteById(id);
  }
}