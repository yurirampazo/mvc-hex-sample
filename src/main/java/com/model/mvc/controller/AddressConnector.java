package com.model.mvc.controller;

import com.model.mvc.model.dto.AddressResponseDTO;
import com.model.mvc.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressConnector {

  private final AddressService addressService;

  @GetMapping("/{zipCode}")
  ResponseEntity<AddressResponseDTO> getByZipCode(@RequestParam("zipCode") String zipCode) {
    return ResponseEntity.ok(addressService.findByZipCode(zipCode));
  }
}
