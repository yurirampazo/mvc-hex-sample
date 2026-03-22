package com.model.mvc.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

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
  @Column(name = "address_number")
  private String number;
  private String streetName;
  private String neighbourhood;
  @NotNull
  private String city;
  @NotNull
  private String state;

  @ManyToMany(mappedBy = "addresses", cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH} )
  private Set<Customer> customers = new HashSet<>();
}
