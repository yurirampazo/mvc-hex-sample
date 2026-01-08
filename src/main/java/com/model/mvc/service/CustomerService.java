package com.model.mvc.service;

import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.CustomerResponseDTO;

import java.util.List;

public interface CustomerService {

  CustomerResponseDTO getCustomerById(Integer id);
  List<Customer> getCustomers();
  Customer createCustomer(Customer customer);
  void updateCustomer(Integer id, Customer customer);
  void deleteCustomer(Integer id);
}
