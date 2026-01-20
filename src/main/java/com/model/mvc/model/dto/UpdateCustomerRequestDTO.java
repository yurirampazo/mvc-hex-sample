package com.model.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCustomerRequestDTO {
  private String name;
  private String email;
  private LocalDate birthDate;
  private Set<UpdateAddressRequestDTO> addresses;
}
