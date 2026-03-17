package com.model.mvc.infrastructure.web.controller;

import com.model.mvc.domain.ports.in.AddressUseCase;
import com.model.mvc.infrastructure.web.dto.AddressResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

  private final AddressUseCase addressUseCase;

  @GetMapping("/{zipCode}")
  ResponseEntity<AddressResponseDTO> getByZipCode(@PathVariable("zipCode") String zipCode) {
    return ResponseEntity.ok(addressUseCase.findByZipCode(zipCode));
  }
}
