package com.model.mvc.service;

import com.model.mvc.mapper.CustomerMapper;
import com.model.mvc.model.Address;
import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.*;
import com.model.mvc.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CustomerService {

  private final CustomerRepository repository;
  private final AddressService addressService;
  private final CustomerMapper mapper;

  public CustomerService(
      CustomerRepository repository,
      AddressService addressService,
      CustomerMapper mapper
  ) {
    this.repository = repository;
    this.addressService = addressService;
    this.mapper = mapper;
  }

  public CustomerResponseDTO getById(Integer id) {
    return mapper.toResponse(
        repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Customer not found"))
    );
  }

  public List<CustomerResponseDTO> getAll() {
    return repository.findAll()
        .stream()
        .map(mapper::toResponseGetAll)
        .toList();
  }

  public CustomerResponseDTO create(CreateCustomerRequestDTO dto) {

    Customer customer = mapper.fromCreateDto(dto);

    for (CreateAddressRequestDTO addrDto : dto.getAddresses()) {
      Address address = addressService.create(addrDto);
      customer.getAddresses().add(address);
      address.getCustomers().add(customer);
    }

    return mapper.toResponse(repository.save(customer));
  }

  public CustomerResponseDTO update(Integer id, UpdateCustomerRequestDTO dto) {

    Customer customer = repository.findById(id)
        .orElseThrow(() -> new RuntimeException("Customer not found"));

    mapper.updateFromDto(dto, customer);

    if (dto.getAddresses() != null) {
      customer.getAddresses().forEach(a -> a.getCustomers().remove(customer));
      customer.getAddresses().clear();

      for (UpdateAddressRequestDTO addrDto : dto.getAddresses()) {
        Address address = addressService.update(addrDto);
        customer.getAddresses().add(address);
        address.getCustomers().add(customer);
      }
    }

    return mapper.toResponse(repository.save(customer));
  }

  public void delete(Integer id) {
    repository.deleteById(id);
  }
}
