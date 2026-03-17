package com.model.mvc.domain.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Customer {

  private Integer id;
  private String name;
  private String email;
  private LocalDate birthDate;
  private Set<Address> addresses = new HashSet<>();

  public Customer() {}

  public Customer(Integer id, String name, String email, LocalDate birthDate, Set<Address> addresses) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.birthDate = birthDate;
    this.addresses = addresses;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public LocalDate getBirthDate() {
    return birthDate;
  }

  public void setBirthDate(LocalDate birthDate) {
    this.birthDate = birthDate;
  }

  public Set<Address> getAddresses() {
    return addresses;
  }

  public void setAddresses(Set<Address> addresses) {
    this.addresses = addresses;
  }
}