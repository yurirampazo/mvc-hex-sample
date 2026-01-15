package com.model.mvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Builder
@Getter
@Setter
@Entity
@Table(name = "address")
@NoArgsConstructor
@AllArgsConstructor
public class Address {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
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

  @ManyToMany(mappedBy = "addresses")
  private Set<Customer> customers;
}
