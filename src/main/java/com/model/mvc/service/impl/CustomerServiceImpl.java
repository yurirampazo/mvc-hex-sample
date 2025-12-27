package com.model.mvc.service.impl;

import com.model.mvc.model.Address;
import com.model.mvc.model.Customer;
import com.model.mvc.service.CustomerService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

  private static final Customer mockedCustomer = Customer.builder()
      .id(1)
      .name("First Customer")
      .age(LocalDate.now().getYear() - 1995)
      .birthDate(LocalDate.of(1995, 12, 20))
      .address(Address.builder()
          .id(1)
          .city("São Paulo")
          .state("SP")
          .zipCode("04144130")
          .streetName("mocked street")
          .neighbourhood("mocked neighbourhood")
          .build())
      .build();

  @Override
  public Customer getCustomerById(Integer id) {
    return mockedCustomer;
  }

  @Override
  public List<Customer> getCustomers() {
    return List.of(mockedCustomer);
  }

  @Override
  public Customer createCustomer(Customer customer) {
    return mockedCustomer;
  }

  @Override
  public void updateCustomer(Integer id, Customer customer) {

  }

  @Override
  public void deleteCustomer(Integer id) {

  }
}
