package com.model.hex.domain.ports.out;

import com.model.hex.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
  Optional<Customer> findById(Integer id);

  List<Customer> findAll();

  Customer save(Customer customer);

  void deleteById(Integer id);
}