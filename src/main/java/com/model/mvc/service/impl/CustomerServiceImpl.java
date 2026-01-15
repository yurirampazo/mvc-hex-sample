package com.model.mvc.service.impl;

import com.model.mvc.mapper.CustomerMapper;
import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.CreateCustomerRequestDTO;
import com.model.mvc.model.dto.CustomerResponseDTO;
import com.model.mvc.repository.CustomerRepository;
import com.model.mvc.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class CustomerServiceImpl implements CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  @Autowired
  private CustomerMapper customerMapper;

  @Override
  public CustomerResponseDTO getCustomerById(Integer id) {
    return customerMapper.toCustomerResponseDto(customerRepository.findById(id).orElseThrow(RuntimeException::new));
  }

  @Override
  public List<Customer> getCustomers() {
    return customerRepository.findAll();
  }

  @Override
  public Customer createCustomer(CreateCustomerRequestDTO customer) {
    if (Objects.isNull(customer)) {
      throw new IllegalArgumentException("Unprocessable Entity");
    }
    var customerEntity = customerMapper.toCustomerCreatingResource(customer);
    return customerRepository.save(customerEntity);
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
