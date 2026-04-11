package com.model.mvc.controller;

import com.model.mvc.model.dto.AddressResponseDTO;
import com.model.mvc.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

  private final AddressService addressService;

  @GetMapping("/{zipCode}")
  ResponseEntity<AddressResponseDTO> getByZipCode(@PathVariable("zipCode") String zipCode) {
    return ResponseEntity.ok(addressService.findByZipCode(zipCode));
  }
}
