package com.model.mvc.controller;

import com.model.mvc.model.dto.CreateCustomerRequestDTO;
import com.model.mvc.model.dto.CustomerResponseDTO;
import com.model.mvc.model.dto.UpdateCustomerRequestDTO;
import com.model.mvc.service.CustomerService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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
    return ResponseEntity.ok().body(customerService.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponseDTO>> getCustomers() {
    return ResponseEntity.ok().body(customerService.getAll());
  }

  @PostMapping
  @Transactional
  public ResponseEntity<CustomerResponseDTO> registerCustomer(@Valid @RequestBody CreateCustomerRequestDTO customer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(customerService.create(customer));
  }

  @PutMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> updateCustomer(@Valid @PathVariable Integer id, @RequestBody UpdateCustomerRequestDTO customer) {
    customerService.update(id, customer);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  @Transactional
  public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
    customerService.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}
