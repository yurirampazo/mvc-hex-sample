package com.model.mvc.domain.ports.out;

import com.model.mvc.domain.model.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryPort {
  Optional<Customer> findById(Integer id);

  List<Customer> findAll();

  Customer save(Customer customer);

  void deleteById(Integer id);
}