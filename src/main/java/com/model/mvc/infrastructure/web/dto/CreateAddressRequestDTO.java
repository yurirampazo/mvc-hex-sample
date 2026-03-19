package com.model.mvc.infrastructure.web.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateAddressRequestDTO {
  @NotNull
  private String zipCode;
  private Integer number;
  private String streetName;
  private String neighbourhood;
  @NotNull
  private String city;
  @NotNull
  private String state;
}
