package com.model.mvc.model.dto;

import java.time.LocalDate;
import java.util.Set;

public class UpdateCustomerRequestDTO {
  private String name;
  private String email;
  private LocalDate birthDate;
  private Set<CreateAddressRequestDTO> addresses;
}
