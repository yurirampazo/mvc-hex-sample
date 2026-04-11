package com.model.hex.infrastructure.web.controller;

import com.model.hex.domain.ports.in.CustomerUseCase;
import com.model.hex.infrastructure.web.dto.CreateCustomerRequestDTO;
import com.model.hex.infrastructure.web.dto.CustomerResponseDTO;
import com.model.hex.infrastructure.web.dto.UpdateCustomerRequestDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

  private final CustomerUseCase customerUseCase;

  public CustomerController(CustomerUseCase customerUseCase) {
    this.customerUseCase = customerUseCase;
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Integer id) {
    return ResponseEntity.ok(customerUseCase.getById(id));
  }

  @GetMapping
  public ResponseEntity<List<CustomerResponseDTO>> getCustomers() {
    return ResponseEntity.ok(customerUseCase.getAll());
  }

  @PostMapping
  public ResponseEntity<CustomerResponseDTO> registerCustomer(
      @Valid @RequestBody CreateCustomerRequestDTO customer) {
    return ResponseEntity.status(HttpStatus.CREATED).body(customerUseCase.create(customer));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Void> updateCustomer(
      @Valid @PathVariable Integer id,
      @RequestBody UpdateCustomerRequestDTO customer) {
    customerUseCase.update(id, customer);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Integer id) {
    customerUseCase.delete(id);
    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
  }
}