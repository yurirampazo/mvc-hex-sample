package com.model.mvc.model;

import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private Integer id;
  private String name;
  private String email;
  private Integer age;
  private LocalDate birthDate;
  private Address address;
}
