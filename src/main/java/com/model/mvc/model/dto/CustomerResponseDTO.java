package com.model.mvc.model.dto;

import java.time.LocalDate;
import java.util.Set;

public class CustomerResponseDTO {
  private Integer id;
  private String name;
  private String email;
  private Integer age;
  private LocalDate birthDate;
  private Set<AddressResponseDTO> addresses;
}
