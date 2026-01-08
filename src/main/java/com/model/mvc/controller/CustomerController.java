package com.model.mvc.controller;

import com.model.mvc.model.Customer;
import com.model.mvc.model.dto.CustomerResponseDTO;
import com.model.mvc.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerService customerService;

  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Integer id) {
    return ResponseEntity.ok().body(customerService.getCustomerById(id));
  }

  @GetMapping
  public ResponseEntity<List<Customer>> getCustomers() {
    return ResponseEntity.ok().body(customerService.getCustomers());
  }

  @PostMapping
  @Transactional
  public ResponseEntity<Customer> registerCustomer(@Valid @RequestBody Customer customer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(customer));
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> updateCustomer(@Valid @PathVariable Integer id, @RequestBody Customer customer) {
    customerService.updateCustomer(id, customer);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
    customerService.deleteCustomer(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
