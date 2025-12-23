package com.model.mvc.controller;

import com.model.mvc.model.Address;
import com.model.mvc.model.Customer;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

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

  @GetMapping("/{id}")
  private ResponseEntity<Customer> getCustomerById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(mockedCustomer);
  }

  @GetMapping
  private ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok().body(List.of(mockedCustomer));
  }

  @PostMapping
  @Transactional
  private ResponseEntity<Customer> registerCustomer(@RequestBody Customer customer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(mockedCustomer);
  }

  @PutMapping
  @Transactional
  private ResponseEntity<Void> updateCustomer(@RequestBody Customer customer) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping
  @Transactional
  private ResponseEntity<Void> deleteCustomer(@RequestBody Customer customer) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
