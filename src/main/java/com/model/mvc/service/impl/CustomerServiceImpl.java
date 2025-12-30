package com.model.mvc.service.impl;

import com.model.mvc.model.Customer;
import com.model.mvc.repository.CustomerRepository;
import com.model.mvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  public Customer getCustomerById(Integer id) {
    return customerRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
  }

  @Override
  public List<Customer> getCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public Customer createCustomer(Customer customer) {
    return customerRepository.save(customer);
  }

  @Override
  public void updateCustomer(Integer id, Customer customer) {
    customerRepository.findById(id).ifPresent(cust -> customerRepository.save(customer));
  }

  @Override
  public void deleteCustomer(Integer id) {
    customerRepository.findById(id).ifPresent(customer -> customerRepository.deleteById(id));
  }
}
