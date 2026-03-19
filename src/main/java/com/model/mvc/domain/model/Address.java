package com.model.mvc.domain.model;

import java.util.HashSet;
import java.util.Set;

public class Address {

  private Integer id;
  private String zipCode;
  private Integer countryCode;
  private String streetName;
  private String neighbourhood;
  private String city;
  private String state;
  private Set<Customer> customers = new HashSet<>();

  public Address() {}

  public Address(Integer id, Set<Customer> customers, String state, String city, String neighbourhood, String streetName, Integer countryCode, String zipCode) {
    this.id = id;
    this.customers = customers;
    this.state = state;
    this.city = city;
    this.neighbourhood = neighbourhood;
    this.streetName = streetName;
    this.countryCode = countryCode;
    this.zipCode = zipCode;
  }

  public String getZipCode() {
    return zipCode;
  }

  public void setZipCode(String zipCode) {
    this.zipCode = zipCode;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getStreetName() {
    return streetName;
  }

  public void setStreetName(String streetName) {
    this.streetName = streetName;
  }

  public Integer getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(Integer countryCode) {
    this.countryCode = countryCode;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getNeighbourhood() {
    return neighbourhood;
  }

  public void setNeighbourhood(String neighbourhood) {
    this.neighbourhood = neighbourhood;
  }

  public Set<Customer> getCustomers() {
    return customers;
  }

  public void setCustomers(Set<Customer> customers) {
    this.customers = customers;
  }
}