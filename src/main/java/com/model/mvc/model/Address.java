package com.model.mvc.model;

import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  private Integer id;
  private String zipCode;
  private String countryCode;
  private String streetName;
  private String neighbourhood;
  private String city;
  private String state;
}
