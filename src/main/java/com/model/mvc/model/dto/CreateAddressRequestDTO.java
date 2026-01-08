package com.model.mvc.model.dto;

import jakarta.validation.constraints.NotNull;

public class CreateAddressRequestDTO {
  @NotNull
  private String zipCode;
  @NotNull
  private String countryCode;
  private String streetName;
  private String neighbourhood;
  @NotNull
  private String city;
  @NotNull
  private String state;
}
