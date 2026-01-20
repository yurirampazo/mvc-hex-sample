package com.model.mvc.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressRequestDTO {
  private Integer id;
  private String zipCode;
  private String countryCode;
  private String streetName;
  private String neighbourhood;
  private String city;
  private String state;
}
