package com.model.mvc.model.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

public class CreateCustomerRequestDTO {
  @NotNull
  private String name;
  @NotNull
  private String email;
  @NotNull
  @DateTimeFormat(pattern = "dd-MM-yyyy")
  private LocalDate birthDate;
  @NotNull
  @Valid
  private Set<CreateAddressRequestDTO> addresses;
}
